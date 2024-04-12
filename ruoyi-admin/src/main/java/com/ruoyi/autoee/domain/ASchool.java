package com.ruoyi.autoee.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 学校管理对象 a_school
 * 
 * @author ruoyi
 * @date 2024-04-12
 */
public class ASchool extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 学校编号 */
    @Excel(name = "学校编号")
    private Long id;

    /** 学校名称 */
    @Excel(name = "学校名称")
    private String schoolName;

    /** 学校类型 */
    @Excel(name = "学校类型")
    private String schoolType;

    /** 所在地区 */
    @Excel(name = "所在地区")
    private String location;

    /** 校园面积 */
    @Excel(name = "校园面积")
    private String campusArea;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setSchoolName(String schoolName) 
    {
        this.schoolName = schoolName;
    }

    public String getSchoolName() 
    {
        return schoolName;
    }
    public void setSchoolType(String schoolType) 
    {
        this.schoolType = schoolType;
    }

    public String getSchoolType() 
    {
        return schoolType;
    }
    public void setLocation(String location) 
    {
        this.location = location;
    }

    public String getLocation() 
    {
        return location;
    }
    public void setCampusArea(String campusArea) 
    {
        this.campusArea = campusArea;
    }

    public String getCampusArea() 
    {
        return campusArea;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("schoolName", getSchoolName())
            .append("schoolType", getSchoolType())
            .append("location", getLocation())
            .append("campusArea", getCampusArea())
            .append("delFlag", getDelFlag())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}
