package com.ruoyi.system.domain.dto.play;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 广告监控
 */
@Data
public class AdMoitor {
    @ApiModelProperty(value = "监控类型 多个,连接：1-发图片 2-发视频 3-发链接")
    private Integer types;

    @ApiModelProperty(value = "判断刷屏消息数量")
    private Integer spammingNum;

    @ApiModelProperty(value = "刷屏监测时间")
    private Integer spammingTime;

    @ApiModelProperty(value = "刷屏监测时间单位: 1-秒 2-分")
    private Integer spammingTimeUnit;

    @ApiModelProperty(value = "触发监控处理方式：0-禁言 1踢出群")
    private Integer disposalType;
}
