package com.ruoyi.staff.domain;

import java.math.BigDecimal;
import com.ruoyi.common.annotation.Excel;
    import com.ruoyi.common.core.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 员工钱包对象 bus_staff_wallet
 *
 * @author Lam
 * @date 2023-09-14
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Accessors(chain = true)
@ApiModel("admin-员工钱包对象")
public class StaffWallet extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /** 主键 */
    private Long id;

    /** 用户标识 */
    @Excel(name = "用户标识")
    @ApiModelProperty("用户标识")
    private Long userId;

    /** 待结算 */
    @Excel(name = "待结算")
    @ApiModelProperty("待结算")
    private BigDecimal waitCommission;

    /** 已结算 */
    @Excel(name = "已结算")
    @ApiModelProperty("已结算")
    private BigDecimal commissionSettled;

    /** 订单总值 */
    @Excel(name = "订单总值")
    @ApiModelProperty("订单总值")
    private BigDecimal totalOrderPrice;


}
