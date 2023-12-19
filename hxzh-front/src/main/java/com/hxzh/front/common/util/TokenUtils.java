package com.hxzh.front.common.util;

import com.hxzh.front.common.constant.Constants;
import com.sun.istack.internal.NotNull;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;

public class TokenUtils {

    /**
     * 解析令牌
     *
     * @param token  令牌
     * @param secret 密钥
     * @return {@link Claims}
     */
    public static final  Claims parseToken(String token, String secret)
    {
        return Jwts.parser()
                .setSigningKey(secret)
                .parseClaimsJws(token)
                .getBody();
    }

    /**
     * 提取令牌
     *
     * @param header  头部
     * @param scheme  方案
     * @param prefix  前缀
     * @param request 请求
     * @return {@link String}
     */
    public static final String getTokenByRequest(@NotNull String header, String scheme, String prefix, HttpServletRequest request)
    {
        String token = request.getHeader(header);
        if (StringUtils.isNotEmpty(token))
        {
            token = StringUtils.replace(token,scheme+ Constants.PERMISSION_DELIMETER,"");
            token = StringUtils.replace(token,prefix+Constants.PERMISSION_DELIMETER,"");
            token=StringUtils.trim(token);
        }
        return token;
    }

    public static final String createToken(Claims claims, String secret)
    {
        return Jwts.builder()
                .setClaims(claims)
                .signWith(io.jsonwebtoken.SignatureAlgorithm.HS512, secret)
                .compact();
    }
    /**
     * 拼接redis key
     *
     * @param uuid   uuid
     * @param prefix 前缀
     * @return {@link String}
     */
    public static final  String getRedisKey(String uuid, String prefix)
    {
        return prefix+ uuid;
    }

}
