package com.ruoyi.system.extend.data;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class GetPoolCountryAreaCountsOutDTO {

    @ApiModelProperty(value = "编号")
    private String countryCode;

    @ApiModelProperty(value = "名称")
    private String countryName;

    @ApiModelProperty(value = "数")
    private Long num;
}
