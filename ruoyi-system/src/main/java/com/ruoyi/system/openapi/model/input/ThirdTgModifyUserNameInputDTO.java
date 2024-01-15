package com.ruoyi.system.openapi.model.input;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class ThirdTgModifyUserNameInputDTO {

    @ApiModelProperty(value = "tg机器人编号", required = true)
    private String tgRobotId;

    @ApiModelProperty("用户号")
    private String userName;

    @ApiModelProperty(value = "商家透传字段", required = false)
    private String extend;

}
