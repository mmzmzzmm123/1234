package com.ruoyi.system.domain.vo;

import com.alibaba.fastjson2.annotation.JSONField;
import com.ruoyi.common.annotation.Excel;
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
@ApiModel("筛选任务明细信息结果VO")
public class ScreeningTaskResultVO {

    @ApiModelProperty(value = "批次数")
    private Integer batchCount;

    @Excel(name = "批次")
    @ApiModelProperty(value = "批次名")
    private String batchName;

    @Excel(name = "手机号")
    @ApiModelProperty(value = "手机号")
    private String target;

    @Excel(name = "检查结果", readConverterExp = "0=待检测,1=有效,2=无效")
    @ApiModelProperty(value = "检查结果  0-待检测 1-有效 2-无效")
    private Integer result;

    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "检查时间", dateFormat = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "检查时间")
    private LocalDateTime callbackTime;



}
