package com.gox.basic.domain;

import com.gox.common.plugin.AutoId;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.gox.common.annotation.Excel;
import com.gox.common.core.domain.BaseEntity;

/**
 *   对象 node
 * 
 * @author gox
 * @date 2021-03-11
 */
public class Node extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    @AutoId
    private Long id;

    /** 下一节点主键 */
    @Excel(name = "下一节点主键")
    private String nextid;

    /** 工作流主键 */
    @Excel(name = "工作流主键")
    private Long workid;

    /** 名称 */
    @Excel(name = "名称")
    private String text;

    /** 排序 */
    @Excel(name = "排序")
    private Long sort;

    /** 节点描述 */
    @Excel(name = "节点描述")
    private String desci;

    /** 审批范围 */
    @Excel(name = "审批范围")
    private String approvescope;


    /** 角色名称 */
    @Excel(name = "角色名称")
    private String rolename;

    /**
     * 角色组
     */
    private Long[] roleIds;


    /**
     * 下一节点名称
     */
    private String nexttext;


    public Long[] getRoleIds() {
        return roleIds;
    }


    public String getNexttext() {
        return nexttext;
    }

    public void setNexttext(String nexttext) {
        this.nexttext = nexttext;
    }

    public void setRoleIds(Long[] roleIds) {
        this.roleIds = roleIds;
    }

    public String getApprovescope() {
        return approvescope;
    }

    public void setApprovescope(String approvescope) {
        this.approvescope = approvescope;
    }

    public String getRolename() {
        return rolename;
    }

    public void setRolename(String rolename) {
        this.rolename = rolename;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setNextid(String nextid) 
    {
        this.nextid = nextid;
    }

    public String getNextid() 
    {
        return nextid;
    }

    public Long getWorkid() {
        return workid;
    }

    public void setWorkid(Long workid) {
        this.workid = workid;
    }

    public void setText(String text)
    {
        this.text = text;
    }

    public String getText() 
    {
        return text;
    }
    public void setSort(Long sort) 
    {
        this.sort = sort;
    }

    public Long getSort() 
    {
        return sort;
    }
    public void setDesci(String desci) 
    {
        this.desci = desci;
    }

    public String getDesci() 
    {
        return desci;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("nextid", getNextid())
            .append("workid", getWorkid())
            .append("text", getText())
            .append("sort", getSort())
            .append("desci", getDesci())
            .toString();
    }
}
