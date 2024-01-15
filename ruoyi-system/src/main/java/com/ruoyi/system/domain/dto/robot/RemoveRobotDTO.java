package com.ruoyi.system.domain.dto.robot;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@ApiModel("RemoveRobotDTO")
@Data
public class RemoveRobotDTO {
    @ApiModelProperty(value = "机器人编号")
    private List<String> robotSerialNos;
}
