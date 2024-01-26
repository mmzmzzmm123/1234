package com.ruoyi.system.domain.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author 朱少波
 * @date 2024/1/25/025 11:28
 */
@Data
@ApiModel("批次信息VO")
public class ScreeningBatchVO {
    @ApiModelProperty(value = "批次id 对应外部接口任务ID")
    private String batchId;

    @ApiModelProperty(value = "批次数")
    private Integer batchCount;

    @ApiModelProperty(value = "批次名")
    private String batchName;
}
