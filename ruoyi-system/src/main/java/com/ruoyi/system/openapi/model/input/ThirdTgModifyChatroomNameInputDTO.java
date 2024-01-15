package com.ruoyi.system.openapi.model.input;


import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class ThirdTgModifyChatroomNameInputDTO {
    @ApiModelProperty(value = "tg机器人编号", required = true)
    private String tgRobotId;
    @ApiModelProperty(value = "商家透传字段", required = false)
    private String extend;
    @ApiModelProperty("群聊编号")
    private String chatroomSerialNo;
    @ApiModelProperty("修改群聊名称")
    private String chatroomNameBase64;
}
