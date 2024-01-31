package com.ruoyi.system.domain.vo;

import com.ruoyi.common.annotation.Excel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
public class AutoReplayLogVO {
    @Excel(name = "收消息时间")
    @ApiModelProperty("收消息时间")
    private Date createTime;

    @Excel(name = "群编号")
    @ApiModelProperty(value = "群编号")
    private String groupSerialNo;

    @Excel(name = "群名称")
    @ApiModelProperty(value = "群名称")
    private String groupName;

    @Excel(name = "收消息内容")
    @ApiModelProperty("内容")
    private String content;

    @Excel(name = "机器人编号")
    @ApiModelProperty(value = "机器人编号")
    private String robotId;

    @Excel(name = "状态", readConverterExp = "0=成功,1=失败")
    @ApiModelProperty("状态：0-成功 1-失败")
    private Integer state;

    private String id;
    private String playId;
}
