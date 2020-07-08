package com.ruoyi.project.data.cases.domain;

import java.math.BigDecimal;

/**
 * 办公案例汇总
 *
 * @author purple
 */
public class OfficeAggregationCase extends OriginalOfficeCase {
    private Integer yearMonth;
    private String buildingId;
    private String communityId;
    private String totalFloor;
    private BigDecimal floorCoefficient;
    private Integer decorationCoefficient;
    private BigDecimal priceAmend;
    private BigDecimal mainPrice_1;
    private BigDecimal mainPriceRent_1;
    private BigDecimal listedPrice_1;
    private BigDecimal listedPriceRent_1;
    private BigDecimal listedPrice;
    private BigDecimal listedPriceRent;
    private BigDecimal vopp;
    private String voppt;

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

    public String getTotalFloor() {
        return totalFloor;
    }

    public void setTotalFloor(String totalFloor) {
        this.totalFloor = totalFloor;
    }

    public BigDecimal getFloorCoefficient() {
        return floorCoefficient;
    }

    public void setFloorCoefficient(BigDecimal floorCoefficient) {
        this.floorCoefficient = floorCoefficient;
    }

    public Integer getDecorationCoefficient() {
        return decorationCoefficient;
    }

    public void setDecorationCoefficient(Integer decorationCoefficient) {
        this.decorationCoefficient = decorationCoefficient;
    }

    public BigDecimal getPriceAmend() {
        return priceAmend;
    }

    public void setPriceAmend(BigDecimal priceAmend) {
        this.priceAmend = priceAmend;
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

    public BigDecimal getListedPrice_1() {
        return listedPrice_1;
    }

    public void setListedPrice_1(BigDecimal listedPrice_1) {
        this.listedPrice_1 = listedPrice_1;
    }

    public BigDecimal getListedPriceRent_1() {
        return listedPriceRent_1;
    }

    public void setListedPriceRent_1(BigDecimal listedPriceRent_1) {
        this.listedPriceRent_1 = listedPriceRent_1;
    }

    public BigDecimal getListedPrice() {
        return listedPrice;
    }

    public void setListedPrice(BigDecimal listedPrice) {
        this.listedPrice = listedPrice;
    }

    public BigDecimal getListedPriceRent() {
        return listedPriceRent;
    }

    public void setListedPriceRent(BigDecimal listedPriceRent) {
        this.listedPriceRent = listedPriceRent;
    }

    public BigDecimal getVopp() {
        return vopp;
    }

    public void setVopp(BigDecimal vopp) {
        this.vopp = vopp;
    }

    public String getVoppt() {
        return voppt;
    }

    public void setVoppt(String voppt) {
        this.voppt = voppt;
    }
}
