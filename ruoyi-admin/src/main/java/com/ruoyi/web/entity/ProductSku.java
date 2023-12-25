package com.ruoyi.web.entity;

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

    @ApiModelProperty(value = "sku名称")
    private String name;

    @ApiModelProperty(value = "价格")
    private Long price;

    @ApiModelProperty(value = "库存")
    private Integer stock;

    @ApiModelProperty(value = "商品销售属性，json格式")
    private String spData;

    @ApiModelProperty(value = "状态:1未上架/2已上架/3已下架")
    private Integer status;
}
