package com.ruoyi.system.domain.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Data
public class ProductSkuDTO {
    private Long id;

    @ApiModelProperty(value = "商品id")
    private Long productId;

    @NotNull(message="价格不能为空")
    @Positive(message = "价格必须为正数")
    @ApiModelProperty(value = "价格")
    private Long price;

    @ApiModelProperty(value = "价格单位")
    private String priceUnit;

    @Positive(message = "支撑量必须是正数")
    @ApiModelProperty(value = "支撑量")
    private Integer stock;

    @NotNull(message="国家编码不能为空")
    @ApiModelProperty(value = "国家编码")
    private String countyCode;

    @NotBlank(message="国家名不能为空")
    @ApiModelProperty(value = "国家名")
    private String countyName;
}
