package com.ruoyi.common.core.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@TableName("t_product_sku")
public class ProductSku {
    @TableId(type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "商品id")
    private Long productId;

    @ApiModelProperty(value = "价格")
    private Long price;

    @ApiModelProperty(value = "价格单位")
    private String priceUnit;

    @ApiModelProperty(value = "支持量")
    private Integer stock;

    @ApiModelProperty(value = "国家id")
    private Integer countyId;
}
