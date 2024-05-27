package com.onethinker.sms.config;

import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author yangyouqi
 * @date 2024/5/27
 * 短信配置
 */
@Component
@Data
@ConfigurationProperties(prefix = "onethinker.sms-storage")
public class SmsStroageProperties {
    /**
     * 默认下发平台
     */
    private String defaultPlatform;

    /**
     * 各平台相关配置
     **/
    private TencentSmsConfig tencentCos;

    @Data
    @Accessors(chain = true)
    public class TencentSmsConfig {
        String appId;
        String appKey;
    }
}
