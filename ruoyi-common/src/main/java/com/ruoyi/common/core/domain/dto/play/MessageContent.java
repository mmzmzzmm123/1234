package com.ruoyi.common.core.domain.dto.play;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class MessageContent {
    @ApiModelProperty(value = "消息类型 2001文字 2002图片 2003语音 2004视频 2005链接")
    private Integer momentTypeId;

    private String mateContent;

    private String mateImgUrl;

    private String mateTitle;

    private String uuid;
}
