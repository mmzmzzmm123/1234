package com.ruoyi.system.domain.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author 朱少波
 * @date 2024/1/15/015 16:01
 */
@Data
@ApiModel("群选择DTO")
public class GroupQueryDTO {

    @ApiModelProperty(value = "群注册天数")
    private Integer registrationDay;

    @ApiModelProperty(value = "群数")
    private Integer groupNum;
}
