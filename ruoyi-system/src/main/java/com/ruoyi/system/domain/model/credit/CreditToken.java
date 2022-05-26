package com.ruoyi.system.domain.model.credit;


/**
 * 泉州市公共信用信息平台（三期）
 * Token对象
 */
public class CreditToken {

    private String access_token;
    private String refresh_token;
    private String scope;
    private String mobile;
    private String name;
    private String token_type;
    private long expires_in;
    private String userId;
    private String email;
    private String jti;
    private String username;

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

    public String getAccess_token() {
        return access_token;
    }

    public void setRefresh_token(String refresh_token) {
        this.refresh_token = refresh_token;
    }

    public String getRefresh_token() {
        return refresh_token;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public String getScope() {
        return scope;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getMobile() {
        return mobile;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setToken_type(String token_type) {
        this.token_type = token_type;
    }

    public String getToken_type() {
        return token_type;
    }

    public void setExpires_in(long expires_in) {
        this.expires_in = expires_in;
    }

    public long getExpires_in() {
        return expires_in;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserId() {
        return userId;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setJti(String jti) {
        this.jti = jti;
    }

    public String getJti() {
        return jti;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

}