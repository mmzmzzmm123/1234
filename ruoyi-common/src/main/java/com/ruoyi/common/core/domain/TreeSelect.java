package com.ruoyi.common.core.domain;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

import cn.hutool.core.lang.tree.Tree;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.ruoyi.common.core.domain.entity.SysDept;
import com.ruoyi.common.core.domain.entity.SysMenu;

/**
 * Treeselect树结构实体类
 * 
 * @author ruoyi
 */
public class TreeSelect implements Serializable
{
    private static final long serialVersionUID = 1L;

    /** 节点ID */
    private Long id;

    /** 节点名称 */
    private String label;

    /** 子节点 */
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private List<TreeSelect> children;

    public TreeSelect()
    {

    }

    public TreeSelect(SysDept dept)
    {
        this.id = dept.getDeptId();
        this.label = dept.getDeptName();
        this.children = dept.getChildren().stream().map(TreeSelect::new).collect(Collectors.toList());
    }

    public TreeSelect(SysMenu menu)
    {
        this.id = menu.getMenuId();
        this.label = menu.getMenuName();
        this.children = menu.getChildren().stream().map(TreeSelect::new).collect(Collectors.toList());
    }
    public TreeSelect(Tree<Long> dept) {
        this.id = dept.getId();
        this.label = dept.getName().toString();
        List<Tree<Long>> children = dept.getChildren();
        if (children != null && !children.isEmpty()) {
            this.children = children.stream().map(TreeSelect::new).collect(Collectors.toList());
        }

    }


    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public String getLabel()
    {
        return label;
    }

    public void setLabel(String label)
    {
        this.label = label;
    }

    public List<TreeSelect> getChildren()
    {
        return children;
    }

    public void setChildren(List<TreeSelect> children)
    {
        this.children = children;
    }
}
