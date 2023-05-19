package com.ruoyi.stu.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 学生信息对象 stu_info
 * 
 * @author ruoyi
 * @date 2023-05-19
 */
public class StuInfo extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键id */
    private Long stuId;

    /** 学号 */
    @Excel(name = "学号")
    private String stuNo;

    /** 姓名 */
    @Excel(name = "姓名")
    private String stuName;

    /** 班级 */
    @Excel(name = "班级")
    private String stuCls;

    /** 年级 */
    @Excel(name = "年级")
    private String stuClsYear;

    /** 性别 */
    @Excel(name = "性别")
    private String stuSex;

    /** 学生类型 */
    @Excel(name = "学生类型")
    private String stuType;

    /** 录取专业 */
    @Excel(name = "录取专业")
    private String stuMajor;

    /** 家庭住址 */
    @Excel(name = "家庭住址")
    private String stuAddress;

    /** 联系电话 */
    @Excel(name = "联系电话")
    private String stuTel;

    public void setStuId(Long stuId) 
    {
        this.stuId = stuId;
    }

    public Long getStuId() 
    {
        return stuId;
    }
    public void setStuNo(String stuNo) 
    {
        this.stuNo = stuNo;
    }

    public String getStuNo() 
    {
        return stuNo;
    }
    public void setStuName(String stuName) 
    {
        this.stuName = stuName;
    }

    public String getStuName() 
    {
        return stuName;
    }
    public void setStuCls(String stuCls) 
    {
        this.stuCls = stuCls;
    }

    public String getStuCls() 
    {
        return stuCls;
    }
    public void setStuClsYear(String stuClsYear) 
    {
        this.stuClsYear = stuClsYear;
    }

    public String getStuClsYear() 
    {
        return stuClsYear;
    }
    public void setStuSex(String stuSex) 
    {
        this.stuSex = stuSex;
    }

    public String getStuSex() 
    {
        return stuSex;
    }
    public void setStuType(String stuType) 
    {
        this.stuType = stuType;
    }

    public String getStuType() 
    {
        return stuType;
    }
    public void setStuMajor(String stuMajor) 
    {
        this.stuMajor = stuMajor;
    }

    public String getStuMajor() 
    {
        return stuMajor;
    }
    public void setStuAddress(String stuAddress) 
    {
        this.stuAddress = stuAddress;
    }

    public String getStuAddress() 
    {
        return stuAddress;
    }
    public void setStuTel(String stuTel) 
    {
        this.stuTel = stuTel;
    }

    public String getStuTel() 
    {
        return stuTel;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("stuId", getStuId())
            .append("stuNo", getStuNo())
            .append("stuName", getStuName())
            .append("stuCls", getStuCls())
            .append("stuClsYear", getStuClsYear())
            .append("stuSex", getStuSex())
            .append("stuType", getStuType())
            .append("stuMajor", getStuMajor())
            .append("stuAddress", getStuAddress())
            .append("stuTel", getStuTel())
            .toString();
    }
}
