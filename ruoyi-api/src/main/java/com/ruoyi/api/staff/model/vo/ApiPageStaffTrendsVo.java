package com.ruoyi.api.staff.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;

/**
 * @author LAM
 * @date 2023/10/3 16:53
 */
@ApiModel("api-员工动态分页实体")
@Data
@Accessors(chain = true)
public class ApiPageStaffTrendsVo implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("数据")
    private List<ApiStaffTrendsVo> data;

    @ApiModelProperty("总数")
    private long total;
}
