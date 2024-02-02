package com.ruoyi.system.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 好友机器人表(FriendRobot)表实体类
 *
 */
@ApiModel(value = "好友机器人表")
@TableName("t_friend_robot")
@Data
public class FriendRobot {

    /**
     * 主键ID
     */
    @ApiModelProperty(value = "主键ID")
    @TableId
    private String id;

    /**
     * 好友编号
     */
    @ApiModelProperty(value = "好友编号")
    @TableField("friend_serial_no")
    private String friendSerialNo;

    /**
     * 状态 0-未处理 1-已调用 2-已同步
     */
    @ApiModelProperty(value = "状态 0-未处理 1-已调用 2-已同步")
    @TableField("status")
    private Integer status;

    /**
     * 名字
     */
    @ApiModelProperty(value = "名字")
    @TableField("first_name")
    private String firstName;

    /**
     * 姓氏
     */
    @ApiModelProperty(value = "姓氏")
    @TableField("last_name")
    private String lastName;

    /**
     * 用户号
     */
    @ApiModelProperty(value = "用户号")
    @TableField("username")
    private String username;

    /**
     * 机器人编号
     */
    @ApiModelProperty(value = "机器人编号")
    @TableField("robot_serial_no")
    private String robotSerialNo;

    /**
     * 手机号
     */
    @ApiModelProperty(value = "手机号")
    @TableField("phone")
    private String phone;

    /**
     * 是否bot 0-否 1-是
     */
    @ApiModelProperty(value = "是否bot 0-否 1-是")
    @TableField("is_bot")
    private Integer isBot;

    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间")
    @TableField("create_time")
    private LocalDateTime createTime;

    /**
     * 修改时间
     */
    @ApiModelProperty(value = "修改时间")
    @TableField("update_time")
    private LocalDateTime updateTime;
}
