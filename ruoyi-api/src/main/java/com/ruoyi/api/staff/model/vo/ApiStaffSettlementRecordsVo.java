package com.ruoyi.api.staff.model.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author LAM
 * @date 2023/10/19 14:57
 */
@ApiModel("api-员工薪资结算记录实体")
@Data
@Accessors(chain = true)
public class ApiStaffSettlementRecordsVo implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("结算金额")
    private BigDecimal settlementAmount;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @ApiModelProperty("结算开始时间")
    private Date startTime;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @ApiModelProperty("结算结束时间")
    private Date endTime;
}
