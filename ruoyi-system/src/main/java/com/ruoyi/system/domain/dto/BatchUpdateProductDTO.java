package com.ruoyi.system.domain.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class BatchUpdateProductDTO {
    @ApiModelProperty(value = "商品id列表")
    private List<Long> productIds;

    @ApiModelProperty(value = "商品状态")
    private Integer status;

    @ApiModelProperty(value = "显示状态")
    private Integer showStatus;
}
