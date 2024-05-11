package com.jjpt.business.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 题库信息对象 el_repo
 * 
 * @author ruoyi
 * @date 2024-05-11
 */
public class Repo extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 题库ID */
    private String id;

    /** 题库编号 */
    @Excel(name = "题库编号")
    private String code;

    /** 题库名称 */
    @Excel(name = "题库名称")
    private String title;

    public void setId(String id) 
    {
        this.id = id;
    }

    public String getId() 
    {
        return id;
    }
    public void setCode(String code) 
    {
        this.code = code;
    }

    public String getCode() 
    {
        return code;
    }
    public void setTitle(String title) 
    {
        this.title = title;
    }

    public String getTitle() 
    {
        return title;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("code", getCode())
            .append("title", getTitle())
            .append("remark", getRemark())
            .append("createTime", getCreateTime())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}
