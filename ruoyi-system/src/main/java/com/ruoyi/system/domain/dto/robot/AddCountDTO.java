package com.ruoyi.system.domain.dto.robot;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class AddCountDTO {
    @ApiModelProperty(value = "是否需要设置管理员,0-否,1-是")
    private Integer isSetAdmin;
    @ApiModelProperty(value = "机器号")
    private List<String> robotSerialNos;
}
