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
 * 筛查任务筛查目标表(ScreeningTaskTarget)表实体类
 *
 * @author 朱少波
 * @since 2024-01-25 10:39:26
 */
@ApiModel(value = "筛查任务筛查目标表")
@TableName("t_screening_task_target")
@Data
public class ScreeningTaskTarget {
    
    /**
    * id
    */
    @ApiModelProperty(value = "id")
    @TableId
    private String id;
    
    /**
    * 目标
    */
    @ApiModelProperty(value = "目标")
    @TableField("target")
    private String target;
    
    /**
    * 批次id
    */
    @ApiModelProperty(value = "批次id")
    @TableField("batch_id")
    private String batchId;
    
    /**
    * 目标状态 0-待检测 1-有效 2-无效
    */
    @ApiModelProperty(value = "目标状态 0-待检测 1-有效 2-无效")
    @TableField("result")
    private Integer result;
    
    /**
    * 用户名
    */
    @ApiModelProperty(value = "用户名")
    @TableField("user_name")
    private String userName;
    
    /**
    * 回调时间
    */
    @ApiModelProperty(value = "回调时间")
    @TableField("callback_time")
    private LocalDateTime callbackTime;
    
}
