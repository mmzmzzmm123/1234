package com.muster.web.controller.muster.weixin;


import java.io.Serializable;

/**
 * @ClassName WxJsapiSignature
 * @Description TOOD
 * @Author guoconglin
 * @DATE 2020/4/10 13:14
 * @Version 1.0
 **/

public class WxJsapiSignature implements Serializable {

    private static final long serialVersionUID = -1116808193154384804L;

    /**
     * 公众号的唯一标识
     */
    private String appId;

    /**
     * 生成签名的随机串
     */
    private String nonceStr;

    /**
     * 生成签名的时间戳
     */
    private long timestamp;

    /**
     * 地址
     */
    private String url;

    /**
     * 签名
     */
    private String signature;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getNonceStr() {
        return nonceStr;
    }

    public void setNonceStr(String nonceStr) {
        this.nonceStr = nonceStr;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }
}
