package com.hxzh.front.security.tokenservice.impl;

import com.hxzh.front.common.RedisCache;
import com.hxzh.front.common.util.TokenUtils;
import com.hxzh.front.security.detail.LoginUser;
import com.hxzh.front.security.tokenservice.TokenService;
import io.jsonwebtoken.Claims;
import lombok.Data;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import javax.servlet.http.HttpServletRequest;

@ConfigurationProperties(prefix = "token.user")
@Data
public class UserTokenService implements TokenService {
    private static final Logger log = LoggerFactory.getLogger(UserTokenService.class);


    /**
     * 自定义标识，默认为Authentication
     */
    @Value("${token.header}")
    private String header;

    /**
     * 密钥
     */
    private String secret;

    /**
     * 过期时间
     */
    private int expireTime;

    /**
     * 方案（默认Bearer）
     */
    private String scheme;

    /**
     * 类型前缀
     */
    private String prefix;


    @Autowired
    private RedisCache redisCache;


    @Override
    public Authentication getAuthentication(HttpServletRequest request) {
        String token = TokenUtils.getTokenByRequest(header, scheme, prefix, request);
        Claims claims = TokenUtils.parseToken(token, secret);
        String redisKey = TokenUtils.getRedisKey(claims.getId(), prefix);
        LoginUser loginUser = redisCache.getCacheObject(redisKey);
        if (ObjectUtils.isNotEmpty(loginUser)) {
            Authentication authentication = new UsernamePasswordAuthenticationToken(loginUser, null, loginUser.getAuthorities());
            return authentication;
        }
        return null;
    }

    @Override
    public boolean isSupport(HttpServletRequest request) {
        String token = request.getHeader(header);
        if (StringUtils.isNotEmpty(token) && token.contains(scheme) && token.contains(prefix)) {
            return true;
        }
        return false;
    }

    @Override
    public String createToken(Claims claims) {
        String token = TokenUtils.createToken(claims, secret);
        return token;
    }

    @Override
    public String getTokenByRequest(HttpServletRequest request) {
        String token = TokenUtils.getTokenByRequest(header, scheme, prefix, request);
        return token;
    }

    @Override
    public Claims parseToken(String token) {
        Claims claims = TokenUtils.parseToken(token, secret);
        return claims;
    }

    @Override
    public String getRedisKey(String id) {
        String key = TokenUtils.getRedisKey(id, prefix);
        return key;
    }
}
