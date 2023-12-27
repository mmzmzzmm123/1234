package com.ruoyi.system.domain.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import java.util.List;

@Data
public class UpdateProductDTO {
    @NotBlank(message="服务id不能为空")
    @Positive(message = "服务id必须是正数")
    @ApiModelProperty(value = "服务id")
    private List<Long> productId;

    @NotBlank(message="商品价格不能为空")
    @Positive(message = "商品价格必须是正数")
    @ApiModelProperty(value = "商品价格")
    private Long price;
}
