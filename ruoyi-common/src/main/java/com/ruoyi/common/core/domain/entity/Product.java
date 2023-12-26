package com.ruoyi.common.core.domain.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@TableName("t_product")
public class Product {
    @TableId
    @ApiModelProperty(value = "商品id")
    private Long productId;

    @ApiModelProperty(value = "分类id")
    private Integer categoryId;

    @ApiModelProperty(value = "商品名称")
    private String name;

    @ApiModelProperty(value = "商品副标题")
    private String subtitle;

    @ApiModelProperty(value = "商品描述")
    private String intro;

    @ApiModelProperty(value = "产品详情网页内容")
    private String detailHtml;

    @ApiModelProperty(value = "商品图")
    private String pic;

    @ApiModelProperty(value = "价格")
    private Long price;

    @ApiModelProperty(value = "价格单位")
    private String productUnit;

    @ApiModelProperty(value = "库存数量")
    private Integer stock;

    @ApiModelProperty(value = "商品状态:1-未上架 2-上架 3-下架")
    private Integer status;

    @ApiModelProperty(value = "是否显示:0否 1是")
    private Integer isShow;

    @ApiModelProperty(value = "是否组合商品 1是 0否")
    private Integer boundle;
}
