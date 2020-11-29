package com.stdiet.custom.domain;

import java.math.BigDecimal;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.stdiet.common.annotation.Excel;
import com.stdiet.common.core.domain.BaseEntity;

/**
 * 微信用户记录对象 sys_wx_user_log
 *
 * @author wonder
 * @date 2020-11-29
 */
public class SysWxUserLog extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 微信openid */
    private String openid;

    /** 体重 */
    @Excel(name = "体重")
    private BigDecimal weight;

    /** 微信appid */
    @Excel(name = "微信appid")
    private String appid;

    /** 电话 */
    @Excel(name = "电话")
    private String phone;

    /** 睡觉时间 */
    @Excel(name = "睡觉时间")
    private String sleepTime;

    /** 起床时间 */
    @Excel(name = "起床时间")
    private String wakeupTime;

    /** 运动情况（Y是 N否） */
    @Excel(name = "运动情况", readConverterExp = "Y=是,N=否")
    private String sport;

    /** 用户头像 */
    @Excel(name = "用户头像")
    private String avatarUrl;

    /** 饮食情况（Y是 N否） */
    @Excel(name = "饮食情况", readConverterExp = "Y=是,N=否")
    private String diet;

    /** 熬夜失眠（Y是 N否） */
    @Excel(name = "熬夜失眠", readConverterExp = "Y=是,N=否")
    private String insomnia;

    /** 排便情况（Y是 N否） */
    @Excel(name = "排便情况", readConverterExp = "Y=是,N=否")
    private String defecation;

    /** 饮水量 */
    @Excel(name = "饮水量")
    private Long water;

    public void setOpenid(String openid)
    {
        this.openid = openid;
    }

    public String getOpenid()
    {
        return openid;
    }
    public void setWeight(BigDecimal weight)
    {
        this.weight = weight;
    }

    public BigDecimal getWeight()
    {
        return weight;
    }
    public void setAppid(String appid)
    {
        this.appid = appid;
    }

    public String getAppid()
    {
        return appid;
    }
    public void setPhone(String phone)
    {
        this.phone = phone;
    }

    public String getPhone()
    {
        return phone;
    }
    public void setSleepTime(String sleepTime)
    {
        this.sleepTime = sleepTime;
    }

    public String getSleepTime()
    {
        return sleepTime;
    }
    public void setWakeupTime(String wakeupTime)
    {
        this.wakeupTime = wakeupTime;
    }

    public String getWakeupTime()
    {
        return wakeupTime;
    }
    public void setSport(String sport)
    {
        this.sport = sport;
    }

    public String getSport()
    {
        return sport;
    }
    public void setAvatarUrl(String avatarUrl)
    {
        this.avatarUrl = avatarUrl;
    }

    public String getAvatarUrl()
    {
        return avatarUrl;
    }
    public void setDiet(String diet)
    {
        this.diet = diet;
    }

    public String getDiet()
    {
        return diet;
    }
    public void setInsomnia(String insomnia)
    {
        this.insomnia = insomnia;
    }

    public String getInsomnia()
    {
        return insomnia;
    }
    public void setDefecation(String defecation)
    {
        this.defecation = defecation;
    }

    public String getDefecation()
    {
        return defecation;
    }
    public void setWater(Long water)
    {
        this.water = water;
    }

    public Long getWater()
    {
        return water;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
                .append("openid", getOpenid())
                .append("weight", getWeight())
                .append("appid", getAppid())
                .append("phone", getPhone())
                .append("sleepTime", getSleepTime())
                .append("wakeupTime", getWakeupTime())
                .append("sport", getSport())
                .append("avatarUrl", getAvatarUrl())
                .append("diet", getDiet())
                .append("insomnia", getInsomnia())
                .append("defecation", getDefecation())
                .append("water", getWater())
                .append("createBy", getCreateBy())
                .append("createTime", getCreateTime())
                .append("updateBy", getUpdateBy())
                .append("updateTime", getUpdateTime())
                .append("remark", getRemark())
                .toString();
    }
}