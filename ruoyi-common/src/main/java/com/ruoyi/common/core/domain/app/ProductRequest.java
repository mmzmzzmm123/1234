package com.ruoyi.common.core.domain.app;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class ProductRequest {
    @ApiModelProperty("分类id")
    private Integer categoryId;

    @ApiModelProperty("商品名")
    private String name;

    private Integer page = 1;
    private Integer limit = 20;
}
