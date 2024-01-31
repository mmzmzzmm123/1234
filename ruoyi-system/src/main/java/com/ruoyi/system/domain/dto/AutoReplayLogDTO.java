package com.ruoyi.system.domain.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
public class AutoReplayLogDTO  {
    @ApiModelProperty("剧本ID")
    private String playId;

    @ApiModelProperty("开始时间")
    private Date startTime;

    @ApiModelProperty("结束时间")
    private Date endTime;

    @ApiModelProperty("状态：0-成功 1-失败")
    private Integer state;

    @ApiModelProperty(value = "查询群条件: 1名称 2编号")
    private Integer groupType;

    @ApiModelProperty(value = "查询群内容")
    private String groupVal;

    @ApiModelProperty("机器人编号")
    private String robotId;

    @ApiModelProperty("每页条数")
    private int limit = 1;

    @ApiModelProperty("页码")
    private int page = 20;
}
