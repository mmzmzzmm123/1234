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
 * 筛查任务表(ScreeningTask)表实体类
 *
 * @author 朱少波
 * @since 2024-01-25 11:07:15
 */
@ApiModel(value = "筛查任务表")
@TableName("t_screening_task")
@Data
public class ScreeningTask {
    
    /**
    * 任务id
    */
    @ApiModelProperty(value = "任务id")
    @TableId
    private String taskId;
    
    /**
    * 任务名称
    */
    @ApiModelProperty(value = "任务名称")
    @TableField("task_name")
    private String taskName;
    
    /**
    * 任务状态：  0-待检查 1-检查中 3-已暂停 4-已取消 5-已完成 
    */
    @ApiModelProperty(value = "任务状态：  0-待检查 1-检查中 3-已暂停 4-已取消 5-已完成 ")
    @TableField("task_state")
    private Integer taskState;
    
    /**
    * 批次数
    */
    @ApiModelProperty(value = "批次数")
    @TableField("batch_count")
    private Integer batchCount;
    
    /**
    * 创建时间
    */
    @ApiModelProperty(value = "创建时间")
    @TableField("create_time")
    private LocalDateTime createTime;
    
    /**
    * 更新时间
    */
    @ApiModelProperty(value = "更新时间")
    @TableField("update_time")
    private LocalDateTime updateTime;
    
    /**
    * 完成时间
    */
    @ApiModelProperty(value = "完成时间")
    @TableField("completion_time")
    private LocalDateTime completionTime;
    
    /**
    * 商家id
    */
    @ApiModelProperty(value = "商家id")
    @TableField("merchant_id")
    private String merchantId;
    
    /**
    * 用户id
    */
    @ApiModelProperty(value = "用户id")
    @TableField("user_id")
    private Long userId;
    
    /**
    * 商品id
    */
    @ApiModelProperty(value = "商品id")
    @TableField("product_id")
    private Long productId;
    
    /**
    * 商品价格
    */
    @ApiModelProperty(value = "商品价格")
    @TableField("price")
    private Long price;

    @ApiModelProperty(value = "国家代码")
    @TableField("country_code")
    private String countryCode;
}
