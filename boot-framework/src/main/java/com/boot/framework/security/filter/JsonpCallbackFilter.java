package com.boot.framework.security.filter;

import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JsonpCallbackFilter  extends OncePerRequestFilter {
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		String requestURI = request.getRequestURI();
		if (requestURI.endsWith("jsonp")) {
			response.addHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN, "*");
			response.addHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_METHODS, "GET, POST, PUT, DELETE");
			response.addHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_HEADERS, "Content-Type, Access-Control-Allow-Headers, Authorization, X-Requested-With");
			response.setContentType("application/javascript");
			String callbackName = request.getParameter("callback");
			response.getWriter().write(callbackName + "(");
			filterChain.doFilter(request, response);
			response.getWriter().write(");");
		} else {
			filterChain.doFilter(request, response);
		}
	}
}
