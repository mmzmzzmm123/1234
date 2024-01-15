package com.ruoyi.system.domain.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author 朱少波
 * @date 2024/1/15/015 15:54
 */
@Data
@ApiModel("群内机器人信息VO")
public class GroupRobotVO {
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
