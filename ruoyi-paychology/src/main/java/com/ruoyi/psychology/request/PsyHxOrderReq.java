package com.ruoyi.psychology.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * *
 * @author ruoyi
 * @date 2023-06-26
 */
@Data
public class PsyHxOrderReq implements Serializable
{

    private static final long serialVersionUID = 2762732422972095683L;

    @ApiModelProperty(value = "核销时间")
    private List<String> times;

    @ApiModelProperty(value = "订单id")
    private Long id;

}
