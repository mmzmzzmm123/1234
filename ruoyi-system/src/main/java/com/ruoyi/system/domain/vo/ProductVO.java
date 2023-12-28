package com.ruoyi.system.domain.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.ruoyi.system.domain.dto.ProductSkuDTO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class ProductVO {
    @JsonSerialize(using = ToStringSerializer.class)
    @ApiModelProperty(value = "商品id")
    private Long productId;

    @ApiModelProperty(value = "商品名称")
    private String name;

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

    @ApiModelProperty(value = "列表图")
    private String albumPics;

    @ApiModelProperty(value = "海报图")
    private String pic;

    @ApiModelProperty(value = "默认展示规格")
    private ProductSkuDTO skuAttr;

    @ApiModelProperty(value = "产品描述信息")
    private String intro;
}
