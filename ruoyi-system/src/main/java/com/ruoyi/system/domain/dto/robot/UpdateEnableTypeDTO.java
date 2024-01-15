package com.ruoyi.system.domain.dto.robot;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@ApiModel("UpdateEnableTypeDTO")
@Data
public class UpdateEnableTypeDTO {
    @ApiModelProperty(value = "机器人编号")
    private List<String> robotSerialNos;
    @ApiModelProperty(value = "禁用/启用,0-禁用,1-启用")
    private Integer enableType;
}
