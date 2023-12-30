package com.ruoyi.common.core.domain.app;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Size;

@Data
public class ProductRequest {
    @ApiModelProperty("分类id")
    private Integer categoryId;

    @Size(min = 1, max = 64, message = "商品名长度为1-64字")
    @ApiModelProperty("商品名")
    private String name;

    private Integer page = 1;
    private Integer limit = 20;
}
