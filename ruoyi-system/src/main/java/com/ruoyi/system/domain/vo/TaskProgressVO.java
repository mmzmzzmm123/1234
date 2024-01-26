package com.ruoyi.system.domain.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * @author 朱少波
 * @date 2024/1/25/025 11:10
 */
@Data
@ApiModel("筛选任务进度VO")
public class TaskProgressVO {
    @ApiModelProperty(value = "任务ID")
    private String taskId;


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
    private BigDecimal price;

    public void setPrice(BigDecimal price) {
        if (price == null) {
            this.price = BigDecimal.ZERO;
            return;
        }
        this.price = price.divide(BigDecimal.valueOf(100L), 2, RoundingMode.HALF_UP);
    }

    @ApiModelProperty(value = "资金消耗")
    private BigDecimal totalPrice;


    public TaskProgressVO calculateRatio() {
        if (price == null || screeningCount == null) {
            totalPrice = BigDecimal.ZERO;
        } else {
            totalPrice = price.multiply(BigDecimal.valueOf(screeningCount));
        }

        if (validCount == null || screeningCount == null || validCount <= 0 || screeningCount <= 0) {
            validRadio = BigDecimal.ZERO;
        } else {
            validRadio = BigDecimal.valueOf(validCount).multiply(BigDecimal.valueOf(100)).divide(BigDecimal.valueOf(screeningCount), 2, RoundingMode.HALF_UP);
        }

        if (targetCount == null || screeningCount == null || targetCount <= 0 || screeningCount <= 0) {
            screeningRadio = BigDecimal.ZERO;
        } else {
            screeningRadio = BigDecimal.valueOf(screeningCount).multiply(BigDecimal.valueOf(100)).divide(BigDecimal.valueOf(targetCount), 2, RoundingMode.HALF_UP);
        }
        return this;
    }

}
