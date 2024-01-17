package com.ruoyi.system.domain.vo.robot;

import com.ruoyi.common.core.domain.entity.robot.Robot;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel("SelectRobotListVO")
@Data
public class SelectRobotListVO extends Robot {
    @ApiModelProperty(value = "今日带群数")
    private Integer groupToDayNum;
    @ApiModelProperty(value = "累计带群数")
    private Integer groupTotalNum;
    @ApiModelProperty(value = "水军占用状态,0-未占用,1-已占用")
    private Integer occupyType;

}
