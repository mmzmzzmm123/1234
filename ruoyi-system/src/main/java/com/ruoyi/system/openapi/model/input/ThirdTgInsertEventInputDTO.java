package com.ruoyi.system.openapi.model.input;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class ThirdTgInsertEventInputDTO {

    @ApiModelProperty("活动页面")
    private String activityRule;
    @ApiModelProperty("域名分组传10")
    private Integer domainGroupTypeId;
    @ApiModelProperty("商家编号")
    private String unioUserNo;
    @ApiModelProperty("10：跳转落地URL 20：直接跳转原始URL")
    private Integer redirectWay;
}
