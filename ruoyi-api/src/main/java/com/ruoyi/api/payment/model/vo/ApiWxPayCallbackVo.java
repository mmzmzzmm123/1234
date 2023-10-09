package com.ruoyi.api.payment.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @author LAM
 * @date 2023/4/22 18:14
 */
@Data
@Accessors(chain = true)
@ApiModel("微信支付成功回调数据")
public class ApiWxPayCallbackVo implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("应用ID")
    private String appid;

    @ApiModelProperty("附加数据")
    private String attach;

    @ApiModelProperty("支付金额（分）")
    private Integer totalFee;

    @ApiModelProperty("商户订单号")
    private String outTradeNo;

    @ApiModelProperty("微信支付订单号")
    private String transactionId;

    @ApiModelProperty("渠道返回数据")
    private String callbackData;
}
