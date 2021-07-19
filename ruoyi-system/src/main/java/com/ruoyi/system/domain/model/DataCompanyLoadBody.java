package com.ruoyi.system.domain.model;

import com.ruoyi.common.annotation.Excel;

import java.math.BigDecimal;

public class DataCompanyLoadBody {

    private static final long serialVersionUID = 1L;

    /**
     * 闽政通用户ID
     */
    @Excel(name = "闽政通用户ID")
    private String mztUserId;

    /**
     * 企业名称
     */
    @Excel(name = "企业名称")
    private String companyName;

    /**
     * 意向银行（逗号隔开）
     */
    @Excel(name = "意向银行", readConverterExp = "逗=号隔开")
    private String loanBand;

    /**
     * 贷款金额
     */
    @Excel(name = "贷款金额")
    private BigDecimal loanAmount;

    /**
     * 是否首次贷款（0是 1不是）
     */
    @Excel(name = "是否首次贷款", readConverterExp = "0=是,1=不是")
    private String loanFirst;

    /**
     * 贷款用途
     */
    @Excel(name = "贷款用途")
    private String loanPurpose;

    /**
     * 联系人姓名
     */
    @Excel(name = "联系人姓名")
    private String contactName;

    /**
     * 联系人手机
     */
    @Excel(name = "联系人手机")
    private String contactPhone;

    /**
     * 手机验证码
     */
    @Excel(name = "手机验证码")
    private String code;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setMztUserId(String mztUserId) {
        this.mztUserId = mztUserId;
    }

    public String getMztUserId() {
        return mztUserId;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setLoanBand(String loanBand) {
        this.loanBand = loanBand;
    }

    public String getLoanBand() {
        return loanBand;
    }

    public void setLoanAmount(BigDecimal loanAmount) {
        this.loanAmount = loanAmount;
    }

    public BigDecimal getLoanAmount() {
        return loanAmount;
    }

    public void setLoanFirst(String loanFirst) {
        this.loanFirst = loanFirst;
    }

    public String getLoanFirst() {
        return loanFirst;
    }

    public void setLoanPurpose(String loanPurpose) {
        this.loanPurpose = loanPurpose;
    }

    public String getLoanPurpose() {
        return loanPurpose;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public String getContactName() {
        return contactName;
    }

    public void setContactPhone(String contactPhone) {
        this.contactPhone = contactPhone;
    }

    public String getContactPhone() {
        return contactPhone;
    }

}
