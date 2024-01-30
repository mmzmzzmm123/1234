package com.ruoyi.common.config.dymic;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 读取项目相关配置
 * 
 * @author ruoyi
 */
@Data
@Component
@ConfigurationProperties(prefix = "search")
public class TelegramSearchConfigure
{

    private String requestPath;
    private String username;
    private String password;
    private Long callTimeoutSecond;
    private Long connectTimeoutSecond;
    private String strKey;
    private String callBackUrl;
}
