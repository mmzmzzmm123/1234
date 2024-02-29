package com.ruoyi.system.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 用户展示信息对象 vehicle_userinfo
 * 
 * @author carol
 * @date 2024-02-29
 */
public class VehicleUserinfo extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 用户id */
    private Long id;

    /** 微信用户唯一标识 */
    @Excel(name = "微信用户唯一标识")
    private String openid;

    /** 姓名 */
    @Excel(name = "姓名")
    private String name;

    /** 性别（0未知1男2女） */
    @Excel(name = "性别", readConverterExp = "0=未知1男2女")
    private Long sex;

    /** 头像 */
    @Excel(name = "头像")
    private String avatarUrl;

    /** 手机号码 */
    @Excel(name = "手机号码")
    private String phone;

    /** 所在地 */
    @Excel(name = "所在地")
    private String address;

    /** 出生年月 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "出生年月", width = 30, dateFormat = "yyyy-MM-dd")
    private Date birth;

    /** 邮箱 */
    @Excel(name = "邮箱")
    private String email;

    /** 单位 */
    @Excel(name = "单位")
    private String unit;

    /** 网名 */
    @Excel(name = "网名")
    private String nickName;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setOpenid(String openid) 
    {
        this.openid = openid;
    }

    public String getOpenid() 
    {
        return openid;
    }
    public void setName(String name) 
    {
        this.name = name;
    }

    public String getName() 
    {
        return name;
    }
    public void setSex(Long sex) 
    {
        this.sex = sex;
    }

    public Long getSex() 
    {
        return sex;
    }
    public void setAvatarUrl(String avatarUrl) 
    {
        this.avatarUrl = avatarUrl;
    }

    public String getAvatarUrl() 
    {
        return avatarUrl;
    }
    public void setPhone(String phone) 
    {
        this.phone = phone;
    }

    public String getPhone() 
    {
        return phone;
    }
    public void setAddress(String address) 
    {
        this.address = address;
    }

    public String getAddress() 
    {
        return address;
    }
    public void setBirth(Date birth) 
    {
        this.birth = birth;
    }

    public Date getBirth() 
    {
        return birth;
    }
    public void setEmail(String email) 
    {
        this.email = email;
    }

    public String getEmail() 
    {
        return email;
    }
    public void setUnit(String unit) 
    {
        this.unit = unit;
    }

    public String getUnit() 
    {
        return unit;
    }
    public void setNickName(String nickName) 
    {
        this.nickName = nickName;
    }

    public String getNickName() 
    {
        return nickName;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("openid", getOpenid())
            .append("name", getName())
            .append("sex", getSex())
            .append("avatarUrl", getAvatarUrl())
            .append("phone", getPhone())
            .append("address", getAddress())
            .append("birth", getBirth())
            .append("email", getEmail())
            .append("unit", getUnit())
            .append("createTime", getCreateTime())
            .append("nickName", getNickName())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}
