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
public class OrderRefundVO {

    private String refundId;
    /**
     * 订单ID
     */
    private String orderId;

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 商家id
     */
    private String merchantId;

    /**
     * 计算退款 金额
     */
    private Long calculateRefundPrice;

    /**
     * 实际退款金额
     */
    private Long actualRefundPrice;

    private String remark;

    /**
     * 下单时间
     */
    private Date createTime;

    /**
     * 任务名称
     */
    private String taskId;

    public Double getCalculateRefundPrice() {
        if (null == calculateRefundPrice) {
            return 0d;
        }
        return BigDecimal.valueOf(calculateRefundPrice).divide(BigDecimal.valueOf(100L), 2, RoundingMode.HALF_UP).doubleValue();

    }

    public Double getActualRefundPrice() {
        if (null == actualRefundPrice) {
            return 0d;
        }
        return BigDecimal.valueOf(actualRefundPrice).divide(BigDecimal.valueOf(100L), 2, RoundingMode.HALF_UP).doubleValue();

    }
}
