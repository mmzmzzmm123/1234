package com.ruoyi.system.openapi.model.input;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class ThirdTgJoinUserInputDTO {

    @ApiModelProperty(value = "tg机器人编号", required = true)
    private String tgRobotId;

    @ApiModelProperty(value = "好友编号")
    private String friendSerialNo;

    @ApiModelProperty(value = "商家透传字段", required = false)
    private String extend;
}
