package com.ruoyi.stu.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 班级年级参数对象 stu_cls
 * 
 * @author ruoyi
 * @date 2023-05-24
 */
public class StuCls extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** Id */
    private Long id;

    /** 班级 */
    @Excel(name = "班级")
    private String cls;

    /** 年级 */
    @Excel(name = "年级")
    private String clsYear;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setCls(String cls) 
    {
        this.cls = cls;
    }

    public String getCls() 
    {
        return cls;
    }
    public void setClsYear(String clsYear) 
    {
        this.clsYear = clsYear;
    }

    public String getClsYear() 
    {
        return clsYear;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("cls", getCls())
            .append("clsYear", getClsYear())
            .toString();
    }
}
