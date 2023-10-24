package com.ruoyi.onethinker.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 平台用户详情信息对象 t_platform_user_detail
 *
 * @author yangyouqi
 * @date 2023-10-23
 */
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
     * 电话
     */
    @Excel(name = "电话")
    private String phone;

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

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getEnabled() {
        return enabled;
    }

    public void setEnabled(Integer enabled) {
        this.enabled = enabled;
    }

    public Long getWeight() {
        return weight;
    }

    public void setWeight(Long weight) {
        this.weight = weight;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDataId() {
        return dataId;
    }

    public void setDataId(String dataId) {
        this.dataId = dataId;
    }

    public Long getLoginEndTime() {
        return loginEndTime;
    }

    public void setLoginEndTime(Long loginEndTime) {
        this.loginEndTime = loginEndTime;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("id", getId())
                .append("createTime", getCreateTime())
                .append("updateTime", getUpdateTime())
                .append("enabled", getEnabled())
                .append("weight", getWeight())
                .append("phone", getPhone())
                .append("avatarUrl", getAvatarUrl())
                .append("type", getType())
                .append("dataId", getDataId())
                .append("loginEndTime", getLoginEndTime())
                .append("remark", getRemark())
                .append("username", getUsername())
                .append("password", getPassword())
                .toString();
    }
}
