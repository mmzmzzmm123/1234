package com.ruoyi.web.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class ProductAttributeCategoryVO {
    private Long id;

    private String name;

    @ApiModelProperty(value = "属性数量")
    private Integer attributeCount;

    @ApiModelProperty(value = "参数数量")
    private Integer paramCount;
}
