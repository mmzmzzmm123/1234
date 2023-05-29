package com.ruoyi.office.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 微信用户信息对象 t_wx_user
 * 
 * @author ruoyi
 * @date 2023-05-29
 */
public class WxUser extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** id */
    private Long id;

    /** 微信app id */
    @Excel(name = "微信app id")
    private String appId;

    /** uinion id */
    @Excel(name = "uinion id")
    private String unionId;

    /** 微信code */
    @Excel(name = "微信code")
    private String wxCode;

    /** 昵称 */
    @Excel(name = "昵称")
    private String nickName;

    /** 性别 */
    @Excel(name = "性别")
    private String sex;

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
    public void setWxCode(String wxCode) 
    {
        this.wxCode = wxCode;
    }

    public String getWxCode() 
    {
        return wxCode;
    }
    public void setNickName(String nickName) 
    {
        this.nickName = nickName;
    }

    public String getNickName() 
    {
        return nickName;
    }
    public void setSex(String sex) 
    {
        this.sex = sex;
    }

    public String getSex() 
    {
        return sex;
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
            .append("appId", getAppId())
            .append("unionId", getUnionId())
            .append("wxCode", getWxCode())
            .append("nickName", getNickName())
            .append("sex", getSex())
            .append("status", getStatus())
            .append("remark", getRemark())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}
