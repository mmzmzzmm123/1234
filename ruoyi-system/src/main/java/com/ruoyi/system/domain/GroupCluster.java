package com.ruoyi.system.domain;

import java.time.LocalDateTime;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 群分组信息(GroupCluster)表实体类
 *
 * @author 朱少波
 * @since 2024-01-15 09:31:46
 */
@ApiModel(value = "群分组信息")
@TableName("t_group_cluster")
@Data
public class GroupCluster {
    
    /**
    * 分组Id
    */
    @ApiModelProperty(value = "分组Id")
    @TableId
    private String clusterId;
    
    /**
    * 分组名
    */
    @ApiModelProperty(value = "分组名")
    @TableField("cluster_name")
    private String clusterName;
    
    /**
    * 分组类型 0-普通群 1-默认群
    */
    @ApiModelProperty(value = "分组类型 0-普通群 1-默认群")
    @TableField("cluster_type")
    private Integer clusterType;
    
    /**
    * 商家id
    */
    @ApiModelProperty(value = "商家id")
    @TableField("merchant_id")
    private String merchantId;
    
    /**
    * 创建时间
    */
    @ApiModelProperty(value = "创建时间")
    @TableField(value = "create_time",updateStrategy = FieldStrategy.NEVER)
    private LocalDateTime createTime;
    
    /**
    * 更新时间
    */
    @ApiModelProperty(value = "更新时间")
    @TableField("update_time")
    private LocalDateTime updateTime;
    
}
