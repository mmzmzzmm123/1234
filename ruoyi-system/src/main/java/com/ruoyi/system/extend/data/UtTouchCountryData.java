package com.ruoyi.system.extend.data;

import io.swagger.annotations.ApiModelProperty;

@lombok.Data
public class UtTouchCountryData {

    @ApiModelProperty("国家代码")
    private String countryCode;

    @ApiModelProperty("国家名称")
    private String countryName;
}
