package com.ruoyi.system.domain.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class ProductSkuDTO {
    private Long id;

    @ApiModelProperty(value = "商品id")
    private Long productId;

    @NotNull(message="价格不能为空")
    @ApiModelProperty(value = "价格")
    private Long price;

    @ApiModelProperty(value = "价格单位")
    private String priceUnit;

    @ApiModelProperty(value = "支持量")
    private Integer stock;

    @NotNull(message="国家id不能为空")
    @ApiModelProperty(value = "国家id")
    private Integer countyId;

    @NotBlank(message="国家名不能为空")
    @ApiModelProperty(value = "国家名")
    private String countyName;
}
