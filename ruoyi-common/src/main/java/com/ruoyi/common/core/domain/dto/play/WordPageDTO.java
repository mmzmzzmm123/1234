package com.ruoyi.common.core.domain.dto.play;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class WordPageDTO {
    @ApiModelProperty(value = "文件id")
    private String fileId;

    @ApiModelProperty(value = "总页数")
    private Integer page;

    @ApiModelProperty(value = "每页获取数")
    private Integer pageSize;

    private Long redaFileTime;
    private Long saveFileTime;
}
