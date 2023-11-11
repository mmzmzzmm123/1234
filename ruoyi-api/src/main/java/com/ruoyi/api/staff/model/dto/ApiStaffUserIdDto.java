package com.ruoyi.api.staff.model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @author LAM
 * @date 2023/10/24 16:57
 */
@ApiModel("api-店员用户标识")
@Data
@Accessors(chain = true)
public class ApiStaffUserIdDto implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("店员用户标识")
    private Long staffUserId;
}
