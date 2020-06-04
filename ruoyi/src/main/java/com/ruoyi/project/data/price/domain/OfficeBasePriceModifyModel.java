package com.ruoyi.project.data.price.domain;

import java.math.BigDecimal;

public class OfficeBasePriceModifyModel {
    private Integer id;
    private Integer yearMonth;
    private BigDecimal mainPrice;
    private BigDecimal mainPricePst;
    private BigDecimal mainPriceRent;
    private BigDecimal mainPriceRentPst;
    private String comment;

    public OfficeBasePriceModifyModel(Integer id, Integer yearMonth) {
        this.id = id;
        this.yearMonth = yearMonth;
    }


    public Integer getId() {
        return id;
    }

    public Integer getYearMonth() {
        return yearMonth;
    }

    public BigDecimal getMainPrice() {
        return mainPrice;
    }

    public void setMainPrice(BigDecimal mainPrice) {
        this.mainPrice = mainPrice;
    }

    public BigDecimal getMainPricePst() {
        return mainPricePst;
    }

    public void setMainPricePst(BigDecimal mainPricePst) {
        this.mainPricePst = mainPricePst;
    }

    public BigDecimal getMainPriceRent() {
        return mainPriceRent;
    }

    public void setMainPriceRent(BigDecimal mainPriceRent) {
        this.mainPriceRent = mainPriceRent;
    }

    public BigDecimal getMainPriceRentPst() {
        return mainPriceRentPst;
    }

    public void setMainPriceRentPst(BigDecimal mainPriceRentPst) {
        this.mainPriceRentPst = mainPriceRentPst;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
