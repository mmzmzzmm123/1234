package com.ruoyi.system.domain.dto;

import com.ruoyi.common.core.domain.entity.Product;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class ProductDTO extends Product {

    @ApiModelProperty("规格设置")
    private List<ProductSkuDTO> skuList;
}
