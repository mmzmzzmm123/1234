package com.hxzh.front.security.tokenservice.impl;

import com.hxzh.front.common.constant.Constants;
import com.hxzh.front.common.util.TokenUtils;
import com.hxzh.front.security.authentication.TaskAuthCodeAuthentiction;
import com.hxzh.front.security.tokenservice.TokenService;
import io.jsonwebtoken.Claims;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.security.core.Authentication;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@ConfigurationProperties(prefix = "token.task")
@Data
public class TaskTokenService implements TokenService {
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
    private String schema;

    /**
     * 类型前缀
     */
    private String prefix;

    @Override
    public Authentication getAuthentication(HttpServletRequest request) {
        String token = TokenUtils.getTokenByRequest(header, schema, prefix, request);
        Claims claims = TokenUtils.parseToken(token, secret);
        String id = claims.getId();
        Authentication authentication = new TaskAuthCodeAuthentiction(id, null, null);
        return authentication;
    }

    @Override
    public boolean isSupport(HttpServletRequest request) {
        String token = request.getHeader(header);
        if (StringUtils.isNotEmpty(token) && StringUtils.startsWith(token, schema) && StringUtils.contains(token, prefix)) {
            return true;
        }
        return false;
    }

    @Override
    public String createToken(Claims claims) {
        Date exp = new Date(System.currentTimeMillis() + expireTime * 60 * 1000);
        claims.setExpiration(exp);
        String token = TokenUtils.createToken(claims, secret);
        return schema + Constants.PERMISSION_DELIMETER + prefix+Constants.PERMISSION_DELIMETER + token;
    }

    @Override
    public String getTokenByRequest(HttpServletRequest request) {
        String token = TokenUtils.getTokenByRequest(header, schema, prefix, request);
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
