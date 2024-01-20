package com.ruoyi.system.domain.dto;

import com.alibaba.fastjson2.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.TableField;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.system.domain.base.PageBaseDTO;
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
@ApiModel("群分页查询DTO")
public class GroupPageQueryDTO extends PageBaseDTO {

    @ApiModelProperty(value = "分组Id")
    private String clusterId;

    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "入库时间-开始")
    private String createTimeStart;

    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "入库时间-结束")
    private String createTimeEnd;

    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "最后一次使用时间-开始")
    private String usedTimeStart;

    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "最后一次使用时间-结束")
    private String usedTimeEnd;

    @ApiModelProperty(value = "来源 10-开平导入群  20-外部自建群")
    private Integer createType;

    @ApiModelProperty(value = "群类型 10-私密群 20-公开群")
    private Integer groupType;


    @ApiModelProperty(value = "群主是否机器人 0-否 1-是")
    private Integer leaderRobot;

    @ApiModelProperty(value = "在线状态 20 在线 10 离线")
    private Integer heartbeatStatus;

    @ApiModelProperty(value = "封号状态 10:未封号 20:疑似封号 30:封号离线")
    private Integer sealStatus;

    @ApiModelProperty(value = "是否占用水军 0-否 1-是")
    private Integer robotIn;

    @ApiModelProperty(value = "是否回收 0-否 1-是")
    private Integer recycle;

    @ApiModelProperty(value = "是否使用 0-未使用 1-已使用")
    private Integer groupUsed;

    @ApiModelProperty(value = "是否有bot检测 0-无 1-已有bot检测")
    private Integer botCheck;

    @ApiModelProperty(value = "bot是否是管理员 0-否 1-是")
    private Integer botAdmin;

    @ApiModelProperty(value = "是否黑群主 0-否 1-是")
    private Integer blackLeader;

    @ApiModelProperty(value = "任务编号")
    private String playId;


    @ApiModelProperty(value = "群状态 0-正常 1-封群")
    private Integer groupStatus;

    @ApiModelProperty(value = "封群时间-开始")
    private String groupStatusTimeStart;

    @ApiModelProperty(value = "封群时间-结束")
    private String groupStatusTimeEnd;

    @ApiModelProperty(value = "群名称")
    private String groupName;

    @ApiModelProperty(value = "群Id")
    private String groupId;

    @ApiModelProperty(value = "群编号")
    private String groupSerialNo;

    @ApiModelProperty(value = "老编号")
    private String oldGroupSerialNo;

    @ApiModelProperty(value = "群范围 0-所有  1-超级群 2-普通群")
    private Integer groupRange;

    @ApiModelProperty(value = "群是否已升级 0-否 1-是")
    private Integer groupUpgrade;

    @ApiModelProperty(value = "群编号集合")
    private List<String> groupSerialNos;

    @ApiModelProperty(value = "群邀请链接")
    private String groupInviteLink;

    @ApiModelProperty(value = "所属机器人-名字")
    private String fullName;

    @ApiModelProperty(value = "所属机器人-手机号")
    private String phone;

    @ApiModelProperty(value = "所属机器人-编号")
    private String robotSerialNo;

}
