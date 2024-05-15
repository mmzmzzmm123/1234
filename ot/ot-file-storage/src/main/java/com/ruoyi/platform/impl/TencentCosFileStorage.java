package com.ruoyi.platform.impl;

import cn.hutool.core.collection.CollUtil;
import com.qcloud.cos.COSClient;
import com.qcloud.cos.ClientConfig;
import com.qcloud.cos.auth.BasicCOSCredentials;
import com.qcloud.cos.auth.COSCredentials;
import com.qcloud.cos.exception.CosClientException;
import com.qcloud.cos.http.HttpProtocol;
import com.qcloud.cos.model.ObjectMetadata;
import com.qcloud.cos.model.PutObjectRequest;
import com.qcloud.cos.model.PutObjectResult;
import com.qcloud.cos.region.Region;
import com.ruoyi.FileStorageProperties.TencentCosConfig;
import com.ruoyi.bean.FileInfo;
import com.ruoyi.platform.FileStorage;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
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

    public TencentCosFileStorage(TencentCosConfig config) {
        this.platform = config.getPlatform();
        this.secretId = config.getSecretId();
        this.secretKey = config.getSecretKey();
        this.bucketName = config.getBucketName();
        this.region = config.getRegion();
        this.basePath = config.getBasePath();
        this.cosClient = initTencentCloudCos();
    }

    @Override
    public void close() {
        System.out.println("关闭系统");
        //关闭客户端(关闭后台线程)
        cosClient.shutdown();
        cosClient = null;
    }

    @Override
    public String upload(MultipartFile multipartFile, FileInfo fileInfo) {
        try {
            // 指定要上传到 COS 上对象键 (也是最终访问地址)
            String newKey = getFileKey(basePath,multipartFile.getName());
            // 获取临时File文件并进行上传
            ObjectMetadata metadata = getObjectMetadata(multipartFile);
            // 上传到腾讯云存储对象中
            PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, newKey,multipartFile.getInputStream(), metadata);
            PutObjectResult putObjectResult = cosClient.putObject(putObjectRequest);
            System.out.println(putObjectResult);
            // 返回内容
            return newKey;
        } catch (CosClientException | IOException e) {
            log.error("腾讯云文件上传异常：", e);
            throw new RuntimeException("文件上传异常：" + e.getMessage());
        } finally {
            //关闭客户端(关闭后台线程)
            cosClient.shutdown();
        }
    }

    /**
     * 初始化并生成cos客户端
     *
     * @return
     */
    private COSClient initTencentCloudCos() {
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
        return cosClient;
    }


    /**
     * 获取对象的元数据
     */
    public ObjectMetadata getObjectMetadata(FileInfo fileInfo) {
        ObjectMetadata metadata = new ObjectMetadata();
        if (fileInfo.getSize() != null) metadata.setContentLength(fileInfo.getSize());
        if (fileInfo.getContentType() != null) metadata.setContentType(fileInfo.getContentType());
        metadata.setUserMetadata(fileInfo.getUserMetadata());
        if (CollUtil.isNotEmpty(fileInfo.getMetadata())) {
            fileInfo.getMetadata().forEach(metadata::setHeader);
        }
        return metadata;
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
