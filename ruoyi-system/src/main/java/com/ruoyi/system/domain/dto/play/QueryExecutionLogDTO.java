package com.ruoyi.system.domain.dto.play;

import com.ruoyi.system.domain.base.PageBaseDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @Author : XGF（徐桂烽）
 * @create 2024/1/19/019 20:55
 * @Description :
 */
@Data
@ApiModel("查询执行日志DTO")
public class QueryExecutionLogDTO extends PageBaseDTO {

    @ApiModelProperty("剧本ID")
    @NotBlank(message = "剧本ID不能为空")
    private String playId;

}
