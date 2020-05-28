package com.ruoyi.project.system.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.framework.aspectj.lang.annotation.Excel;
import com.ruoyi.framework.web.domain.BaseEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * 多级字典管理对象 sys_dict_moedata
 * 
 * @author tsbz
 * @date 2020-05-28
 */
public class SysDictMoedata extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 编号 */
    private Long id;

    /** 父节点 */
    @Excel(name = "父节点")
    private Long pid;

    /** 所有节点 */
    @Excel(name = "所有节点")
    private String ancestors;

    /** 名称 */
    @Excel(name = "名称")
    private String name;

    /** 排序 */
    @Excel(name = "排序")
    private Long ordersum;

    /** 标识关键字 */
    @Excel(name = "标识关键字")
    private String keyword;

    /** 状态 */
    @Excel(name = "状态")
    private String status;

    /** 是否删除 */
    @Excel(name = "是否删除")
    private String isdel;

    /** 子部门 */
    private List<SysDictMoedata> children = new ArrayList<SysDictMoedata>();

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setPid(Long pid) 
    {
        this.pid = pid;
    }

    public Long getPid() 
    {
        return pid;
    }
    public void setAncestors(String ancestors) 
    {
        this.ancestors = ancestors;
    }

    public String getAncestors() 
    {
        return ancestors;
    }
    public void setName(String name) 
    {
        this.name = name;
    }

    public String getName() 
    {
        return name;
    }
    public void setOrdersum(Long ordersum) 
    {
        this.ordersum = ordersum;
    }

    public Long getOrdersum() 
    {
        return ordersum;
    }
    public void setKeyword(String keyword) 
    {
        this.keyword = keyword;
    }

    public String getKeyword() 
    {
        return keyword;
    }
    public void setStatus(String status) 
    {
        this.status = status;
    }

    public String getStatus() 
    {
        return status;
    }
    public void setIsdel(String isdel) 
    {
        this.isdel = isdel;
    }

    public String getIsdel() 
    {
        return isdel;
    }
    public List<SysDictMoedata> getChildren()
    {
        return children;
    }

    public void setChildren(List<SysDictMoedata> children)
    {
        this.children = children;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("pid", getPid())
            .append("ancestors", getAncestors())
            .append("name", getName())
            .append("ordersum", getOrdersum())
            .append("keyword", getKeyword())
            .append("status", getStatus())
            .append("isdel", getIsdel())
            .append("createTime", getCreateTime())
            .toString();
    }
}
