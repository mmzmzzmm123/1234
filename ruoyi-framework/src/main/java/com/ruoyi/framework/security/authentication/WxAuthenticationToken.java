package com.ruoyi.framework.security.authentication;

import com.ruoyi.common.core.domain.model.WxLoginBody;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public class WxAuthenticationToken extends AbstractAuthenticationToken {
    private Object principal;
    private WxLoginBody credentials;

    public WxAuthenticationToken(WxLoginBody credentials){
        super(null);
        this.credentials = credentials;
    }

    public WxAuthenticationToken(Object principal, WxLoginBody credentials, Collection<? extends GrantedAuthority> authorities){
        super(authorities);
        this.principal = principal;
        this.credentials = credentials;
        super.setAuthenticated(true);
    }

    @Override
    public Object getCredentials() {
        return credentials;
    }

    @Override
    public Object getPrincipal() {
        return principal;
    }
}
