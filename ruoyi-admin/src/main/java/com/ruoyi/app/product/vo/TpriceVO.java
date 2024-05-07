package com.ruoyi.app.product.vo;

import com.ruoyi.common.annotation.Excel;
import io.swagger.annotations.ApiModelProperty;

import java.math.BigDecimal;

public class TpriceVO {
    private Long id;

    /** 商品id */
    @ApiModelProperty(name = "商品id")
    private Long productId;

    /** 商品价格 */
    @ApiModelProperty(name = "商品价格")
    private BigDecimal price;

    @ApiModelProperty(name = "商品隐藏价格")
    private String hidePrice;

    /** 商品回收价 */
    @ApiModelProperty(name = "商品回收价")
    private BigDecimal recoveredPrice;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getHidePrice() {
        return hidePrice;
    }

    public void setHidePrice(String hidePrice) {
        this.hidePrice = hidePrice;
    }

    public BigDecimal getRecoveredPrice() {
        return recoveredPrice;
    }

    public void setRecoveredPrice(BigDecimal recoveredPrice) {
        this.recoveredPrice = recoveredPrice;
    }
}
