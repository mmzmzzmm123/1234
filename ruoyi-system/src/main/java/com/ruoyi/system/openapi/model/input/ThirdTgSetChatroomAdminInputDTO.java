package com.ruoyi.system.openapi.model.input;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class ThirdTgSetChatroomAdminInputDTO {

    @ApiModelProperty(value = "tg机器人编号", required = true)
    private String tgRobotId;

    @ApiModelProperty(value = "商家透传字段", required = false)
    private String extend;

    @ApiModelProperty("群编号")
    private String chatroomSerialNo;
    @ApiModelProperty("成员编号")
    private String memberSerialNo;
    @ApiModelProperty("是否全部")
    private Boolean isAll;
    @ApiModelProperty("修改群组信息")
    private Boolean changeInfo;
    @ApiModelProperty("删除消息")
    private Boolean deleteMessages;
    @ApiModelProperty("封禁该用户")
    private Boolean banUsers;
    @ApiModelProperty("生成邀请链接")
    private Boolean inviteUsers;
    @ApiModelProperty("置顶消息")
    private Boolean pinMessages;
    @ApiModelProperty("管理视频通话")
    private Boolean manageCall;
    @ApiModelProperty("保持匿名")
    private Boolean anonymous;
    @ApiModelProperty("添加新管理员")
    private Boolean addAdmins;
    @ApiModelProperty("成员hash key")
    private String memberUserAccessHash;


    private String groupId;
}
