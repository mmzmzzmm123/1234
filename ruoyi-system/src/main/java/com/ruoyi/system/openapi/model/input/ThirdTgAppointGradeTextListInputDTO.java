package com.ruoyi.system.openapi.model.input;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class ThirdTgAppointGradeTextListInputDTO {

    @ApiModelProperty("文本内容")
    private String textContent;

    @ApiModelProperty("获取条数，各离散等级条数限制 L1： 10 L2： 100 L3： 500 L4-L5： 1000")
    private Integer num;

    @ApiModelProperty("离散等级：1-5")
    private Integer level;
}
