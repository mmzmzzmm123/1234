package com.ruoyi.psychology.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * *
 * @author ruoyi
 * @date 2023-06-26
 */
@Data
public class PsyRefOrderReq implements Serializable
{

    private static final long serialVersionUID = 7606216068627604082L;

    @ApiModelProperty(value = "订单id")
    private Long orderId;

    @ApiModelProperty(value = "咨询师id")
    private Long consultId;

    @ApiModelProperty(value = "原因")
    private String reason;

}
