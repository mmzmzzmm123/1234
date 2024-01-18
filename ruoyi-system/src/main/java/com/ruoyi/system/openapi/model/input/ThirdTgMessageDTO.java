package com.ruoyi.system.openapi.model.input;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class ThirdTgMessageDTO {
    @ApiModelProperty("消息类型（2001 文本消息, 2002 图片 2003 语音（目前仅支持.ogg和 .mp3文件后缀） 2004 视频 2005 2007 emoji表情 链接 2010 文件 2015 贴图消息 2034 超文本链接 2035 postbot消息）")
    private Integer msgType;
    @ApiModelProperty("消息序号")
    private Integer msgNum;
    @ApiModelProperty("消息内容")
    private String msgContent;

    @ApiModelProperty("地图标题（在发送地图消息时传入该参数）")
    private String msgTitle;
    @ApiModelProperty("经度（在发送地图消息时传入该参数）")
    private Double latitude = 0.0;
    @ApiModelProperty("纬度（在发送地图消息时传入该参数）")
    private Double longitude = 0.0;
    @ApiModelProperty("精度（在发送地图消息时传入该参数）")
    private String accuracyRadius;
    private String mapsSetId;
}
