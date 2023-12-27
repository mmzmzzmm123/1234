package com.ruoyi.system.domain.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class BatchUpdateProductDTO {
    @ApiModelProperty(value = "商品id列表")
    private List<Long> productIds;

    @ApiModelProperty(value = "商品状态:1-未上架 2-上架")
    private Integer status;

    @ApiModelProperty(value = "是否显示:0否 1是")
    private Integer showStatus;
}
