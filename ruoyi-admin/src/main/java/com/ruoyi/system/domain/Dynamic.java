package com.ruoyi.system.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 动态信息管理对象 dynamic
 * 
 * @author carol
 * @date 2024-03-01
 */
public class Dynamic extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键id */
    private Long id;

    /** 标题 */
    @Excel(name = "标题")
    private String title;

    /** 简介 */
    @Excel(name = "简介")
    private String intro;

    /** 文章内容 */
    @Excel(name = "文章内容")
    private String content;

    /** 文章封面图 */
    @Excel(name = "文章封面图")
    private String picture;

    /** 车源地 */
    @Excel(name = "车源地")
    private String sourceAddr;

    /** 微信号 */
    @Excel(name = "微信号")
    private String wxNum;

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
    public void setIntro(String intro) 
    {
        this.intro = intro;
    }

    public String getIntro() 
    {
        return intro;
    }
    public void setContent(String content) 
    {
        this.content = content;
    }

    public String getContent() 
    {
        return content;
    }
    public void setPicture(String picture) 
    {
        this.picture = picture;
    }

    public String getPicture() 
    {
        return picture;
    }
    public void setSourceAddr(String sourceAddr) 
    {
        this.sourceAddr = sourceAddr;
    }

    public String getSourceAddr() 
    {
        return sourceAddr;
    }
    public void setWxNum(String wxNum) 
    {
        this.wxNum = wxNum;
    }

    public String getWxNum() 
    {
        return wxNum;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("title", getTitle())
            .append("intro", getIntro())
            .append("content", getContent())
            .append("picture", getPicture())
            .append("sourceAddr", getSourceAddr())
            .append("wxNum", getWxNum())
            .append("createTime", getCreateTime())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}
