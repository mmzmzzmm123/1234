package com.ruoyi.system.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 可视化配置对象 blade_visual_config
 * 
 * @author yangsj
 * @date 2021-10-25
 */
public class BladeVisualConfig extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键 */
    private Long id;

    /** 鍙鍖栬〃涓婚敭 */
    @Excel(name = "鍙鍖栬〃涓婚敭")
    private Long visualId;

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
    public void setVisualId(Long visualId) 
    {
        this.visualId = visualId;
    }

    public Long getVisualId() 
    {
        return visualId;
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
            .append("visualId", getVisualId())
            .append("detail", getDetail())
            .append("component", getComponent())
            .toString();
    }
}
