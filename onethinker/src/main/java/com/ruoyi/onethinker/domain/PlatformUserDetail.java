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

    /**
     * 表主键
     */
    private Long id;

    /**
     * 是否可见，0为不可见，1为可见
     */
    @Excel(name = "是否可见，0为不可见，1为可见")
    private Long enabled;

    /**
     * 权重
     */
    @Excel(name = "权重")
    private Long weight;

    /**
     * 电话
     */
    @Excel(name = "电话")
    private Long phone;

    /**
     * 用户头像
     */
    @Excel(name = "用户头像")
    private String avatarUrl;

    /**
     * 用户类型: 1：微信用户
     */
    @Excel(name = "用户类型: 1：微信用户")
    private Long type;

    /**
     * 抽象id，针对不同平台标识唯一标识
     */
    @Excel(name = "抽象id，针对不同平台标识唯一标识")
    private Long dataId;

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

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setEnabled(Long enabled) {
        this.enabled = enabled;
    }

    public Long getEnabled() {
        return enabled;
    }

    public void setWeight(Long weight) {
        this.weight = weight;
    }

    public Long getWeight() {
        return weight;
    }

    public void setPhone(Long phone) {
        this.phone = phone;
    }

    public Long getPhone() {
        return phone;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setType(Long type) {
        this.type = type;
    }

    public Long getType() {
        return type;
    }

    public void setDataId(Long dataId) {
        this.dataId = dataId;
    }

    public Long getDataId() {
        return dataId;
    }

    public void setLoginEndTime(Long loginEndTime) {
        this.loginEndTime = loginEndTime;
    }

    public Long getLoginEndTime() {
        return loginEndTime;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
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
