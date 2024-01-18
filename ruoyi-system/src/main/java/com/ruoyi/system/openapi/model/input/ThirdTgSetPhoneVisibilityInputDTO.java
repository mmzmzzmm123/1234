package com.ruoyi.system.openapi.model.input;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class ThirdTgSetPhoneVisibilityInputDTO {
    @ApiModelProperty(value = "tg机器人编号", required = true)
    private String tgRobotId;

    @ApiModelProperty(value = "商家透传字段", required = false)
    private String extend;
    @ApiModelProperty("类型：type =1 不允许任何人 type =2 我的联系人 type =3 所有人 不传默认1")
    private Integer type;
    @ApiModelProperty("账号类型：10 我的联系人 11 所有人 默认为11 只有当type =1 这个字段才需要传值 不传默认11")
    private Integer accountPrivacy;
}
