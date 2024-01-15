package com.ruoyi.common.core.domain.dto.play;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class PlayMessageDTO {
    @ApiModelProperty(value = "发言人标识")
    private String label;

    @ApiModelProperty(value = "剧本内容")
    private List<MessageContent> messageContent;
}
