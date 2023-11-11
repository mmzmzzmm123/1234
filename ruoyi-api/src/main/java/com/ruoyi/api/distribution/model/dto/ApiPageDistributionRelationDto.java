package com.ruoyi.api.distribution.model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @author LAM
 * @date 2023/11/4 20:05
 */
@ApiModel("api-分页分销关系dto")
@Data
@Accessors(chain = true)
public class ApiPageDistributionRelationDto implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("等级")
    private Long level;
}
