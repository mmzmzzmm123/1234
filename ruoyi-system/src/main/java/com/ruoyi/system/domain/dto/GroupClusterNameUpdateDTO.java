package com.ruoyi.system.domain.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author 朱少波
 * @date 2024/1/15/015 9:51
 */
@Data
@ApiModel("群分组名称修改DTO")
public class GroupClusterNameUpdateDTO extends GroupClusterNameDTO {
    /**
     * 分组Id
     */
    @ApiModelProperty(value = "分组Id")
    private String clusterId;
}
