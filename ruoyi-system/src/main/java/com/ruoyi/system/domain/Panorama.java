package com.ruoyi.system.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 全景管理对象 panorama
 * 
 * @author ruoyi
 * @date 2021-11-02
 */
public class Panorama extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 全景图ID */
    private Long id;

    /** 全景图名称 */
    @Excel(name = "全景图名称")
    private String name;

    /** 全景图描述信息 */
    @Excel(name = "全景图描述信息")
    private String descri;

    /** 删除标志（0代表存在 2代表删除） */
    private String delFlag;

    /** 配置json */
    @Excel(name = "配置json")
    private String detail;

    /** 组件json */
    @Excel(name = "组件json")
    private String component;

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
    public void setDescri(String descri) 
    {
        this.descri = descri;
    }

    public String getDescri() 
    {
        return descri;
    }
    public void setDelFlag(String delFlag) 
    {
        this.delFlag = delFlag;
    }

    public String getDelFlag() 
    {
        return delFlag;
    }
    public void setDetail(String detail) 
    {
        this.detail = detail;
    }

    public String getDetail() 
    {
        return detail;
    }
    public void setComponent(String component) 
    {
        this.component = component;
    }

    public String getComponent() 
    {
        return component;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("name", getName())
            .append("descri", getDescri())
            .append("delFlag", getDelFlag())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("detail", getDetail())
            .append("component", getComponent())
            .toString();
    }
}
