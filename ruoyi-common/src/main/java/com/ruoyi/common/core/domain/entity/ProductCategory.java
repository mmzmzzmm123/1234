package com.ruoyi.common.core.domain.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("t_product_category")
public class ProductCategory {
    private Integer categoryId;

    private Integer parentId;

    private String name;

    private Integer status;

    private Integer sort;
}
