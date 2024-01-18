package com.ruoyi.system.domain.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author 朱少波
 * @date 2024/1/15/015 9:48
 */
@Data
@ApiModel("群分组信息VO")
public class GroupClusterVO {


    /**
     * 分组Id
     */
    @ApiModelProperty(value = "分组Id")
    private String clusterId;

    /**
     * 分组名
     */
    @ApiModelProperty(value = "分组名")
    private String clusterName;


    @ApiModelProperty(value = "分组类型 0-普通群 1-默认群")
    private Integer clusterType;
}
