package com.ruoyi.system.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 群bot监控返回信息(GroupMonitorInfo)表实体类
 *
 * @author 朱少波
 * @since 2024-01-15 13:51:31
 */
@ApiModel(value = "群bot监控返回信息")
@TableName("t_group_monitor_info")
@Data
public class GroupMonitorInfo {

    /**
     * 群Id
     */
    @ApiModelProperty(value = "群Id")
    @TableId
    private String groupId;

    /**
     * 检测bot id
     */
    @ApiModelProperty(value = "检测bot id")
    @TableField("bot_id")
    private String botId;

    /**
     * 检测bot 用户名
     */
    @ApiModelProperty(value = "检测bot 用户名")
    @TableField("bot_username")
    private String botUsername;

    /**
     * 检测bot 机器人serial_no 开平编号
     */
    @ApiModelProperty(value = "检测bot 机器人serial_no 开平编号")
    @TableField("bot_serial_no")
    private String botSerialNo;

    /**
     * 是否有bot进群检查 0-否 1-有
     */
    @ApiModelProperty(value = "是否有bot进群检查 0-否 1-有")
    @TableField("bot_check")
    private Integer botCheck;

    @ApiModelProperty(value = "bot是否是admin 0-否 1-有")
    @TableField("bot_admin")
    private Integer botAdmin;


    /**
     * 邀请bot入群的机器人id
     */
    @ApiModelProperty(value = "邀请bot入群的机器人id")
    @TableField("robot_id")
    private String robotId;

    /**
     * 群配置的剧本id
     */
    @ApiModelProperty(value = "群配置的剧本id")
    @TableField("bot_play_id")
    private String botPlayId;

    /**
     * 是否设置广告监控 0-否 1-有
     */
    @ApiModelProperty(value = "是否设置广告监控 0-否 1-有")
    @TableField("bot_ad_monitor")
    private Integer botAdMonitor;

    /**
     * 原始官方群Id
     */
    @ApiModelProperty(value = "原始官方群Id")
    @TableField("original_group_id")
    private String originalGroupId;

    /**
     * 群成员数
     */
    @ApiModelProperty(value = "群成员数")
    @TableField("member_count")
    private Integer memberCount;

    /**
     * 进群人数
     */
    @ApiModelProperty(value = "进群人数")
    @TableField("join_count")
    private Integer joinCount;

    /**
     * 退群人数
     */
    @ApiModelProperty(value = "退群人数")
    @TableField("exit_count")
    private Integer exitCount;

    /**
     * 链接进群人数
     */
    @ApiModelProperty(value = "链接进群人数")
    @TableField("link_join_count")
    private Integer linkJoinCount;

    /**
     * 群状态 0-正常 1-封群
     */
    @ApiModelProperty(value = "群状态 0-正常 1-封群")
    @TableField("group_status")
    private Integer groupStatus;
}
