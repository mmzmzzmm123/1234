package com.ruoyi.common.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@Data
@ConfigurationProperties(prefix = "wx.miniapp.pay")
public class WxPayConfig {
    private String appid;
    private String mchid;
    private String notify_url;
    private String postUrl;

}
