package com.ruoyi.system.domain.vo;

import com.alibaba.fastjson2.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.TableField;
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
@ApiModel("筛选任务明细信息VO")
public class ScreeningTaskDetailVO {

    @ApiModelProperty(value = "批次数")
    private Integer batchCount;

    @Excel(name = "批次")
    @ApiModelProperty(value = "批次名")
    private String batchName;

    public String getBatchName() {
        return batchCount == null ? "" : String.format("第%s批次", batchCount);
    }

    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "检查时间", dateFormat = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "检查时间")
    private LocalDateTime callbackTime;

    @Excel(name = "国家")
    @ApiModelProperty(value = "国家")
    private String country;

    @Excel(name = "手机号")
    @ApiModelProperty(value = "手机号")
    private String target;

    @Excel(name = "检查结果", readConverterExp = "0=待检测,1=有效,2=无效")
    @ApiModelProperty(value = "检查结果  0-待检测 1-有效 2-无效")
    private Integer result;

    @Excel(name = "费用")
    @ApiModelProperty(value = "费用")
    private BigDecimal price;
}
