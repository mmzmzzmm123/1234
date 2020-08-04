package com.ruoyi.project.data.cases.domain;

import org.springframework.util.DigestUtils;

import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;

/**
 * 新的租赁成交案例
 *
 * @author lihe
 */
public class OtherResidenceRentClosingCase {
    /**
     * id
     */
    private String caseId;
    /**
     * 案例区域
     */
    private String caseDistrict;
    /**
     * 案例小区名称
     */
    private String caseCommunityName;
    /**
     * 案例地址
     */
    private String caseAddress;
    /**
     * 面积
     */
    private BigDecimal caseArea;
    /**
     * 成交日期
     */
    private Date closingDate;
    /**
     * 总价
     */
    private BigDecimal caseTotalPrice;
    /**
     * 朝向
     */
    private String caseDecoration;
    /**
     * 总楼层
     */
    private Integer caseTotalFloor;
    /**
     * 所在层
     */
    private String caseCurrentFloor;
    /**
     * 朝向
     */
    private String caseToward;
    /**
     * 楼层
     */
    private String caseFloor;
    /**
     * 小区
     */
    private String uvCommunityId;
    /**
     * 楼栋
     */
    private String uvBuildingId;


    public String getCaseId() {
        return UUID.randomUUID().toString().replace("-","");
    }

    public void setCaseId(String caseId) {
        this.caseId = caseId;
    }

    public String getCaseDistrict() {
        return caseDistrict;
    }

    public void setCaseDistrict(String caseDistrict) {
        this.caseDistrict = caseDistrict;
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

    public Date getClosingDate() {
        return closingDate;
    }

    public void setClosingDate(Date closingDate) {
        this.closingDate = closingDate;
    }

    public BigDecimal getCaseTotalPrice() {
        return caseTotalPrice;
    }

    public void setCaseTotalPrice(BigDecimal caseTotalPrice) {
        this.caseTotalPrice = caseTotalPrice;
    }

    public String getCaseDecoration() {
        return caseDecoration;
    }

    public void setCaseDecoration(String caseDecoration) {
        this.caseDecoration = caseDecoration;
    }

    public Integer getCaseTotalFloor() {
        return caseTotalFloor;
    }

    public void setCaseTotalFloor(Integer caseTotalFloor) {
        this.caseTotalFloor = caseTotalFloor;
    }

    public String getCaseCurrentFloor() {
        return caseCurrentFloor;
    }

    public void setCaseCurrentFloor(String caseCurrentFloor) {
        this.caseCurrentFloor = caseCurrentFloor;
    }

    public String getCaseToward() {
        return caseToward;
    }

    public void setCaseToward(String caseToward) {
        this.caseToward = caseToward;
    }

    public String getCaseFloor() {
        return caseFloor;
    }

    public void setCaseFloor(String caseFloor) {
        this.caseFloor = caseFloor;
    }

    public String getUvCommunityId() {
        return uvCommunityId;
    }

    public void setUvCommunityId(String uvCommunityId) {
        this.uvCommunityId = uvCommunityId;
    }

    public String getUvBuildingId() {
        return uvBuildingId;
    }

    public void setUvBuildingId(String uvBuildingId) {
        this.uvBuildingId = uvBuildingId;
    }
}
