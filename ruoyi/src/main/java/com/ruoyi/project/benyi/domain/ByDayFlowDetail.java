package com.ruoyi.project.benyi.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.framework.aspectj.lang.annotation.Excel;
import com.ruoyi.framework.web.domain.BaseEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * 一日流程对象 by_day_flow_detail
 * 
 * @author tsbz
 * @date 2020-05-07
 */
public class ByDayFlowDetail extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 流程ID */
    private Long id;

    /** 树状父类ID */
    private Long parentid;

    /** 树状子类 */
    private List<ByDayFlowDetail> children = new ArrayList<ByDayFlowDetail>();

    /** 流程序号 */
    @Excel(name = "流程序号")
    private Long sortNumber;

    /** 标准数量 */
    @Excel(name = "标准数量")
    private Long targetCount;

    /** 名称 */
    @Excel(name = "名称")
    private String name;

    /** 导言 */
    @Excel(name = "导言")
    private String content;

    /** 目的 */
    @Excel(name = "目的")
    private String note;

    /** 类型（1标准,2相关） */
    @Excel(name = "类型", readConverterExp = "1=标准,2相关")
    private String flowType;

    /** 创建人 */
    @Excel(name = "创建人")
    private Long createUser;

    /** 修改人 */
    @Excel(name = "修改人")
    private Long updateUser;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setSortNumber(Long sortNumber) 
    {
        this.sortNumber = sortNumber;
    }

    public Long getSortNumber() 
    {
        return sortNumber;
    }
    public void setTargetCount(Long targetCount) 
    {
        this.targetCount = targetCount;
    }

    public Long getTargetCount() 
    {
        return targetCount;
    }
    public void setName(String name) 
    {
        this.name = name;
    }

    public String getName() 
    {
        return name;
    }
    public void setContent(String content) 
    {
        this.content = content;
    }

    public String getContent() 
    {
        return content;
    }
    public void setNote(String note) 
    {
        this.note = note;
    }

    public String getNote() 
    {
        return note;
    }
    public void setFlowType(String flowType)
    {
        this.flowType = flowType;
    }

    public String getFlowType()
    {
        return flowType;
    }
    public void setCreateUser(Long createUser) 
    {
        this.createUser = createUser;
    }

    public Long getCreateUser() 
    {
        return createUser;
    }
    public void setUpdateUser(Long updateUser) 
    {
        this.updateUser = updateUser;
    }

    public Long getUpdateUser() 
    {
        return updateUser;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("sortNumber", getSortNumber())
            .append("targetCount", getTargetCount())
            .append("name", getName())
            .append("content", getContent())
            .append("note", getNote())
            .append("flowType", getFlowType())
            .append("createUser", getCreateUser())
            .append("updateUser", getUpdateUser())
            .append("createTime", getCreateTime())
            .append("updateTime", getUpdateTime())
            .append("parentid", getParentid())
            .toString();
    }

    public Long getParentid() {
        return parentid;
    }

    public void setParentid(Long parentid) {
        this.parentid = parentid;
    }

    public List<ByDayFlowDetail> getChildren() {
        return children;
    }

    public void setChildren(List<ByDayFlowDetail> children) {
        this.children = children;
    }
}
