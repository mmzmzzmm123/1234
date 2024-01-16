package com.onethinker.user.domain;

import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;
import lombok.Data;

/**
 * 平台用户详情信息对象 t_platform_user_detail
 *
 * @author yangyouqi
 * @date 2023-10-23
 */
@Data
public class PlatformUserDetail extends BaseEntity {
    private static final long serialVersionUID = 1L;

    public static final String DEFAULT_PASSWORD = "default_password";

    /**
     * 表主键
     */
    private Long id;

    /**
     * 是否可见，0为不可见，1为可见
     */
    @Excel(name = "是否可见，0为不可见，1为可见")
    private Integer enabled;

    /**
     * 权重
     */
    @Excel(name = "权重")
    private Long weight;

    /**
     * 用户头像
     */
    @Excel(name = "用户头像")
    private String avatarUrl;

    /**
     * 用户类型: 1：微信用户
     */
    @Excel(name = "用户类型: 1：微信用户 2:账户密码登录")
    private String type;

    /**
     * 抽象id，针对不同平台标识唯一标识
     */
    @Excel(name = "抽象id，针对不同平台标识唯一标识")
    private String dataId;

    /**
     * 最后登录时间
     */
    @Excel(name = "最后登录时间")
    private Long loginEndTime;

    /**
     * 用户账号
     */
    @Excel(name = "用户账号")
    private String username;

    /**
     * 用户密码
     */
    @Excel(name = "用户密码")
    private String password;

    /**
     * 用户昵称
     */
    @Excel(name = "用户昵称")
    private String nickName;
}
