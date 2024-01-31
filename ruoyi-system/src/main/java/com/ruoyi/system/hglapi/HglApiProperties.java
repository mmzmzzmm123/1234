package com.ruoyi.system.hglapi;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;


@Data
@Configuration
@ConfigurationProperties(prefix = "hgl")
public class HglApiProperties {

    private String loginName = "hgl2024";

    private String loginPassword = "Hgl20240129";

    private String apiHost = "https://gateway.qianxunko.com";

}
