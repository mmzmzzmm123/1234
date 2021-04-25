package com.ruoyi.framework.config;

import com.github.pagehelper.util.StringUtil;
import com.google.common.util.concurrent.RateLimiter;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ruoyi.common.constant.HttpStatus;
import com.ruoyi.common.exception.CustomException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import lombok.extern.slf4j.Slf4j;


@Slf4j
public class GInterceptor implements HandlerInterceptor{
    private Logger LOG = LoggerFactory.getLogger(GInterceptor.class);

    // // 用来存放不同接口的RateLimiter(key为接口名称，value为RateLimiter)
    private ConcurrentHashMap<String, RateLimiterDelay> map = new ConcurrentHashMap<>();

    /**
     * 1、判断是否有登陆 token 是否存在 2、如果token不存在
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        String ip = this.getClientIp(request);
        String uri = request.getRequestURI();
        String token = request.getHeader("token");
        String deviceType = request.getHeader("device_type");
        String deviceInfo = request.getHeader("device_info");
        String version = request.getHeader("version");
        StringBuffer buffer = new StringBuffer();
        String request_key = "";
        if (StringUtil.isEmpty(token)) {
            request_key = buffer.append(ip).append(":").append(uri).toString();
        } else {
            request_key = buffer.append(token).append(":").append(uri).toString();
        }

        if (uri != null && uri.length() > 5) {
            RateLimiterDelay rateLimiterDelay = map.get(request_key);
            if (rateLimiterDelay == null) {
                rateLimiterDelay = new RateLimiterDelay( RateLimiter.create(10));

                RateLimiterDelay putByOtherThread = map.putIfAbsent(request_key,rateLimiterDelay);
                if (putByOtherThread != null) {
                    rateLimiterDelay = putByOtherThread;
                }
            }

            if (rateLimiterDelay.tryAcquire()) {

            } else {
                LOG.debug(request_key + "：重复提交---------:" + System.currentTimeMillis());
                throw new CustomException("获取用户信息异常", HttpStatus.FREQUENT_OPERATION);
            }
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
                           ModelAndView modelAndView) throws Exception {
        HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
            throws Exception {
        HandlerInterceptor.super.afterCompletion(request, response, handler, ex);

    }

    private String getClientIp(HttpServletRequest request) {

        String remoteAddr = "";

        if (request != null) {
            remoteAddr = request.getHeader("X-FORWARDED-FOR");
            if (remoteAddr == null || "".equals(remoteAddr)) {
                remoteAddr = request.getRemoteAddr();
            }
        }
        return remoteAddr;
    }

    @Override
    public int hashCode() {
        // TODO Auto-generated method stub
        return super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        // TODO Auto-generated method stub
        return super.equals(obj);
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        // TODO Auto-generated method stub
        return super.clone();
    }

    @Override
    public String toString() {
        // TODO Auto-generated method stub
        return super.toString();
    }

    @Override
    protected void finalize() throws Throwable {
        // TODO Auto-generated method stub
        super.finalize();
    }



    static final class RateLimiterDelay{
        RateLimiter rateLimiter;
        long timeMillis;
        AtomicInteger firstSecondCount;
        public RateLimiterDelay(RateLimiter rateLimiter){
            this.rateLimiter = rateLimiter;
            firstSecondCount = new AtomicInteger(0);
            timeMillis = System.currentTimeMillis();
        }
        public boolean tryAcquire() {
            if (System.currentTimeMillis() - timeMillis < 500 && firstSecondCount.get()<20){
                firstSecondCount.addAndGet(1);
                return true;
            }
            return rateLimiter.tryAcquire();

        }
    }

}



