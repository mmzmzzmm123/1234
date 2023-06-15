package com.ruoyi.tuangou.entity;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class TuangouProductQueryProductResponseEntity {
    /**
     * 商品id
     */
    private Integer product_item_id;
    /**
     * 产品id
     */
    private Integer product_id;
    /**
     * 商品名称
     */
    private String name;
    /**
     * 价格
     */
    private BigDecimal price;
    /**
     * 市场价格
     */
    private BigDecimal market_price;
    /**
     * 商品类型，1081: 次卡
     */
    private Integer type;
}
