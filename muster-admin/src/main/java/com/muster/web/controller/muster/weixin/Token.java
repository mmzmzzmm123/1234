package com.muster.web.controller.muster.weixin;


/**
 * @ClassName Token
 * @Description TOOD 获取的token 和有效时间
 * @Author guoconglin
 * @DATE 2020/4/10 10:40
 * @Version 1.0
 **/

public class Token {

    // 接口访问凭证
    private String accessToken;
    // 凭证有效期，单位：秒
    private int expiresIn;

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public int getExpiresIn() {
        return expiresIn;
    }

    public void setExpiresIn(int expiresIn) {
        this.expiresIn = expiresIn;
    }
}
