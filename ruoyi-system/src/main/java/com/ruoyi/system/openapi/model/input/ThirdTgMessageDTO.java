package com.ruoyi.system.openapi.model.input;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class ThirdTgMessageDTO {
    @ApiModelProperty("消息类型（文字：2001）")
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
