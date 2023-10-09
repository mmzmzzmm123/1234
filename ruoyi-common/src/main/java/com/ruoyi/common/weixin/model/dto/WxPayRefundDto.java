package com.ruoyi.common.weixin.model.dto;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @author LAM
 * @date 2023/5/11 0:42
 */
@Data
@Accessors(chain = true)
public class WxPayRefundDto implements Serializable {

    private final static Long serialVersionUID = 1L;

    /**
     * <pre>
     * 字段名：微信订单号.
     * 变量名：transaction_id
     * 是否必填：跟out_trade_no二选一
     * 类型：String(28)
     * 示例值：1217752501201400000000000000
     * 描述：微信生成的订单号，在支付通知中有返回
     * </pre>
     */
    private String transactionId;

    /**
     * <pre>
     * 字段名：商户退款单号.
     * 变量名：out_refund_no
     * 是否必填：是
     * 类型：String(32)
     * 示例值：1217752501201400000000000000
     * 描述：商户系统内部的退款单号，商户系统内部唯一，同一退款单号多次请求只退一笔
     * </pre>
     */
    private String outRefundNo;

    /**
     * <pre>
     * 字段名：订单金额.
     * 变量名：total_fee
     * 是否必填：是
     * 类型：Int
     * 示例值：100
     * 描述：订单总金额，单位为分，只能为整数，详见支付金额
     * </pre>
     */
    private Integer totalFee;

    /**
     * <pre>
     * 字段名：退款金额.
     * 变量名：refund_fee
     * 是否必填：是
     * 类型：Int
     * 示例值：100
     * 描述：退款总金额，订单总金额，单位为分，只能为整数，详见支付金额
     * </pre>
     */
    private Integer refundFee;

    /**
     * <pre>
     * 字段名：退款原因.
     * 变量名：refund_account
     * 是否必填：否
     * 类型：String(80)
     * 示例值：商品已售完
     * 描述：若商户传入，会在下发给用户的退款消息中体现退款原因
     * </pre>
     */
    private String refundDesc;
}
