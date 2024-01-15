package com.ruoyi.common.openapi.model.input;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class ThirdTgExitChatroomInputDTO {
    @ApiModelProperty(value = "tg机器人编号", required = true)
    private String tgRobotId;

    @ApiModelProperty(value = "商家透传字段", required = false)
    private String extend;

    @ApiModelProperty("群组编号")
    private String chatroomSerialNo;
}
