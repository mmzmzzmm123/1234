package com.onethinker.user.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.onethinker.common.annotation.Excel;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 平台用户详情信息对象 t_platform_user_detail
 *
 * @author yangyouqi
 * @date 2023-10-23
 */
@Data
@TableName("t_platform_user_detail")
public class PlatformUserDetail implements Serializable {

    /**
     * 启用
     */
    public static final Integer STATE_TYPE_ENABLED = 1;

    /**
     * 禁用
     */
    public static final Integer STATE_TYPE_DISABLE = 0;


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

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    /**
     * 更新时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;

    @Excel(name = "备注")
    private String remark;
}
