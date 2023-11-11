package com.ruoyi.common.weixin.model.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @author LAM
 * @date 2023/4/21 23:19
 */
@Data
@Accessors(chain = true)
public class WxPayResultVo implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("应用标识")
    private String appId;

    @ApiModelProperty("随机字符串")
    private String nonceStr;

    @ApiModelProperty("微信生成的预支付回话标识，用于后续接口调用中使用，该值有效期为2小时")
    private String prepayId;

    @ApiModelProperty("加密类型")
    private String signType;

    @ApiModelProperty("签名")
    private String paySign;

    @ApiModelProperty("时间戳")
    private String timeStamp;

    @ApiModelProperty("api密钥")
    private String mchKey;
}
