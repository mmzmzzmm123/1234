package com.ruoyi.system.domain.dto.play;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @Author : XGF（徐桂烽）
 * @create 2024/1/17/017 15:42
 * @Description :
 */
@ApiModel("查询任务账号明细DTO")
@Data
public class QueryRobotDetailDTO {

    @ApiModelProperty("剧本ID")
    @NotBlank(message = "剧本ID不能为空")
    private String playId;

    @ApiModelProperty("国家名称")
    private String countryName;

}
