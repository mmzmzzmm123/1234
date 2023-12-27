package com.ruoyi.system.domain.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Data
public class UpdateProductDTO {
    @NotNull(message="服务id不能为空")
    @Positive(message = "服务id必须是正数")
    @ApiModelProperty(value = "服务id")
    private Long productId;

    @NotNull(message="商品价格不能为空")
    @Positive(message = "商品价格必须是正数")
    @ApiModelProperty(value = "商品价格")
    private Long price;
}
