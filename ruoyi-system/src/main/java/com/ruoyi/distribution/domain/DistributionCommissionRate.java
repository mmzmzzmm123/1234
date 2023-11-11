package com.ruoyi.distribution.domain;

import java.math.BigDecimal;
import com.ruoyi.common.annotation.Excel;
    import com.ruoyi.common.core.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 分销佣金比例对象 bus_distribution_commission_rate
 *
 * @author Lam
 * @date 2023-11-02
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Accessors(chain = true)
@ApiModel("admin-分销佣金比例对象")
public class DistributionCommissionRate extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /** 主键 */
    private Long id;

    /** 层级 */
    @Excel(name = "层级")
    @ApiModelProperty("层级")
    private Long level;

    /** 比例 */
    @Excel(name = "比例")
    @ApiModelProperty("比例")
    private BigDecimal rate;


}
