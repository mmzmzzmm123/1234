package com.ruoyi.system.domain.dto.robot;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@ApiModel("SetUserNameDTO")
@Data
public class SetUserNameDTO {
    @ApiModelProperty(value = "机器人编号")
    private List<String> robotSerialNos;
    @ApiModelProperty(value = "用户名")
    private String userName;
    @ApiModelProperty(value = "编号")
    private String code;
}
