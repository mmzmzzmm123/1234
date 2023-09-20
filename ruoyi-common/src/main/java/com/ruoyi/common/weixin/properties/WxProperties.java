package com.ruoyi.common.weixin.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author LAM
 * @date 2023/9/19 17:20
 */
@Data
@Component
@ConfigurationProperties(prefix = "weixin.application-info")
public class WxProperties {

    private MiNiApplet miNiApplet;

    @Data
    public static class MiNiApplet{
        private String appId;
        private String appSecret;
    }
}
