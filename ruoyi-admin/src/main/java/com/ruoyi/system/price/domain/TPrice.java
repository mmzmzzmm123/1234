package com.ruoyi.system.price.domain;

import java.math.BigDecimal;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 商品价格对象 t_price
 *
 * @author ruoyi
 * @date 2024-04-19
 */
public class TPrice extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private Long id;

    /** 商品id */
    @Excel(name = "商品id")
    private Long productId;

    /** 商品价格 */
    @Excel(name = "商品价格")
    private BigDecimal price;

    @Excel(name = "商品隐藏价格")
    private BigDecimal hidePrice;

    /** 商品回收价 */
    @Excel(name = "商品回收价")
    private BigDecimal recoveredPrice;

    public BigDecimal getHidePrice() {
        return hidePrice;
    }

    public void setHidePrice(BigDecimal hidePrice) {
        this.hidePrice = hidePrice;
    }

    public BigDecimal getRecoveredPrice() {
        return recoveredPrice;
    }

    public void setRecoveredPrice(BigDecimal recoveredPrice) {
        this.recoveredPrice = recoveredPrice;
    }



    public void setId(Long id)
    {
        this.id = id;
    }

    public Long getId()
    {
        return id;
    }
    public void setProductId(Long productId)
    {
        this.productId = productId;
    }

    public Long getProductId()
    {
        return productId;
    }
    public void setPrice(BigDecimal price)
    {
        this.price = price;
    }

    public BigDecimal getPrice()
    {
        return price;
    }

    @Override
    public String toString() {
        return "TPrice{" +
                "id=" + id +
                ", productId=" + productId +
                ", price=" + price +
                ", hidePrice=" + hidePrice +
                ", recoveredPrice=" + recoveredPrice +
                '}';
    }
}
