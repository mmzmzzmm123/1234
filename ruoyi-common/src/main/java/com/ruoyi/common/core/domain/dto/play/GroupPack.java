package com.ruoyi.common.core.domain.dto.play;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * 群包装
 */
@Data
public class GroupPack {
    @ApiModelProperty(value = "群名称")
    private List<String> name;

    @ApiModelProperty(value = "群头像")
    private List<String> pic;
}
