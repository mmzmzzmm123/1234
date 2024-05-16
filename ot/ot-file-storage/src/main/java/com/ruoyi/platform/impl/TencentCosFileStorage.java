package com.ruoyi.platform.impl;

import com.qcloud.cos.COSClient;
import com.qcloud.cos.ClientConfig;
import com.qcloud.cos.auth.BasicCOSCredentials;
import com.qcloud.cos.auth.COSCredentials;
import com.qcloud.cos.exception.CosClientException;
import com.qcloud.cos.http.HttpProtocol;
import com.qcloud.cos.model.GetObjectRequest;
import com.qcloud.cos.model.ObjectMetadata;
import com.qcloud.cos.model.PutObjectRequest;
import com.qcloud.cos.model.PutObjectResult;
import com.qcloud.cos.region.Region;
import com.ruoyi.common.constant.ServicePathConstant;
import com.ruoyi.config.FileStorageProperties;
import com.ruoyi.bean.FileInfo;
import com.ruoyi.event.FormFileUploadSuccessEvent;
import com.ruoyi.platform.FileStorage;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;
import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.geometry.Positions;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.tika.utils.StringUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.util.Base64;
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
    private COSClient cosClient;
    private List<String> allowMime;
    private FileStorageProperties.WaterMark waterMark;
    private FileStorageProperties.Thumbnail thumbnail;
    private String fileName;
    private MultipartFile source;
    private List<String> allowExtension;

    private static final int DEFAULT_THUMBNAIL_SIZE = 150;
    private static final String IMAGE = "image";
    private static final String WEBP_FILE = "webp";
    private static final String WEBP_ORIGINAL_FILE = "original.webp";
    private static final String THUMBNAIL_FILE = "thumb.jpg";
    private static final String WATERMARK_FILE = "watermark.jpg";
    private static final String WEBP_MIME = "image/webp";
    private static final String WEBP_EXTENSION = "." + WEBP_FILE;
    private static final String WEBP_ORIGIN_EXTENSION = "." + WEBP_ORIGINAL_FILE;
    private static final String JPG_MIME = "image/jpeg";
    private static final String JPG_EXTENSION = ".jpg";
    private static final String YZY_APP_NAME = "YZY_H5";
    private static final String SERVICE_UPLOAD_CONTENT_TYPE = "application/offset+octet-stream";
    String UPLOAD_PATH = ServicePathConstant.PREFIX_PUBLIC_PATH + "/tus/upload";

    public TencentCosFileStorage(FileStorageProperties config, ApplicationContext applicationContext) {
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
        try {
            String name = StringUtils.isBlank(fileName) ? file.getName() : this.fileName;
            String extension = getExtension(name, allowExtension);

            initTencentCloudCos();
            FileInfo fileInfo = new FileInfo();
            // 指定要上传到 COS 上对象键 (也是最终访问地址)
            String newKey = getFileKey(basePath,fileInfo);
            // 获取对象的元数据
            ObjectMetadata metadata = getObjectMetadata(file);
            // 上传到腾讯云存储对象中
            PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, newKey,file.getInputStream(), metadata);
            cosClient.putObject(putObjectRequest);

            // fileInfo id 保存在 getFileKey方法中
            fileInfo.setExtension(extension);
            fileInfo.setPath(newKey.replace("/" + DATA_FILE, ""));
            fileInfo.setDiskPath(newKey);
            fileInfo.setMimeType(queryDetectMime(file,allowMime));
            try (InputStream is = file.getInputStream()) {
                String md5 = DigestUtils.md5Hex(is);
                // 设置文件指纹
                fileInfo.setFingerprint(md5);
            } catch (Exception e) {
                log.error("", e);
            }
            // 返回内容
            return fileInfo;
        } catch (CosClientException | IOException e) {
            log.error("腾讯云文件上传异常：", e);
            throw new RuntimeException("文件上传异常：" + e.getMessage());
        } finally {
            //关闭客户端(关闭后台线程)
            cosClient.shutdown();
            cosClient = null;
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
            try {
                processWatermark(fileInfo);
            } catch (Exception e) {
                log.error("Process Thumbnail Error:{}", formFileUploadSuccessEvent.getId(), e);
            }
//            try {
//                processThumbnail(formFileUploadSuccessEvent.getId(), fileInfo);
//            } catch (Exception e) {
//                log.error("Process Thumbnail Error:{}", formFileUploadSuccessEvent.getId(), e);
//            }
//            try {
//                processWebp(formFileUploadSuccessEvent.getId(), fileInfo);
//            } catch (Exception e) {
//                log.error("Process WebP Error:{}", formFileUploadSuccessEvent.getId(), e);
//            }
//            try {
//                processWebpOriginal(formFileUploadSuccessEvent.getId(), fileInfo);
//            } catch (Exception e) {
//                log.error("Process WebP Original Error:{}", formFileUploadSuccessEvent.getId(), e);
//            }
            log.debug("Finish Process Image Ext:{}", formFileUploadSuccessEvent.getId());
        }
    }

    private void processWatermark(FileInfo fileInfo) throws IOException {
        log.debug("Start Process Watermark:{},{},{}", platform,fileInfo.getId(), fileInfo.getCreateUserId());
        if (waterMark == null || !waterMark.isEnabled()) {
            return;
        }
        int minWidth = 300;
        int minHeight = 100;
        if (waterMark.getMinWidth() > 0) {
            minWidth = waterMark.getMinWidth();
        }
        if (waterMark.getMinHeight() > 0) {
            minHeight = waterMark.getMinHeight();
        }
        int[] imgSize = getImgSize(source);
        if (imgSize[0] > minWidth && imgSize[1] > minHeight) {

            log.debug("Start Process Watermark:{}", fileInfo.getId());
            Thumbnails.Builder<BufferedImage> builder = Thumbnails.of(ImageIO.read(source.getInputStream()));
            BufferedImage bufferedImage = getWatermark(fileInfo.getCreateUserId());
            builder.watermark(Positions.CENTER, bufferedImage, 0.5f);
            if (imgSize[0] > (minWidth + minWidth)) {
                builder.watermark(Positions.BOTTOM_LEFT, bufferedImage, 0.5f)
                        .watermark(Positions.BOTTOM_RIGHT, bufferedImage, 0.5f)
                        .watermark(Positions.TOP_LEFT, bufferedImage, 0.5f)
                        .watermark(Positions.TOP_RIGHT, bufferedImage, 0.5f);
            } else {
                builder.watermark(Positions.BOTTOM_CENTER, bufferedImage, 0.5f)
                        .watermark(Positions.TOP_CENTER, bufferedImage, 0.5f);
            }
//            builder.scale(1).toFile(targetFile.toFile());
            log.debug("Finish Process Watermark:{}", fileInfo.getId());
            try {
                initTencentCloudCos();
                String newKey = getTransFile(fileInfo.getPath(),WATERMARK_FILE);
                ObjectMetadata metadata = getObjectMetadata(source);
                PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, newKey,source.getInputStream(),metadata);
                // 上传到腾讯云存储对象中
                PutObjectResult putObjectResult = cosClient.putObject(putObjectRequest);
            }finally {
                // 关闭COS客户端
                cosClient.shutdown();
                cosClient = null;
            }


        }
    }

    /**
     * 初始化并生成cos客户端
     */
    private void initTencentCloudCos() {
        if (Objects.isNull(cosClient)) {
            synchronized (this) {
                if (Objects.isNull(cosClient)) {
                    // 1 初始化用户身份信息（secretId, secretKey）。
                    COSCredentials cred = new BasicCOSCredentials(secretId, secretKey);
                    // 2 设置 bucket 的区域, COS 地域的简称请参照 https://cloud.tencent.com/document/product/436/6224
                    // clientConfig 中包含了设置 region, https(默认 http), 超时, 代理等 set 方法, 使用可参见源码或者常见问题 Java SDK 部分。
                    ClientConfig clientConfig = new ClientConfig(new Region(region));
                    // 从 5.6.54 版本开始，默认使用了 https
                    clientConfig.setHttpProtocol(HttpProtocol.https);
                    // 3 生成 cos 客户端。
                    this.cosClient = new COSClient(cred, clientConfig);
                }
            }
        }
    }

    /**
     * 获取对象的元数据
     */
    public ObjectMetadata getObjectMetadata(MultipartFile multipartFile) {
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentLength(multipartFile.getSize());
        metadata.setContentType(multipartFile.getContentType());
        return metadata;
    }
}
