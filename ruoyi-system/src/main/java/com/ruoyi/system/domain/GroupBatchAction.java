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
 * 群批量操作记录表(GroupBatchAction)表实体类
 *
 * @author 朱少波
 * @since 2024-01-16 10:26:08
 */
@ApiModel(value = "群批量操作记录表")
@TableName("t_group_batch_action")
@Data
public class GroupBatchAction {
    
    /**
    * 批次id
    */
    @ApiModelProperty(value = "批次id")
    @TableId
    private String batchId;
    
    /**
    * 0-邀请bot进群检查 
    */
    @ApiModelProperty(value = "0-邀请bot进群检查 ")
    @TableField("set_type")
    private Integer setType;
    
    /**
    * 群Id
    */
    @ApiModelProperty(value = "群Id")
    @TableField("group_id")
    private String groupId;
    
    /**
    * 设置状态 0-设置中 1-失败 2-成功
    */
    @ApiModelProperty(value = "设置状态 0-设置中 1-失败 2-成功")
    @TableField("batch_status")
    private Integer batchStatus;
    
    /**
    * 创建时间
    */
    @ApiModelProperty(value = "创建时间")
    @TableField("create_time")
    private LocalDateTime createTime;
    
}
