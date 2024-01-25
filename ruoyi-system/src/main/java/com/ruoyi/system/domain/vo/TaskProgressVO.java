package com.ruoyi.system.domain.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @author 朱少波
 * @date 2024/1/25/025 11:10
 */
@Data
@ApiModel("筛选任务进度VO")
public class TaskProgressVO {

    @ApiModelProperty(value = "号码总数")
    private Long targetCount;

    @ApiModelProperty(value = "筛查总数")
    private Long screeningCount;

    @ApiModelProperty(value = "进度")
    private BigDecimal screeningRadio;

    @ApiModelProperty(value = "有效号码数")
    private Long validCount;

    @ApiModelProperty(value = "有效率")
    private BigDecimal validRadio;

    @ApiModelProperty(value = "单价")
    private Long price;

    @ApiModelProperty(value = "资金消耗")
    private Long totalPrice;

}
