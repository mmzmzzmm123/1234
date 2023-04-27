package com.ruoyi.framework.aspectj;

import org.apache.commons.codec.digest.DigestUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * 操作日志记录处理
 *
 * @author ruoyi
 */
@Aspect
@Component
public class TokenInterceptorAdapter {

    private static final String SALT = "qzdsj@123";
    private static final Logger log = LoggerFactory.getLogger(TokenInterceptorAdapter.class);
    public static final String HEADER_TOKEN="TOKEN";
    public static final String HEADER_TIMESTAMP="TIMESTAMP";
    @Around("@annotation(com.ruoyi.common.annotation.TokenValidation)")
    public Object validateToken(ProceedingJoinPoint joinPoint) throws Throwable {
        // 获取方法的参数值
        Object[] args = joinPoint.getArgs();
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        assert attributes != null;
        HttpServletRequest request = attributes.getRequest();

        // 获取Header
        String token = request.getHeader(HEADER_TOKEN);
        String timestampStr = request.getHeader(HEADER_TIMESTAMP);
        // 转换时间戳字符串为long型
        long timestamp = Long.parseLong(timestampStr);
        // 计算用于md5验证的原始字符串
        String original = timestampStr + SALT;
        String md5 = DigestUtils.md5Hex(original);
        log.info("md5数据："+md5);
        // 验证token是否正确
        if (!md5.equals(token)) {
            throw new RuntimeException("invalid token");
        }
        // 检查时间戳是否超过5分钟
        long currentTime = System.currentTimeMillis();
        if (currentTime - timestamp > 5 * 60 * 1000) {
            throw new RuntimeException("timestamp is expired");
        }
        // 调用目标方法
        return joinPoint.proceed(args);
    }
}
