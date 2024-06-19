package com.ruoyi.framework.security.filter;

import com.alibaba.fastjson2.JSONObject;
import com.ruoyi.common.constant.RespMessageConstants;
import com.ruoyi.common.core.domain.dto.ConsultDTO;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.framework.web.service.ConsultantTokenService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * token过滤器 验证token有效性
 *
 * @author ruoyi
 */
@Component
public class ConsultJwtAuthenticationTokenFilter extends OncePerRequestFilter {

    private static final Logger log = LoggerFactory.getLogger(ConsultJwtAuthenticationTokenFilter.class);

    @Autowired
    private ConsultantTokenService consultantTokenService;

    private static final String COMMA = ",";

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {

        // 检查URL
        if (request.getRequestURI().startsWith("/consult")) {
            ConsultDTO loginUser = consultantTokenService.getLoginUser(request);

            if (StringUtils.isNull(loginUser)) {
                JSONObject json = new JSONObject();
                json.put("code", 401);
                json.put("msg", "用户未登录");
                log.error("用户未登录 code: {}", RespMessageConstants.ACCESS_TOKEN_EXPIRED_CODE);
                response.setContentType("application/json;charset=UTF-8");
                response.getWriter().write(JSONObject.toJSONString(json));
                return;
            } else {
                consultantTokenService.verifyToken(loginUser);
            }
        }

        chain.doFilter(request, response);
    }



    public void output(Object json, HttpServletRequest request, HttpServletResponse response) throws IOException {
        String header = request.getHeader("Origin");
        response.setContentType("application/json;charset=UTF-8;");
        ServletOutputStream out = response.getOutputStream();
        out.println(JSONObject.toJSONString(json));
        out.flush();
        out.close();
    }
}
