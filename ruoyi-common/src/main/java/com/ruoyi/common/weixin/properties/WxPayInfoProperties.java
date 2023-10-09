package com.ruoyi.common.weixin.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author gideon
 * @date 2022/10/30
 */
@Data
@Component
@ConfigurationProperties(prefix = "weixin.pay-info")
public class WxPayInfoProperties {

    /**
     * 商户号
     */
    private String mchId;

    /**
     * api密钥
     */
    private String mchKey;

    /**
     * apiv3密钥
     */
    private String mchKeyV3;

    /**
     * 加密方式
     */
    private String signType;

    /**
     * 商户证书文件路径(apiclient_cert.p12)
     */
    private String keyPath;

    /**
     * 商户key证书路径(apiclient_key.pem)
     */
    private String appKeyPath;

    /**
     * 商户key证书路径(apiclient_cert.pem)
     */
    private String appCertPath;

    /**
     * 支付通知回调地址
     */
    private String notifyUrl;

    /**
     * 退款通知回调地址
     */
    private String refundNotifyUrl;
}
