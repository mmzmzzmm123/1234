package com.ruoyi.headmaster.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 主任信息对象 headmaster
 * 
 * @author ruoyi
 * @date 2022-08-03
 */
public class Headmaster extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** id */
    private Long id;

    /** 班主任id */
    @Excel(name = "班主任id")
    private Long headmasterId;

    /** 班主任姓名 */
    @Excel(name = "班主任姓名")
    private String headmasterName;

    /** 性别 */
    @Excel(name = "性别")
    private String sex;

    /** 年龄 */
    @Excel(name = "年龄")
    private Integer age;

    /** 电话 */
    @Excel(name = "电话")
    private String phone;

    /** 班级id */
    @Excel(name = "班级id")
    private Long clazzId;

    /** 年级id */
    @Excel(name = "年级id")
    private Long gradeId;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setHeadmasterId(Long headmasterId) 
    {
        this.headmasterId = headmasterId;
    }

    public Long getHeadmasterId() 
    {
        return headmasterId;
    }
    public void setHeadmasterName(String headmasterName) 
    {
        this.headmasterName = headmasterName;
    }

    public String getHeadmasterName() 
    {
        return headmasterName;
    }
    public void setSex(String sex) 
    {
        this.sex = sex;
    }

    public String getSex() 
    {
        return sex;
    }
    public void setAge(Integer age) 
    {
        this.age = age;
    }

    public Integer getAge() 
    {
        return age;
    }
    public void setPhone(String phone) 
    {
        this.phone = phone;
    }

    public String getPhone() 
    {
        return phone;
    }
    public void setClazzId(Long clazzId) 
    {
        this.clazzId = clazzId;
    }

    public Long getClazzId() 
    {
        return clazzId;
    }
    public void setGradeId(Long gradeId) 
    {
        this.gradeId = gradeId;
    }

    public Long getGradeId() 
    {
        return gradeId;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("headmasterId", getHeadmasterId())
            .append("headmasterName", getHeadmasterName())
            .append("sex", getSex())
            .append("age", getAge())
            .append("phone", getPhone())
            .append("clazzId", getClazzId())
            .append("gradeId", getGradeId())
            .toString();
    }
}
