package com.ruoyi.web.vo;

import io.swagger.annotations.ApiModelProperty;

public class ProductAttributeVO {
    private Long id;

    private Long productAttributeCategoryId;

    private String name;

    @ApiModelProperty(value = "属性录入方式：0->手工录入；1->从列表中选取")
    private Integer inputType;
}
