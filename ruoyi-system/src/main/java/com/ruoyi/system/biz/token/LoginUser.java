package com.ruoyi.system.biz.token;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ruoyi.system.domain.DataWechatUser;

public class LoginUser {

    /**
     * 当前登录 token
     */
    private String token;

    private DataWechatUser user;

    private String sessionKey;

    /**
     * 用于 redis 缓存的唯一标识
     */
    @JsonIgnore
    private String uuid;
    /**
     * 登录时间
     */
    @JsonIgnore
    private Long loginTime;

    /**
     * 过期时间
     */
    @JsonIgnore
    private Long expireTime;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public DataWechatUser getUser() {
        return user;
    }

    public void setUser(DataWechatUser user) {
        this.user = user;
    }

    public Long getLoginTime() {
        return loginTime;
    }

    public void setLoginTime(Long loginTime) {
        this.loginTime = loginTime;
    }

    public Long getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(Long expireTime) {
        this.expireTime = expireTime;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getSessionKey() {
        return sessionKey;
    }

    public void setSessionKey(String sessionKey) {
        this.sessionKey = sessionKey;
    }
}
