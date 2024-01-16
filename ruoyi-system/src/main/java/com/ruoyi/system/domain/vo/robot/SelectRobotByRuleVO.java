package com.ruoyi.system.domain.vo.robot;

import io.swagger.annotations.ApiModel;
import lombok.Data;

@ApiModel("SelectRobotByRuleVO")
@Data
public class SelectRobotByRuleVO {
    private String id;
    private String robotSerialNo;
}
