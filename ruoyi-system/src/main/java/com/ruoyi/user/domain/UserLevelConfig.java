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
 * 用户等级配置对象 bus_user_level_config
 *
 * @author Lam
 * @date 2023-09-13
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Accessors(chain = true)
@ApiModel("admin-用户等级配置对象")
public class UserLevelConfig extends BaseEntity {

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

    /** 充值折扣 */
    @Excel(name = "充值折扣")
    @ApiModelProperty("充值折扣")
    private BigDecimal rechargeDiscount;

    /** 等级图标 */
    @Excel(name = "等级图标")
    @ApiModelProperty("等级图标")
    private String icoUrl;

    /** 头像框 */
    @Excel(name = "头像框")
    @ApiModelProperty("头像框")
    private String avatarUrl;


}
