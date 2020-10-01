package com.ruoyi.bookmark.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * 书签_标签对象 sq_tag
 *
 * @author ruoyi
 * @date 2020-09-05
 */
@Table(name="sq_tag")
public class SqTag
{
    private static final long serialVersionUID = 1L;
    /** 书签标签id */
    @Id
    @GeneratedValue(generator = "JDBC")//此处加上注解
    private Long id;

    /** 标签名字 */
    @Excel(name = "标签名字")
    @Column(name = "name")
    private String name;

    /** 引用数量 */
    @Excel(name = "引用数量")
    @Column(name = "icount")
    private Integer icount;

    /** 谁增加的该标签 */
    @Excel(name = "谁增加的该标签")
    @Column(name = "user_Id")
    private Long userId;

    /** S系统标签,P书签标签,B便签书签 */
    @Excel(name = "S系统标签,P书签标签,B便签书签")
    @Column(name = "tag_type")
    private String tagType;

    /** 链接 */
    @Excel(name = "链接")
    @Column(name = "url")
    private String url;

    /** 状态 */
    @Excel(name = "状态")
    @Column(name = "status")
    private Integer status;


    /** 创建时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(name = "create_time")
    private Date createTime;

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
    public void setUserId(Long userId)
    {
        this.userId = userId;
    }

    public Long getUserId()
    {
        return userId;
    }
    public void setTagType(String tagType)
    {
        this.tagType = tagType;
    }

    public String getTagType()
    {
        return tagType;
    }
    public void setUrl(String url)
    {
        this.url = url;
    }

    public String getUrl()
    {
        return url;
    }
    public void setStatus(Integer status)
    {
        this.status = status;
    }

    public Integer getStatus()
    {
        return status;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("name", getName())
            .append("icount", getIcount())
            .append("userId", getUserId())
            .append("tagType", getTagType())
            .append("url", getUrl())
            .append("status", getStatus())
            .append("createTime", getCreateTime())
            .toString();
    }
}
