package com.hxzh.front.security;


import com.hxzh.front.security.tokenservice.TokenServiceMannager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * token过滤器 验证token有效性
 *
 * @author ruoyi
 */
@Component
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {
    @Autowired
    TokenServiceMannager tokenServiceMannager;
    //TODO 过滤掉可以匿名访问的路径,比如访问登录接口并携带token会被拦截;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
        tokenServiceMannager.handler(request);
        chain.doFilter(request, response);
    }
}
