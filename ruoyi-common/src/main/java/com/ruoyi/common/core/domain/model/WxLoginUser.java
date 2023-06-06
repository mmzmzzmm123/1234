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
    private String openId;


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

    public WxLoginUser(String openId, String unionId, WxUser user, Set<String> permissions) {
        this.openId = openId;
        this.unionId = unionId;
        this.wxUser = user;
        super.setPermissions(permissions);
    }

    @Override
    public WxUser getWxUser() {
        return this.wxUser;
    }

}
