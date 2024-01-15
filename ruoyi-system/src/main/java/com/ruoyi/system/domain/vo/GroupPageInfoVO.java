package com.ruoyi.system.domain.vo;

import com.alibaba.fastjson2.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.TableField;
import com.ruoyi.common.annotation.Excel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author 朱少波
 * @date 2024/1/15/015 9:51
 */
@Data
@ApiModel("群分页信息VO")
public class GroupPageInfoVO {

    @Excel(name = "群名称")
    @ApiModelProperty(value = "群名称")
    private String groupName;

    @ApiModelProperty(value = "群Id")
    private String groupId;

    @ApiModelProperty(value = "群编号")
    private String groupSerialNo;

    @Excel(name = "群邀请链接")
    @ApiModelProperty(value = "群邀请链接")
    private String groupInviteLink;

    @Excel(name = "bot检测", readConverterExp = "0=无,1=已有bot检测")
    @ApiModelProperty(value = "bot检测 0-无 1-已有bot检测")
    private Integer botCheck;

    @Excel(name = "群状态", readConverterExp = "0=正常,1=封群")
    @ApiModelProperty(value = "群状态 0-正常 1-封群")
    private Integer groupStatus;

    @Excel(name = "是否使用", readConverterExp = "0=未使用,1=已使用")
    @ApiModelProperty(value = "是否使用 0-未使用 1-已使用")
    private Integer groupUsed;


    @Excel(name = "占用水军数")
    @ApiModelProperty(value = "占用水军数")
    private Integer robotNum;

    @Excel(name = "总人数")
    @ApiModelProperty(value = "总人数")
    private Integer memberCount;

    @Excel(name = "分组")
    @ApiModelProperty(value = "分组")
    private String clusterName;

    @Excel(name = "群类型", readConverterExp = "10=私密群,20=公开群")
    @ApiModelProperty(value = "群类型 10私密群 20公开群")
    private Integer groupType;

    @Excel(name = "群主是否机器人", readConverterExp = "0=否,1=是")
    @ApiModelProperty(value = "群主是否机器人 0-否 1-是")
    private Integer leaderRobot;

    @Excel(name = "所属机器人-名字")
    @ApiModelProperty(value = "所属机器人-名字")
    private String fullName;

    @Excel(name = "所属机器人-手机号")
    @ApiModelProperty(value = "所属机器人-手机号")
    private String phone;

    @Excel(name = "所属机器人-编号")
    @ApiModelProperty(value = "所属机器人-编号")
    private String robotSerialNo;

    @Excel(name = "在线状态", readConverterExp = "10=离线,20=在线")
    @ApiModelProperty(value = "在线状态 20 在线 10 离线")
    private Integer heartbeatStatus;

    @Excel(name = "封号状态", readConverterExp = "10=未封号,20=疑似封号 30=封号离线")
    @ApiModelProperty(value = "封号状态 10:未封号 20:疑似封号 30:封号离线")
    private Integer sealStatus;

    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "入库时间", dateFormat = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "入库时间")
    private LocalDateTime createTime;

    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "最后一次使用时间", dateFormat = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "最后一次使用时间")
    private LocalDateTime usedTime;

}
