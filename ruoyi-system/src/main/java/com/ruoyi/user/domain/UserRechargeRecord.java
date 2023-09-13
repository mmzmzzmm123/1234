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
 * 用户充值记录对象 bus_user_recharge_record
 *
 * @author Lam
 * @date 2023-09-13
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Accessors(chain = true)
@ApiModel("admin-用户充值记录对象")
public class UserRechargeRecord extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /** 主键 */
    private Long id;

    /** 用户标识 */
    @Excel(name = "用户标识")
    @ApiModelProperty("用户标识")
    private Long userId;

    /** 优惠券标识 */
    @Excel(name = "优惠券标识")
    @ApiModelProperty("优惠券标识")
    private Long couponId;

    /** 充值金额 */
    @Excel(name = "充值金额")
    @ApiModelProperty("充值金额")
    private BigDecimal rechargeAmount;

    /** 优惠金额 */
    @Excel(name = "优惠金额")
    @ApiModelProperty("优惠金额")
    private BigDecimal discountAmount;

    /** 充值余额前 */
    @Excel(name = "充值余额前")
    @ApiModelProperty("充值余额前")
    private BigDecimal rechargeAmountBefore;

    /** 充值余额后 */
    @Excel(name = "充值余额后")
    @ApiModelProperty("充值余额后")
    private BigDecimal rechargeAmountAfter;


}
