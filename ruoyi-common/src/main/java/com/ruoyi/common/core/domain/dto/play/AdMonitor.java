package com.ruoyi.common.core.domain.dto.play;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * 广告监控
 */
@Data
public class AdMonitor {
    @ApiModelProperty(value = "监控类型 多个,连接：1-发图片 2-发视频 3-发链接")
    private String types;

    @ApiModelProperty(value = "监控关键词")
    private List<String> keywordRule;

    @ApiModelProperty(value = "判断刷屏消息数量")
    private Integer spammingNum;

    @ApiModelProperty(value = "刷屏监测时间")
    private Integer spammingTime;

    @ApiModelProperty(value = "刷屏监测时间单位: 1-秒 2-分")
    private Integer spammingTimeUnit;

    @ApiModelProperty(value = "触发监控处理方式 多个,连接：0-禁言 1踢出群 2踢出群并删除消息")
    private String disposalType;

    @ApiModelProperty(value = "是否删掉除水军外的其他成员发言: 0否 1是")
    private Integer isDelMemberMsg;

    @ApiModelProperty(value = "是否群成员禁言: 0否 1是")
    private Integer isTabooMemberMsg;
}
