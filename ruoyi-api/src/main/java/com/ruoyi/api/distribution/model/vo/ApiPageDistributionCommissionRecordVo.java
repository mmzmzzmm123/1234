package com.ruoyi.api.distribution.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;

/**
 * @author LAM
 * @date 2023/11/6 12:15
 */
@ApiModel("api-分页分销佣金记录")
@Data
@Accessors(chain = true)
public class ApiPageDistributionCommissionRecordVo implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("分销佣金记录集合")
    private List<ApiDistributionCommissionRecordVo> data;

    @ApiModelProperty("总数")
    private long total;
}
