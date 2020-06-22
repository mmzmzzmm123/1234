package com.ruoyi.project.data.price.domain;

import com.ruoyi.framework.aspectj.lang.annotation.Excel;
import com.ruoyi.framework.web.domain.BaseEntity;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 人工修正住宅租赁对象
 *
 * @author ruoyi
 * @date 2020-05-20
 */
public class ArtificialResidenceRentBasePrice extends BaseEntity {

    /**
     * 年月
     */
    private Integer yearMonth;
    private Integer pageIndex;
    private Integer pageSize;

    @Excel(name = "id")
    private String id;
    @Excel(name = "小区ID")
    private String communityId;
    @Excel(name = "平均租金")
    private BigDecimal rentPrice;
    @Excel(name = "租金主力面积系数")
    private BigDecimal mainRentCoefficient;
    @Excel(name = "主力面积租金")
    private BigDecimal mainRentPrice;
    @Excel(name = "上月AI租金")
    private BigDecimal rentPrice_1;
    @Excel(name = "价格涨跌幅类型-调整后")
    private String voppat;
    @Excel(name = "价格涨跌幅-调整后")
    private BigDecimal voppa;

    /**
     * 价值时点
     */
    private Date valuePoint;
    /**
     * 上期价值时点
     */
    private Date lastValuePoint;
    /**
     * 价格id（随机生成）
     */
    private String priceId;


    public Integer getYearMonth() {
        return yearMonth;
    }

    public void setYearMonth(Integer yearMonth) {
        this.yearMonth = yearMonth;
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

    public String getCommunityId() {
        return communityId;
    }

    public void setCommunityId(String communityId) {
        this.communityId = communityId;
    }

    public BigDecimal getMainRentCoefficient() {
        return mainRentCoefficient;
    }

    public void setMainRentCoefficient(BigDecimal mainRentCoefficient) {
        this.mainRentCoefficient = mainRentCoefficient;
    }

    public BigDecimal getRentPrice() {
        return rentPrice;
    }

    public void setRentPrice(BigDecimal rentPrice) {
        this.rentPrice = rentPrice;
    }

    public BigDecimal getMainRentPrice() {
        return mainRentPrice;
    }

    public void setMainRentPrice(BigDecimal mainRentPrice) {
        this.mainRentPrice = mainRentPrice;
    }

    public BigDecimal getRentPrice_1() {
        return rentPrice_1;
    }

    public void setRentPrice_1(BigDecimal rentPrice_1) {
        this.rentPrice_1 = rentPrice_1;
    }

    public String getVoppat() {
        return voppat;
    }

    public void setVoppat(String voppat) {
        this.voppat = voppat;
    }

    public BigDecimal getVoppa() {
        return voppa;
    }

    public void setVoppa(BigDecimal voppa) {
        this.voppa = voppa;
    }

    public Date getValuePoint() {
        return valuePoint;
    }

    public void setValuePoint(Date valuePoint) {
        this.valuePoint = valuePoint;
    }

    public Date getLastValuePoint() {
        return lastValuePoint;
    }

    public void setLastValuePoint(Date lastValuePoint) {
        this.lastValuePoint = lastValuePoint;
    }

    public String getPriceId() {
        return priceId;
    }

    public void setPriceId(String priceId) {
        this.priceId = priceId;
    }
}
