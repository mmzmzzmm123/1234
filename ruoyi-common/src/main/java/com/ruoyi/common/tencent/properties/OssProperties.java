package com.ruoyi.common.tencent.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author LAM
 * @date 2023/9/12 18:17
 */
@Data
@Component
@ConfigurationProperties(prefix = "tencent-info.oss")
public class OssProperties {

    private int threadPool;

    private String region;

    private String bucketName;

    private String baseUrl;
}
