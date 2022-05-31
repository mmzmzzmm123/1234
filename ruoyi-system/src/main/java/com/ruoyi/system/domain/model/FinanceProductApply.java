package com.ruoyi.system.domain.model;

import java.math.BigDecimal;

/**
 * @author renjf
 * @date 2022年05月26日 16:13
 */
public class FinanceProductApply {
    // 申请来源：1-产品申请，2-需求对接，3-直通车申请
    private String source;

    // 需求对接ID
    private Long takeDmdId;

    // 贷款产品ID
    private Long proId;

    // 申请额度（万元）
    private BigDecimal applyLimit;

    // 申请期限（月）
    private Long applyPeriod;

    // 意向利率（年化）
    private BigDecimal applyRate;

    // 金融机构id
    private Long capOrgId;

    // 贷款用途（字典数据value）
    private String purpose;

    // 担保方式（字典数据value，复数值用英文逗号分隔）
    private String guaranteeMode;

    // 联系人
    private String contactPerson;

    // 联系人电话
    private String contactPhone;

    // 贷用户手机号（判断用户是否入驻平台）
    private String userPhone;

    // 社会信用代码（判断企业是否入驻平台）
    private String socialCode;

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public Long getTakeDmdId() {
        return takeDmdId;
    }

    public void setTakeDmdId(Long takeDmdId) {
        this.takeDmdId = takeDmdId;
    }

    public Long getProId() {
        return proId;
    }

    public void setProId(Long proId) {
        this.proId = proId;
    }

    public BigDecimal getApplyLimit() {
        return applyLimit;
    }

    public void setApplyLimit(BigDecimal applyLimit) {
        this.applyLimit = applyLimit;
    }

    public Long getApplyPeriod() {
        return applyPeriod;
    }

    public void setApplyPeriod(Long applyPeriod) {
        this.applyPeriod = applyPeriod;
    }

    public BigDecimal getApplyRate() {
        return applyRate;
    }

    public void setApplyRate(BigDecimal applyRate) {
        this.applyRate = applyRate;
    }

    public Long getCapOrgId() {
        return capOrgId;
    }

    public void setCapOrgId(Long capOrgId) {
        this.capOrgId = capOrgId;
    }

    public String getPurpose() {
        return purpose;
    }

    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }

    public String getGuaranteeMode() {
        return guaranteeMode;
    }

    public void setGuaranteeMode(String guaranteeMode) {
        this.guaranteeMode = guaranteeMode;
    }

    public String getContactPerson() {
        return contactPerson;
    }

    public void setContactPerson(String contactPerson) {
        this.contactPerson = contactPerson;
    }

    public String getContactPhone() {
        return contactPhone;
    }

    public void setContactPhone(String contactPhone) {
        this.contactPhone = contactPhone;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    public String getSocialCode() {
        return socialCode;
    }

    public void setSocialCode(String socialCode) {
        this.socialCode = socialCode;
    }

    @Override
    public String toString() {
        return "FinanceProductApply{" +
                "source='" + source + '\'' +
                ", takeDmdId=" + takeDmdId +
                ", proId=" + proId +
                ", applyLimit=" + applyLimit +
                ", applyPeriod=" + applyPeriod +
                ", applyRate=" + applyRate +
                ", capOrgId=" + capOrgId +
                ", purpose='" + purpose + '\'' +
                ", guaranteeMode='" + guaranteeMode + '\'' +
                ", contactPerson='" + contactPerson + '\'' +
                ", contactPhone='" + contactPhone + '\'' +
                ", userPhone='" + userPhone + '\'' +
                ", socialCode='" + socialCode + '\'' +
                '}';
    }
}
