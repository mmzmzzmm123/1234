package com.ruoyi.system.extend;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author Jing.Zhang
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "ut-touch")
public class UtTouchProperties {

    /**
     * 调用地址
     */
    private String apiUrl;

    /**
     * 调用方账号
     */
    private String merchantAccount;

    /**
     * 调用方密码
     */
    private String merchantPassword;

    /**
     * 大商家ID
     */
    private String touchMerchantId = "1735130303135006720";

}
