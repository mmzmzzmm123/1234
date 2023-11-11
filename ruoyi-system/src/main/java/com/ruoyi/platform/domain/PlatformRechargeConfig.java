package com.ruoyi.platform.domain;

import java.math.BigDecimal;
import com.ruoyi.common.annotation.Excel;
    import com.ruoyi.common.core.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 平台充值配置对象 bus_platform_recharge_config
 *
 * @author Lam
 * @date 2023-10-04
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Accessors(chain = true)
@ApiModel("admin-平台充值配置对象")
public class PlatformRechargeConfig extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /** 主键 */
    private Long id;

    /** 充值金额 */
    @Excel(name = "充值金额")
    @ApiModelProperty("充值金额")
    private BigDecimal amount;

    /** 赠送金额 */
    @Excel(name = "赠送金额")
    @ApiModelProperty("赠送金额")
    private BigDecimal giveAmount;

    @Excel(name = "排序")
    @ApiModelProperty("排序")
    private Long sortNum;
}
