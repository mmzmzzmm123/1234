package com.ruoyi.system.domain.vo.robot;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel("GetRobotVO")
@Data
public class GetRobotVO {
    @ApiModelProperty(value = "机器号编号")
    private String robotSerialNo;
    @ApiModelProperty(value = "每日设为群管理员可用次数")
    private Integer oneDaySetAdminCount;
    @ApiModelProperty(value = "单个水军可带群数")
    private Integer groupCount;
}
