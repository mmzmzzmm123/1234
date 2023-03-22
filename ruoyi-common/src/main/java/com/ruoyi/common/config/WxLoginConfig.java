package com.ruoyi.common.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 微信登录相关配置
 */
@Component
@ConfigurationProperties(prefix = "wx.login")
@Data
public class WxLoginConfig
{
    private String appId;

    private String appSecret;

    private String redirectUri;
}
