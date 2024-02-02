package com.ruoyi.common.core.domain.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class VibeRuleTargetParam {
    @ApiModelProperty(value = "内容目标国是否为all: 0否 1是")
    private Integer allState = 0;

    @ApiModelProperty(value = "内容目标国编码")
    private String countryCode;

    @ApiModelProperty(value = "内容目标国名称")
    private String countryName;

    @ApiModelProperty(value = "优选群主号和水军国编码")
    private List<String> preferenceCodes;

    @ApiModelProperty(value = "优选群主号和水军国名称")
    private List<String> preferenceNames;

    @ApiModelProperty(value = "是否必要,0否,1是")
    private Integer required = 0;
}