package com.ruoyi.system.domain.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author 朱少波
 * @date 2024/1/15/015 9:51
 */
@Data
@ApiModel("群分组Id DTO")
public class GroupClusterIdDTO {

    @ApiModelProperty(value = "群分组id")
    private String clusterId;
}
