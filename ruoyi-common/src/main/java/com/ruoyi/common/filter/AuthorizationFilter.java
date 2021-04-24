package com.ruoyi.common.filter;

import com.ruoyi.common.properties.RuoyiProperties;
import com.ruoyi.common.utils.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
public class AuthorizationFilter implements Filter {

    private static final List<String> excludedList = new ArrayList<>();

    @Autowired
    private RuoyiProperties ruoyiProperties;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        if (ObjectUtils.isEmpty(ruoyiProperties.getExcludedUri())) {
            excludedList.addAll(Arrays.stream(ruoyiProperties.getExcludedUri().split(",")).distinct()
                    .collect(Collectors.toList()));
        }
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        if (StringUtil.isWildCardsMatchStr(req.getRequestURI(), excludedList)) {
            filterChain.doFilter(servletRequest, servletResponse);
            return;
        }
    }

    @Override
    public void destroy() {

    }
}
