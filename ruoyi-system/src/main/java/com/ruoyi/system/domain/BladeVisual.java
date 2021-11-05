package com.ruoyi.system.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 可视化对象 blade_visual
 * 
 * @author yangsj
 * @date 2021-10-25
 */
public class BladeVisual extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键 */
    private Long id;

    /** 大屏标题 */
    @Excel(name = "大屏标题")
    private String title;

    /** 预览图地址 */
    @Excel(name = "预览图地址")
    private String backgroundUrl;

    /** 大屏类型 */
    @Excel(name = "大屏类型")
    private Long category;

    /** 发布密码 */
    @Excel(name = "发布密码")
    private String password;

    /** 鍒涘缓閮ㄩ棬 */
    @Excel(name = "鍒涘缓閮ㄩ棬")
    private Long createDept;

    /** 状态 */
    @Excel(name = "状态")
    private Long status;

    /** 鏄惁宸插垹闄? */
    @Excel(name = "鏄惁宸插垹闄?")
    private Long isdeleted;

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
    public void setBackgroundUrl(String backgroundUrl) 
    {
        this.backgroundUrl = backgroundUrl;
    }

    public String getBackgroundUrl() 
    {
        return backgroundUrl;
    }
    public void setCategory(Long category) 
    {
        this.category = category;
    }

    public Long getCategory() 
    {
        return category;
    }
    public void setPassword(String password) 
    {
        this.password = password;
    }

    public String getPassword() 
    {
        return password;
    }
    public void setCreateDept(Long createDept) 
    {
        this.createDept = createDept;
    }

    public Long getCreateDept() 
    {
        return createDept;
    }
    public void setStatus(Long status) 
    {
        this.status = status;
    }

    public Long getStatus() 
    {
        return status;
    }
    public void setIsdeleted(Long isdeleted) 
    {
        this.isdeleted = isdeleted;
    }

    public Long getIsdeleted() 
    {
        return isdeleted;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("title", getTitle())
            .append("backgroundUrl", getBackgroundUrl())
            .append("category", getCategory())
            .append("password", getPassword())
            .append("createDept", getCreateDept())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("status", getStatus())
            .append("isdeleted", getIsdeleted())
            .toString();
    }
}
