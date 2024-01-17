package com.ruoyi.system.domain.vo.play;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Author : XGF（徐桂烽）
 * @create 2024/1/17/017 15:44
 * @Description :
 */
@ApiModel("账号统计VO")
@Data
public class RobotStatisticsVO {

    @ApiModelProperty("群数")
    private Integer groupNum;

    @ApiModelProperty("水军号数")
    private Integer navyRobotNum;

    @ApiModelProperty("备用号数")
    private Integer spareRobotNum;

    @ApiModelProperty("双向号数")
    private Integer bidirectionalRobotNum;

    @ApiModelProperty("封号数")
    private Integer robotClosureNum;

    @ApiModelProperty("封群数")
    private Integer groupClosureNum;

    @ApiModelProperty("入群失败号数")
    private Integer joinGroupFailNum;

}
