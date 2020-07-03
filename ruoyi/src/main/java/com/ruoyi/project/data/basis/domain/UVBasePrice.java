package com.ruoyi.project.data.basis.domain;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.framework.aspectj.lang.annotation.Excel;
import com.ruoyi.framework.web.domain.BaseEntity;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.util.Date;

public class UVBasePrice extends BaseEntity {
    /**
     * 小区id
     */
    @Excel(name = "小区ID")
    private String communityId;
    /**
     * 价格类型
     */
    @Excel(name = "价格类型")
    private Integer priceType;

    /**
     * 小区类型(1、住宅；2、办公)
     */
    @Excel(name = "项目类型")
    private Integer communityType;

    /**
     * 基价（售价、租金）
     */
    @Excel(name = "主力基价", cellType = Excel.ColumnType.NUMERIC)
    private BigDecimal standardPrice;
    /**
     * 主力面积基价（售价、租金）
     */
    @Excel(name = "主力面积基价", cellType = Excel.ColumnType.NUMERIC)
    private BigDecimal mainAreaPrice;
    /**
     * 价值时点
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "价值时点", dateFormat = "yyyy-MM-dd")
    private Date valuePoint;
    /**
     * 修改说明
     */
    @Excel(name = "价格说明")
    private String comment;

    public String getCommunityId() {
        return communityId;
    }

    public void setCommunityId(String communityId) {
        this.communityId = communityId;
    }

    public Integer getPriceType() {
        return priceType;
    }

    public void setPriceType(Integer priceType) {
        this.priceType = priceType;
    }

    public Integer getCommunityType() {
        return communityType;
    }

    public void setCommunityType(Integer communityType) {
        this.communityType = communityType;
    }

    public BigDecimal getStandardPrice() {
        return standardPrice;
    }

    public void setStandardPrice(BigDecimal standardPrice) {
        this.standardPrice = standardPrice;
    }

    public BigDecimal getMainAreaPrice() {
        return mainAreaPrice;
    }

    public void setMainAreaPrice(BigDecimal mainAreaPrice) {
        this.mainAreaPrice = mainAreaPrice;
    }

    public Date getValuePoint() {
        return valuePoint;
    }

    public void setValuePoint(Date valuePoint) {
        this.valuePoint = valuePoint;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
