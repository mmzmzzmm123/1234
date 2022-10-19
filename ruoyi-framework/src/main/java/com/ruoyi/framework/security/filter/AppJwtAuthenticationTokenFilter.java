package com.ruoyi.framework.security.filter;

import com.ruoyi.common.core.domain.dto.LoginDTO;
import com.ruoyi.common.core.domain.model.LoginUser;
import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.framework.web.service.AppTokenService;
import com.ruoyi.framework.web.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
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
public class AppJwtAuthenticationTokenFilter extends OncePerRequestFilter
{
    @Autowired
    private AppTokenService appTokenService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException
    {
        LoginDTO loginUser = appTokenService.getLoginUser(request);
        AntPathMatcher matcher = new AntPathMatcher();

        if(matcher.match("/app/**" ,request.getRequestURI())){
            if(!matcher.match("/app/login/**" ,request.getRequestURI())){
                if(StringUtils.isNull(loginUser)){
                    throw new ServiceException("用户未登录");
                }
                appTokenService.verifyToken(loginUser);
            }
        }
        chain.doFilter(request, response);
    }
}
