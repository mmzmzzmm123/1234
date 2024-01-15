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
 * 群分组关联表(GroupClusterRef)表实体类
 *
 * @author 朱少波
 * @since 2024-01-15 09:32:03
 */
@ApiModel(value = "群分组关联表")
@TableName("t_group_cluster_ref")
@Data
public class GroupClusterRef {
    
    /**
    * 关联id
    */
    @ApiModelProperty(value = "关联id")
    @TableId
    private String refId;
    
    /**
    * 分组Id
    */
    @ApiModelProperty(value = "分组Id")
    @TableField("cluster_id")
    private String clusterId;
    
    /**
    * 群Id
    */
    @ApiModelProperty(value = "群Id")
    @TableField("group_id")
    private String groupId;
    
    /**
    * 创建时间
    */
    @ApiModelProperty(value = "创建时间")
    @TableField("create_time")
    private LocalDateTime createTime;
    
}
