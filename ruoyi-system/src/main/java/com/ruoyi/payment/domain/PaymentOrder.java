package com.ruoyi.payment.domain;

import java.math.BigDecimal;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.common.annotation.Excel;
    import com.ruoyi.common.core.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 支付订单对象 bus_payment_order
 *
 * @author Lam
 * @date 2023-09-15
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Accessors(chain = true)
@ApiModel("admin-支付订单对象")
public class PaymentOrder extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /** 主键 */
    private Long id;

    /** 订单类型 */
    @Excel(name = "订单类型")
    @ApiModelProperty("订单类型")
    private String orderType;

    /** 订单标识 */
    @Excel(name = "订单标识")
    @ApiModelProperty("订单标识")
    private Long orderId;

    /** 交易订单号 */
    @Excel(name = "交易订单号")
    @ApiModelProperty("交易订单号")
    private String outTradeNo;

    /** 支付订单号 */
    @Excel(name = "支付订单号")
    @ApiModelProperty("支付订单号")
    private String transactionId;

    /** 商品标题 */
    @Excel(name = "商品标题")
    @ApiModelProperty("商品标题")
    private String subject;

    /** 商品描述 */
    @Excel(name = "商品描述")
    @ApiModelProperty("商品描述")
    private String body;

    /** 支付方式（0微信，1余额） */
    @Excel(name = "支付方式", readConverterExp = "0=微信，1余额")
    @ApiModelProperty("支付方式")
    private String payWay;

    /** 支付金额 */
    @Excel(name = "支付金额")
    @ApiModelProperty("支付金额")
    private BigDecimal amount;

    /** 支付状态 */
    @Excel(name = "支付状态")
    @ApiModelProperty("支付状态")
    private String payState;

    /** 用户ip */
    @Excel(name = "用户ip")
    @ApiModelProperty("用户ip")
    private String userIp;

    /** 订单失效时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "订单失效时间", width = 30, dateFormat = "yyyy-MM-dd")
    @ApiModelProperty("订单失效时间")
    private Date expireTime;

    /** 回调数据 */
    @Excel(name = "回调数据")
    @ApiModelProperty("回调数据")
    private String channelNotifyData;

    /** 支付成功时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "支付成功时间", width = 30, dateFormat = "yyyy-MM-dd")
    @ApiModelProperty("支付成功时间")
    private Date successTime;


}
