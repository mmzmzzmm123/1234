package com.ruoyi.web.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class BoundleDTO {
    @ApiModelProperty("组合商品id")
    private Long id;

    @ApiModelProperty("组合类型:1组合商品,2sku")
    private Integer type;
}
