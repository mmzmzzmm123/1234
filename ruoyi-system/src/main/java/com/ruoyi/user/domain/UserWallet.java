package com.ruoyi.user.domain;

import java.math.BigDecimal;
import com.ruoyi.common.annotation.Excel;
    import com.ruoyi.common.core.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 用户钱包管理对象 bus_user_wallet
 *
 * @author Lam
 * @date 2023-09-13
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Accessors(chain = true)
@ApiModel("admin-用户钱包管理对象")
public class UserWallet extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /** 主键 */
    private Long id;

    /** 用户标识 */
    @Excel(name = "用户标识")
    @ApiModelProperty("用户标识")
    private Long userId;

    /** 余额 */
    @Excel(name = "余额")
    @ApiModelProperty("余额")
    private BigDecimal balance;

    /** 赠送余额 */
    @Excel(name = "赠送余额")
    @ApiModelProperty("赠送余额")
    private BigDecimal giftBalance;

    @Excel(name = "分销佣金")
    @ApiModelProperty("分销佣金")
    private BigDecimal distributionCommission;

    /** 消费总额 */
    @Excel(name = "消费总额")
    @ApiModelProperty("消费总额")
    private BigDecimal totalBalance;

    /** 赠送总额 */
    @Excel(name = "赠送总额")
    @ApiModelProperty("赠送总额")
    private BigDecimal totalGiftBalance;

    @Excel(name = "分销佣金总额")
    @ApiModelProperty("分销佣金总额")
    private BigDecimal totalDistributionCommission;

}
