package com.ruoyi.common.tencent.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author LAM
 * @date 2023/9/12 18:15
 */
@Data
@Component
@ConfigurationProperties(prefix = "tencent-info")
public class TencentProperties {

    private String secretId;

    private String secretKey;
}
