package com.stdiet.custom.service;

import com.stdiet.custom.domain.WxXmlData;

import javax.servlet.http.HttpServletRequest;

public interface ISysWxService {
    /**
     * 微信token验证
     *
     * @param signature
     * @param timestamp
     * @param nonce
     * @return
     */
    public String wxCheckAuth(String signature, String timestamp, String nonce, String echostr);

    public String autoResponse(HttpServletRequest request);
}
