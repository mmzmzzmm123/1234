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
 * 群操作记录表(GroupActionLog)表实体类
 *
 * @author 朱少波
 * @since 2024-01-16 10:25:53
 */
@ApiModel(value = "群操作记录表")
@TableName("t_group_action_log")
@Data
public class GroupActionLog {

    /**
     * id
     */
    @ApiModelProperty(value = "id")
    @TableId
    private String id;

    /**
     * 操作编号
     */
    @ApiModelProperty(value = "操作编号")
    @TableField("opt_no")
    private String optNo;

    /**
     * 0-设置头像 1-设置群名 2-设置群类型 3-搜索bot 4-添加bot 5-邀请bot进入群聊 6-设置管理员
     */
    @ApiModelProperty(value = "0-设置头像 1-设置群名 2-设置群类型 3-搜索bot 4-添加bot 5-邀请bot进入群聊 6-设置管理员")
    @TableField("set_type")
    private Integer setType;

    /**
     * 群Id
     */
    @ApiModelProperty(value = "群Id")
    @TableField("group_id")
    private String groupId;


    /**
     * 机器人id
     */
    @ApiModelProperty(value = "机器人id")
    @TableField("robot_id")
    private String robotId;

    /**
     * 批次id
     */
    @ApiModelProperty(value = "批次id")
    @TableField("batch_id")
    private String batchId;

    /**
     * 参数
     */
    @ApiModelProperty(value = "参数")
    @TableField("para")
    private String para;

    /**
     * 成功后需要修改的值
     */
    @ApiModelProperty(value = "成功后需要修改的值")
    @TableField("change_value")
    private String changeValue;

    /**
     * 设置状态 0-请求开平中 1-请求失败 2-请求成功
     */
    @ApiModelProperty(value = "设置状态 0-请求开平中 1-请求失败 2-请求成功")
    @TableField("set_status")
    private Integer setStatus;

    /**
     * 成功后需要修改的值
     */
    @ApiModelProperty(value = "成功后需要修改的值")
    @TableField("fail_msg")
    private String failMsg;

    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间")
    @TableField("create_time")
    private LocalDateTime createTime;
    
}
