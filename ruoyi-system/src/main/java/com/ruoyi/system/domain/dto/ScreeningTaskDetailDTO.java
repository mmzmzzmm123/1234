package com.ruoyi.system.domain.dto;

import com.alibaba.fastjson2.annotation.JSONField;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.system.domain.base.PageBaseDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @author 朱少波
 * @date 2024/1/25/025 10:31
 */
@Data
@ApiModel("筛选任务明细信息DTO")
public class ScreeningTaskDetailDTO extends PageBaseDTO {

    @ApiModelProperty(value = "任务Id")
    private String taskId;

    @ApiModelProperty(value = "批次id")
    private String batchId;

    @ApiModelProperty(value = "国家")
    private String country;

    @ApiModelProperty(value = "手机号")
    private String target;

    @ApiModelProperty(value = "检查结果  0-待检测 1-有效 2-无效")
    private Integer result;

}
