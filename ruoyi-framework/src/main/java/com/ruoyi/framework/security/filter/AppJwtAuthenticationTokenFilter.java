package com.ruoyi.framework.security.filter;

import com.ruoyi.common.constant.RespMessageConstants;
import com.ruoyi.common.core.domain.dto.LoginDTO;
import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.framework.web.service.AppTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
public class AppJwtAuthenticationTokenFilter extends OncePerRequestFilter {
    @Autowired
    private AppTokenService appTokenService;

    private static String[] FILTER_URLS = {"/app/user/bindPhone", "/app/wxPay/**", "/app/gauge/result/**"};

    @Value("${app.filter.urls}")
    private String filterUrls;

    private static final String COMMA = ",";

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {
        LoginDTO loginUser = appTokenService.getLoginUser(request);
        boolean flag = false;
        for (String filterUrl : filterUrls.split(COMMA)) {
            AntPathMatcher matcher = new AntPathMatcher();
            if (matcher.match(filterUrl, request.getRequestURI())) {
                flag = true;
                break;
            }
        }
        if (flag) {
            if (StringUtils.isNull(loginUser)) {
                throw new ServiceException("用户未登录" , RespMessageConstants.ACCESS_TOKEN_EXPIRED_CODE);
            }
            appTokenService.verifyToken(loginUser);
        }

        chain.doFilter(request, response);
    }
}
