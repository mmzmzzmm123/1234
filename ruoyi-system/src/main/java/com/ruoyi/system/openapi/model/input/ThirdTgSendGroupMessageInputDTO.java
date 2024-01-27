package com.ruoyi.system.openapi.model.input;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class ThirdTgSendGroupMessageInputDTO {

    @ApiModelProperty(value = "tg机器人编号", required = true)
    private String tgRobotId;

    @ApiModelProperty("消息数据")
    private List<ThirdTgMessageDTO> messageData;

    @ApiModelProperty("群聊编码")
    private String chatroomSerialNo;

    @ApiModelProperty(value = "商家透传字段", required = false)
    private String extend;

    private String extendInfo;

    private String firstOptSerialNo;

}
