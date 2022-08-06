/**
 * Copyright (c) 2016-2019 人人开源 All rights reserved.
 *
 * https://www.renren.io
 *
 * 版权所有，侵权必究！
 */

package com.ruoyi.api.resolver;

import cn.hutool.core.util.ObjectUtil;
import com.ruoyi.api.annotation.LoginUser;
import com.ruoyi.api.interceptor.AuthInterceptor;
import com.ruoyi.data.domain.User;
import com.ruoyi.data.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.annotation.Resource;

/**
 * 有@LoginUser注解的方法参数，注入当前登录用户
 * @author Mark sunlightcs@gmail.com
 */
@Component
@Slf4j
public class UserArgumentResolver implements HandlerMethodArgumentResolver {
    @Resource
    private UserService userService;

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(LoginUser.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer container,NativeWebRequest request, WebDataBinderFactory factory) {
        Object object = request.getAttribute(AuthInterceptor.USER_KEY, RequestAttributes.SCOPE_REQUEST);
        return ObjectUtil.isEmpty(object) ? null : userService.getById((Long)object);
    }
}
