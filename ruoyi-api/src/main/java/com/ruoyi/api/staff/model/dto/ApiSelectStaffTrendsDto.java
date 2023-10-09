package com.ruoyi.api.staff.model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @author LAM
 * @date 2023/10/6 17:14
 */
@ApiModel("api-查询员工动态数据")
@Data
@Accessors(chain = true)
public class ApiSelectStaffTrendsDto implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("用户标识")
    private Long userId;

    @ApiModelProperty("性别（0男，1女，2未知）")
    private String sex;


}
