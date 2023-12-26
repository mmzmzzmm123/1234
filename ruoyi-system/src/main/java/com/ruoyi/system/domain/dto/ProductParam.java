package com.ruoyi.system.domain.dto;

import com.ruoyi.common.core.domain.entity.Product;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class ProductParam extends Product {

    @ApiModelProperty("商品的sku库存信息")
    private List<ProductSkuDTO> skuStockList;

    @ApiModelProperty("商品组合信息")
    private List<BoundleDTO> boundleList;
}
