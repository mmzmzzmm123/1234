package com.ruoyi.system.openapi.model.input;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class ThirdTgInviteJoinChatroomInputDTO {

    @ApiModelProperty("成员编号（成员编号集合与成员编号必传其一）")
    private String memberSerialNo;
    @ApiModelProperty("群组编号")
    private String chatroomSerialNo;
    @ApiModelProperty("成员编号集合（成员编号集合与成员编号必传其一）")
    private List<String> memberSerialNoList;

    @ApiModelProperty(value = "tg机器人编号", required = true)
    private String tgRobotId;

    @ApiModelProperty(value = "商家透传字段", required = false)
    private String extend;
}
