package com.ruoyi.common.core.domain.entity;


/**
 * 角色和菜单关联 sys_role_menu
 * 
 * @author ruoyi
 */
public class RoleMenuPerms {
    /** 角色ID */
    private Long roleId;
    
    /** 菜单ID */
    private Long menuId;

    /** 权限 */
    private String perms;

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public Long getMenuId() {
        return menuId;
    }

    public void setMenuId(Long menuId) {
        this.menuId = menuId;
    }

    public String getPerms() {
        return perms;
    }

    public void setPerms(String perms) {
        this.perms = perms;
    }

}
