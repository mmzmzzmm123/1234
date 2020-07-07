package com.ruoyi.project.data.cases.domain;

import org.springframework.util.DigestUtils;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 租赁成交案例
 */
public class OriginalResidenceRentClosingCase {
    private String caseId;
    /**
     * 合同编号
     */
    private String caseContractNo;
    /**
     * 小区名称
     */
    private String caseCommunityName;
    /**
     * 挂牌时间
     */
    private Date caseOpeningDate;
    /**
     * 交易时间
     */
    private Date caseClosingDate;
    /**
     * 租金
     */
    private BigDecimal caseRentPrice;
    /**
     * 总层
     */
    private Integer caseTotalFloor;
    /**
     * 面积
     */
    private BigDecimal caseArea;
    /**
     * 朝向
     */
    private String caseToward;
    /**
     * 户型
     */
    private String caseApartmentLayout;
    /**
     * 装修
     */
    private String caseDecoration;
    /**
     * 联城小区id
     */
    private String cleanCommunityId;
    /**
     * 联城楼栋id
     */
    private String cleanBuildingId;


    public String getCaseContractNo() {
        return caseContractNo;
    }

    public void setCaseContractNo(String caseContractNo) {
        this.caseContractNo = caseContractNo;
    }

    public String getCaseCommunityName() {
        return caseCommunityName;
    }

    public void setCaseCommunityName(String caseCommunityName) {
        this.caseCommunityName = caseCommunityName;
    }

    public Date getCaseOpeningDate() {
        return caseOpeningDate;
    }

    public void setCaseOpeningDate(Date caseOpeningDate) {
        this.caseOpeningDate = caseOpeningDate;
    }

    public Date getCaseClosingDate() {
        return caseClosingDate;
    }

    public void setCaseClosingDate(Date caseClosingDate) {
        this.caseClosingDate = caseClosingDate;
    }

    public BigDecimal getCaseRentPrice() {
        return caseRentPrice;
    }

    public void setCaseRentPrice(BigDecimal caseRentPrice) {
        this.caseRentPrice = caseRentPrice;
    }

    public Integer getCaseTotalFloor() {
        return caseTotalFloor;
    }

    public void setCaseTotalFloor(Integer caseTotalFloor) {
        this.caseTotalFloor = caseTotalFloor;
    }

    public String getCaseToward() {
        return caseToward;
    }

    public void setCaseToward(String caseToward) {
        this.caseToward = caseToward;
    }

    public String getCaseApartmentLayout() {
        return caseApartmentLayout;
    }

    public void setCaseApartmentLayout(String caseApartmentLayout) {
        this.caseApartmentLayout = caseApartmentLayout;
    }

    public String getCaseDecoration() {
        return caseDecoration;
    }

    public void setCaseDecoration(String caseDecoration) {
        this.caseDecoration = caseDecoration;
    }

    public String getCleanCommunityId() {
        return cleanCommunityId;
    }

    public void setCleanCommunityId(String cleanCommunityId) {
        this.cleanCommunityId = cleanCommunityId;
    }

    public String getCleanBuildingId() {
        return cleanBuildingId;
    }

    public void setCleanBuildingId(String cleanBuildingId) {
        this.cleanBuildingId = cleanBuildingId;
    }

    public BigDecimal getCaseArea() {
        return caseArea;
    }

    public void setCaseArea(BigDecimal caseArea) {
        this.caseArea = caseArea;
    }

    public String getCaseId() {
        return DigestUtils.md5DigestAsHex(getCaseContractNo().getBytes());
    }
}
