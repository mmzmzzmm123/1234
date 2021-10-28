package com.ruoyi.system.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 可视化分类对象 blade_visual_category
 * 
 * @author yangsj
 * @date 2021-10-25
 */
public class BladeVisualCategory extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键 */
    private Long id;

    /** 鍒嗙被閿€? */
    @Excel(name = "鍒嗙被閿€?")
    private String categoryKey;

    /** 鍒嗙被鍚嶇О */
    @Excel(name = "鍒嗙被鍚嶇О")
    private String categoryValue;

    /** 鏄惁宸插垹闄? */
    @Excel(name = "鏄惁宸插垹闄?")
    private Long isDeleted;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setCategoryKey(String categoryKey) 
    {
        this.categoryKey = categoryKey;
    }

    public String getCategoryKey() 
    {
        return categoryKey;
    }
    public void setCategoryValue(String categoryValue) 
    {
        this.categoryValue = categoryValue;
    }

    public String getCategoryValue() 
    {
        return categoryValue;
    }
    public void setIsDeleted(Long isDeleted) 
    {
        this.isDeleted = isDeleted;
    }

    public Long getIsDeleted() 
    {
        return isDeleted;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("categoryKey", getCategoryKey())
            .append("categoryValue", getCategoryValue())
            .append("isDeleted", getIsDeleted())
            .toString();
    }
}
