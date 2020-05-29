package com.ruoyi.project.data.price.domain;

import com.alibaba.fastjson.annotation.JSONField;
import com.ruoyi.framework.aspectj.lang.annotation.Excel;
import com.ruoyi.framework.web.domain.BaseEntity;

import java.util.Date;

/**
 * 【请填写功能名称】对象 office_base_price_ultimate
 *
 * @author ruoyi
 * @date 2020-05-20
 */
public class ArtificialResidenceSaleBasePrice extends BaseEntity {

    private Integer pageIndex;
    private Integer pageSize;

    @Excel(name = "ID")
    private String id;
    @JSONField(serialize = false)
    private Integer yearMonth;
    @Excel(name = "楼栋ID")
    private String buildingId;
    @Excel(name = "小区ID")
    private String communityId;
    @Excel(name = "主力基价")
    private String mainPrice;
    @Excel(name = "主力租金")
    private String mainPriceRent;
    @Excel(name = "主力基价涨跌幅")
    private String mainPricePst;
    @Excel(name = "主力租金涨跌幅")
    private String mainPriceRentPst;
    @Excel(name = "主力基价类型")
    private String mainPriceType;
    @Excel(name = "主力租金类型")
    private String mainPriceRentType;
    @Excel(name = "更新日期")
    private Date updateDate;
    @Excel(name = "状态")
    private Boolean status;
    @Excel(name = "是否标准楼栋")
    private Boolean isStandardBuilding;
    @Excel(name = "更改价格说明")
    private String adjustPriceComment;

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

    public String getMainPrice() {
        return mainPrice;
    }

    public void setMainPrice(String mainPrice) {
        this.mainPrice = mainPrice;
    }

    public String getMainPriceRent() {
        return mainPriceRent;
    }

    public void setMainPriceRent(String mainPriceRent) {
        this.mainPriceRent = mainPriceRent;
    }

    public String getMainPricePst() {
        return mainPricePst;
    }

    public void setMainPricePst(String mainPricePst) {
        this.mainPricePst = mainPricePst;
    }

    public String getMainPriceRentPst() {
        return mainPriceRentPst;
    }

    public void setMainPriceRentPst(String mainPriceRentPst) {
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
