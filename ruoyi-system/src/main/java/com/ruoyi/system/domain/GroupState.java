package com.ruoyi.system.domain;

import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 群状态信息(GroupState)表实体类
 *
 * @author 朱少波
 * @since 2024-01-15 09:32:55
 */
@ApiModel(value = "群状态信息")
@TableName("t_group_state")
@Data
public class GroupState {

    /**
     * 群Id
     */
    @ApiModelProperty(value = "群Id")
    @TableId
    private String groupId;

    /**
     * 是否使用 0-未使用 1-已使用
     */
    @ApiModelProperty(value = "是否使用 0-未使用 1-已使用")
    @TableField("group_used")
    private Integer groupUsed;

    /**
     * 群状态 0-正常 1-封群
     */
    @ApiModelProperty(value = "群状态 0-正常 1-封群")
    @TableField("group_status")
    private Integer groupStatus;

    /**
     * 最后一次使用时间
     */
    @ApiModelProperty(value = "最后一次使用时间")
    @TableField("used_time")
    private LocalDateTime usedTime;

    /**
     * 群主是否机器人 0-否 1-是
     */
    @ApiModelProperty(value = "群主是否机器人 0-否 1-是")
    @TableField("leader_robot")
    private Integer leaderRobot;
    
}
