package com.ruoyi.system.domain.dto.robot;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@ApiModel("ClearSealDataDTO")
@Data
public class ClearSealDataDTO {
    @ApiModelProperty(value = "号编号")
    private List<String> robotSerialNos;
}
