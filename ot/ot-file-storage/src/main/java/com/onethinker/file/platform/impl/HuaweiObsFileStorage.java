package com.onethinker.file.platform.impl;

import cn.hutool.core.lang.Assert;
import com.onethinker.file.event.FormFileUploadSuccessEvent;
import com.onethinker.file.platform.impl.clientwrapper.HuaweiObsClientWrapper;
import com.onethinker.file.domain.FileInfo;
import com.onethinker.file.config.FileStorageProperties;
import com.onethinker.file.config.FileStorageProperties.*;
import com.onethinker.file.platform.FileStorage;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.nio.file.Path;
import java.util.Objects;

/**
 * @author yangyouqi
 * @date 2024/5/17
 */
@Data
@NoArgsConstructor
@Log4j2
public class HuaweiObsFileStorage implements FileStorage {
    private String platform;
    private String accessKey;
    private String secretKey;
    private String endPoint;
    private String bucketName;
    private String basePath;
    private String domain;
    private Boolean enableStorage;
    private WaterMark waterMark;
    private Thumbnail thumbnail;
    private MultipartFile source;

    public HuaweiObsFileStorage(FileStorageProperties config) {
        this.platform = config.getHuaweiObs().getPlatform();
        this.accessKey = config.getHuaweiObs().getAccessKey();
        this.secretKey = config.getHuaweiObs().getSecretKey();
        this.endPoint = config.getHuaweiObs().getEndPoint();
        this.bucketName = config.getHuaweiObs().getBucketName();
        this.basePath = config.getHuaweiObs().getBasePath();
        this.domain = config.getHuaweiObs().getDomain();
        this.enableStorage = config.getHuaweiObs().getEnableStorage();

        this.waterMark = config.getWaterMark();
        this.thumbnail = config.getThumbnail();
    }

    @Override
    public FileInfo upload(MultipartFile file) {
        Assert.isTrue(enableStorage,"enableStorage is False");
        this.setSource(file);
        try (HuaweiObsClientWrapper obsClient = new HuaweiObsClientWrapper(accessKey, secretKey, endPoint)) {
            FileInfo fileInfo = new FileInfo();
            // 指定要上传到 obs 上对象键 (也是最终访问地址)
            String newKey = getFileKey(basePath, fileInfo);
            obsClient.putObject(bucketName, newKey, file);
            // fileInfo id 保存在 getFileKey方法中
            fileInfo.setPath(newKey.replace("/" + DATA_FILE, ""));
            fileInfo.setDiskPath(newKey);
            // 返回内容
            return fileInfo;
        } catch (Exception e) {
            log.error("{}文件上传异常：",platform, e);
            throw new RuntimeException("文件上传异常：" + e.getMessage());
        }
    }

    @Override
    public FileInfo upload() {
        Assert.isTrue(Objects.nonNull(source),"source is null");
        return this.upload(source);
    }

    @Override
    public FileStorage serFile(MultipartFile source) {
        this.source = source;
        return this;
    }

    @Override
    public void handleFormFileUploadSuccessEvent(FormFileUploadSuccessEvent formFileUploadSuccessEvent) {
        log.debug("handleFormFileUploadSuccessEvent:{}", formFileUploadSuccessEvent);
        FileInfo fileInfo = (FileInfo) formFileUploadSuccessEvent.getSource();
        String fileMimeType = formFileUploadSuccessEvent.getMimeType();
        if (fileMimeType != null && fileMimeType.startsWith(IMAGE)) {
            log.debug("Start Process Image Ext:{}", formFileUploadSuccessEvent.getId());
            try (HuaweiObsClientWrapper obsClient = new HuaweiObsClientWrapper(accessKey, secretKey, endPoint)) {
                processWatermark(fileInfo, obsClient);
                processThumbnail(fileInfo, obsClient);
                processWebp(fileInfo, obsClient);
                processWebpOriginal(fileInfo, obsClient);
            } catch (Exception e) {
                log.error("{}客户端连接有误",platform, e);
            } finally {
                String path = basePath + "/" + fileInfo.getId();
                // 清理临时文件内容
                deleteDirectory(path);
            }
            log.debug("Finish Process Image Ext End :{}", formFileUploadSuccessEvent.getId());
        }
    }

    private void processWebpOriginal(FileInfo fileInfo, HuaweiObsClientWrapper obsClient) {
        try {
            Path path = processWebpOriginal(source, fileInfo,basePath + "/" + fileInfo.getId());
            String newKey = getTransFile(fileInfo.getPath(), WEBP_ORIGINAL_FILE);
            obsClient.putObject(bucketName, newKey, path.toFile());
        } catch (Exception e) {
            log.error("Process WebP Original Error:{}", fileInfo.getId(), e);
        }
        log.debug("Finish Process Webp Original:{}", fileInfo.getId());
    }

    private void processWebp(FileInfo fileInfo, HuaweiObsClientWrapper obsClient) {
        log.debug("Start Process Webp:{}", fileInfo.getId());
        try {
            Path webpFile = processWebp(source, fileInfo,basePath + "/" + fileInfo.getId());
            String newKey = getTransFile(fileInfo.getPath(), WEBP_FILE);
            obsClient.putObject(bucketName, newKey, webpFile.toFile());
        } catch (Exception e) {
            log.error("Process WebP Error:{}", fileInfo.getId(), e);
        }
        log.debug("Finish Process Webp:{}", fileInfo.getId());
    }

    private void processThumbnail(FileInfo fileInfo, HuaweiObsClientWrapper obsClient) {
        try (InputStream is = processThumbnail(source, fileInfo, thumbnail)) {
            String newKey = getTransFile(fileInfo.getPath(), THUMBNAIL_FILE);
            obsClient.putObject(bucketName, newKey, is);
        } catch (Exception e) {
            log.error("Process Thumbnail Error:{}", fileInfo.getId(), e);
        }
        log.debug("Finish Process Thumbnail:{}", fileInfo.getId());
    }

    private void processWatermark(FileInfo fileInfo, HuaweiObsClientWrapper obsClientWrapper) {
        try (InputStream is = processWatermark(source, fileInfo, waterMark)) {
            String newKey = getTransFile(fileInfo.getPath(), WATERMARK_FILE);
            obsClientWrapper.putObject(bucketName, newKey, is);
        } catch (Exception e) {
            log.error("Process Thumbnail Error:{}", fileInfo.getId(), e);
        }
    }

}
