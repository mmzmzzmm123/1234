package com.ruoyi.framework.config;

import com.ruoyi.common.utils.StringUtils;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 解决跨域问题
 *
 * @author wuchunhao
 * @date 2019-06-01
 */
@Component
@WebFilter(urlPatterns = "/*")
@Order(-9999)
public class CorsFilter extends HttpFilter {

    private static final long serialVersionUID = 1L;
    private static final String OPTIONS_METHOD = "OPTIONS";

    @Override
    protected void doFilter(HttpServletRequest req, HttpServletResponse res, FilterChain chain) throws IOException, ServletException {
        String origin = req.getHeader(HttpHeaders.ORIGIN);
        if (!StringUtils.isEmpty(origin)) {
            res.addHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN, origin);
            res.addHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_HEADERS, "*");
            res.addHeader(HttpHeaders.ACCESS_CONTROL_REQUEST_HEADERS, "*");
            res.addHeader(HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS, "*");
            res.addHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_CREDENTIALS, "true");
            res.addHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_METHODS, "GET, POST, PUT, OPTIONS, DELETE");
            if (OPTIONS_METHOD.equalsIgnoreCase(req.getMethod())) {
                res.setStatus(HttpServletResponse.SC_NO_CONTENT);
                res.setContentType(MediaType.TEXT_HTML_VALUE);
                res.setCharacterEncoding("utf-8");
                res.setContentLength(0);
                res.addHeader(HttpHeaders.ACCESS_CONTROL_MAX_AGE, "1800");
                return;
            }
        }
        super.doFilter(req, res, chain);
    }
}
