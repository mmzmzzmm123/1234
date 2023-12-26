package com.ruoyi.system.domain.dto;

import lombok.Data;

@Data
public class ProductDTO {
    private Long productId;

    private Integer categoryId;

    private String name;

    private String subtitle;

    private String detail;

    private Integer skuType;

    private String pic;

    private Long price;

    private String productUnit;

    private Integer stock;

    private String spuSpecs;

    private Integer status;

    private Integer isShow;
}
