package com.ruoyi.system.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 群内机器人信息(GroupRobot)表实体类
 *
 * @author 朱少波
 * @since 2024-01-15 09:32:38
 */
@ApiModel(value = "群内机器人信息")
@TableName("t_group_robot")
@Data
public class GroupRobot {

    /**
     * id
     */
    @ApiModelProperty(value = "id")
    @TableId
    private String id;

    /**
     * 群Id
     */
    @ApiModelProperty(value = "群Id")
    @TableField("group_id")
    private String groupId;

    /**
     * 机器人Id
     */
    @ApiModelProperty(value = "机器人Id")
    @TableField("robot_id")
    private String robotId;

    /**
     * 是否是bot 0-否 1-是
     */
    @ApiModelProperty(value = "是否是bot 0-否 1-是")
    @TableField("bot_type")
    private Integer botType;

    /**
     * 成员身份 0普通 1群主 2管理员
     */
    @ApiModelProperty(value = "成员身份 0普通 1群主 2管理员")
    @TableField("member_type")
    private Integer memberType;


}
