package com.ruoyi.bookmark.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 标签管理对象 sq_user_tag
 * 
 * @author wang
 * @date 2020-09-04
 */
public class SqUserTag extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** ID */
    private Long id;

    /** 用户ID */
    @Excel(name = "用户ID")
    private Long userId;

    /** 书签ID */
    @Excel(name = "书签ID")
    private Long tagId;

    /** 书签引用的数量 */
    @Excel(name = "书签引用的数量")
    private Integer icount;

    /** 书签排序 */
    @Excel(name = "书签排序")
    private Integer iorder;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setUserId(Long userId) 
    {
        this.userId = userId;
    }

    public Long getUserId() 
    {
        return userId;
    }
    public void setTagId(Long tagId) 
    {
        this.tagId = tagId;
    }

    public Long getTagId() 
    {
        return tagId;
    }
    public void setIcount(Integer icount) 
    {
        this.icount = icount;
    }

    public Integer getIcount() 
    {
        return icount;
    }
    public void setIorder(Integer iorder) 
    {
        this.iorder = iorder;
    }

    public Integer getIorder() 
    {
        return iorder;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("userId", getUserId())
            .append("tagId", getTagId())
            .append("icount", getIcount())
            .append("iorder", getIorder())
            .toString();
    }
}
