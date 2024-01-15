package com.ruoyi.system.openapi.model.input;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class ThirdTgSearchMemberInputDTO {
    @ApiModelProperty(value = "tg机器人编号", required = true)
    private String tgRobotId;

    @ApiModelProperty(value = "商家透传字段", required = false)
    private String extend;

    @ApiModelProperty(value = "群编号", required = true)
    private String chatroomSerialNo;
    @ApiModelProperty(value = "成员搜索", required = true)
    private String searchContent;
    @ApiModelProperty(value = "页码", required = true)
    private Integer pageIndex;
}
