package com.ruoyi.system.callback.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class Called50005005DTO {

    /**
     * {"beforeBelongsMerchantId":"1699696048446873600","afterBelongsMerchantId":"1699696048446873600","robotSerialNo":"720168EDA3EE6EBBB87E36A85E3951F3"}
     */
    @ApiModelProperty(value = "变更前商家Id")
    private String beforeBelongsMerchantId;

    @ApiModelProperty(value = "变更后商家id")
    private String afterBelongsMerchantId;

    @ApiModelProperty(value = "机器人编号")
    private String robotSerialNo;

    @ApiModelProperty(value = "操作类型：未知-1 0登录回调 1=购买 2=划分")
    private Integer optType = -1;
    @ApiModelProperty(value = "登录回调商家透传字段")
    private String extend;
}
