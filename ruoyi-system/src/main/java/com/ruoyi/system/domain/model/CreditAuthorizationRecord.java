package com.ruoyi.system.domain.model;

/**
 * 信贷直通车新增企业用户授权书同意记录
 */
public class CreditAuthorizationRecord {

    private Long authorizationId;

    private String socialCode;

    private String busiType;

    private String userPhone;

    public Long getAuthorizationId() {
        return authorizationId;
    }

    public void setAuthorizationId(Long authorizationId) {
        this.authorizationId = authorizationId;
    }

    public String getSocialCode() {
        return socialCode;
    }

    public void setSocialCode(String socialCode) {
        this.socialCode = socialCode;
    }

    public String getBusiType() {
        return busiType;
    }

    public void setBusiType(String busiType) {
        this.busiType = busiType;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    @Override
    public String toString() {
        return "CreditAuthorizationRecord{" +
                "authorizationId=" + authorizationId +
                ", socialCode='" + socialCode + '\'' +
                ", busiType='" + busiType + '\'' +
                ", userPhone='" + userPhone + '\'' +
                '}';
    }
}
