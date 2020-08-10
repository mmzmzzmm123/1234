package com.ruoyi.project.data.basis.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.framework.aspectj.lang.annotation.Excel;
import com.ruoyi.framework.web.domain.BaseEntity;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 联城买卖成交案例
 *
 * @author lihe
 */
public class UvClosingCase extends BaseEntity {

    /**
     * pk 案例id(唯一)
     */
    private String dealId;
    /**
     * 案例类型(一手、二手)
     */
    private String dealType;

    /**
     * 区域
     */
    private String caseDistrict;
    private String caseBlock;
    private String caseLoop;

    /**
     * 总价（元）
     */
    private BigDecimal caseTotalPrice;
    /**
     * 单价（元）
     */
    private BigDecimal caseUnitPrice;
    /**
     * 案例小区名称
     */
    private String caseCommunityName;
    /**
     * 案例地址
     */
    private String caseAddress;
    /**
     * 案例面积
     */
    private BigDecimal caseArea;
    /**
     * 案例房屋类型
     */
    private String caseHouseType;
    /**
     * 案例房屋性质
     */
    private String caseHouseProperty;
    /**
     * 签约日期
     */
    private Date caseContractDate;
    /**
     * 楼层
     */
    private String caseFloor;
    /**
     * 案例房型
     */
    private String caseApartmentLayout;
    /**
     * 物业类型
     */
    private String propertyType;
    /**
     * 小区id
     */
    private String communityId;
    private String communityName;
    /**
     * 楼栋id
     */
    private String buildingId;
    private String buildingName;
    /**
     * 单套id
     */
    private String condoId;
    private String condoName;

    public String getDealId() {
        return dealId;
    }

    public void setDealId(String dealId) {
        this.dealId = dealId;
    }

    public String getDealType() {
        return dealType;
    }

    public void setDealType(String dealType) {
        this.dealType = dealType;
    }

    public String getCaseDistrict() {
        return caseDistrict;
    }

    public void setCaseDistrict(String caseDistrict) {
        this.caseDistrict = caseDistrict;
    }

    public String getCaseBlock() {
        return caseBlock;
    }

    public void setCaseBlock(String caseBlock) {
        this.caseBlock = caseBlock;
    }

    public String getCaseLoop() {
        return caseLoop;
    }

    public void setCaseLoop(String caseLoop) {
        this.caseLoop = caseLoop;
    }

    public BigDecimal getCaseTotalPrice() {
        return caseTotalPrice;
    }

    public void setCaseTotalPrice(BigDecimal caseTotalPrice) {
        this.caseTotalPrice = caseTotalPrice;
    }

    public BigDecimal getCaseUnitPrice() {
        return caseUnitPrice;
    }

    public void setCaseUnitPrice(BigDecimal caseUnitPrice) {
        this.caseUnitPrice = caseUnitPrice;
    }

    public String getCaseCommunityName() {
        return caseCommunityName;
    }

    public void setCaseCommunityName(String caseCommunityName) {
        this.caseCommunityName = caseCommunityName;
    }

    public String getCaseAddress() {
        return caseAddress;
    }

    public void setCaseAddress(String caseAddress) {
        this.caseAddress = caseAddress;
    }

    public BigDecimal getCaseArea() {
        return caseArea;
    }

    public void setCaseArea(BigDecimal caseArea) {
        this.caseArea = caseArea;
    }

    public String getCaseHouseType() {
        return caseHouseType;
    }

    public void setCaseHouseType(String caseHouseType) {
        this.caseHouseType = caseHouseType;
    }

    public String getCaseHouseProperty() {
        return caseHouseProperty;
    }

    public void setCaseHouseProperty(String caseHouseProperty) {
        this.caseHouseProperty = caseHouseProperty;
    }

    public Date getCaseContractDate() {
        return caseContractDate;
    }

    public void setCaseContractDate(Date caseContractDate) {
        this.caseContractDate = caseContractDate;
    }

    public String getCaseFloor() {
        return caseFloor;
    }

    public void setCaseFloor(String caseFloor) {
        this.caseFloor = caseFloor;
    }

    public String getPropertyType() {
        return propertyType;
    }

    public void setPropertyType(String propertyType) {
        this.propertyType = propertyType;
    }

    public String getCommunityId() {
        return communityId;
    }

    public void setCommunityId(String communityId) {
        this.communityId = communityId;
    }

    public String getBuildingId() {
        return buildingId;
    }

    public void setBuildingId(String buildingId) {
        this.buildingId = buildingId;
    }

    public String getCondoId() {
        return condoId;
    }

    public void setCondoId(String condoId) {
        this.condoId = condoId;
    }

    public String getCommunityName() {
        return communityName;
    }

    public void setCommunityName(String communityName) {
        this.communityName = communityName;
    }

    public String getBuildingName() {
        return buildingName;
    }

    public void setBuildingName(String buildingName) {
        this.buildingName = buildingName;
    }

    public String getCondoName() {
        return condoName;
    }

    public void setCondoName(String condoName) {
        this.condoName = condoName;
    }

    public String getCaseApartmentLayout() {
        return caseApartmentLayout;
    }

    public void setCaseApartmentLayout(String caseApartmentLayout) {
        this.caseApartmentLayout = caseApartmentLayout;
    }

}
