package com.ruoyi.system.domain.dto.play;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.util.List;

/**
 * @Author : XGF（徐桂烽）
 * @create 2024/1/17/017 10:35
 * @Description :
 */
@ApiModel("查询任务进度DTO")
@Data
public class QueryTaskProgressDTO {

    @NotEmpty(message = "参数不能为空")
    @ApiModelProperty("剧本ID集合")
    private List<String> playIds;

}
