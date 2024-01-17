package com.ruoyi.common.core.domain.dto.play;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class AnalysisPlayFile implements Serializable {
    private static final long serialVersionUID = -8212280510667906849L;

    @ApiModelProperty(value = "剧本内容")
    private List<List<String>> messageContent;
}
