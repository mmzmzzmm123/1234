package com.ruoyi.common.core.domain.result;


import com.ruoyi.common.constant.HttpStatus;
import com.ruoyi.common.core.domain.Result;
import com.ruoyi.common.core.domain.entity.SysPost;
import com.ruoyi.common.core.domain.entity.SysRole;
import com.ruoyi.common.core.domain.entity.SysUser;

import java.util.List;

/**
 * @author shoreu
 */
public class ResultUserRole<T> extends Result<T> {

    private SysUser user;
    private List<SysRole> roles;
    private List<Long> roleIds;
    private List<SysPost> posts;
    private List<Long> postIds;

    public ResultUserRole(SysUser user, List<SysRole> roles) {
        super(HttpStatus.SUCCESS);
        this.user = user;
        this.roles = roles;
    }

    public ResultUserRole(List<SysRole> roles, List<SysPost> posts) {
        super(HttpStatus.SUCCESS);
        this.roles = roles;
        this.posts = posts;
    }

    public static ResultUserRole userRoles(SysUser user, List<SysRole> roles) {
        return new ResultUserRole(user, roles);
    }

    public static ResultUserRole rolesPosts(List<SysRole> roles, List<SysPost> posts) {
        return new ResultUserRole(roles, posts);
    }

    public void setIds(T data, List<Long> roleIds, List<Long> postIds) {
        super.setData(data);
        this.roleIds = roleIds;
        this.postIds = postIds;
    }
}
