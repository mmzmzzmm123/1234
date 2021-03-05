package com.stdiet.common.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class AliyunOSSConfig {

    public static String AccessKeyID;

    public static String AccessKeySecret;

    public static String Buckets;

    public static String EndPoint;

    public static String casePrefix;

    @Value("${aliyun.oss.AccessKeyID}")
    public void setAccessKeyID(String AccessKeyID){
        AliyunOSSConfig.AccessKeyID = AccessKeyID;
    }

    @Value("${aliyun.oss.AccessKeySecret}")
    public void setAccessKeySecret(String AccessKeySecret){
        AliyunOSSConfig.AccessKeySecret = AccessKeySecret;
    }

    @Value("${aliyun.oss.Buckets}")
    public void setBuckets(String Buckets){
        AliyunOSSConfig.Buckets = Buckets;
    }

    @Value("${aliyun.oss.EndPoint}")
    public void setEndPoint(String EndPoint){
        AliyunOSSConfig.EndPoint = EndPoint;
    }

    @Value("${aliyun.oss.casePrefix}")
    public void setCasePrefix(String casePrefix){
        AliyunOSSConfig.casePrefix = casePrefix;
    }
}
