package com.ruoyi.system.domain.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Data
public class BatchUpdateProductDTO {
    @NotNull(message = "商品id不能为空")
    @Size(min = 1,max = 100,message = "超过数量限制，单次提交至少1条，最多不超过100条")
    @ApiModelProperty(value = "商品id列表")
    private List<Long> productIds;
}
