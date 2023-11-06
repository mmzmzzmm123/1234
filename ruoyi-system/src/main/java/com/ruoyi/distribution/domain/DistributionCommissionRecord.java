package com.ruoyi.distribution.domain;

import java.math.BigDecimal;
import com.ruoyi.common.annotation.Excel;
    import com.ruoyi.common.core.domain.BaseEntity;
import com.ruoyi.user.domain.UserInfo;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 分销佣金记录对象 bus_distribution_commission_record
 *
 * @author Lam
 * @date 2023-11-02
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Accessors(chain = true)
@ApiModel("admin-分销佣金记录对象")
public class DistributionCommissionRecord extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /** 主键 */
    private Long id;

    /** 用户标识 */
    @Excel(name = "用户标识")
    @ApiModelProperty("用户标识")
    private Long userId;

    /** 订单标识 */
    @Excel(name = "订单标识")
    @ApiModelProperty("订单标识")
    private Long orderId;

    @Excel(name = "分销佣金记录类型", dictType = "commission_record_type")
    @ApiModelProperty("分销佣金记录类型")
    private String commissionRecordType;

    /** 订单金额 */
    @Excel(name = "订单金额")
    @ApiModelProperty("订单金额")
    private BigDecimal orderAmount;

    /** 分销用户 */
    @Excel(name = "分销用户")
    @ApiModelProperty("分销用户")
    private Long distributionUserId;

    @ApiModelProperty("分销用户信息")
    private UserInfo distributionUserInfo;

    /** 分销层级 */
    @Excel(name = "分销层级")
    @ApiModelProperty("分销层级")
    private Long distributionLevel;

    /** 分销比例 */
    @Excel(name = "分销比例")
    @ApiModelProperty("分销比例")
    private BigDecimal distributionRate;

    /** 分销佣金 */
    @Excel(name = "分销佣金")
    @ApiModelProperty("分销佣金")
    private BigDecimal distributionCommission;


}
