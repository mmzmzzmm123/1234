package com.ruoyi.system.domain.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class ForceFinishGroupDTO {

    @ApiModelProperty("剧本ID")
    private String playId;

    @ApiModelProperty("群ID")
    private String groupId;
}
