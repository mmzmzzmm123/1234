package com.ruoyi.common.openapi.model.input;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class ThirdTgModifyNameInputDTO {

    @ApiModelProperty(value = "tg机器人编号", required = true)
    private String tgRobotId;
    @ApiModelProperty(value = "商家透传字段", required = false)
    private String extend;
    @ApiModelProperty("名字")
    private String firstNameBase64;
    @ApiModelProperty("姓氏")
    private String lastNameBase64;
    @ApiModelProperty("个人简介")
    private String briefIntroBase64;
}
