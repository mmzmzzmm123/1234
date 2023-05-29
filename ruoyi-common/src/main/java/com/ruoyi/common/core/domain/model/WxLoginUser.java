package com.ruoyi.common.core.domain.model;

import com.alibaba.fastjson2.annotation.JSONField;
import com.ruoyi.common.core.domain.entity.WxUser;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Set;

@Data
public class WxLoginUser extends LoginUser implements UserDetails {

    private static final long serialVersionUID = 1L;

    /**
     * 用户ID
     */
    private String appid;


    private String unionId;

    /**
     * 用户信息
     */
    private WxUser wxUser;

    public WxLoginUser() {
    }

    public WxLoginUser(WxUser user, Set<String> permissions) {
        this.wxUser = user;
        super.setPermissions(permissions);
    }

    public WxLoginUser(String appid, String unionId, WxUser user, Set<String> permissions) {
        this.appid = appid;
        this.unionId = unionId;
        this.wxUser = user;
        super.setPermissions(permissions);
    }

    /**
     * 账户是否未过期,过期无法验证
     */
    @JSONField(serialize = false)
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    /**
     * 指定用户是否解锁,锁定的用户无法进行身份验证
     *
     * @return
     */
    @JSONField(serialize = false)
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    /**
     * 指示是否已过期的用户的凭据(密码),过期的凭据防止认证
     *
     * @return
     */
    @JSONField(serialize = false)
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    /**
     * 是否可用 ,禁用的用户不能身份验证
     *
     * @return
     */
    @JSONField(serialize = false)
    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @JSONField(serialize = false)
    @Override
    public String getPassword() {
        return wxUser.getAppId();
    }

    @Override
    public String getUsername() {
        return wxUser.getAppId();
    }
}
