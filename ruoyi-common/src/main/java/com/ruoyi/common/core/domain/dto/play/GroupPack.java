package com.ruoyi.common.core.domain.dto.play;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 群包装
 */
@Data
public class GroupPack {
    @ApiModelProperty(value = "群名称")
    private String name;

    @ApiModelProperty(value = "群头像")
    private String pic;
}
