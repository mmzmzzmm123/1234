package com.ruoyi.system.openapi.model.input;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class ThirdTelegramPersonalCallbackRegInputDTO {

    @ApiModelProperty("注册的telegram个人号ID列表")
    private List<String> telegramIdList;
    @ApiModelProperty("回调消息类型,传空或不传代表所有类型")
    private List<Integer> subTypeList;
}
