package com.ruoyi.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class SourcePageQeq {

    @ApiModelProperty("子目号")
    private String itemNum;

    @ApiModelProperty("子目名称")
    private String itemName;
}
