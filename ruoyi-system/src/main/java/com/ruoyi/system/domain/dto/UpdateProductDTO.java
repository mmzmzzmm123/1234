package com.ruoyi.system.domain.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class UpdateProductDTO {
    @ApiModelProperty(value = "商品id")
    private List<Long> productId;

    @ApiModelProperty(value = "商品价格")
    private Long price;
}
