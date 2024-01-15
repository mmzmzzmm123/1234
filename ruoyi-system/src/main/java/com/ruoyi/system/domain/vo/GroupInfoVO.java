package com.ruoyi.system.domain.vo;

import com.alibaba.fastjson2.annotation.JSONField;
import com.ruoyi.common.annotation.Excel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author 朱少波
 * @date 2024/1/15/015 15:54
 */
@Data
@ApiModel("群信息VO")
public class GroupInfoVO {

    @Excel(name = "群名称")
    @ApiModelProperty(value = "群名称")
    private String groupName;

    @ApiModelProperty(value = "群Id")
    private String groupId;

    @ApiModelProperty(value = "群编号 - 开平群编号")
    private String groupSerialNo;

    @ApiModelProperty(value = "群邀请链接")
    private String groupInviteLink;

    @ApiModelProperty(value = "群类型 10私密群 20公开群")
    private Integer groupType;

    @ApiModelProperty(value = "总人数")
    private Integer memberCount;

    @ApiModelProperty(value = "群内机器人")
    private List<GroupRobotVO> robots;

}
