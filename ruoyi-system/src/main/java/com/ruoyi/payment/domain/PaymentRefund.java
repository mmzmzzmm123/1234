package com.ruoyi.payment.domain;

import com.ruoyi.common.annotation.Excel;
    import com.ruoyi.common.core.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 支付单退款对象 bus_payment_refund
 *
 * @author Lam
 * @date 2023-09-15
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Accessors(chain = true)
@ApiModel("admin-支付单退款对象")
public class PaymentRefund extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /** 主键 */
    private Long id;

    /** 用户标识 */
    @Excel(name = "用户标识")
    @ApiModelProperty("用户标识")
    private Long userId;

    /** 订单标识 */
    @Excel(name = "订单标识")
    @ApiModelProperty("订单标识")
    private Long orderId;

    /** 订单明细标识 */
    @Excel(name = "订单明细标识")
    @ApiModelProperty("订单明细标识")
    private Long orderDetailsId;

    /** 退款编号 */
    @Excel(name = "退款编号")
    @ApiModelProperty("退款编号")
    private String refundNo;

    /** 退款路径（0微信，1余额） */
    @Excel(name = "退款路径", readConverterExp = "0=微信，1余额")
    @ApiModelProperty("退款路径")
    private String refundRoute;

    /** 状态（-1已取消，0已退款，1申请中） */
    @Excel(name = "状态", readConverterExp = "-=1已取消，0已退款，1申请中")
    @ApiModelProperty("状态")
    private String start;


}
