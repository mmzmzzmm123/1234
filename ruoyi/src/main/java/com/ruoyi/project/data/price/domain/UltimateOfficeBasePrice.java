package com.ruoyi.project.data.price.domain;

import com.alibaba.fastjson.annotation.JSONField;
import com.ruoyi.framework.aspectj.lang.annotation.Excel;
import com.ruoyi.framework.web.domain.BaseEntity;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 【请填写功能名称】对象 office_base_price_ultimate
 *
 * @author ruoyi
 * @date 2020-05-20
 */
public class UltimateOfficeBasePrice extends BaseEntity {

    private Integer pageIndex;
    private Integer pageSize;

    @Excel(name = "id")
    private String id;
//    @JSONField(serialize = false)
    private Integer yearMonth;
    @JSONField(serialize = false)
    private Integer lastYearMonth;
    @Excel(name = "楼栋ID-平台")
    private String buildingId;
    @Excel(name = "小区ID-平台")
    private String communityId;
    @Excel(name = "项目名称")
    private String communityName;
    @Excel(name = "办公项目地址")
    private String communityAddress;
    @Excel(name = "楼栋地址")
    private String buildingAddress;
    @Excel(name = "区域")
    private String countyName;
    @Excel(name = "板块")
    private String blockName;
    @Excel(name = "街道")
    private String streetName;
    @Excel(name = "环线")
    private String loopName;
    @Excel(name = "建成年代")
    private Integer year;
    @Excel(name = "平均面积")
    private BigDecimal avgArea;
    @Excel(name = "总层数")
    private String totalFloorSum;
    @Excel(name = "地上层数")
    private String upperFloorSum;
    @Excel(name = "办公分类")
    private String officeClass;
    @Excel(name = "办公等级")
    private String officeLevel;
    @Excel(name = "主力基价(上期)")
    private BigDecimal mainPrice_1;
    @Excel(name = "主力租金(上期)")
    private BigDecimal mainPriceRent_1;
    @Excel(name = "主力基价")
    private BigDecimal mainPrice;
    @Excel(name = "主力租金")
    private BigDecimal mainPriceRent;
    @Excel(name = "主力基价涨跌幅")
    private BigDecimal mainPricePst;
    @Excel(name = "主力租金涨跌幅")
    private BigDecimal mainPriceRentPst;
    @Excel(name = "主力基价类型")
    private String mainPriceType;
    @Excel(name = "主力租金类型")
    private String mainPriceRentType;
    @Excel(name = "面积修正系数")
    private BigDecimal areaCoefficient;
    @Excel(name = "年份修正系数")
    private BigDecimal yearCoefficient;
    @Excel(name = "基价楼栋系数")
    private BigDecimal buildingCoefficient;
//    @Excel(name = "更新日期")
    private Date updateDate;
//    @Excel(name = "状态")
    private Boolean status;
    @Excel(name = "是否标准楼栋")
    private Boolean isStandardBuilding;
//    @Excel(name = "更改价格说明")
    private String adjustPriceComment;

    public Integer getLastYearMonth() {
        return lastYearMonth;
    }

    public void setLastYearMonth(Integer lastYearMonth) {
        this.lastYearMonth = lastYearMonth;
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getYearMonth() {
        return yearMonth;
    }

    public void setYearMonth(Integer yearMonth) {
        this.yearMonth = yearMonth;
    }

    public String getBuildingId() {
        return buildingId;
    }

    public void setBuildingId(String buildingId) {
        this.buildingId = buildingId;
    }

    public String getCommunityId() {
        return communityId;
    }

    public void setCommunityId(String communityId) {
        this.communityId = communityId;
    }

    public String getCommunityName() {
        return communityName;
    }

    public void setCommunityName(String communityName) {
        this.communityName = communityName;
    }

    public String getCommunityAddress() {
        return communityAddress;
    }

    public void setCommunityAddress(String communityAddress) {
        this.communityAddress = communityAddress;
    }

    public String getBuildingAddress() {
        return buildingAddress;
    }

    public void setBuildingAddress(String buildingAddress) {
        this.buildingAddress = buildingAddress;
    }

    public String getCountyName() {
        return countyName;
    }

    public void setCountyName(String countyName) {
        this.countyName = countyName;
    }

    public String getBlockName() {
        return blockName;
    }

    public void setBlockName(String blockName) {
        this.blockName = blockName;
    }

    public String getStreetName() {
        return streetName;
    }

    public void setStreetName(String streetName) {
        this.streetName = streetName;
    }

    public String getLoopName() {
        return loopName;
    }

    public void setLoopName(String loopName) {
        this.loopName = loopName;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public BigDecimal getAvgArea() {
        return avgArea;
    }

    public void setAvgArea(BigDecimal avgArea) {
        this.avgArea = avgArea;
    }

    public String getTotalFloorSum() {
        return totalFloorSum;
    }

    public void setTotalFloorSum(String totalFloorSum) {
        this.totalFloorSum = totalFloorSum;
    }

    public String getUpperFloorSum() {
        return upperFloorSum;
    }

    public void setUpperFloorSum(String upperFloorSum) {
        this.upperFloorSum = upperFloorSum;
    }

    public String getOfficeClass() {
        return officeClass;
    }

    public void setOfficeClass(String officeClass) {
        this.officeClass = officeClass;
    }

    public String getOfficeLevel() {
        return officeLevel;
    }

    public void setOfficeLevel(String officeLevel) {
        this.officeLevel = officeLevel;
    }

    public BigDecimal getMainPrice_1() {
        return mainPrice_1;
    }

    public void setMainPrice_1(BigDecimal mainPrice_1) {
        this.mainPrice_1 = mainPrice_1;
    }

    public BigDecimal getMainPriceRent_1() {
        return mainPriceRent_1;
    }

    public void setMainPriceRent_1(BigDecimal mainPriceRent_1) {
        this.mainPriceRent_1 = mainPriceRent_1;
    }

    public BigDecimal getMainPrice() {
        return mainPrice;
    }

    public void setMainPrice(BigDecimal mainPrice) {
        this.mainPrice = mainPrice;
    }

    public BigDecimal getMainPriceRent() {
        return mainPriceRent;
    }

    public void setMainPriceRent(BigDecimal mainPriceRent) {
        this.mainPriceRent = mainPriceRent;
    }

    public BigDecimal getMainPricePst() {
        return mainPricePst;
    }

    public void setMainPricePst(BigDecimal mainPricePst) {
        this.mainPricePst = mainPricePst;
    }

    public BigDecimal getMainPriceRentPst() {
        return mainPriceRentPst;
    }

    public void setMainPriceRentPst(BigDecimal mainPriceRentPst) {
        this.mainPriceRentPst = mainPriceRentPst;
    }

    public String getMainPriceType() {
        return mainPriceType;
    }

    public void setMainPriceType(String mainPriceType) {
        this.mainPriceType = mainPriceType;
    }

    public String getMainPriceRentType() {
        return mainPriceRentType;
    }

    public void setMainPriceRentType(String mainPriceRentType) {
        this.mainPriceRentType = mainPriceRentType;
    }

    public BigDecimal getAreaCoefficient() {
        return areaCoefficient;
    }

    public void setAreaCoefficient(BigDecimal areaCoefficient) {
        this.areaCoefficient = areaCoefficient;
    }

    public BigDecimal getYearCoefficient() {
        return yearCoefficient;
    }

    public void setYearCoefficient(BigDecimal yearCoefficient) {
        this.yearCoefficient = yearCoefficient;
    }

    public BigDecimal getBuildingCoefficient() {
        return buildingCoefficient;
    }

    public void setBuildingCoefficient(BigDecimal buildingCoefficient) {
        this.buildingCoefficient = buildingCoefficient;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public Boolean getStandardBuilding() {
        return isStandardBuilding;
    }

    public void setStandardBuilding(Boolean standardBuilding) {
        isStandardBuilding = standardBuilding;
    }

    public String getAdjustPriceComment() {
        return adjustPriceComment;
    }

    public void setAdjustPriceComment(String adjustPriceComment) {
        this.adjustPriceComment = adjustPriceComment;
    }

}
