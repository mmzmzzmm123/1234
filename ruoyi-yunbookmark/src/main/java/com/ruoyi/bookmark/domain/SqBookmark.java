package com.ruoyi.bookmark.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;


import javax.persistence.*;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 书签管理对象 sq_bookmark   不存在的字段注解 @Transient
 *
 * @author wanghao
 * @date 2020-08-02
 * @GeneratedValue让通用mapper在执行insert操作之后将自动生成的主键值回写到当前实体对象对应的属性当中
 */
@Table(name="sq_bookmark")
public class SqBookmark
{
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(generator = "JDBC")//返回自增长主键
    @Column(name = "bookmark_id")
    /** $column.columnComment */
    private Long bookmarkId;

    /** 所属用户ID */
    @Column(name = "userid")
    @Excel(name = "所属用户ID")
    private Long userid;

    /** 书签标题 */
    @Excel(name = "书签标题")
    @Column(name = "title")
    private String title;

    /** 书签地址 */
    @Excel(name = "书签地址")
    @Column(name = "url")
    private String url;

    /** $column.columnComment */
    @Excel(name = "官网地址")
    @Column(name = "urls")
    private String urls;

    /** 书签描述 */
    @Excel(name = "书签描述")
    @Column(name = "description")
    private String description;

    /** $column.columnComment */
    @Excel(name = "图片")
    @Column(name = "image")
    private String image;

    /** 标签 */
    @Excel(name = "标签")
    @Column(name = "Label")
    private String label;

    /** 分类ID */
    @Excel(name = "分类ID")
    @Column(name = "menu_id")
    private Long menuId;

    /** 点赞数 */
    @Excel(name = "点赞数")
    @Column(name = "zcount")
    private Long zcount;

    /** 0 未删除 1表示删除 */
    @Excel(name = "0 未删除 1表示删除")
    @Column(name = "IDelete")
    private Integer idelete;

    /** 0公开显示 1隐藏显示 2好友显示 3稍后再看 */
    @Excel(name = "0公开显示 1隐藏显示 2好友显示 3稍后再看")
    @Column(name = "Start")
    private Integer start;

    /** 创建时间 */
    @Excel(name = "创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    private List<Map<String,Object>> sqTags;


    public List<Map<String, Object>> getSqTags() {
        return sqTags;
    }

    public void setSqTags(List<Map<String, Object>> sqTags) {
        this.sqTags = sqTags;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public void setBookmarkId(Long bookmarkId)
    {
        this.bookmarkId = bookmarkId;
    }

    public Long getBookmarkId()
    {
        return bookmarkId;
    }
    public void setUserid(Long userid)
    {
        this.userid = userid;
    }

    public Long getUserid()
    {
        return userid;
    }
    public void setTitle(String title)
    {
        this.title = title;
    }

    public String getTitle()
    {
        return title;
    }
    public void setUrl(String url)
    {
        this.url = url;
    }

    public String getUrl()
    {
        return url;
    }
    public void setUrls(String urls)
    {
        this.urls = urls;
    }

    public String getUrls()
    {
        return urls;
    }
    public void setDescription(String description)
    {
        this.description = description;
    }

    public String getDescription()
    {
        return description;
    }
    public void setImage(String image)
    {
        this.image = image;
    }

    public String getImage()
    {
        return image;
    }
    public void setLabel(String label)
    {
        this.label = label;
    }

    public String getLabel()
    {
        return label;
    }
    public void setMenuId(Long menuId)
    {
        this.menuId = menuId;
    }

    public Long getMenuId()
    {
        return menuId;
    }
    public void setZcount(Long zcount)
    {
        this.zcount = zcount;
    }

    public Long getZcount()
    {
        return zcount;
    }
    public void setIdelete(Integer idelete)
    {
        this.idelete = idelete;
    }

    public Integer getIdelete()
    {
        return idelete;
    }
    public void setStart(Integer start)
    {
        this.start = start;
    }

    public Integer getStart()
    {
        return start;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("bookmarkId", getBookmarkId())
            .append("userid", getUserid())
            .append("title", getTitle())
            .append("url", getUrl())
            .append("urls", getUrls())
            .append("description", getDescription())
            .append("image", getImage())
            .append("label", getLabel())
            .append("menuId", getMenuId())
            .append("zcount", getZcount())
            .append("idelete", getIdelete())
            .append("start", getStart())
            .append("createTime", getCreateTime())
            .toString();
    }
}
