package com.ruoyi.onethinker.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 平台用户对象 t_platform_user
 *
 * @author yangyouqi
 * @date 2023-10-23
 */
public class PlatformUser extends BaseEntity {

    private static final long serialVersionUID = 1L;

    public static final String DEFAULT_AVATAR_URL = "default_avatar_url";

    public static final String DEFAULT_NICK_NAME = "default_nick_name";

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
     * 用户昵称
     */
    @Excel(name = "用户昵称")
    private String nickName;

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

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setEnabled(Integer enabled) {
        this.enabled = enabled;
    }

    public Integer getEnabled() {
        return enabled;
    }

    public void setWeight(Long weight) {
        this.weight = weight;
    }

    public Long getWeight() {
        return weight;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getNickName() {
        return nickName;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPhone() {
        return phone;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("id", getId())
                .append("createTime", getCreateTime())
                .append("updateTime", getUpdateTime())
                .append("enabled", getEnabled())
                .append("weight", getWeight())
                .append("nickName", getNickName())
                .append("phone", getPhone())
                .append("avatarUrl", getAvatarUrl())
                .append("remark", getRemark())
                .toString();
    }
}
