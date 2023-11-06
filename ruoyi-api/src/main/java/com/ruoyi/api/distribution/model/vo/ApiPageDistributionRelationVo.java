package com.ruoyi.api.distribution.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;

/**
 * @author LAM
 * @date 2023/11/4 20:14
 */
@ApiModel("api-分页分销关系")
@Data
@Accessors(chain = true)
public class ApiPageDistributionRelationVo implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("分销关系集合")
    private List<ApiDistributionRelationVo> data;

    @ApiModelProperty("总数")
    private long total;
}
