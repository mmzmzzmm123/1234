package com.ruoyi.system.domain.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @Author : XGF（徐桂烽）
 * @create 2024/1/9/009 20:45
 * @Description :
 */
@Data
@ApiModel("混淆重试DTO")
public class ConfoundRetryDTO {

    @ApiModelProperty("主键ID集合")
    private List<Integer> ids;

}
