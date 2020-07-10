package com.ruoyi.project.data.cases.domain;

import org.springframework.util.DigestUtils;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 一手房案例
 *
 * @author lihe
 * @date 2020年7月9日
 */
public class OriginalNewHouseCase {
    private static final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
    private Integer pageIndex;
    private Integer pageSize;

    private Integer yearMonth;
    /**
     * 案例id
     */
    private String caseId;
    /**
     * 案例标签
     */
    private String caseLabel;
    /**
     * 区域
     */
    private String caseCounty;
    /**
     * 板块
     */
    private String caseBlock;
    /**
     * 环线
     */
    private String caseLoop;
    /**
     * 小区名称
     */
    private String caseCommunityName;
    /**
     * 地址
     */
    private String caseAddress;
    /**
     * 面积
     */
    private BigDecimal caseArea;
    /**
     * 总价
     */
    private BigDecimal caseTotalPrice;
    /**
     * 单价
     */
    private BigDecimal caseUnitPrice;
    /**
     * 房屋类型
     */
    private String caseHouseType;
    /**
     * 签约日期
     */
    private Date caseSigningDate;
    /**
     * 楼层
     */
    private Integer caseFloor;
    /**
     * 房屋性质
     */
    private String caseHouseProperty;
    /**
     * 户型
     */
    private String caseApartmentLayout;
    /**
     * 计算单价
     */
    private BigDecimal computeUnitPrice;
    /**
     * 计算总价
     */
    private BigDecimal computeTotalPrice;
    /**
     * 参考单价
     */
    private BigDecimal referenceUnitPrice;
    /**
     * 参考总价
     */
    private BigDecimal referenceTotalPrice;

    public String getCaseCounty() {
        return caseCounty;
    }

    public void setCaseCounty(String caseCounty) {
        this.caseCounty = caseCounty;
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

    public String getCaseHouseType() {
        return caseHouseType;
    }

    public void setCaseHouseType(String caseHouseType) {
        this.caseHouseType = caseHouseType;
    }

    public Date getCaseSigningDate() {
        return caseSigningDate;
    }

    public void setCaseSigningDate(Date caseSigningDate) {
        this.caseSigningDate = caseSigningDate;
    }

    public Integer getCaseFloor() {
        return caseFloor;
    }

    public void setCaseFloor(Integer caseFloor) {
        this.caseFloor = caseFloor;
    }

    public String getCaseHouseProperty() {
        return caseHouseProperty;
    }

    public void setCaseHouseProperty(String caseHouseProperty) {
        this.caseHouseProperty = caseHouseProperty;
    }

    public String getCaseApartmentLayout() {
        return caseApartmentLayout;
    }

    public void setCaseApartmentLayout(String caseApartmentLayout) {
        this.caseApartmentLayout = caseApartmentLayout;
    }

    public BigDecimal getComputeUnitPrice() {
        return computeUnitPrice;
    }

    public void setComputeUnitPrice(BigDecimal computeUnitPrice) {
        this.computeUnitPrice = computeUnitPrice;
    }

    public BigDecimal getComputeTotalPrice() {
        return computeTotalPrice;
    }

    public void setComputeTotalPrice(BigDecimal computeTotalPrice) {
        this.computeTotalPrice = computeTotalPrice;
    }

    public BigDecimal getReferenceUnitPrice() {
        return referenceUnitPrice;
    }

    public void setReferenceUnitPrice(BigDecimal referenceUnitPrice) {
        this.referenceUnitPrice = referenceUnitPrice;
    }

    public BigDecimal getReferenceTotalPrice() {
        return referenceTotalPrice;
    }

    public void setReferenceTotalPrice(BigDecimal referenceTotalPrice) {
        this.referenceTotalPrice = referenceTotalPrice;
    }

    public Integer getYearMonth() {
        return yearMonth;
    }

    public void setYearMonth(Integer yearMonth) {
        this.yearMonth = yearMonth;
    }

    public String getCaseId() {
        String text = getCaseCounty() + getCaseAddress() + simpleDateFormat.format(getCaseSigningDate());
        return DigestUtils.md5DigestAsHex(text.getBytes());
    }

    public String getCaseLabel() {
        return caseLabel;
    }

    public void setCaseLabel(String caseLabel) {
        this.caseLabel = caseLabel;
    }

    public Integer getPageIndex() {
        return pageIndex;
    }

    public void setPageIndex(Integer pageIndex) {
        this.pageIndex = pageIndex;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }
}
