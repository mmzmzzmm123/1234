package com.ruoyi.system.domain.dto.play;

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
    private Integer source;

    @ApiModelProperty(value = "提交群信息参数")
    private HypeGroupParams groupParams;

    @ApiModelProperty(value = "群消息包装")
    private List<GroupPack> groupPackList;

    @ApiModelProperty(value = "每个群演员数")
    private Integer performerNum;

    @ApiModelProperty(value = "演员信息")
    private List<Performer> performerList;

    @ApiModelProperty(value = "剧本内容")
    private List<PlayMessage> playMessageList;

    @ApiModelProperty(value = "剧本是否混淆：0-否 1-是")
    private Integer isConfound;

    @ApiModelProperty(value = "发送机制")
    private SendMechanism sendMechanism;

    @ApiModelProperty(value = "广告监控")
    private AdMoitor adMoitor;

    @ApiModelProperty(value = "其它配置")
    private PlayExt playExt;

    @ApiModelProperty(value = "粉号池")
    private String urlPool;
}
