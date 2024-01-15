package com.ruoyi.system.domain.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author 朱少波
 * @date 2024/1/15/015 9:51
 */
@Data
@ApiModel("群分组名称DTO")
public class GroupClusterNameDTO {
    /**
     * 分组名
     */
    @ApiModelProperty(value = "分组名")
    private String clusterName;
}
