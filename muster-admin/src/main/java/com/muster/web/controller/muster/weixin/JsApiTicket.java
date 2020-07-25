package com.muster.web.controller.muster.weixin;


/**
 * @ClassName JsApiTicket
 * @Description TOOD  获取授权页ticket 获取的ticket和有效期
 * @Author guoconglin
 * @DATE 2020/4/10 10:39
 * @Version 1.0
 **/

public class JsApiTicket {

    // 接口访问凭证
    private String ticket;
    // 凭证有效期，单位：秒
    private int expiresIn;

    public String getTicket() {
        return ticket;
    }

    public void setTicket(String ticket) {
        this.ticket = ticket;
    }

    public int getExpiresIn() {
        return expiresIn;
    }

    public void setExpiresIn(int expiresIn) {
        this.expiresIn = expiresIn;
    }
}
