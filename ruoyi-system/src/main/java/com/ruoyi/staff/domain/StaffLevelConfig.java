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
 * 员工等级配置对象 bus_staff_level_config
 *
 * @author Lam
 * @date 2023-09-14
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Accessors(chain = true)
@ApiModel("admin-员工等级配置对象")
public class StaffLevelConfig extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /** 主键 */
    private Long id;

    /** 名称 */
    @Excel(name = "名称")
    @ApiModelProperty("名称")
    private String name;

    /** 阈值 */
    @Excel(name = "阈值")
    @ApiModelProperty("阈值")
    private BigDecimal threshold;

    /** 等级 */
    @Excel(name = "等级")
    @ApiModelProperty("等级")
    private Long level;

    /** 首单提成 */
    @Excel(name = "首单提成")
    @ApiModelProperty("首单提成")
    private BigDecimal firstOrderRatio;

    /** 续单提成 */
    @Excel(name = "续单提成")
    @ApiModelProperty("续单提成")
    private BigDecimal orderRatio;

    /** 打赏提成 */
    @Excel(name = "打赏提成")
    @ApiModelProperty("打赏提成")
    private BigDecimal rewardRatio;


}
