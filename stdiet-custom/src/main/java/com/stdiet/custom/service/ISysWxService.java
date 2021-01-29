package com.stdiet.custom.service;

public interface ISysWxService {
    /**
     * 微信token验证
     *
     * @param signature
     * @param timestamp
     * @param nonce
     * @return
     */
    public boolean wxCheckAuth(String signature, String timestamp, String nonce);
}
