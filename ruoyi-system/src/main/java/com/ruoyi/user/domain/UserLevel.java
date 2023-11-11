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
 * 用户等级记录对象 bus_user_level
 *
 * @author Lam
 * @date 2023-09-13
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Accessors(chain = true)
@ApiModel("admin-用户等级记录对象")
public class UserLevel extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /** 主键 */
    private Long id;

    /** 用户标识 */
    @Excel(name = "用户标识")
    @ApiModelProperty("用户标识")
    private Long userId;

    /** 等级标识 */
    @Excel(name = "等级标识")
    @ApiModelProperty("等级标识")
    private Long levelConfigId;

    /** 当前积分 */
    @Excel(name = "当前积分")
    @ApiModelProperty("当前积分")
    private BigDecimal currentPoints;

    /** 当前等级 */
    @Excel(name = "当前等级")
    @ApiModelProperty("当前等级")
    private Long currentLevel;

    /** 总积分 */
    @Excel(name = "总积分")
    @ApiModelProperty("总积分")
    private BigDecimal totalPoints;


}
