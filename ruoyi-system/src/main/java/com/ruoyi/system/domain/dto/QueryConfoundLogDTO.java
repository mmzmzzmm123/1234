package com.ruoyi.system.domain.dto;

import com.ruoyi.system.domain.base.PageBaseDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @Author : XGF（徐桂烽）
 * @create 2024/1/9/009 15:40
 * @Description :
 */
@Data
@ApiModel("查询混淆日志DTO")
public class QueryConfoundLogDTO extends PageBaseDTO {

    @ApiModelProperty("剧本ID")
    @NotBlank
    private String playId;

    @ApiModelProperty("开始时间")
    private String startTime;

    @ApiModelProperty("结束时间")
    private String endTime;

    @ApiModelProperty("执行结果 0-待混淆 1-成功 2-失败 3-重试中")
    private Integer state;

    @ApiModelProperty("混淆类型 2001-文案 2002-图片 2005-落地页")
    private Integer momentTypeId;

    @ApiModelProperty("原文案内容")
    private String confoundContent;

}
