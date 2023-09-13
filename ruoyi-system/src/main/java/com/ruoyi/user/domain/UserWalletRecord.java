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
 * 用户钱包记录对象 bus_user_wallet_record
 *
 * @author Lam
 * @date 2023-09-13
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Accessors(chain = true)
@ApiModel("admin-用户钱包记录对象")
public class UserWalletRecord extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /** 主键 */
    private Long id;

    /** 用户标识 */
    @Excel(name = "用户标识")
    @ApiModelProperty("用户标识")
    private Long userId;

    /** 充值记录标识 */
    @Excel(name = "充值记录标识")
    @ApiModelProperty("充值记录标识")
    private Long rechargeRecordId;

    /** 记录类型 */
    @Excel(name = "记录类型")
    @ApiModelProperty("记录类型")
    private String walletRecordType;

    /** 金额 */
    @Excel(name = "金额")
    @ApiModelProperty("金额")
    private BigDecimal amount;

    /** 余额前 */
    @Excel(name = "余额前")
    @ApiModelProperty("余额前")
    private BigDecimal amountBefore;

    /** 余额后 */
    @Excel(name = "余额后")
    @ApiModelProperty("余额后")
    private BigDecimal amountAfter;


}
