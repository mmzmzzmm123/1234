package com.ruoyi.system.domain.model;

import java.math.BigDecimal;

public class DataCompanyLoanBody {

    private static final long serialVersionUID = 1L;

    /**
     * 闽政通用户ID
     */
    private String mztUserId;

    /**
     * 企业名称
     */
    private String companyName;

    /**
     * 意向银行（逗号隔开）
     */
    private String loanBand;

    /**
     * 意向银行名称（逗号隔开）
     */
    private String loanBandNames;

    /**
     * 贷款金额
     */
    private BigDecimal loanAmount;

    /**
     * 是否首次贷款（0是 1不是）
     */
    private String loanFirst;

    /**
     * 贷款用途
     */
    private String loanPurpose;

    /**
     * 联系人姓名
     */
    private String contactName;

    /**
     * 联系人手机
     */
    private String contactPhone;

    /**
     * 银行网点
     */
    private String bankBranch;

    /**
     * 客户经理
     */
    private String customerManager;
    /**
     * 贷款对象类型：法人类型(企业法人: c01 社会组织法人:c02 事业单位法人 c03 个体工商户:c04 )
     */
    private String loanObjectType;

    /**
     *  信用代码
     */
    private String xydm;

    /**
     * 手机验证码
     */
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

    public String getLoanBandNames() {
        return loanBandNames;
    }

    public void setLoanBandNames(String loanBandNames) {
        this.loanBandNames = loanBandNames;
    }

    public String getLoanObjectType() {
        return loanObjectType;
    }

    public void setLoanObjectType(String loanObjectType) {
        this.loanObjectType = loanObjectType;
    }

    public String getBankBranch() {
        return bankBranch;
    }

    public void setBankBranch(String bankBranch) {
        this.bankBranch = bankBranch;
    }

    public String getCustomerManager() {
        return customerManager;
    }

    public void setCustomerManager(String customerManager) {
        this.customerManager = customerManager;
    }

    public String getXydm() {
        return xydm;
    }

    public void setXydm(String xydm) {
        this.xydm = xydm;
    }
}
