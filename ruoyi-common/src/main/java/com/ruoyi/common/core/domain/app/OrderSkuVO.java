package com.ruoyi.common.core.domain.app;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;

/**
 * @author Jing.Zhang
 */
@Data
public class OrderSkuVO {

    private String orderSkuId;
    /**
     * 订单ID
     */
    private String orderId;

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 商品id
     */
    private Long productId;

    /**
     * 总价格
     */
    private Double price;

    /**
     * 购买数量
     */
    private Integer buyCount;

    /**
     * sku id
     */
    private Long skuId;

    /**
     * 下单时间
     */
    private Date createTime;

    public void setPrice(Double price) {
        if (price == null) {
            this.price = 0d;
            return;
        }
        this.price =  BigDecimal.valueOf(price).divide(BigDecimal.valueOf(10000L), 4, RoundingMode.HALF_UP).doubleValue();
    }
}
