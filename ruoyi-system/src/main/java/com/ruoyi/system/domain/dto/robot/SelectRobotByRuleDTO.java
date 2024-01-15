package com.ruoyi.system.domain.dto.robot;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
@ApiModel("SelectRobotByRuleDTO")
public class SelectRobotByRuleDTO extends GetRobotDTO{
    @ApiModelProperty(value = "号每日入群数量")
    private Integer oneDayJoinGroupCount;
    @ApiModelProperty(value = "号累计入群数量")
    private Integer totalJoinGroupCount;
    @ApiModelProperty(value = "号每日设管理员数量")
    private Integer oneDaySetAdminCount;
    @ApiModelProperty(value = "号累计设管理员数量")
    private Integer totalSetAdminCount;
    @ApiModelProperty(value = "号带群数")
    private Integer groupCount;
}
