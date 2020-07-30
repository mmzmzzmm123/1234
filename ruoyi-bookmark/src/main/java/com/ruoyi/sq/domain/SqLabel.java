package com.ruoyi.sq.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 书签_标签对象 sq_label
 * 
 * @author wanghao
 * @date 2020-07-26
 */
public class SqLabel extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private Long id;

    /** 书签所属id */
    @Excel(name = "书签所属id")
    private Integer userId;

    /** 书签名字 */
    @Excel(name = "书签名字")
    private String labelName;

    /** 排序 */
    @Excel(name = "排序")
    private Integer order;

    /** 使用数量 */
    @Excel(name = "使用数量")
    private Long labelCount;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setUserId(Integer userId) 
    {
        this.userId = userId;
    }

    public Integer getUserId() 
    {
        return userId;
    }
    public void setLabelName(String labelName) 
    {
        this.labelName = labelName;
    }

    public String getLabelName() 
    {
        return labelName;
    }
    public void setOrder(Integer order) 
    {
        this.order = order;
    }

    public Integer getOrder() 
    {
        return order;
    }
    public void setLabelCount(Long labelCount) 
    {
        this.labelCount = labelCount;
    }

    public Long getLabelCount() 
    {
        return labelCount;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("userId", getUserId())
            .append("labelName", getLabelName())
            .append("order", getOrder())
            .append("createTime", getCreateTime())
            .append("labelCount", getLabelCount())
            .toString();
    }
}
