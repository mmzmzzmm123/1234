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
 * 筛查任务批次表(ScreeningTaskBatch)表实体类
 *
 * @author 朱少波
 * @since 2024-01-25 11:20:16
 */
@ApiModel(value = "筛查任务批次表")
@TableName("t_screening_task_batch")
@Data
public class ScreeningTaskBatch {
    
    /**
    * 批次id 对应外部接口任务ID
    */
    @ApiModelProperty(value = "批次id 对应外部接口任务ID")
    @TableId
    private String batchId;
    
    /**
    * 批次数
    */
    @ApiModelProperty(value = "批次数")
    @TableField("batch_count")
    private Integer batchCount;
    
    /**
    * 任务id
    */
    @ApiModelProperty(value = "任务id")
    @TableField("task_id")
    private String taskId;
    
    /**
    * 批次任务状态： 0，待进行  1、进行中 2、暂停 3、停止 4、已完成
    */
    @ApiModelProperty(value = "批次任务状态： 0，待进行  1、进行中 2、暂停 3、停止 4、已完成")
    @TableField("batch_state")
    private Integer batchState;
    
    /**
    * 创建时间
    */
    @ApiModelProperty(value = "创建时间")
    @TableField("create_time")
    private LocalDateTime createTime;
    
    /**
    * 订单id
    */
    @ApiModelProperty(value = "订单id")
    @TableField("order_id")
    private String orderId;
    
}
