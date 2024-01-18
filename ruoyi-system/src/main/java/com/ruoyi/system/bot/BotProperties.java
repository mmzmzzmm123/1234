package com.ruoyi.system.bot;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;


@Data
@Configuration
@ConfigurationProperties(prefix = "bot")
public class BotProperties {

    /**
     * 调用地址
     */
    private String apiUrl;


}
