package com.ruoyi.system.domain.dto.robot;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
@ApiModel("GetRobotDTO")
public class GetRobotDTO {
    @ApiModelProperty(value = "需要的号数量")
    private Integer count;
    @ApiModelProperty(value = "国家编码")
    private List<String> countryCode;
}
