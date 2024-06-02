package com.baoli.sysmanage.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 提成规则参数对象 baoli_biz_bonus_rule_param
 * 
 * @author niujs
 * @date 2024-05-03
 */
public class BaoliBizBonusRuleParam extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private Long id;

    /** 参数类型 */
    @Excel(name = "参数类型")
    private String paramType;

    /** 标签 */
    @Excel(name = "标签")
    private String label;

    /** 内容 */
    @Excel(name = "内容")
    private String content;

    /** 编辑类型 */
    @Excel(name = "编辑类型")
    private String editType;

    /** 序号 */
    @Excel(name = "序号")
    private Long sortNo;

    /** 上级 */
    @Excel(name = "上级")
    private Long parentId;

    private String inputParam;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setParamType(String paramType) 
    {
        this.paramType = paramType;
    }

    public String getParamType() 
    {
        return paramType;
    }
    public void setLabel(String label) 
    {
        this.label = label;
    }

    public String getLabel() 
    {
        return label;
    }
    public void setContent(String content) 
    {
        this.content = content;
    }

    public String getContent() 
    {
        return content;
    }
    public void setEditType(String editType) 
    {
        this.editType = editType;
    }

    public String getEditType() 
    {
        return editType;
    }
    public void setSortNo(Long sortNo) 
    {
        this.sortNo = sortNo;
    }

    public Long getSortNo() 
    {
        return sortNo;
    }
    public void setParentId(Long parentId) 
    {
        this.parentId = parentId;
    }

    public Long getParentId() 
    {
        return parentId;
    }

    public String getInputParam() {
        return inputParam;
    }

    public void setInputParam(String inputParam) {
        this.inputParam = inputParam;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("paramType", getParamType())
            .append("label", getLabel())
            .append("content", getContent())
            .append("editType", getEditType())
            .append("sortNo", getSortNo())
            .append("parentId", getParentId())
            .toString();
    }
}
