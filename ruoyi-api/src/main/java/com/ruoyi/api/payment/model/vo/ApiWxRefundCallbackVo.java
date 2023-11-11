package com.ruoyi.api.payment.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @author LAM
 * @date 2023/5/11 14:23
 */
@Data
@Accessors(chain = true)
@ApiModel("微信退款回调数据")
public class ApiWxRefundCallbackVo implements Serializable {

    private static final Long serialVersionUID = 1L;

    @ApiModelProperty("商户交易订单号")
    private String outTradeNo;

    @ApiModelProperty("商户退款单号")
    private String refundId;

    @ApiModelProperty("退款状态(SUCCESS：退款成功，CLOSED：退款关闭，ABNORMAL：退款异常，退款到银行发现用户的卡作废或者冻结了，导致原路退款银行卡失败，可前往【商户平台—>交易中心】，手动处理此笔退款)")
    private String refundStatus;

    @ApiModelProperty("返回数据")
    private String result;
}
