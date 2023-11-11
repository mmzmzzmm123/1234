package com.ruoyi.api.staff.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;

/**
 * @author LAM
 * @date 2023/10/19 14:57
 */
@ApiModel("api-员工薪资结算分页实体")
@Data
@Accessors(chain = true)
public class ApiPageStaffSettlementRecordsVo implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("店员结算记录集合")
    private List<ApiStaffSettlementRecordsVo> data;

    @ApiModelProperty("总数")
    private long total;
}
