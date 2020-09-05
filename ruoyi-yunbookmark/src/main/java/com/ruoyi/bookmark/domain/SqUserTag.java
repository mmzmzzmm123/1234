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
 * 标签管理对象 sq_user_tag
 *
 * @author wang
 * @date 2020-09-04
 */
@Table(name="sq_user_tag")
public class SqUserTag extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** ID */
    @Id
    @GeneratedValue(generator = "JDBC")//此处加上注解
    private Long id;

    /** 用户ID */
    @Excel(name = "用户ID")
    @Column(name = "user_id")
    private Long userId;

    /** 书签ID */
    @Excel(name = "书签ID")
    @Column(name = "tag_id")
    private Long tagId;

    /** 书签引用的数量 */
    @Excel(name = "书签引用的数量")
    @Column(name = "icount")
    private Integer icount;

    /** 书签排序 */
    @Excel(name = "书签排序")
    @Column(name = "iorder")
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
    public SqUserTag(){

    }
    public SqUserTag(Long userId, Long tagId){
        this.userId=userId;
        this.tagId = tagId;
        this.icount=1;
        this.iorder=1;
    }

}
