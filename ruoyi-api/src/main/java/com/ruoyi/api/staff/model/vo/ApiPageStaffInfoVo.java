package com.ruoyi.api.staff.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;

/**
 * @author LAM
 * @date 2023/10/7 17:42
 */
@ApiModel("api-员工信息分页实体")
@Data
@Accessors(chain = true)
public class ApiPageStaffInfoVo implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("店员信息集合")
    private List<ApiStaffInfoVo> data;

    @ApiModelProperty("总数")
    private long total;
}
