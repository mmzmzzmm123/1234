package com.ruoyi.staff.domain;

import java.math.BigDecimal;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.common.annotation.Excel;
    import com.ruoyi.common.core.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 员工结算记录对象 bus_staff_settlement_records
 *
 * @author Lam
 * @date 2023-09-14
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Accessors(chain = true)
@ApiModel("admin-员工结算记录对象")
public class StaffSettlementRecords extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /** 主键 */
    private Long id;

    /** 员工标识 */
    @Excel(name = "员工标识")
    @ApiModelProperty("员工标识")
    private Long userId;

    /** 结算金额 */
    @Excel(name = "结算金额")
    @ApiModelProperty("结算金额")
    private BigDecimal settlementAmount;

    /** 结算开始时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "结算开始时间", width = 30, dateFormat = "yyyy-MM-dd")
    @ApiModelProperty("结算开始时间")
    private Date startTime;

    /** 结算结束时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "结算结束时间", width = 30, dateFormat = "yyyy-MM-dd")
    @ApiModelProperty("结算结束时间")
    private Date endTime;


}
