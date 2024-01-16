package com.ruoyi.system.domain.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author 朱少波
 * @date 2024/1/15/015 15:18
 */
@Data
@ApiModel("群内管理员设置DTO")
public class GroupAdminSetDTO {

    @ApiModelProperty(value = "名称")
    private String groupId;

    @ApiModelProperty(value = "成员编号集合")
    private List<String> memberIds;


    @ApiModelProperty("是否全部")
    private Boolean isAll = true;
    @ApiModelProperty("修改群组信息")
    private Boolean changeInfo = true;
    @ApiModelProperty("删除消息")
    private Boolean deleteMessages = true;
    @ApiModelProperty("封禁该用户")
    private Boolean banUsers = true;
    @ApiModelProperty("生成邀请链接")
    private Boolean inviteUsers = true;
    @ApiModelProperty("置顶消息")
    private Boolean pinMessages = true;
    @ApiModelProperty("管理视频通话")
    private Boolean manageCall = true;
    @ApiModelProperty("保持匿名")
    private Boolean anonymous = true;
    @ApiModelProperty("添加新管理员")
    private Boolean addAdmins = true;
}
