package com.ruoyi.system.openapi.model.input;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class ThirdTgSelectRobotListByRadioDTO {

    @ApiModelProperty("标签id")
    private String radioId;

    @ApiModelProperty(value = "商户编号")
    private String merchantId;

    @ApiModelProperty(
            value = "页码",
            example = "1"
    )
    private Integer page = 1;
    @ApiModelProperty(
            value = "每页显示记录数",
            example = "10"
    )
    private Integer limit = 10;
}
