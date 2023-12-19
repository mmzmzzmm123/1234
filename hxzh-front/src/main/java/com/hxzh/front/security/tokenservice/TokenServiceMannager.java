package com.hxzh.front.security.tokenservice;

import com.hxzh.front.security.context.AuthenticationContextHolder;
import com.hxzh.front.security.tokenservice.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
@Component
public class TokenServiceMannager {
    @Autowired(required = false)
    @Qualifier("taskTokenService")
    List<TokenService> tokenServiceList;
    public boolean handler(HttpServletRequest request){
        for (TokenService tokenService : tokenServiceList) {
            if(tokenService.isSupport(request)){
                Authentication authentication = tokenService.getAuthentication(request);
                SecurityContextHolder.getContext().setAuthentication(authentication);
                return true;
            }
        }
        return false;
    }
}
