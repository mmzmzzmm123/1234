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

    /**
     * {@link com.ruoyi.common.enums.AppType}
     */
    @ApiModelProperty(value = "平台类型")
    private Integer appType;

    /**
     * {@link com.ruoyi.common.enums.ProductCategoryType}
     */
    @ApiModelProperty(value = "商品类型")
    private Integer categoryId;

    @ApiModelProperty(value = "商品名称")
    private String name;

    @ApiModelProperty(value = "列表图")
    private String albumPics;

    @ApiModelProperty(value = "海报图")
    private String pic;

    @ApiModelProperty(value = "商品状态:1-未上架 2-上架")
    private Integer status;

    @ApiModelProperty(value = "是否显示:0否 1是")
    private Integer isShow;

    @ApiModelProperty(value = "产品描述信息")
    private String intro;

//    @ApiModelProperty(value = "价格")
//    private Long price;

    @ApiModelProperty(value = "支持量")
    private Integer stock;

    @ApiModelProperty(value = "规格:1统一规格 2多规格")
    private Integer specsType;

    @ApiModelProperty(value = "默认展示规格")
    private String skuAttr;

    @ApiModelProperty(value = "创建时间")
    private String createDate;

    @ApiModelProperty(value = "创建人")
    private String operatorUser;

    @ApiModelProperty(value = "是否删除:0否 1是")
    private Integer isDel;
}
