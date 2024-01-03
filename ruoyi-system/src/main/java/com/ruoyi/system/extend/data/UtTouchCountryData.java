package com.ruoyi.system.extend.data;

import io.swagger.annotations.ApiModelProperty;

@lombok.Data
public class UtTouchCountryData {

    @ApiModelProperty("国家代码")
    private String countryCode;

    @ApiModelProperty("国家名称")
    private String countryName;

    public UtTouchCountryData() {}

    public UtTouchCountryData(String countryCode, String countryName) {
        this.countryCode = countryCode;
        this.countryName = countryName;
    }

    public UtTouchCountryData(String countryCode) {
        this.countryCode = countryCode;
    }
}
