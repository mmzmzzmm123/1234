package com.ruoyi.common.core.domain.model;

import com.alibaba.fastjson2.annotation.JSONField;
import com.ruoyi.common.core.domain.IStoreRole;
import com.ruoyi.common.core.domain.entity.WxUser;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.Set;

@Data
public class WxLoginUser extends LoginUser implements UserDetails {

    private static final long serialVersionUID = 1L;

    /**
     * 用户信息
     */
    private WxUser wxUser;

    private Collection<IStoreRole> storeRoles;

    public WxLoginUser(WxUser user, Set<String> permissions, Collection<IStoreRole> storeRoles) {
        this.wxUser = user;
        this.storeRoles = storeRoles;
        super.setPermissions(permissions);
    }

    @Override
    public WxUser getWxUser() {
        return this.wxUser;
    }

    public String getOpenId(){
        return wxUser.getOpenId();
    }

    public String getUnionId(){
        return wxUser.getUnionId();
    }

}
