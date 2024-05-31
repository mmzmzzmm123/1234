package com.onethinker.user.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.onethinker.common.annotation.Excel;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 平台用户详情信息对象 t_platform_user
 *
 * @author yangyouqi
 * @date 2023-10-23
 */
@Data
@TableName("t_platform_user")
public class PlatformUser implements Serializable {

    private static final long serialVersionUID = 1L;

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
     * 用户头像
     */
    @Excel(name = "用户平台头像")
    private String avatarUrl;

    /**
     * 用户类型
     */
    @Excel(name = "用户类型")
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
     * 用户手机号 每个渠道来源下唯一
     */
    @Excel(name = "用户手机号")
    private String phone;

    /**
     * 用户邮箱 每个渠道来源下唯一
     */
    @Excel(name = "邮箱")
    private String email;

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
