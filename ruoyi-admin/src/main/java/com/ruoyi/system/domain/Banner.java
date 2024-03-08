package com.ruoyi.system.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * banner管理对象 banner
 * 
 * @author carol
 * @date 2024-03-06
 */
public class Banner extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** id */
    private Long id;

    /** url地址 */
    @Excel(name = "url地址")
    private String url;

    /** 应用场景 */
    @Excel(name = "应用场景")
    private Long scene;

    /** 状态 */
    @Excel(name = "状态")
    private Long status;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setUrl(String url) 
    {
        this.url = url;
    }

    public String getUrl() 
    {
        return url;
    }
    public void setScene(Long scene) 
    {
        this.scene = scene;
    }

    public Long getScene() 
    {
        return scene;
    }
    public void setStatus(Long status) 
    {
        this.status = status;
    }

    public Long getStatus() 
    {
        return status;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("url", getUrl())
            .append("scene", getScene())
            .append("status", getStatus())
            .append("createTime", getCreateTime())
            .toString();
    }
}
