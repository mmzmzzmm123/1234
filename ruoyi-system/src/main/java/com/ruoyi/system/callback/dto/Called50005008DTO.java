package com.ruoyi.system.callback.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class Called50005008DTO {

    @ApiModelProperty(value = "变更前商家Id")
    private String beforeBelongsMerchantId;

    @ApiModelProperty(value = "变更后商家id")
    private String afterBelongsMerchantId;

    @ApiModelProperty(value = "机器人编号")
    private String robotSerialNo;

}
