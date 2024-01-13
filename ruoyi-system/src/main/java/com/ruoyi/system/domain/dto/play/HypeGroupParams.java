package com.ruoyi.system.domain.dto.play;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class HypeGroupParams {
    @ApiModelProperty("群需求数量")
    private Integer num;

    @ApiModelProperty("群注册天数")
    private Integer dayNum;

    @ApiModelProperty("炒群条件：0-人数 1-时间")
    private Integer condition;

    @ApiModelProperty("群人数")
    private Integer userNum;

    @ApiModelProperty("时间条件开始类型：0-立即开始 1定时开始")
    private Integer startType;

    @ApiModelProperty("群邀请链接")
    private List<String> groupUrlList;
}