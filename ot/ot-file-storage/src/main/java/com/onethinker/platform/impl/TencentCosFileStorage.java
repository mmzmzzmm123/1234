package com.onethinker.platform.impl;

import cn.hutool.core.lang.Assert;
import com.onethinker.domain.FileInfo;
import com.onethinker.config.FileStorageProperties;
import com.onethinker.config.FileStorageProperties.Thumbnail;
import com.onethinker.config.FileStorageProperties.WaterMark;
import com.onethinker.event.FormFileUploadSuccessEvent;
import com.onethinker.platform.FileStorage;
import com.onethinker.platform.impl.clientwrapper.COSClientWrapper;
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
 * 腾讯云 COS 存储
 *
 * @author yangyouqi
 * @date 2024/5/14
 */
@Data
@NoArgsConstructor
@Log4j2
public class TencentCosFileStorage implements FileStorage {
    private String platform;
    private String secretId;
    private String secretKey;
    private String region;
    private String bucketName;
    private String basePath;
    private String domain;
    private Boolean enableStorage;
    private WaterMark waterMark;
    private Thumbnail thumbnail;
    private MultipartFile source;
    public TencentCosFileStorage(FileStorageProperties config) {
        this.platform = config.getTencentCos().getPlatform();
        this.secretId = config.getTencentCos().getSecretId();
        this.secretKey = config.getTencentCos().getSecretKey();
        this.bucketName = config.getTencentCos().getBucketName();
        this.region = config.getTencentCos().getRegion();
        this.basePath = config.getTencentCos().getBasePath();
        this.domain = config.getTencentCos().getDomain();
        this.enableStorage = config.getTencentCos().getEnableStorage();
        this.waterMark = config.getWaterMark();
        this.thumbnail = config.getThumbnail();
    }

    @Override
    public FileInfo upload(MultipartFile file) {
        Assert.isTrue(enableStorage,"enableStorage is False");
        this.setSource(file);
        try (COSClientWrapper cosClient = new COSClientWrapper(secretId, secretKey, region)) {
            FileInfo fileInfo = new FileInfo();
            // 指定要上传到 COS 上对象键 (也是最终访问地址)
            String newKey = getFileKey(basePath, fileInfo);
            cosClient.putObject(bucketName, newKey, file);
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
            try (COSClientWrapper cosClientWrapper = new COSClientWrapper(secretId, secretKey, region)) {
                processWatermark(fileInfo, cosClientWrapper);
                processThumbnail(fileInfo, cosClientWrapper);
                processWebp(fileInfo, cosClientWrapper);
                processWebpOriginal(fileInfo, cosClientWrapper);
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

    private void processWebpOriginal(FileInfo fileInfo, COSClientWrapper cosClient) {
        try {
            Path path = processWebpOriginal(source, fileInfo,basePath + "/" + fileInfo.getId());
            String newKey = getTransFile(fileInfo.getPath(), WEBP_ORIGINAL_FILE);
            cosClient.putObject(bucketName, newKey, path.toFile());
        } catch (Exception e) {
            log.error("Process WebP Original Error:{}", fileInfo.getId(), e);
        }
        log.debug("Finish Process Webp Original:{}", fileInfo.getId());
    }

    private void processWebp(FileInfo fileInfo, COSClientWrapper cosClient) {
        log.debug("Start Process Webp:{}", fileInfo.getId());
        try {
            Path webpFile = processWebp(source, fileInfo,basePath + "/" + fileInfo.getId());
            String newKey = getTransFile(fileInfo.getPath(), WEBP_FILE);
            cosClient.putObject(bucketName, newKey, webpFile.toFile());
        } catch (Exception e) {
            log.error("Process WebP Error:{}", fileInfo.getId(), e);
        }
        log.debug("Finish Process Webp:{}", fileInfo.getId());
    }

    private void processThumbnail(FileInfo fileInfo, COSClientWrapper cosClient) {
        try (InputStream is = processThumbnail(source, fileInfo, thumbnail)) {
            String newKey = getTransFile(fileInfo.getPath(), THUMBNAIL_FILE);
            // 上传到腾讯云存储对象中
            cosClient.putObject(bucketName, newKey, is);
        } catch (Exception e) {
            log.error("Process Thumbnail Error:{}", fileInfo.getId(), e);
        }
        log.debug("Finish Process Thumbnail:{}", fileInfo.getId());
    }

    private void processWatermark(FileInfo fileInfo, COSClientWrapper cosClientWrapper) {
        try (InputStream is = processWatermark(source, fileInfo, waterMark)) {
            String newKey = getTransFile(fileInfo.getPath(), WATERMARK_FILE);
            cosClientWrapper.putObject(bucketName, newKey, is);
        } catch (Exception e) {
            log.error("Process Thumbnail Error:{}", fileInfo.getId(), e);
        }
    }
}
