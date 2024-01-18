package com.ruoyi.system.domain.dto.play;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;

@Data
public class PlayStateDTO {
    @ApiModelProperty("炒群任务ID")
    @NotNull(message = "炒群任务ID不能为空")
    private List<String> playIds;

    @ApiModelProperty("状态: 2开启 3暂停 4取消")
    @NotNull(message = "操作类型不能为空")
    private Integer state;
}
