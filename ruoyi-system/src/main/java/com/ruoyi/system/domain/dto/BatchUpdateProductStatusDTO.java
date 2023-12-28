package com.ruoyi.system.domain.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class BatchUpdateProductStatusDTO extends BatchUpdateProductDTO {
    @ApiModelProperty(value = "商品状态:1-未上架 2-上架")
    private Integer status;
}
