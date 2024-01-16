package com.ruoyi.system.openapi.model.input;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class ThirdTgDisperseImageInputDTO {

    @ApiModelProperty("图片地址")
    private String imageUrl;

    @ApiModelProperty("数量")
    private Integer num;
}
