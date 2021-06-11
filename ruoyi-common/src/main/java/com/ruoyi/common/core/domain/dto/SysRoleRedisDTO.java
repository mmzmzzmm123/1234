package com.ruoyi.common.core.domain.dto;

import com.ruoyi.common.core.domain.entity.SysRole;

import java.util.Set;

/**
 * 系统角色序列化redis中dto
 *
 * @author songweiwei
 */
public class SysRoleRedisDTO extends SysRole
{

    /**
     * 该角色对应的权限字符
     */
    private Set<String> perms;

    public Set<String> getPerms() {
        return perms;
    }

    public void setPerms(Set<String> perms) {
        this.perms = perms;
    }

}
