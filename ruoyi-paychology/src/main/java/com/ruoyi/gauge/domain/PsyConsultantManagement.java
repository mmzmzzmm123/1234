package com.ruoyi.gauge.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 咨询师管理对象 psy_consultant_management
 * 
 * @author yangchuang
 * @date 2023-01-29
 */
public class PsyConsultantManagement extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private Long id;

    /** 咨询师名称 */
    @Excel(name = "咨询师名称")
    private String name;

    /** 所在城市 */
    @Excel(name = "所在城市")
    private String city;

    /** 性别 */
    @Excel(name = "性别")
    private Long sex;

    /** 咨询方向 */
    @Excel(name = "咨询方向")
    private String consultingDirection;

    /** 简介 */
    @Excel(name = "简介")
    private String summary;

    /** 咨询人数 */
    @Excel(name = "咨询人数")
    private Integer consultingNum;

    /** 服务时长 */
    @Excel(name = "服务时长")
    private Integer serviceDuration;

    /** 从业年限（或者存从业时间） */
    @Excel(name = "从业年限", readConverterExp = "或=者存从业时间")
    private Date employmentTime;

    /** 咨询服务（只存id） */
    @Excel(name = "咨询服务", readConverterExp = "只=存id")
    private String consultationMethods;

    /** 老师介绍 */
    @Excel(name = "老师介绍")
    private String introduce;

    /** 头像 */
    @Excel(name = "头像")
    private String headPortrait;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setName(String name) 
    {
        this.name = name;
    }

    public String getName() 
    {
        return name;
    }
    public void setCity(String city) 
    {
        this.city = city;
    }

    public String getCity() 
    {
        return city;
    }
    public void setSex(Long sex) 
    {
        this.sex = sex;
    }

    public Long getSex() 
    {
        return sex;
    }
    public void setConsultingDirection(String consultingDirection) 
    {
        this.consultingDirection = consultingDirection;
    }

    public String getConsultingDirection() 
    {
        return consultingDirection;
    }
    public void setSummary(String summary) 
    {
        this.summary = summary;
    }

    public String getSummary() 
    {
        return summary;
    }
    public void setConsultingNum(Integer consultingNum) 
    {
        this.consultingNum = consultingNum;
    }

    public Integer getConsultingNum() 
    {
        return consultingNum;
    }
    public void setServiceDuration(Integer serviceDuration) 
    {
        this.serviceDuration = serviceDuration;
    }

    public Integer getServiceDuration() 
    {
        return serviceDuration;
    }
    public void setEmploymentTime(Date employmentTime) 
    {
        this.employmentTime = employmentTime;
    }

    public Date getEmploymentTime() 
    {
        return employmentTime;
    }
    public void setConsultationMethods(String consultationMethods) 
    {
        this.consultationMethods = consultationMethods;
    }

    public String getConsultationMethods() 
    {
        return consultationMethods;
    }
    public void setIntroduce(String introduce) 
    {
        this.introduce = introduce;
    }

    public String getIntroduce() 
    {
        return introduce;
    }
    public void setHeadPortrait(String headPortrait) 
    {
        this.headPortrait = headPortrait;
    }

    public String getHeadPortrait() 
    {
        return headPortrait;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("name", getName())
            .append("city", getCity())
            .append("sex", getSex())
            .append("consultingDirection", getConsultingDirection())
            .append("summary", getSummary())
            .append("consultingNum", getConsultingNum())
            .append("serviceDuration", getServiceDuration())
            .append("employmentTime", getEmploymentTime())
            .append("consultationMethods", getConsultationMethods())
            .append("introduce", getIntroduce())
            .append("headPortrait", getHeadPortrait())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}
