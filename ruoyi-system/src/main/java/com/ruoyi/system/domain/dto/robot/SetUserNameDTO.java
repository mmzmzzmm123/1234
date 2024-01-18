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
    @ApiModelProperty(value = "开始数字")
    private Integer startNum;
    @ApiModelProperty(value = "结束数字")
    private Integer endNum;
}
