package com.ruoyi.thirdparty.oss.service.impl;

import com.alibaba.fastjson.JSON;
import com.aliyun.oss.ClientException;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.OSSException;
import com.aliyun.oss.model.ObjectMetadata;
import com.aliyun.oss.model.PutObjectResult;
import com.ruoyi.common.core.property.AppProperties;
import com.ruoyi.common.enums.AppConfKey;
import com.ruoyi.thirdparty.oss.domain.FileUploadResponse;
import com.ruoyi.thirdparty.oss.enums.FileCategoryType;
import com.ruoyi.thirdparty.oss.service.AliyunOssService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.InputStream;

/**
 * @author : yelifeng
 * @date : 2020/10/16 0016 13:02 xuc5566@gmail.com
 */
@Slf4j
@Service
public class AliyunOssServiceImpl implements AliyunOssService {

    @Override
    public FileUploadResponse uploadFile(InputStream inputStream, String path, FileCategoryType type) {
        String errorMessage = "";
        String bucketName = this.getBucketName(type);

        OSS client = new OSSClientBuilder().build(
                AppProperties.getProperty(AppConfKey.ALIYUN_OSS_ENDPOINT),
                AppProperties.getProperty(AppConfKey.ALIYUN_OSS_ACCESS_KEY_ID),
                AppProperties.getProperty(AppConfKey.ALIYUN_OSS_ACCESS_KEY_SECRET));
        try {
            ObjectMetadata meta = new ObjectMetadata();
            //meta.setCacheControl("no-cache");

            PutObjectResult ret = client.putObject(bucketName, path, inputStream, meta);
            System.out.println(JSON.toJSONString(ret.getResponse()));
        } catch (OSSException oe) {
            log.error("OSS exception: [{}] [{}] [{}] [{}]", oe.getErrorMessage(), oe.getErrorCode(), oe.getRequestId(), oe.getHostId());
            errorMessage = oe.getErrorMessage();
        } catch (ClientException ce) {
            log.error("OSS client exception: [{}]", ce.getMessage());
            errorMessage = ce.getMessage();
        } finally {
            client.shutdown();
        }


        return FileUploadResponse.builder()
                .filePath(this.getUploadFileUri(path,type))
                .errorMessage(errorMessage)
                .build();
    }

    private String getUploadFileUri(String path, FileCategoryType type) {

        return new StringBuilder()
                .append(getUriByType(type))
                .append(path).toString();
    }

    private String getUriByType(FileCategoryType type) {
        switch (type) {
            case IMAGE:
                return AppProperties.getProperty(AppConfKey.ALIYUN_CDN_IMAGES);
            case AUDIO:
                return AppProperties.getProperty(AppConfKey.ALIYUN_CDN_VIDEO);
            case VIDEO:
                return AppProperties.getProperty(AppConfKey.ALIYUN_CDN_VIDEO);
            default:
                return AppProperties.getProperty(AppConfKey.ALIYUN_CDN_IMAGES);
        }
    }

    private String getBucketName(FileCategoryType type) {
        switch (type) {
            case IMAGE:
                return AppProperties.getProperty(AppConfKey.ALIYUN_OSS_BUCKET_IMG_NAME);
            case AUDIO:
                return AppProperties.getProperty(AppConfKey.ALIYUN_OSS_BUCKET_AUDIO_NAME);
            case VIDEO:
                return AppProperties.getProperty(AppConfKey.ALIYUN_OSS_BUCKET_VIDEO_NAME);
            default:
                return AppProperties.getProperty(AppConfKey.ALIYUN_OSS_BUCKET_IMG_NAME);
        }
    }
}
