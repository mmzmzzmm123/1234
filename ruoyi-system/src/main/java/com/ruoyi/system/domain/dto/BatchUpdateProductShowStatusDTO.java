package com.ruoyi.system.domain.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class BatchUpdateProductShowStatusDTO extends BatchUpdateProductDTO {
    @ApiModelProperty(value = "是否显示:0否 1是")
    private Integer showStatus;
}
