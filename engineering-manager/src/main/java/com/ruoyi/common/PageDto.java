package com.ruoyi.common;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class PageDto {
    @ApiModelProperty("当前页")
    private Integer pageNum;
    @ApiModelProperty("每页数目")
    private Integer pageSize;
}
