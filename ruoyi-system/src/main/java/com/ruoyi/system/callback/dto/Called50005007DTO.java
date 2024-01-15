package com.ruoyi.system.callback.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class Called50005007DTO {

    @ApiModelProperty("机器人编号集合")
    private List<String> robotSerialNos;
}
