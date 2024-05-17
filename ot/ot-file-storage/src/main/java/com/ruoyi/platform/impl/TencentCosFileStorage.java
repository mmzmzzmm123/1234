package com.ruoyi.platform.impl;

import com.qcloud.cos.COSClient;
import com.qcloud.cos.ClientConfig;
import com.qcloud.cos.auth.BasicCOSCredentials;
import com.qcloud.cos.auth.COSCredentials;
import com.qcloud.cos.http.HttpProtocol;
import com.qcloud.cos.region.Region;
import com.ruoyi.bean.FileInfo;
import com.ruoyi.config.FileStorageProperties;
import com.ruoyi.config.FileStorageProperties.Thumbnail;
import com.ruoyi.config.FileStorageProperties.WaterMark;
import com.ruoyi.event.FormFileUploadSuccessEvent;
import com.ruoyi.platform.FileStorage;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.tika.utils.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.nio.file.Path;
import java.util.List;

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
    private List<String> allowMime;
    private WaterMark waterMark;
    private Thumbnail thumbnail;
    private String fileName;
    private MultipartFile source;
    private List<String> allowExtension;

    public TencentCosFileStorage(FileStorageProperties config) {
        this.platform = config.getTencentCos().getPlatform();
        this.secretId = config.getTencentCos().getSecretId();
        this.secretKey = config.getTencentCos().getSecretKey();
        this.bucketName = config.getTencentCos().getBucketName();
        this.region = config.getTencentCos().getRegion();
        this.basePath = config.getTencentCos().getBasePath();
        this.waterMark = config.getWaterMark();
        this.thumbnail = config.getThumbnail();
        this.allowMime = config.getAllowMime();
        this.allowExtension = config.getAllowExtension();
    }

    @Override
    public FileStorage setName(String name) {
        fileName = name;
        return this;
    }

    @Override
    public FileInfo upload(MultipartFile file) {
        this.setSource(file);
        String name = StringUtils.isBlank(fileName) ? file.getName() : this.fileName;
        String extension = getExtension(name, allowExtension);
        try (COSClientWrapper cosClient = new COSClientWrapper(secretId, secretKey, region)) {
            FileInfo fileInfo = new FileInfo();
            // 指定要上传到 COS 上对象键 (也是最终访问地址)
            String newKey = getFileKey(basePath, fileInfo);
            cosClient.putObject(bucketName, newKey, file);
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
            log.error("腾讯云文件上传异常：", e);
            throw new RuntimeException("文件上传异常：" + e.getMessage());
        }
    }

    @Override
    public FileInfo upload() {
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
            try (COSClientWrapper cosClientWrapper = new COSClientWrapper(secretId, secretKey, region)) {
                processWatermark(fileInfo, cosClientWrapper);
                processThumbnail(fileInfo, cosClientWrapper);
                processWebp(fileInfo, cosClientWrapper);
                processWebpOriginal(fileInfo, cosClientWrapper);
            } catch (Exception e) {
                log.error("腾讯云客户端连接有误", e);
            }
            log.debug("Finish Process Image Ext:{}", formFileUploadSuccessEvent.getId());
        }
    }

    private void processWebpOriginal(FileInfo fileInfo, COSClientWrapper cosClient) {
        try {
            Path path = processWebpOriginal(source, fileInfo);
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
            Path webpFile = processWebp(source, fileInfo);
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

    /**
     * 初始化并生成cos客户端
     */
    private COSClient initTencentCloudCos() {
        // 1 初始化用户身份信息（secretId, secretKey）。
        COSCredentials cred = new BasicCOSCredentials(secretId, secretKey);
        // 2 设置 bucket 的区域, COS 地域的简称请参照 https://cloud.tencent.com/document/product/436/6224
        // clientConfig 中包含了设置 region, https(默认 http), 超时, 代理等 set 方法, 使用可参见源码或者常见问题 Java SDK 部分。
        ClientConfig clientConfig = new ClientConfig(new Region(region));
        // 从 5.6.54 版本开始，默认使用了 https
        clientConfig.setHttpProtocol(HttpProtocol.https);
        // 3 生成 cos 客户端。
        return new COSClient(cred, clientConfig);
    }
}
