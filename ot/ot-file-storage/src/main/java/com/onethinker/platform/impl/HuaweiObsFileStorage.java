package com.onethinker.platform.impl;

import cn.hutool.core.lang.Assert;
import com.onethinker.event.FormFileUploadSuccessEvent;
import com.onethinker.platform.impl.clientwrapper.HuaweiObsClientWrapper;
import com.onethinker.bean.FileInfo;
import com.onethinker.config.FileStorageProperties;
import com.onethinker.config.FileStorageProperties.*;
import com.onethinker.platform.FileStorage;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.tika.utils.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.nio.file.Path;
import java.util.List;
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
    private List<String> allowMime;
    private WaterMark waterMark;
    private Thumbnail thumbnail;
    private String fileName;
    private MultipartFile source;
    private List<String> allowExtension;

    public HuaweiObsFileStorage(FileStorageProperties config) {
        this.platform = config.getHuaweiObs().getPlatform();
        this.accessKey = config.getHuaweiObs().getAccessKey();
        this.secretKey = config.getHuaweiObs().getSecretKey();
        this.endPoint = config.getHuaweiObs().getEndPoint();
        this.bucketName = config.getHuaweiObs().getBucketName();
        this.basePath = config.getHuaweiObs().getBasePath();
        this.allowMime = config.getAllowMime();
        this.waterMark = config.getWaterMark();
        this.thumbnail = config.getThumbnail();
        this.allowExtension = config.getAllowExtension();
    }


    @Override
    public FileStorage setName(String name) {
        this.fileName = name;
        return this;
    }

    @Override
    public FileInfo upload(MultipartFile file) {
        this.setSource(file);

        String name = StringUtils.isBlank(fileName) ? file.getName() : this.fileName;
        String extension = getExtension(name, allowExtension);

        try (HuaweiObsClientWrapper obsClient = new HuaweiObsClientWrapper(accessKey, secretKey, endPoint)) {
            FileInfo fileInfo = new FileInfo();
            // 指定要上传到 obs 上对象键 (也是最终访问地址)
            String newKey = getFileKey(basePath, fileInfo);
            obsClient.putObject(bucketName, newKey, file);
            // fileInfo id 保存在 getFileKey方法中
            fileInfo.setExtension(extension);
            fileInfo.setPath(newKey.replace("/" + DATA_FILE, ""));
            fileInfo.setDiskPath(newKey);
            fileInfo.setMimeType(queryDetectMime(file, allowMime));
            try (InputStream is = file.getInputStream()) {
                String md5 = DigestUtils.md5Hex(is);
                // 设置文件指纹
                fileInfo.setFingerprint(md5);
            } catch (Exception e) {
                log.error("", e);
            }
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
    public FileStorage serFile(MultipartFile source, String fileName) {
        this.source = source;
        this.fileName = fileName;
        return this;
    }

    @Override
    public String detectMime(FileInfo fileInfo) {
        // 在upload那边已经获取到了方法了
        return fileInfo.getMimeType();
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
