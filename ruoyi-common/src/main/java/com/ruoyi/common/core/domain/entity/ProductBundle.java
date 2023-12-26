package com.ruoyi.common.core.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@TableName("t_product_bundle")
public class ProductBundle {
    @TableId(type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "商品id")
    private Long productId;

    @ApiModelProperty(value = "组合商品id")
    private Long bundleId;

    @ApiModelProperty(value = "类型:1组合商品,2sku")
    private Integer entityType;

    @ApiModelProperty(value = "状态:1正常/0删除")
    private Integer status;
}
