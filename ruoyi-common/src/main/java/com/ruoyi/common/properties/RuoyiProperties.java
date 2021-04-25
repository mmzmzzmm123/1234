package com.ruoyi.common.properties;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties("properties")
public class RuoyiProperties {

    /**
     * 排除路径
     */
    private String excludedUri;


}
