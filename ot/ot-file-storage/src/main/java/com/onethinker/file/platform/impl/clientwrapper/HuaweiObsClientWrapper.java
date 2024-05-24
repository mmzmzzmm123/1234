package com.onethinker.file.platform.impl.clientwrapper;

import cn.hutool.core.io.IoUtil;
import com.obs.services.ObsClient;
import com.obs.services.model.ObsObject;
import com.obs.services.model.PutObjectRequest;
import com.obs.services.model.PutObjectResult;
import com.qcloud.cos.model.COSObject;
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
public class HuaweiObsClientWrapper implements AutoCloseable {

    private static final Logger log = LoggerFactory.getLogger(HuaweiObsClientWrapper.class);


    private volatile ObsClient obsClient;

    public HuaweiObsClientWrapper(String accessKey, String secretKey, String endPoint) {
        // 1 生成 cos 客户端。
        this.obsClient = new ObsClient(accessKey, secretKey,endPoint);
    }

    @Override
    public void close() {
        IoUtil.close(obsClient);
        obsClient = null;
    }

    public PutObjectResult putObject(String bucketName, String newKey, MultipartFile file){
        try (InputStream is = file.getInputStream()){
            return this.putObject(bucketName,newKey,is);
        }catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public PutObjectResult putObject(String bucketName, String newKey, InputStream is) {
        PutObjectRequest request = new PutObjectRequest();
        request.setBucketName(bucketName);
        request.setObjectKey(newKey);
        request.setInput(is);
        return obsClient.putObject(request);
    }

    public PutObjectResult putObject(String bucketName, String newKey, File file) {
        PutObjectRequest request = new PutObjectRequest();
        request.setBucketName(bucketName);
        request.setObjectKey(newKey);
        request.setFile(file);
        return obsClient.putObject(request);
    }

    public ObsObject getObject(String bucketName, String filePath) {
        return obsClient.getObject(bucketName, filePath);
    }
}
