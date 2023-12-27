package com.ruoyi.system.domain.vo;

import com.ruoyi.system.domain.dto.ProductSkuDTO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class ProductDetailVO extends ProductVO {
    @ApiModelProperty("商品的sku库存信息")
    private List<ProductSkuDTO> skuList;
}
