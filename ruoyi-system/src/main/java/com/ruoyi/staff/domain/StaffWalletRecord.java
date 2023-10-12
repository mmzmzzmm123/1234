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
 * 钱包佣金记录对象 bus_staff_wallet_record
 *
 * @author Lam
 * @date 2023-10-10
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Accessors(chain = true)
@ApiModel("admin-钱包佣金记录对象")
public class StaffWalletRecord extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /** 主键 */
    private Long id;

    /** 店员标识 */
    @Excel(name = "店员标识")
    @ApiModelProperty("店员标识")
    private Long staffUserId;

    /** 订单标识 */
    @Excel(name = "订单标识")
    @ApiModelProperty("订单标识")
    private Long orderId;

    /** 记录类型（0入账，1扣款，2结算） */
    @Excel(name = "记录类型", readConverterExp = "0=入账，1扣款，2结算")
    @ApiModelProperty("记录类型")
    private String staffWalletRecordType;

    /** 金额 */
    @Excel(name = "金额")
    @ApiModelProperty("金额")
    private BigDecimal amount;


}
