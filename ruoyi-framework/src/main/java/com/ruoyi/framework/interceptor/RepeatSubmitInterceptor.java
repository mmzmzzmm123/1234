package com.ruoyi.framework.interceptor;

import com.alibaba.fastjson.JSONObject;
import com.ruoyi.common.annotation.RepeatSubmit;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.utils.ServletUtils;
import com.ruoyi.common.utils.ip.IpUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.NamedThreadLocal;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Method;
import java.net.URLDecoder;

/**
 * 防止重复提交拦截器
 *
 * @author ruoyi
 */
@Component
public abstract class RepeatSubmitInterceptor implements HandlerInterceptor {

    private static final ThreadLocal<Long> START_TIME_THREAD_LOCAL = new NamedThreadLocal<>("ThreadLocal StartTime");
    protected final Logger logger = LoggerFactory.getLogger(RepeatSubmitInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        //1、开始时间
        //线程绑定变量（该数据只有当前请求的线程可见）
        START_TIME_THREAD_LOCAL.set(System.currentTimeMillis());
        if (handler instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            Method method = handlerMethod.getMethod();
            RepeatSubmit annotation = method.getAnnotation(RepeatSubmit.class);
            if (annotation != null) {
                if (this.isRepeatSubmit(request)) {
                    AjaxResult ajaxResult = AjaxResult.error("不允许重复提交，请稍后再试");
                    ServletUtils.renderString(response, JSONObject.toJSONString(ajaxResult));
                    return false;
                }
            }
        }
        return true;
//        else {
//            return preHandle(request, response, handler);
//        }
    }

    /**
     * 验证是否重复提交由子类实现具体的防重复提交的规则
     */
    public abstract boolean isRepeatSubmit(HttpServletRequest request);

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object o, Exception e) throws UnsupportedEncodingException {
        // 得到线程绑定的局部变量（开始时间）
        Long beginTime = START_TIME_THREAD_LOCAL.get();
        //2、结束时间
        Long endTime = System.currentTimeMillis();
        String uri = URLDecoder.decode(request.getRequestURI(), "utf-8");
        logger.info("耗时:{} IP:{} URI:{}", (endTime - beginTime) + "ms", IpUtils.getIpAddr(request), uri);
        // 删除线程变量中的数据，防止内存泄漏
        START_TIME_THREAD_LOCAL.remove();
    }
}
