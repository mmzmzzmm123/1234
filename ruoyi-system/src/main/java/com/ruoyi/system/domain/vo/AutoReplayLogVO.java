package com.ruoyi.system.domain.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
public class AutoReplayLogVO {
    @ApiModelProperty("创建时间")
    private Date createTime;

    @ApiModelProperty(value = "群编号")
    private String groupSerialNo;

    @ApiModelProperty(value = "群名称")
    private String groupName;

    @ApiModelProperty(value = "机器人编号")
    private String robotId;

    @ApiModelProperty("内容")
    private String content;

    private String id;
    private String playId;
    private Integer state;
}
