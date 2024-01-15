package com.ruoyi.common.core.domain.dto.play;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class PlayMessageDTO {
    @ApiModelProperty(value = "发言顺序")
    private Integer messageSort;

    @ApiModelProperty(value = "剧本内容")
    private List<ContentJson> messageContent;
}
