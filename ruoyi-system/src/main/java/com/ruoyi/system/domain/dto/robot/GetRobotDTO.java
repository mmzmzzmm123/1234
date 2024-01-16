package com.ruoyi.system.domain.dto.robot;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
@ApiModel("GetRobotDTO")
public class GetRobotDTO {
    @ApiModelProperty(value = "需要的普通号数量")
    private Integer count = 0;
    @ApiModelProperty(value = "需要的设置管理员的号数量")
    private Integer setAdminCount = 0;
    @ApiModelProperty(value = "国家编码")
    private List<String> countryCode;
    @ApiModelProperty(value = "是否需要离散,0-否,1-B段离散,2-C段离散")
    private Integer ipType;
}
