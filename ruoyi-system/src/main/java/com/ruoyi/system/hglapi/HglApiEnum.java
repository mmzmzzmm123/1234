package com.ruoyi.system.hglapi;

import lombok.Getter;


@Getter
public enum HglApiEnum {

    GET_MERCHANT_TOKEN("登录获取token的接口", "/Token/GetMerchantToken"),

    ;

    /**
     * 请求地址
     */
    private final String requestUrl;

    /**
     * Api描述
     */
    private final String apiDesc;


    HglApiEnum(String apiDesc, String requestUrl) {
        this.requestUrl = requestUrl;
        this.apiDesc = apiDesc;
    }
}
