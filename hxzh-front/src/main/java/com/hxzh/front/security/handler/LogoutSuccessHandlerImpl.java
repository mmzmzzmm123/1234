package com.hxzh.front.security.handler;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import com.hxzh.front.common.AjaxResult;
import com.hxzh.front.common.RedisCache;
import com.hxzh.front.common.util.ServletUtils;
import com.hxzh.front.security.tokenservice.impl.UserTokenService;
import io.jsonwebtoken.Claims;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import com.alibaba.fastjson2.JSON;



/**
 * 自定义退出处理类 返回成功
 *
 * @author ruoyi
 */
@Configuration
public class LogoutSuccessHandlerImpl implements LogoutSuccessHandler
{
    @Autowired
    private UserTokenService userTokenService;
    @Autowired
    private RedisCache redisCache;
    /**
     * 退出处理
     *
     * @return
     */
    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
            throws IOException, ServletException
    {
        String token = userTokenService.getTokenByRequest(request);
        Claims claims = userTokenService.parseToken(token);
        String userId = claims.getId();
        String key = userTokenService.getRedisKey(userId);
        if (StringUtils.isNotEmpty(key))
        {
            redisCache.deleteObject(key);
        }
        ServletUtils.renderString(response, JSON.toJSONString(AjaxResult.success("退出成功")));
    }
}
