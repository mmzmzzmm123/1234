package com.ruoyi.sq.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 书签对象 sq_bookmark
 * 
 * @author wanghao
 * @date 2020-07-26
 */
public class SqBookmark extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private Long bookmarkId;

    /** 所属用户ID */
    @Excel(name = "所属用户ID")
    private Long userid;

    /** 书签标题 */
    @Excel(name = "书签标题")
    private String title;

    /** 书签地址 */
    @Excel(name = "书签地址")
    private String url;

    /** $column.columnComment */
    @Excel(name = "书签地址")
    private String urls;

    /** 书签描述 */
    @Excel(name = "书签描述")
    private String description;

    /** $column.columnComment */
    @Excel(name = "书签描述")
    private String image;

    /** 标签 */
    @Excel(name = "标签")
    private String label;

    /** 分类ID */
    @Excel(name = "分类ID")
    private Long menuId;

    /** 点赞数 */
    @Excel(name = "点赞数")
    private Long zcount;

    /** 0 未删除 1表示删除 */
    @Excel(name = "0 未删除 1表示删除")
    private Integer idelete;

    /** 0公开显示 1隐藏显示 2好友显示 3稍后再看 */
    @Excel(name = "0公开显示 1隐藏显示 2好友显示 3稍后再看")
    private Integer start;

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
