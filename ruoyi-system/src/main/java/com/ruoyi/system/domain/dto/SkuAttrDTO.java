package com.ruoyi.system.domain.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class SkuAttrDTO {
    private Long skuId;

    private String pic;

    @ApiModelProperty(value = "价格")
    private Long price;

    @ApiModelProperty(value = "价格单位")
    private String priceUnit;

    @ApiModelProperty(value = "支撑量")
    private Integer stock;

    @ApiModelProperty(value = "国家id")
    private Integer countyId;

    @ApiModelProperty(value = "国家名")
    private String countyName;
}
