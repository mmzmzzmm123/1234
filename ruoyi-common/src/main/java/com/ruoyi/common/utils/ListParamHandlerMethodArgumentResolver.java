package com.ruoyi.common.utils;

import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * @author : yelifeng
 * @date : 2020/10/16 0016 16:38 xuc5566@gmail.com
 */
public class ListParamHandlerMethodArgumentResolver implements HandlerMethodArgumentResolver {

    private static final String PAGE_PARAMETER_OFFSET_ = "$offset";
    private static final String PAGE_PARAMETER_LIMIT = "$limit";
    private static final String PAGE_PARAMETER_COUNT = "$count";
    private static final String PAGE_PARAMETER_PAGE = "page";
    private static final String PAGE_PARAMETER_SIZE = "size";
    private static final String PAGE_PARAMETER_FILTER = "$filter";
    private static final String PAGE_PARAMETER_ORDER_BY = "$orderby";

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return SlicePage.class.equals(parameter.getParameterType())
                || ListPage.class.equals(parameter.getParameterType());
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
                                  NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {

        Class entityClass = getEntityClazz(parameter);
        if (entityClass != null) {
            int offset = NumberUtils.toInt(webRequest.getParameter(PAGE_PARAMETER_OFFSET_), 0);
            int limit = NumberUtils.toInt(webRequest.getParameter(PAGE_PARAMETER_LIMIT), 20);

            if (StringUtils.isNotBlank(webRequest.getParameter(PAGE_PARAMETER_SIZE))) {
                limit = NumberUtils.toInt(webRequest.getParameter(PAGE_PARAMETER_SIZE), 20);
            }
            if (StringUtils.isNotBlank(webRequest.getParameter(PAGE_PARAMETER_PAGE))) {
                int page = NumberUtils.toInt(webRequest.getParameter(PAGE_PARAMETER_PAGE), 1);
                offset = (page - 1) * limit;
            }

            boolean count = BooleanUtils.toBoolean(webRequest.getParameter(PAGE_PARAMETER_COUNT));

            if (SlicePage.class.equals(parameter.getParameterType())) {
                return this.slicePageBuild(webRequest, entityClass, offset, limit, count);
            } else {
                return this.listPageBuild(webRequest, entityClass, offset, limit, count);
            }
        }
        return null;
    }

    private <T> Object slicePageBuild(NativeWebRequest request, Class<T> entityClass, int offset, int limit, boolean count) {
        return SlicePage.create()
                .count(count)
                .offset(offset)
                .limit(limit)
                .orderBy(request.getParameter(PAGE_PARAMETER_ORDER_BY))
                .build(entityClass);
    }

    private <T> Object listPageBuild(NativeWebRequest request, Class<T> entityClass, int offset, int limit, boolean count) {
        return ListPage.create()
                .count(count)
                .offset(offset)
                .limit(limit)
                .orderBy(request.getParameter(PAGE_PARAMETER_ORDER_BY))
                .filter(request.getParameter(PAGE_PARAMETER_FILTER))
                .build(entityClass);
    }

    private static Class getEntityClazz(MethodParameter parameter) {
        Type type = parameter.getGenericParameterType();
        if (type instanceof ParameterizedType) {
            ParameterizedType parameterizedType = (ParameterizedType) parameter.getGenericParameterType();
            Type[] entity = parameterizedType.getActualTypeArguments();
            if (entity.length > 0) {
                return (Class) entity[0];
            }
        }
        return null;
    }
}
