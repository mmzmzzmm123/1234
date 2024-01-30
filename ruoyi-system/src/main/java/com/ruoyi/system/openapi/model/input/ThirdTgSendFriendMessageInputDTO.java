package com.ruoyi.system.openapi.model.input;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class ThirdTgSendFriendMessageInputDTO {

    @ApiModelProperty(value = "tg机器人编号", required = true)
    private String tgRobotId;

    @ApiModelProperty("消息数据")
    private List<ThirdTgMessageDTO> messageData;

    @ApiModelProperty("好友编号")
    private String friendSerialNo;

    @ApiModelProperty(value = "商家透传字段", required = false)
    private String extend;

    @ApiModelProperty("国家区号，未知传：-1")
    private String countryCode = "-1";

    @ApiModelProperty("额外的信息，可自由定义。文档中标记的level用于标记离散等级（开平改字段兼容）")
    private String extendInfo;
}
