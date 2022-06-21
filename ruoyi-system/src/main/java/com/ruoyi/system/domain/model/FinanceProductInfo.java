package com.ruoyi.system.domain.model;

/**
 * @author renjf
 * @date 2022年05月26日 16:13
 */
public class FinanceProductInfo {

    private Long id;

    private String applicationArea;

    private String applyCondition;

    private String capAreaName;

    private String capOrgName;

    private String characteristic;

    private String characteristicOption;

    private String customer;

    private String detail;

    private String guaranteeMode;

    private String limitEnd;

    private String limitStart;

    private String limitShow;

    private String loanPurpose;

    private String logoApp;

    private String logoPc;

    private String material;

    private String materialOption;

    private String periodEnd;

    private String periodStart;

    private String periodShow;

    private String proCode;

    private String proName;

    private String proTarget;

    private String publishTime;

    private String rateEnd;

    private String rateStart;

    private String rateShow;

    private String sectionInfo;

    private String state;

    private String subType;

    private String type;

    private String applicationAreaName;

    private String sectionInfoName;

    private String loanPurposeName;

    public String getApplicationAreaName() {
        return applicationAreaName;
    }

    public void setApplicationAreaName(String applicationAreaName) {
        this.applicationAreaName = applicationAreaName;
    }

    public String getSectionInfoName() {
        return sectionInfoName;
    }

    public void setSectionInfoName(String sectionInfoName) {
        this.sectionInfoName = sectionInfoName;
    }

    public String getLoanPurposeName() {
        return loanPurposeName;
    }

    public void setLoanPurposeName(String loanPurposeName) {
        this.loanPurposeName = loanPurposeName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getApplicationArea() {
        return applicationArea;
    }

    public void setApplicationArea(String applicationArea) {
        this.applicationArea = applicationArea;
    }

    public String getApplyCondition() {
        return applyCondition;
    }

    public void setApplyCondition(String applyCondition) {
        this.applyCondition = applyCondition;
    }

    public String getCapAreaName() {
        return capAreaName;
    }

    public void setCapAreaName(String capAreaName) {
        this.capAreaName = capAreaName;
    }

    public String getCapOrgName() {
        return capOrgName;
    }

    public void setCapOrgName(String capOrgName) {
        this.capOrgName = capOrgName;
    }

    public String getCharacteristic() {
        return characteristic;
    }

    public void setCharacteristic(String characteristic) {
        this.characteristic = characteristic;
    }

    public String getCharacteristicOption() {
        return characteristicOption;
    }

    public void setCharacteristicOption(String characteristicOption) {
        this.characteristicOption = characteristicOption;
    }

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getGuaranteeMode() {
        return guaranteeMode;
    }

    public void setGuaranteeMode(String guaranteeMode) {
        this.guaranteeMode = guaranteeMode;
    }

    public String getLimitEnd() {
        return limitEnd;
    }

    public void setLimitEnd(String limitEnd) {
        this.limitEnd = limitEnd;
    }

    public String getLimitStart() {
        return limitStart;
    }

    public void setLimitStart(String limitStart) {
        this.limitStart = limitStart;
    }

    public String getLimitShow() {
        return limitShow;
    }

    public void setLimitShow(String limitShow) {
        this.limitShow = limitShow;
    }

    public String getLoanPurpose() {
        return loanPurpose;
    }

    public void setLoanPurpose(String loanPurpose) {
        this.loanPurpose = loanPurpose;
    }

    public String getLogoApp() {
        return logoApp;
    }

    public void setLogoApp(String logoApp) {
        this.logoApp = logoApp;
    }

    public String getLogoPc() {
        return logoPc;
    }

    public void setLogoPc(String logoPc) {
        this.logoPc = logoPc;
    }

    public String getMaterial() {
        return material;
    }

    public void setMaterial(String material) {
        this.material = material;
    }

    public String getMaterialOption() {
        return materialOption;
    }

    public void setMaterialOption(String materialOption) {
        this.materialOption = materialOption;
    }

    public String getPeriodEnd() {
        return periodEnd;
    }

    public void setPeriodEnd(String periodEnd) {
        this.periodEnd = periodEnd;
    }

    public String getPeriodStart() {
        return periodStart;
    }

    public void setPeriodStart(String periodStart) {
        this.periodStart = periodStart;
    }

    public String getPeriodShow() {
        return periodShow;
    }

    public void setPeriodShow(String periodShow) {
        this.periodShow = periodShow;
    }

    public String getProCode() {
        return proCode;
    }

    public void setProCode(String proCode) {
        this.proCode = proCode;
    }

    public String getProName() {
        return proName;
    }

    public void setProName(String proName) {
        this.proName = proName;
    }

    public String getProTarget() {
        return proTarget;
    }

    public void setProTarget(String proTarget) {
        this.proTarget = proTarget;
    }

    public String getPublishTime() {
        return publishTime;
    }

    public void setPublishTime(String publishTime) {
        this.publishTime = publishTime;
    }

    public String getRateEnd() {
        return rateEnd;
    }

    public void setRateEnd(String rateEnd) {
        this.rateEnd = rateEnd;
    }

    public String getRateStart() {
        return rateStart;
    }

    public void setRateStart(String rateStart) {
        this.rateStart = rateStart;
    }

    public String getRateShow() {
        return rateShow;
    }

    public void setRateShow(String rateShow) {
        this.rateShow = rateShow;
    }

    public String getSectionInfo() {
        return sectionInfo;
    }

    public void setSectionInfo(String sectionInfo) {
        this.sectionInfo = sectionInfo;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getSubType() {
        return subType;
    }

    public void setSubType(String subType) {
        this.subType = subType;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "FinanceProductInfo{" +
                "id=" + id +
                ", applicationArea='" + applicationArea + '\'' +
                ", applyCondition='" + applyCondition + '\'' +
                ", capAreaName='" + capAreaName + '\'' +
                ", capOrgName='" + capOrgName + '\'' +
                ", characteristic='" + characteristic + '\'' +
                ", characteristicOption='" + characteristicOption + '\'' +
                ", customer='" + customer + '\'' +
                ", detail='" + detail + '\'' +
                ", guaranteeMode='" + guaranteeMode + '\'' +
                ", limitEnd='" + limitEnd + '\'' +
                ", limitStart='" + limitStart + '\'' +
                ", limitShow='" + limitShow + '\'' +
                ", loanPurpose='" + loanPurpose + '\'' +
                ", logoApp='" + logoApp + '\'' +
                ", logoPc='" + logoPc + '\'' +
                ", material='" + material + '\'' +
                ", materialOption='" + materialOption + '\'' +
                ", periodEnd='" + periodEnd + '\'' +
                ", periodStart='" + periodStart + '\'' +
                ", periodShow='" + periodShow + '\'' +
                ", proCode='" + proCode + '\'' +
                ", proName='" + proName + '\'' +
                ", proTarget='" + proTarget + '\'' +
                ", publishTime='" + publishTime + '\'' +
                ", rateEnd='" + rateEnd + '\'' +
                ", rateStart='" + rateStart + '\'' +
                ", rateShow='" + rateShow + '\'' +
                ", sectionInfo='" + sectionInfo + '\'' +
                ", state='" + state + '\'' +
                ", subType='" + subType + '\'' +
                ", type='" + type + '\'' +
                ", applicationAreaName='" + applicationAreaName + '\'' +
                ", sectionInfoName='" + sectionInfoName + '\'' +
                ", loanPurposeName='" + loanPurposeName + '\'' +
                '}';
    }
}
