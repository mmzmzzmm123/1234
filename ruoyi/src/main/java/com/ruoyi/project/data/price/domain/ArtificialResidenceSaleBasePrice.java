package com.ruoyi.project.data.price.domain;

import java.util.Date;

/**
 * 人工修正住宅基价
 */
public class ArtificialResidenceSaleBasePrice extends ComputeResidenceSaleBasePrice {

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

    public Date getValuePoint() {
        return valuePoint;
    }

    public void setValuePoint(Date valuePoint) {
        this.valuePoint = valuePoint;
    }

    public String getPriceId() {
        return priceId;
    }

    public void setPriceId(String priceId) {
        this.priceId = priceId;
    }

    public Date getLastValuePoint() {
        return lastValuePoint;
    }

    public void setLastValuePoint(Date lastValuePoint) {
        this.lastValuePoint = lastValuePoint;
    }
}
