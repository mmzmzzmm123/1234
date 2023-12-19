package com.hxzh.front.security.tokenservice;

import io.jsonwebtoken.Claims;
import org.springframework.security.core.Authentication;

import javax.servlet.http.HttpServletRequest;

public interface TokenService {

    Authentication getAuthentication(HttpServletRequest request);
    boolean isSupport(HttpServletRequest request);
    String createToken(Claims claims);
    String getTokenByRequest(HttpServletRequest request);
    Claims parseToken(String token);
    String getRedisKey(String id);

}
