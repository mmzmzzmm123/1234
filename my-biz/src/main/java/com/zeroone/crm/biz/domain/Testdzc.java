package com.zeroone.crm.biz.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 测试使用对象 test_dzc
 * 
 * @author ruoyi
 * @date 2022-08-01
 */
public class Testdzc extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 客户表ID */
    private Integer id;

    /** 客户生日 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "客户生日", width = 30, dateFormat = "yyyy-MM-dd")
    private Date birthday;

    /** 客户名 */
    @Excel(name = "客户名")
    private String name;

    public void setId(Integer id) 
    {
        this.id = id;
    }

    public Integer getId() 
    {
        return id;
    }
    public void setBirthday(Date birthday) 
    {
        this.birthday = birthday;
    }

    public Date getBirthday() 
    {
        return birthday;
    }
    public void setName(String name) 
    {
        this.name = name;
    }

    public String getName() 
    {
        return name;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("birthday", getBirthday())
            .append("name", getName())
            .toString();
    }
}
