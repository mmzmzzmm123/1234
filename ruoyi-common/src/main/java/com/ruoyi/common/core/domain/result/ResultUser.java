package com.ruoyi.common.core.domain.result;


import com.ruoyi.common.constant.HttpStatus;
import com.ruoyi.common.core.domain.Result;
import com.ruoyi.common.core.domain.entity.SysUser;

import java.util.Set;

/**
 * @author shoreu
 */
public class ResultUser<T> extends Result<T> {
    private String roleGroup;
    private String postGroup;
    private String imgUrl;
    private String token;
    private SysUser user;
    private Set<String> permissions;
    private Set<String> roles;

    public ResultUser(SysUser user, Set<String> roles, Set<String> permissions) {
        super(HttpStatus.SUCCESS);
        this.user = user;
        this.roles = roles;
        this.permissions = permissions;
    }

    private ResultUser() {
        super(HttpStatus.SUCCESS);
    }

    private ResultUser(T t) {
        super(HttpStatus.SUCCESS, t);
    }

    private ResultUser(String msg) {
        super(HttpStatus.ERROR, msg);
    }

    public static ResultUser successImg(String imgUrl) {
        ResultUser jsonResultUser = new ResultUser();
        jsonResultUser.setImgUrl(imgUrl);
        return jsonResultUser;
    }

    public static ResultUser error(String msg) {
        return new ResultUser(msg);
    }

    public static <T> ResultUser<T> successUser(T t) {
        return new ResultUser(t);
    }

    public static ResultUser successToken(String token) {
        ResultUser json = new ResultUser();
        json.setToken(token);
        return json;
    }

    public static ResultUser successInfo(SysUser user, Set<String> roles, Set<String> permissions) {
        return new ResultUser(user, roles, permissions);
    }

    public String getRoleGroup() {
        return this.roleGroup;
    }

    public void setRoleGroup(String roleGroup) {
        this.roleGroup = roleGroup;
    }

    public String getPostGroup() {
        return this.postGroup;
    }

    public void setPostGroup(String postGroup) {
        this.postGroup = postGroup;
    }

    public String getImgUrl() {
        return this.imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getToken() {
        return this.token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public SysUser getUser() {
        return this.user;
    }

    public void setUser(SysUser user) {
        this.user = user;
    }

    public Set<String> getPermissions() {
        return this.permissions;
    }

    public void setPermissions(Set<String> permissions) {
        this.permissions = permissions;
    }

    public Set<String> getRoles() {
        return this.roles;
    }

    public void setRoles(Set<String> roles) {
        this.roles = roles;
    }

    public void setGroup(String roleGroup, String postGroup) {
        this.roleGroup = roleGroup;
        this.postGroup = postGroup;
    }

}
