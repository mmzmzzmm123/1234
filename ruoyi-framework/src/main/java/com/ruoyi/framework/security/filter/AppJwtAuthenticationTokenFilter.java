package com.ruoyi.framework.security.filter;

import com.alibaba.fastjson2.JSONObject;
import com.ruoyi.common.constant.RespMessageConstants;
import com.ruoyi.common.core.domain.dto.LoginDTO;
import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.framework.web.service.AppTokenService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import java.io.PrintWriter;
import java.util.Arrays;

/**
 * token过滤器 验证token有效性
 *
 * @author ruoyi
 */
@Component
public class AppJwtAuthenticationTokenFilter extends OncePerRequestFilter {

    private static final Logger log = LoggerFactory.getLogger(AppJwtAuthenticationTokenFilter.class);

    @Autowired
    private AppTokenService appTokenService;

    private static final String[] WHITELIST_URLS = {"/app/home/gauge/label/list", "/app/home/gauge/class/list"};

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
            if (matcher.match(filterUrl, request.getRequestURI()) && !Arrays.asList(WHITELIST_URLS).contains(request.getRequestURI())) {
                flag = true;
                break;
            }
        }
        if (flag) {
            if (StringUtils.isNull(loginUser)) {
                JSONObject json = new JSONObject();
                json.put("code", 401);
                json.put("msg", "用户未登录");
                this.output(json, request, response);
                log.error("用户未登录 code: {}" , RespMessageConstants.ACCESS_TOKEN_EXPIRED_CODE);
            }
            appTokenService.verifyToken(loginUser);
        }

        chain.doFilter(request, response);
    }

    public void output(Object json, HttpServletRequest request, HttpServletResponse response) throws IOException {
        String header = request.getHeader("Origin");
        response.setContentType("application/json;charset=UTF-8;");
        PrintWriter out = response.getWriter();
        out.println(json);
        out.flush();
        out.close();
    }
}
