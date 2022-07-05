package com.ruoyi.system.domain.model;

/**
 * 信贷直通车新增企业用户授权书同意记录
 */
public class CreditAuthorizationRecord {

    private Long authorizationId;

    private Long orgId;

    private Long areaId;

    private String userPhone;

    public Long getAuthorizationId() {
        return authorizationId;
    }

    public void setAuthorizationId(Long authorizationId) {
        this.authorizationId = authorizationId;
    }

    public Long getOrgId() {
        return orgId;
    }

    public void setOrgId(Long orgId) {
        this.orgId = orgId;
    }

    public Long getAreaId() {
        return areaId;
    }

    public void setAreaId(Long areaId) {
        this.areaId = areaId;
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
                "authorizationId：" + authorizationId +
                ", orgId：" + orgId +
                ", areaId：" + areaId +
                ", userPhone：'" + userPhone + '\'' +
                '}';
    }
}
