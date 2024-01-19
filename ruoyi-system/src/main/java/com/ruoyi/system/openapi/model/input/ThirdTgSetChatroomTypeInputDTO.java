package com.ruoyi.system.openapi.model.input;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class ThirdTgSetChatroomTypeInputDTO {

    @ApiModelProperty(value = "tg机器人编号", required = true)
    private String tgRobotId;

    @ApiModelProperty(value = "商家透传字段", required = false)
    private String extend;

    @ApiModelProperty(value = "群编号", required = true)
    private String chatroomSerialNo;

    @ApiModelProperty(value = "私有群聊：传空不改变群类型 公开群聊：（确认唯一后再使用) 字符长度在5-32之间")
    private String username;

    @ApiModelProperty(value = "群类型：10 私密 20公开")
    private Integer chatroomType;

    private String groupId;
}
