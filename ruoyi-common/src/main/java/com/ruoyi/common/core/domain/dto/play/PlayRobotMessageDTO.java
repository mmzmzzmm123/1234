package com.ruoyi.common.core.domain.dto.play;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class PlayRobotMessageDTO {
    @ApiModelProperty(value = "内容")
    private List<ContentJson> messageContent;
}
