package com.ruoyi.system.openapi;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;


@Data
@Configuration
@ConfigurationProperties(prefix = "openapi")
public class OpenApiProperties {

    private String openApiKey = "U89dduMpQ2bdUDwQe6Q7uYD1Qmar7t1k";

    private String openApiSecret = "ZVULlqavc8uVYeDEOwvPMXlT2QdrxfY7";

    private String openApiHost = "https://gateway.bilinl.com";

}
