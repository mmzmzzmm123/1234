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
    private Double calculateRefundPrice;

    /**
     * 实际退款金额
     */
    private Double actualRefundPrice;

    private String remark;

    /**
     * 下单时间
     */
    private Date createTime;

    /**
     * 任务名称
     */
    private String taskId;
}
