package com.ruoyi.distribution.domain;

import com.ruoyi.common.annotation.Excel;
    import com.ruoyi.common.core.domain.BaseEntity;
import com.ruoyi.user.domain.UserInfo;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 分销关系对象 bus_distribution_relation
 *
 * @author Lam
 * @date 2023-11-02
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Accessors(chain = true)
@ApiModel("admin-分销关系对象")
public class DistributionRelation extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /** 主键 */
    private Long id;

    /** 层级 */
    @Excel(name = "层级")
    @ApiModelProperty("层级")
    private Long level;

    /** 父级 */
    @Excel(name = "父级")
    @ApiModelProperty("父级")
    private Long pId;

    /** 子级 */
    @Excel(name = "子级")
    @ApiModelProperty("子级")
    private Long cId;

    @ApiModelProperty("子级用户信息")
    private UserInfo childUserInfo;
}
