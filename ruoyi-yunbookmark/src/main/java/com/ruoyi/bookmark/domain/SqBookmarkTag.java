package com.ruoyi.bookmark.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 书签标签管理对象 sq_bookmark_tag
 *
 * @author ruoyi
 * @date 2020-09-04
 */
@Table(name="sq_bookmark_tag")
public class SqBookmarkTag
{
    private static final long serialVersionUID = 1L;

    /** ID */
    @Id
    @GeneratedValue(generator = "JDBC")//返回自增长主键
    private Long id;

    /** 书签ID */
    @Excel(name = "书签ID")
    @Column(name = "bookmark_id")
    private Long bookmarkId;

    /** 标签ID */
    @Excel(name = "标签ID")
    @Column(name = "tag_id")
    private Long tagId;

    public void setId(Long id)
    {
        this.id = id;
    }

    public Long getId()
    {
        return id;
    }
    public void setBookmarkId(Long bookmarkId)
    {
        this.bookmarkId = bookmarkId;
    }

    public Long getBookmarkId()
    {
        return bookmarkId;
    }
    public void setTagId(Long tagId)
    {
        this.tagId = tagId;
    }

    public Long getTagId()
    {
        return tagId;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("bookmarkId", getBookmarkId())
            .append("tagId", getTagId())
            .toString();
    }
}
