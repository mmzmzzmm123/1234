package com.xinyu.idol.config;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration

public class AliOssInit {

    @Value("${aliyun.oss.endpoint}")
    String endpoint = "https://oss-cn-beijing.aliyuncs.com";
    // EnvironmentVariableCredentialsProvider credentialsProvider = CredentialsProviderFactory.newEnvironmentVariableCredentialsProvider();
    // 填写Bucket名称，例如examplebucket。
    @Value("${aliyun.oss.bucket}")
    String bucketName = "content-resource-dev";
    // 填写Object完整路径，完整路径中不能包含Bucket名称，例如exampledir/exampleobject.txt。
    String objectName = "exampledir/exampleobject";
    // 填写本地文件的完整路径，例如D:\\localpath\\examplefile.txt。

    String ak="LTAI5tSPwRCWRrUkgWArSkhe";
    String sk="ok0oJsjPjazsHOQZAgtu4JgSbO0HJE";

    @Bean
    OSS initAliOssBean(){
        OSS ossClient = new OSSClientBuilder().build(endpoint,ak,sk);
        return ossClient;
    }

}
