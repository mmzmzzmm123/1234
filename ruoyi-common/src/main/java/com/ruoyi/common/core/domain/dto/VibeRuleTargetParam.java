package com.ruoyi.common.core.domain.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class VibeRuleTargetParam {
    @ApiModelProperty(value = "内容目标国编码")
    private String countryCode;

    @ApiModelProperty(value = "内容目标国名称")
    private String countryName;

    @ApiModelProperty(value = "优选群主号和水军国编码")
    private List<String> preferenceCodes;

    @ApiModelProperty(value = "优选群主号和水军国名称")
    private List<String> preferenceNames;
}