package com.ruoyi.app.product.vo;

import com.ruoyi.common.enums.QueryTypeEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "ProductQuery", description = "商品列表查询参数")

public class ProductQuery {
    /**
     * 标签id
     */
    @ApiModelProperty("分类id")

    private Long categoryId;

    @ApiModelProperty("查询方式")

    private QueryTypeEnum queryType;


    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public QueryTypeEnum getQueryType() {
        return queryType;
    }

    public void setQueryType(QueryTypeEnum queryType) {
        this.queryType = queryType;
    }
}
