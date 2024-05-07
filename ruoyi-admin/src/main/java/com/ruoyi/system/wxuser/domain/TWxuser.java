package com.ruoyi.system.wxuser.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 微信用户对象 t_wxuser
 *
 * @author ruoyi
 * @date 2024-04-19
 */
public class TWxuser extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 自增id */
    private Long id;

    /** 租户ID */
    private String tenantId;

    /** 用户ID */
    private Long userId;

    /** 小程序Id */
    private String mpId;

    /** 微信unionId */
    private String unionId;

    /** 微信头像 */
    @Excel(name = "微信头像")
    private String avatar;

    /** 微信手机号 */
    @Excel(name = "微信手机号")
    private String mobile;

    /** 微信昵称 */
    @Excel(name = "微信昵称")
    private String nickname;

    /** vip等级 */
    @Excel(name = "vip等级")
    private Integer vip;

    public void setId(Long id)
    {
        this.id = id;
    }

    public Long getId()
    {
        return id;
    }
    public void setTenantId(String tenantId)
    {
        this.tenantId = tenantId;
    }

    public String getTenantId()
    {
        return tenantId;
    }
    public void setUserId(Long userId)
    {
        this.userId = userId;
    }

    public Long getUserId()
    {
        return userId;
    }
    public void setMpId(String mpId)
    {
        this.mpId = mpId;
    }

    public String getMpId()
    {
        return mpId;
    }
    public void setUnionId(String unionId)
    {
        this.unionId = unionId;
    }

    public String getUnionId()
    {
        return unionId;
    }
    public void setAvatar(String avatar)
    {
        this.avatar = avatar;
    }

    public String getAvatar()
    {
        return avatar;
    }
    public void setMobile(String mobile)
    {
        this.mobile = mobile;
    }

    public String getMobile()
    {
        return mobile;
    }
    public void setNickname(String nickname)
    {
        this.nickname = nickname;
    }

    public String getNickname()
    {
        return nickname;
    }
    public void setVip(Integer vip)
    {
        this.vip = vip;
    }

    public Integer getVip()
    {
        return vip;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
                .append("id", getId())
                .append("tenantId", getTenantId())
                .append("userId", getUserId())
                .append("mpId", getMpId())
                .append("unionId", getUnionId())
                .append("avatar", getAvatar())
                .append("mobile", getMobile())
                .append("nickname", getNickname())
                .append("vip", getVip())
                .append("createTime", getCreateTime())
                .append("updateTime", getUpdateTime())
                .toString();
    }
}
