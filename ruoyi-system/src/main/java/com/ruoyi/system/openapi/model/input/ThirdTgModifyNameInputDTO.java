package com.ruoyi.system.openapi.model.input;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class ThirdTgModifyNameInputDTO {

    @ApiModelProperty(value = "tg机器人编号", required = true)
    private String tgRobotId;
    @ApiModelProperty(value = "商家透传字段", required = false)
    private String extend;
    @ApiModelProperty("名字，不要传base64，接口处理了")
    private String firstNameBase64;
    @ApiModelProperty("姓氏，不要传base64，接口处理了")
    private String lastNameBase64;
    @ApiModelProperty("个人简介，不要传base64，接口处理了")
    private String briefIntroBase64;
}
