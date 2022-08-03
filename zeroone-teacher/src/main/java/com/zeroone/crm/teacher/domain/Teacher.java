package com.zeroone.crm.teacher.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 老师信息对象 teacher
 * 
 * @author ruoyi
 * @date 2022-08-02
 */
public class Teacher extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** id */
    private Long id;

    /** 教工号 */
    @Excel(name = "教工号")
    private Long tId;

    /** 姓名 */
    @Excel(name = "姓名")
    private String tName;

    /** 性别 */
    @Excel(name = "性别")
    private String sex;

    /** 电话 */
    @Excel(name = "电话")
    private String phone;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void settId(Long tId) 
    {
        this.tId = tId;
    }

    public Long gettId() 
    {
        return tId;
    }
    public void settName(String tName) 
    {
        this.tName = tName;
    }

    public String gettName() 
    {
        return tName;
    }
    public void setSex(String sex) 
    {
        this.sex = sex;
    }

    public String getSex() 
    {
        return sex;
    }
    public void setPhone(String phone) 
    {
        this.phone = phone;
    }

    public String getPhone() 
    {
        return phone;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("tId", gettId())
            .append("tName", gettName())
            .append("sex", getSex())
            .append("phone", getPhone())
            .toString();
    }
}
