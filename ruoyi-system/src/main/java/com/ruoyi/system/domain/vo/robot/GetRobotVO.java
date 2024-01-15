package com.ruoyi.system.domain.vo.robot;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel("GetRobotVO")
@Data
public class GetRobotVO {
    @ApiModelProperty(value = "机器号编号")
    private String robotSerialNo;
}
