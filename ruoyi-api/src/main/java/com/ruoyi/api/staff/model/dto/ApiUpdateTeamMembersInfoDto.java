package com.ruoyi.api.staff.model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @author LAM
 * @date 2023/10/24 20:18
 */
@ApiModel("api-组长修改店员数据")
@Data
@Accessors(chain = true)
public class ApiUpdateTeamMembersInfoDto implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("店员用户标识")
    private Long userId;

    @ApiModelProperty("状态")
    private String state;

    @ApiModelProperty("店员等级")
    private Long staffLevel;

    @ApiModelProperty("是否在线")
    private String ifOnline;
}
