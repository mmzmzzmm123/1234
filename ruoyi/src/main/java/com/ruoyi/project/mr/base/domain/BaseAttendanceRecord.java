package com.ruoyi.project.mr.base.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.framework.aspectj.lang.annotation.Excel;
import com.ruoyi.framework.web.domain.BaseEntity;
import java.util.Date;

/**
 * 考勤记录对象 base_attendance_record
 *
 * @author mr
 * @date 2020-02-07
 */
public class BaseAttendanceRecord extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** UUID */
    private String id;

    /** 考勤组名称 */
    @Excel(name = "考勤组名称")
    private String deptName;

    /** 姓名 */
    @Excel(name = "姓名")
    private String userName;

    /** 手机号码 */
    @Excel(name = "手机号码")
    private String phonenumber;

    /** 班次 */
    @Excel(name = "班次")
    private String classes;

    /** 工作日期 */
    @Excel(name = "工作日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date workDate;

    /** 打卡日期 */
    @Excel(name = "打卡日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date punchDate;

    /** 时间段 */
    @Excel(name = "时间段")
    private String timeQuantum;

    /** 打卡类型 */
    @Excel(name = "打卡类型")
    private String punchType;

    /** 打卡时间 */
    @Excel(name = "打卡时间", width = 50, dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date punchTime;

    /** 状态 */
    @Excel(name = "状态")
    private String status;

    /** 拍照路径 */
    @Excel(name = "拍照路径")
    private String picUrl;

    /** 定位地址 */
    @Excel(name = "定位地址")
    private String address;

    public void setId(String id)
    {
        this.id = id;
    }

    public String getId()
    {
        return id;
    }
    public void setDeptName(String deptName)
    {
        this.deptName = deptName;
    }

    public String getDeptName()
    {
        return deptName;
    }
    public void setUserName(String userName)
    {
        this.userName = userName;
    }

    public String getUserName()
    {
        return userName;
    }
    public void setPhonenumber(String phonenumber)
    {
        this.phonenumber = phonenumber;
    }

    public String getPhonenumber()
    {
        return phonenumber;
    }
    public void setClasses(String classes)
    {
        this.classes = classes;
    }

    public String getClasses()
    {
        return classes;
    }
    public void setWorkDate(Date workDate)
    {
        this.workDate = workDate;
    }

    public Date getWorkDate()
    {
        return workDate;
    }
    public void setPunchDate(Date punchDate)
    {
        this.punchDate = punchDate;
    }

    public Date getPunchDate()
    {
        return punchDate;
    }
    public void setTimeQuantum(String timeQuantum)
    {
        this.timeQuantum = timeQuantum;
    }

    public String getTimeQuantum()
    {
        return timeQuantum;
    }
    public void setPunchType(String punchType)
    {
        this.punchType = punchType;
    }

    public String getPunchType()
    {
        return punchType;
    }
    public void setPunchTime(Date punchTime)
    {
        this.punchTime = punchTime;
    }

    public Date getPunchTime()
    {
        return punchTime;
    }
    public void setStatus(String status)
    {
        this.status = status;
    }

    public String getStatus()
    {
        return status;
    }
    public void setPicUrl(String picUrl)
    {
        this.picUrl = picUrl;
    }

    public String getPicUrl()
    {
        return picUrl;
    }
    public void setAddress(String address)
    {
        this.address = address;
    }

    public String getAddress()
    {
        return address;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("deptName", getDeptName())
            .append("userName", getUserName())
            .append("phonenumber", getPhonenumber())
            .append("classes", getClasses())
            .append("workDate", getWorkDate())
            .append("punchDate", getPunchDate())
            .append("timeQuantum", getTimeQuantum())
            .append("punchType", getPunchType())
            .append("punchTime", getPunchTime())
            .append("status", getStatus())
            .append("picUrl", getPicUrl())
            .append("address", getAddress())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("remark", getRemark())
            .toString();
    }
}
