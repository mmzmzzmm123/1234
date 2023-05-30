package com.ruoyi.office.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 微信用户信息 t_wx_user对象 t_wx_user
 * 
 * @author ruoyi
 * @date 2023-05-30
 */
public class TWxUser extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** id */
    private Long id;

    /** 昵称 */
    @Excel(name = "昵称")
    private String nickName;

    /** 微信app id */
    @Excel(name = "微信app id")
    private String appId;

    /** uinion id */
    @Excel(name = "uinion id")
    private String unionId;

    /** 手机号 */
    @Excel(name = "手机号")
    private String phone;

    /** 分享码 */
    @Excel(name = "分享码")
    private String shareCode;

    /** 微信code */
    @Excel(name = "微信code")
    private String wxCode;

    /** 邀请人手机号 */
    @Excel(name = "邀请人手机号")
    private String inviterPhone;

    /** 是否会员 */
    @Excel(name = "是否会员")
    private String isMember;

    /** 性别 */
    @Excel(name = "性别")
    private String sex;

    /** 登录ip */
    @Excel(name = "登录ip")
    private String loginIp;

    /** 登录时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "登录时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date loginDate;

    /** 状态 */
    @Excel(name = "状态")
    private Long status;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setNickName(String nickName) 
    {
        this.nickName = nickName;
    }

    public String getNickName() 
    {
        return nickName;
    }
    public void setAppId(String appId) 
    {
        this.appId = appId;
    }

    public String getAppId() 
    {
        return appId;
    }
    public void setUnionId(String unionId) 
    {
        this.unionId = unionId;
    }

    public String getUnionId() 
    {
        return unionId;
    }
    public void setPhone(String phone) 
    {
        this.phone = phone;
    }

    public String getPhone() 
    {
        return phone;
    }
    public void setShareCode(String shareCode) 
    {
        this.shareCode = shareCode;
    }

    public String getShareCode() 
    {
        return shareCode;
    }
    public void setWxCode(String wxCode) 
    {
        this.wxCode = wxCode;
    }

    public String getWxCode() 
    {
        return wxCode;
    }
    public void setInviterPhone(String inviterPhone) 
    {
        this.inviterPhone = inviterPhone;
    }

    public String getInviterPhone() 
    {
        return inviterPhone;
    }
    public void setIsMember(String isMember) 
    {
        this.isMember = isMember;
    }

    public String getIsMember() 
    {
        return isMember;
    }
    public void setSex(String sex) 
    {
        this.sex = sex;
    }

    public String getSex() 
    {
        return sex;
    }
    public void setLoginIp(String loginIp) 
    {
        this.loginIp = loginIp;
    }

    public String getLoginIp() 
    {
        return loginIp;
    }
    public void setLoginDate(Date loginDate) 
    {
        this.loginDate = loginDate;
    }

    public Date getLoginDate() 
    {
        return loginDate;
    }
    public void setStatus(Long status) 
    {
        this.status = status;
    }

    public Long getStatus() 
    {
        return status;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("nickName", getNickName())
            .append("appId", getAppId())
            .append("unionId", getUnionId())
            .append("phone", getPhone())
            .append("shareCode", getShareCode())
            .append("wxCode", getWxCode())
            .append("inviterPhone", getInviterPhone())
            .append("isMember", getIsMember())
            .append("sex", getSex())
            .append("loginIp", getLoginIp())
            .append("loginDate", getLoginDate())
            .append("status", getStatus())
            .append("remark", getRemark())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}
