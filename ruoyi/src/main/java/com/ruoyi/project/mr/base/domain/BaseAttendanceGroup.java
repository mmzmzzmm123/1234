package com.ruoyi.project.mr.base.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.framework.aspectj.lang.annotation.Excel;
import com.ruoyi.framework.web.domain.BaseEntity;

/**
 * 考勤组管理对象 base_attendance_group
 * 
 * @author mr
 * @date 2020-02-03
 */
public class BaseAttendanceGroup extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** UUID */
    private String id;

    /** 考勤组名称 */
    @Excel(name = "考勤组名称")
    private String attendanceName;

    /** 成员 */
    private String attendancePeople;

    /** 日历 */
    private String calendarId;

    /** 定位地址 */
    private String address;

    /** 定位误差 */
    private String addError;

    /** 拍照设置 */
    @Excel(name = "拍照设置")
    private String photoSet;

    /** 班次类型 */
    @Excel(name = "班次类型")
    private String classType;

    /** 班次 */
    @Excel(name = "班次")
    private String classes;

    public void setId(String id) 
    {
        this.id = id;
    }

    public String getId() 
    {
        return id;
    }
    public void setAttendanceName(String attendanceName) 
    {
        this.attendanceName = attendanceName;
    }

    public String getAttendanceName() 
    {
        return attendanceName;
    }
    public void setAttendancePeople(String attendancePeople) 
    {
        this.attendancePeople = attendancePeople;
    }

    public String getAttendancePeople() 
    {
        return attendancePeople;
    }
    public void setCalendarId(String calendarId) 
    {
        this.calendarId = calendarId;
    }

    public String getCalendarId() 
    {
        return calendarId;
    }
    public void setAddress(String address) 
    {
        this.address = address;
    }

    public String getAddress() 
    {
        return address;
    }
    public void setAddError(String addError) 
    {
        this.addError = addError;
    }

    public String getAddError() 
    {
        return addError;
    }
    public void setPhotoSet(String photoSet) 
    {
        this.photoSet = photoSet;
    }

    public String getPhotoSet() 
    {
        return photoSet;
    }
    public void setClassType(String classType) 
    {
        this.classType = classType;
    }

    public String getClassType() 
    {
        return classType;
    }
    public void setClasses(String classes) 
    {
        this.classes = classes;
    }

    public String getClasses() 
    {
        return classes;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("attendanceName", getAttendanceName())
            .append("attendancePeople", getAttendancePeople())
            .append("calendarId", getCalendarId())
            .append("address", getAddress())
            .append("addError", getAddError())
            .append("photoSet", getPhotoSet())
            .append("classType", getClassType())
            .append("classes", getClasses())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("remark", getRemark())
            .toString();
    }
}
