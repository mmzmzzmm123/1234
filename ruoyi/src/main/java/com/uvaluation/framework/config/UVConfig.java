package com.uvaluation.framework.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 读取项目相关配置
 *
 * @author ruoyi
 */
@Component
@ConfigurationProperties(prefix = "uv")
public class UVConfig {
    /**
     * 项目名称
     */
    private String dataUrl;

    private String aitificialOfficeBasePriceUrl;

    public String getDataUrl() {
        return dataUrl;
    }

    public void setDataUrl(String dataUrl) {
        this.dataUrl = dataUrl;
    }

    public String getAitificialOfficeBasePriceUrl() {
        return getDataUrl() + aitificialOfficeBasePriceUrl;
    }

    public void setAitificialOfficeBasePriceUrl(String aitificialOfficeBasePriceUrl) {
        this.aitificialOfficeBasePriceUrl = aitificialOfficeBasePriceUrl;
    }
}