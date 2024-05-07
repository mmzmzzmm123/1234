package com.ruoyi.system.articles.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 文章对象 t_articles
 * 
 * @author ruoyi
 * @date 2024-04-29
 */
public class TArticles extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private Long id;

    /** 文章表体 */
    @Excel(name = "文章表体")
    private String title;

    /** 文章内容 */
    @Excel(name = "文章内容")
    private String content;

    /** 配图 */
    @Excel(name = "配图")
    private String image;

    /** 发帖人id */
    @Excel(name = "发帖人id")
    private Long userId;

    /** 发帖地区id */
    @Excel(name = "发帖地区id")
    private String regionId;

    /** 发帖地区编码 */
    @Excel(name = "发帖地区编码")
    private String regionCode;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setTitle(String title) 
    {
        this.title = title;
    }

    public String getTitle() 
    {
        return title;
    }
    public void setContent(String content) 
    {
        this.content = content;
    }

    public String getContent() 
    {
        return content;
    }
    public void setImage(String image) 
    {
        this.image = image;
    }

    public String getImage() 
    {
        return image;
    }
    public void setUserId(Long userId) 
    {
        this.userId = userId;
    }

    public Long getUserId() 
    {
        return userId;
    }
    public void setRegionId(String regionId) 
    {
        this.regionId = regionId;
    }

    public String getRegionId() 
    {
        return regionId;
    }
    public void setRegionCode(String regionCode) 
    {
        this.regionCode = regionCode;
    }

    public String getRegionCode() 
    {
        return regionCode;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("title", getTitle())
            .append("content", getContent())
            .append("image", getImage())
            .append("userId", getUserId())
            .append("regionId", getRegionId())
            .append("regionCode", getRegionCode())
            .append("createTime", getCreateTime())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}
