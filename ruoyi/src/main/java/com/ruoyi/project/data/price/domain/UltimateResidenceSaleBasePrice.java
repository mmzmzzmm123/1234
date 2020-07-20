package com.ruoyi.project.data.price.domain;

import com.alibaba.fastjson.annotation.JSONField;
import com.ruoyi.framework.aspectj.lang.annotation.Excel;
import com.ruoyi.framework.web.domain.BaseEntity;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 住宅销售基价
 *
 * @author lihe
 */
public class UltimateResidenceSaleBasePrice extends BaseEntity {

    private Integer pageIndex;
    private Integer pageSize;

    @Excel(name = "ID")
    private String id;
    @JSONField(serialize = false)
    private Integer yearMonth;
    @Excel(name = "小区ID")
    private String communityId;
    @Excel(name = "标准基价")
    private BigDecimal basePrice;
    @Excel(name = "主力面积基价")
    private BigDecimal mainPrice;
    @Excel(name = "主力面积系数")
    private BigDecimal areaCoefficient;
    @Excel(name = "竣工年份系数")
    private BigDecimal yearCoefficient;
    @Excel(name = "涨跌幅")
    private BigDecimal voppa;
    @Excel(name = "涨跌幅类型")
    private String voppat;
    @Excel(name = "价值时点")
    private Date priceDate;
    @Excel(name = "价格说明")
    private String comment;
    @Excel(name = "更新日期")
    private Date updateDate;
    @Excel(name = "状态")
    private Boolean status;
    @Excel(name = "更改价格说明")
    private String adjustPriceComment;


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

    public String getCommunityId() {
        return communityId;
    }

    public void setCommunityId(String communityId) {
        this.communityId = communityId;
    }

    public BigDecimal getBasePrice() {
        return basePrice;
    }

    public void setBasePrice(BigDecimal basePrice) {
        this.basePrice = basePrice;
    }

    public BigDecimal getMainPrice() {
        return mainPrice;
    }

    public void setMainPrice(BigDecimal mainPrice) {
        this.mainPrice = mainPrice;
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

    public BigDecimal getVoppa() {
        return voppa;
    }

    public void setVoppa(BigDecimal voppa) {
        this.voppa = voppa;
    }

    public String getVoppat() {
        return voppat;
    }

    public void setVoppat(String voppat) {
        this.voppat = voppat;
    }

    public Date getPriceDate() {
        return priceDate;
    }

    public void setPriceDate(Date priceDate) {
        this.priceDate = priceDate;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
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

    public String getAdjustPriceComment() {
        return adjustPriceComment;
    }

    public void setAdjustPriceComment(String adjustPriceComment) {
        this.adjustPriceComment = adjustPriceComment;
    }
}
