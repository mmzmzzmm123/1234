package com.ruoyi.common.openapi;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;


@Data
@Configuration
@ConfigurationProperties(prefix = "openapi")
public class OpenApiProperties {

    private String openApiKey = "yriWvNH7Bt83mCvTQYHMLkMbuh7Ll32z";

    private String openApiSecret = "TNRlS8MebxpBYvpbpWEwByzBAhw5TAfg";

    private String openApiHost = "https://gateway.test.csbilin.com";

    private String openApiClientLogin = "/thirdparty/user/login/client";

}
