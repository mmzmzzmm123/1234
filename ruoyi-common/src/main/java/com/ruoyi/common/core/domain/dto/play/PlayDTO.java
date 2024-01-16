package com.ruoyi.common.core.domain.dto.play;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class PlayDTO {
    @ApiModelProperty(value = "任务id")
    private String id;

    @ApiModelProperty(value = "任务名称")
    private String name;

    @ApiModelProperty(value = "群来源：0-平台提供 1-外部群")
    private Integer groupSource;

    @ApiModelProperty("群需求数量")
    private Integer groupNum;

    @ApiModelProperty("群注册天数")
    private Integer groupDay;

    @ApiModelProperty("炒群条件：0-人数 1-时间")
    private Integer groupCondition;

    @ApiModelProperty("炒群条件-群人数")
    private Integer userNum;

    @ApiModelProperty("炒群时间条件类型：0-立即开始 1定时开始")
    private Integer startType;

    @ApiModelProperty("定时开始炒群时间")
    private String startGroupDate;

    @ApiModelProperty("外部群邀请链接")
    private List<String> groupUrls;

    @ApiModelProperty(value = "群消息包装")
    private GroupPack groupPack;

    @ApiModelProperty(value = "每个群演员数")
    private Integer robotNum;

    @ApiModelProperty(value = "发言人包装")
    private List<Performer> performerList;

    @ApiModelProperty(value = "剧本内容")
    private List<PlayMessageDTO> playMessageList;

    @ApiModelProperty(value = "剧本是否混淆：0-否 1-是")
    private Integer isConfound;

    @ApiModelProperty(value = "内容目标国编码")
    private String targetCountyCode;

    @ApiModelProperty(value = "内容目标国名称")
    private String targetCountyName;

    @ApiModelProperty(value = "发送机制配置")
    private SendMechanism sendMechanism;

    @ApiModelProperty(value = "广告监控配置")
    private AdMonitor adMonitor;

    @ApiModelProperty(value = "其它配置")
    private PlayExt playExt;

    @ApiModelProperty(value = "粉号池")
    private String urlPool;

    private String merchantId;

    @ApiModelProperty(value = "word文件链接")
    private String wordUrl;
}
