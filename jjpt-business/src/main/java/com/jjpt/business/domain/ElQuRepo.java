package com.jjpt.business.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 试题题库关联对象 el_qu_repo
 * 
 * @author ruoyi
 * @date 2024-05-17
 */
public class ElQuRepo extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private String id;

    /** 试题 */
    @Excel(name = "试题")
    private String quId;

    /** 归属题库 */
    @Excel(name = "归属题库")
    private String repoId;

    /** 题目类型 */
    @Excel(name = "题目类型")
    private Long quType;

    /** 排序 */
    @Excel(name = "排序")
    private Long sort;

    public void setId(String id) 
    {
        this.id = id;
    }

    public String getId() 
    {
        return id;
    }
    public void setQuId(String quId) 
    {
        this.quId = quId;
    }

    public String getQuId() 
    {
        return quId;
    }
    public void setRepoId(String repoId) 
    {
        this.repoId = repoId;
    }

    public String getRepoId() 
    {
        return repoId;
    }
    public void setQuType(Long quType) 
    {
        this.quType = quType;
    }

    public Long getQuType() 
    {
        return quType;
    }
    public void setSort(Long sort) 
    {
        this.sort = sort;
    }

    public Long getSort() 
    {
        return sort;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("quId", getQuId())
            .append("repoId", getRepoId())
            .append("quType", getQuType())
            .append("sort", getSort())
            .toString();
    }
}
