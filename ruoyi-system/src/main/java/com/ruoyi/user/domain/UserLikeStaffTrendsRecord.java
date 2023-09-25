package com.ruoyi.user.domain;

import com.ruoyi.common.annotation.Excel;
    import com.ruoyi.common.core.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 点赞动态记录对象 bus_user_like_staff_trends_record
 *
 * @author Lam
 * @date 2023-09-21
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Accessors(chain = true)
@ApiModel("admin-点赞动态记录对象")
public class UserLikeStaffTrendsRecord extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /** 主键 */
    private Long id;

    /** 用户标识 */
    @Excel(name = "用户标识")
    @ApiModelProperty("用户标识")
    private Long userId;

    /** 动态标识 */
    @Excel(name = "动态标识")
    @ApiModelProperty("动态标识")
    private Long staffTrendsId;


}
