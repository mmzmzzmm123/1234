package com.ruoyi.framework.interceptor.impl;

import com.ruoyi.common.constant.HttpStatus;
import com.ruoyi.common.exception.CustomException;
import com.ruoyi.common.exception.user.UserException;
import com.ruoyi.common.utils.ServletUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.system.biz.token.LoginUser;
import com.ruoyi.system.biz.token.TokenAuthorize;
import com.ruoyi.system.biz.token.WechatTokenServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.DigestUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

/**
 * 微信小程序相关接口拦截器
 *
 * @author ruoyi
 */
@Component
public class TokenInterceptor implements HandlerInterceptor {

    @Autowired
    private WechatTokenServiceImpl tokenService;

    private static final String SALT = "xdztc@qzdasj";
    private static final String HEADER_KEY_SIGN = "xdztcAuthSign";
    private static final String HEADER_KEY_TS = "xdztcAuthTs";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
    {

        // 如果不是映射到方法直接通过
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        Method method = handlerMethod.getMethod();
        if (method.isAnnotationPresent(TokenAuthorize.class)) {
            TokenAuthorize userLoginToken = method.getAnnotation(TokenAuthorize.class);
            if (userLoginToken.required()) {
                String token = tokenService.getToken(request);
                String sign = request.getHeader(HEADER_KEY_SIGN);
                String ts = request.getHeader(HEADER_KEY_TS);

                if (StringUtils.isEmpty(token)) {
                    throw new UserException("token为空，请重新登录");
                }

                if (StringUtils.isEmpty(sign) || StringUtils.isEmpty(ts)){
                    throw new UserException("参数不合法");
                }

                String signServer = token + ts + SALT;

                // 验证签名:token+时间戳+key
                signServer = DigestUtils.md5DigestAsHex(signServer.getBytes());
                if (!StringUtils.equals(signServer,sign)){
                    throw new UserException("签名不一致");
                }

                // 验证时间戳,超过5分钟为超时-
                long currentTs = System.currentTimeMillis();
                if (currentTs - Long.parseLong(ts) > 5 * 60 * 1000){
                    throw new CustomException("请求超时", HttpStatus.ERROR);
                }

                LoginUser loginUser = tokenService.getLoginUser(ServletUtils.getRequest());
                if (loginUser == null) {
                    throw new CustomException("用户不存在或token过期，请重新登录", HttpStatus.UNAUTHORIZED);
                }
                long userId = loginUser.getUser().getUserId();
                request.setAttribute("userId", userId);
            }
        }
        return true;
    }
}
