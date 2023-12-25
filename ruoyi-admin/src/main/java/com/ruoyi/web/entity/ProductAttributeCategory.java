package com.ruoyi.web.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("t_product_attribute_category")
public class ProductAttributeCategory {
    @TableId(type = IdType.AUTO)
    private Integer id;

    private String name;
}
