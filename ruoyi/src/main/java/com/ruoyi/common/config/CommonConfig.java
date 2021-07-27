package com.ruoyi.common.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CommonConfig {

    @Value("${sms.appkey}")
    private String smsAppkey;

    @Value("${sms.secret}")
    private String smsSecret;

    public String getSmsAppkey() {
        return smsAppkey;
    }

    public void setSmsAppkey(String smsAppkey) {
        this.smsAppkey = smsAppkey;
    }

    public String getSmsSecret() {
        return smsSecret;
    }

    public void setSmsSecret(String smsSecret) {
        this.smsSecret = smsSecret;
    }
}

