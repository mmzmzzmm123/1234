package com.ruoyi.system.openapi;

import cn.hutool.core.util.StrUtil;
import com.ruoyi.common.core.redis.RedisCache;
import com.ruoyi.common.utils.spring.SpringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * @author 朱少波
 * @date 2024/1/18/018 14:46
 */
@Slf4j
public class OpenApiRequestCache {

    public static void cache(String optNo, String json) {
        try {
            SpringUtils.getBean(RedisCache.class).setCacheObject("ruoyi-admin:request:" + optNo
                    , "1", 30 * 60 * 60, TimeUnit.SECONDS);
        } catch (Exception e) {
            log.error("cacheRequestParameters.error={},{}", optNo, json, e);
        }
    }

    public static String getAndRemove(String optNo) {
        String result = "";
        if(StrUtil.isBlank(optNo)){
            return "";
        }

        try {
            String key = "ruoyi-admin:request:" + optNo;
            RedisCache redisCache = SpringUtils.getBean(RedisCache.class);

            result = redisCache.getCacheObject(key);
            if(StrUtil.isNotBlank(result)){
                redisCache.deleteObject(key);
            }
        } catch (Exception e) {
            log.error("getRequestParameters.error={}", optNo, e);
        }
        return result;
    }

}
