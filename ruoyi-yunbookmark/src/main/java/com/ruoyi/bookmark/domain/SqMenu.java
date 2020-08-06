package com.ruoyi.bookmark.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.TreeEntity;

/**
 * 书签菜单对象 sq_menu
 * 
 * @author wanghao
 * @date 2020-08-06
 */
public class SqMenu extends TreeEntity
{
    private static final long serialVersionUID = 1L;

    /** 书签菜单ID */
    private Long menuId;

    /** 归属用户ID */
    @Excel(name = "归属用户ID")
    private Long userId;

    /** 分类名称 */
    @Excel(name = "分类名称")
    private String menuName;

    /** 菜单url */
    @Excel(name = "菜单url")
    private String menuUrl;

    /** 菜单图标 */
    @Excel(name = "菜单图标")
    private String menuIcon;

    /** 菜单顺序 */
    @Excel(name = "菜单顺序")
    private Integer menuOrder;

    public void setMenuId(Long menuId) 
    {
        this.menuId = menuId;
    }

    public Long getMenuId() 
    {
        return menuId;
    }
    public void setUserId(Long userId) 
    {
        this.userId = userId;
    }

    public Long getUserId() 
    {
        return userId;
    }
    public void setMenuName(String menuName) 
    {
        this.menuName = menuName;
    }

    public String getMenuName() 
    {
        return menuName;
    }
    public void setMenuUrl(String menuUrl) 
    {
        this.menuUrl = menuUrl;
    }

    public String getMenuUrl() 
    {
        return menuUrl;
    }
    public void setMenuIcon(String menuIcon) 
    {
        this.menuIcon = menuIcon;
    }

    public String getMenuIcon() 
    {
        return menuIcon;
    }
    public void setMenuOrder(Integer menuOrder) 
    {
        this.menuOrder = menuOrder;
    }

    public Integer getMenuOrder() 
    {
        return menuOrder;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("menuId", getMenuId())
            .append("userId", getUserId())
            .append("menuName", getMenuName())
            .append("menuUrl", getMenuUrl())
            .append("menuIcon", getMenuIcon())
            .append("parentId", getParentId())
            .append("menuOrder", getMenuOrder())
            .append("createTime", getCreateTime())
            .toString();
    }
}
