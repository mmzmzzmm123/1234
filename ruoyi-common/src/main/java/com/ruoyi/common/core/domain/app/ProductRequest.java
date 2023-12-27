package com.ruoyi.common.core.domain.app;

import lombok.Data;

@Data
public class ProductRequest {
    /**
     * 分类
     */
    private Integer categoryId;

    /**
     * 商品名
     */
    private String name;

    private Integer page = 1;
    private Integer limit = 20;
}
