package com.ruoyi.api.payment.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @author LAM
 * @date 2023/4/13 21:57
 */
@Data
@Accessors(chain = true)
@ApiModel(value = "api-微信小程序支付所需参数")
public class ApiOrderPayInfoVo implements Serializable {

    private static final long serialVersionUID = -7966682379048446567L;

    @ApiModelProperty("时间戳")
    private String timeStamp;

    @ApiModelProperty("随机字符串")
    private String nonceStr;

    @ApiModelProperty("订单详情扩展字符串")
    private String packageValue;

    @ApiModelProperty("签名方式")
    private String signType;

    @ApiModelProperty("签名")
    private String paySign;
}
