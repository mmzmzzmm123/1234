package com.gox.basic.domain;

import com.gox.common.annotation.Excel;
import com.gox.common.core.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 档号设置对象 archival_code_setting
 * 
 * @author gox
 * @date 2021-01-23
 */
public class ArchivalCodeSetting extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** id */
    private Long id;

    /** 部门id */
    @Excel(name = "部门id")
    private Long deptId;

    /** 字段1 */
    @Excel(name = "字段1")
    private String field1;

    /** 字段2 */
    @Excel(name = "字段2")
    private String field2;

    /** 字段3 */
    @Excel(name = "字段3")
    private String field3;

    /** 字段4 */
    @Excel(name = "字段4")
    private String field4;

    /** 字段5 */
    @Excel(name = "字段5")
    private String field5;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setDeptId(Long deptId) 
    {
        this.deptId = deptId;
    }

    public Long getDeptId() 
    {
        return deptId;
    }
    public void setField1(String field1) 
    {
        this.field1 = field1;
    }

    public String getField1() 
    {
        return field1;
    }
    public void setField2(String field2) 
    {
        this.field2 = field2;
    }

    public String getField2() 
    {
        return field2;
    }
    public void setField3(String field3) 
    {
        this.field3 = field3;
    }

    public String getField3() 
    {
        return field3;
    }
    public void setField4(String field4) 
    {
        this.field4 = field4;
    }

    public String getField4() 
    {
        return field4;
    }
    public void setField5(String field5) 
    {
        this.field5 = field5;
    }

    public String getField5() 
    {
        return field5;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("deptId", getDeptId())
            .append("field1", getField1())
            .append("field2", getField2())
            .append("field3", getField3())
            .append("field4", getField4())
            .append("field5", getField5())
            .toString();
    }
}
