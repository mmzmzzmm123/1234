package com.ruoyi.bookmark.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 书签_标签对象 sq_tag
 * 
 * @author ruoyi
 * @date 2020-09-04
 */
public class SqTag extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 书签标签id */
    private Long id;

    /** 标签名字 */
    @Excel(name = "标签名字")
    private String name;

    /** 引用数量 */
    @Excel(name = "引用数量")
    private Integer icount;

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
    public void setIcount(Integer icount) 
    {
        this.icount = icount;
    }

    public Integer getIcount() 
    {
        return icount;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("name", getName())
            .append("icount", getIcount())
            .append("createTime", getCreateTime())
            .toString();
    }
}
