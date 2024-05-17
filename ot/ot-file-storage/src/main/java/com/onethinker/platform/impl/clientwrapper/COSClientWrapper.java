package com.onethinker.platform.impl.clientwrapper;

import com.qcloud.cos.COSClient;
import com.qcloud.cos.ClientConfig;
import com.qcloud.cos.auth.BasicCOSCredentials;
import com.qcloud.cos.auth.COSCredentials;
import com.qcloud.cos.http.HttpProtocol;
import com.qcloud.cos.model.ObjectMetadata;
import com.qcloud.cos.model.PutObjectRequest;
import com.qcloud.cos.model.PutObjectResult;
import com.qcloud.cos.region.Region;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author yangyouqi
 * @date 2024/5/17
 */
public class COSClientWrapper implements AutoCloseable {

    private static final Logger log = LoggerFactory.getLogger(COSClientWrapper.class);


    private final COSClient cosClient;

    public COSClientWrapper(String secretId, String secretKey,String region) {
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

    @Override
    public void close() {
        if (cosClient != null) {
            cosClient.shutdown();
        }
    }

    public PutObjectResult putObject(String bucketName, String newKey, MultipartFile file) throws IOException {
        // 获取对象的元数据
        ObjectMetadata metadata = getObjectMetadata(file);
        try (final InputStream inputStream = file.getInputStream()){
            // 上传到腾讯云存储对象中
            PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, newKey, inputStream, metadata);
            return cosClient.putObject(putObjectRequest);
        }
    }

    public PutObjectResult putObject(String bucketName, String newKey, InputStream is) {
        // 上传到腾讯云存储对象中
        ObjectMetadata objectMetadata = getObjectMetadata(is);
        PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, newKey, is,objectMetadata);
        return cosClient.putObject(putObjectRequest);
    }

    public PutObjectResult putObject(String bucketName, String newKey, File file) {
        // 上传到腾讯云存储对象中
        PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, newKey, file);
        return cosClient.putObject(putObjectRequest);
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

    /**
     * 获取对象的元数据
     */
    public ObjectMetadata getObjectMetadata(InputStream is) {
        return new ObjectMetadata();
    }
}
