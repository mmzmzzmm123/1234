package com.ruoyi.user.domain;

import com.ruoyi.common.annotation.Excel;
    import com.ruoyi.common.core.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 用户社交账号管理对象 bus_user_social_accounts
 *
 * @author Lam
 * @date 2023-09-13
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Accessors(chain = true)
@ApiModel("admin-用户社交账号管理对象")
public class UserSocialAccounts extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /** 标识 */
    private Long id;

    /** 用户标识 */
    @Excel(name = "用户标识")
    @ApiModelProperty("用户标识")
    private Long userId;

    /** 账号服务商 */
    @Excel(name = "账号服务商")
    @ApiModelProperty("账号服务商")
    private String accountServiceProvider;

    /** 账号 */
    @Excel(name = "账号")
    @ApiModelProperty("账号")
    private String num;

    /** 是否默认 */
    @Excel(name = "是否默认")
    @ApiModelProperty("是否默认")
    private String ifDefault;


}
