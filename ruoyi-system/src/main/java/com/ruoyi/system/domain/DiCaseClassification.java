package com.ruoyi.system.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 案例分类对象 di_case_classification
 * 
 * @author ruoyi
 * @date 2024-05-17
 */
public class DiCaseClassification extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键 */
    private Long id;

    /** 分类编号 */
    private String classificationNo;

    /** 分类名称 */
    @Excel(name = "分类名称")
    private String classificationName;

    /** 分类标签 */
    private String classificationLabels;

    /** 是否启用 */
    @Excel(name = "是否启用")
    private String isEnabled;

    /** 父节点 */
    private String parentId;

    /** 图标 */
    @Excel(name = "图标")
    private String imageIcon;

    /** 说明 */
    @Excel(name = "说明")
    private String description;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setClassificationNo(String classificationNo) 
    {
        this.classificationNo = classificationNo;
    }

    public String getClassificationNo() 
    {
        return classificationNo;
    }
    public void setClassificationName(String classificationName) 
    {
        this.classificationName = classificationName;
    }

    public String getClassificationName() 
    {
        return classificationName;
    }
    public void setClassificationLabels(String classificationLabels) 
    {
        this.classificationLabels = classificationLabels;
    }

    public String getClassificationLabels() 
    {
        return classificationLabels;
    }
    public void setIsEnabled(String isEnabled) 
    {
        this.isEnabled = isEnabled;
    }

    public String getIsEnabled() 
    {
        return isEnabled;
    }
    public void setParentId(String parentId) 
    {
        this.parentId = parentId;
    }

    public String getParentId() 
    {
        return parentId;
    }
    public void setImageIcon(String imageIcon) 
    {
        this.imageIcon = imageIcon;
    }

    public String getImageIcon() 
    {
        return imageIcon;
    }
    public void setDescription(String description) 
    {
        this.description = description;
    }

    public String getDescription() 
    {
        return description;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("classificationNo", getClassificationNo())
            .append("classificationName", getClassificationName())
            .append("classificationLabels", getClassificationLabels())
            .append("isEnabled", getIsEnabled())
            .append("parentId", getParentId())
            .append("imageIcon", getImageIcon())
            .append("description", getDescription())
            .toString();
    }
}
