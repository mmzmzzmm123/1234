package com.ruoyi.framework.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FileConfig {
    @Value("${file.access.key}")
    private String accessKey;

    @Value("${file.secret.key}")
    private String secretKey;

    @Value("${file.bucket}")
    private String bucket;

    @Value("${file.domain}")
    private String domain;

    public String getAccessKey() {
        return accessKey;
    }

    public String getSecretKey() {
        return secretKey;
    }

    public String getBucket() {
        return bucket;
    }

    public String getDomain() {
        return domain;
    }
}
