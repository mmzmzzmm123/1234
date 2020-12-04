/**
 * Copyright (C), 2015-2020, XXX有限公司
 * FileName: Cache
 * Author:   wh
 * Date:     2020/11/5 19:43
 * Description: 缓存处理类
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.ruoyi.common.utils;

import com.ruoyi.common.constant.CacheConstants;
import com.ruoyi.common.constant.Constants;
import com.ruoyi.common.core.redis.RedisCache;
import com.ruoyi.common.utils.spring.SpringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.concurrent.TimeUnit;

/**
 * 功能描述:
 * <缓存处理类>
 *
 * @program: ruoyi
 * @ClassName: Cache
 * @author: wanghui
 * @date: 2020/11/5 19:43
 * @Version: 1.0.0
 * @Copyright: 2020 Inc. All rights reserved. 注意: 本内容仅限于内部传阅,禁止外泄以及用于其他的商业目
 */
@Component
public class CacheComUtils {

    private static String cacheType;

    @Value("${ruoyi.cache.type:ehcache}")
    public void setCacheType(String type) {
        cacheType = type;
    }

    /**
     * 写入缓存
     * @param cacheName
     * @param key
     * @param value
     */
    public static <T> void put(final String cacheName, final String key, final T value)
    {
        if(CacheConstants.CacheType.redis.getName().equals(cacheType)){
            SpringUtils.getBean(RedisCache.class).setCacheObject(key, value);
        }
        else if(CacheConstants.CacheType.ehcache.getName().equals(cacheType)){
            CacheUtils.put(cacheName, key, value);
        }
    }
    /**
     * 写入缓存，Redis带超时时间
     * @param cacheName 缓存的名称
     * @param key 缓存的键值
     * @param value 缓存的值
     * @param timeout 时间
     * @param timeUnit 时间颗粒度
     */
    public static <T> void put(final String cacheName, final String key, final T value,final Integer timeout, final TimeUnit timeUnit)
    {
        if(CacheConstants.CacheType.redis.getName().equals(cacheType)){
            SpringUtils.getBean(RedisCache.class).setCacheObject(key, value, timeout, timeUnit);
        }
        else if(CacheConstants.CacheType.ehcache.getName().equals(cacheType)){
            CacheUtils.put(cacheName, key, value);
        }
    }

    /**
     * 获取缓存
     *
     * @param cacheName
     * @param key
     * @return
     */
    public static <T> T get(final String cacheName, final String key)
    {
        if(CacheConstants.CacheType.redis.getName().equals(cacheType)){
            return SpringUtils.getBean(RedisCache.class).getCacheObject(key);
        }
        else if(CacheConstants.CacheType.ehcache.getName().equals(cacheType)){
            return CacheUtils.get(cacheName, key);
        }
        return null;
    }

    /**
     * 获得缓存的基本对象列表
     *
     * @param cacheName
     * @return 对象列表
     */
    public static Collection<String> keys(final String cacheName)
    {
        if(CacheConstants.CacheType.redis.getName().equals(cacheType)){
            return SpringUtils.getBean(RedisCache.class).keys( CacheConstants.CacheKey.map.get(cacheName)+ "*");
        }
        else if(CacheConstants.CacheType.ehcache.getName().equals(cacheType)){
            return CacheUtils.getCache(cacheName).keys();
        }
        return null;
    }
    /**
     * 从缓存中移除
     *
     * @param cacheName
     * @param key
     */
    public static void remove(final String cacheName, final String key)
    {
        if(CacheConstants.CacheType.redis.getName().equals(cacheType)){
            Collection<String> keys = SpringUtils.getBean(RedisCache.class).keys( CacheConstants.CacheKey.map.get(cacheName)+ "*");
            SpringUtils.getBean(RedisCache.class).deleteObject(keys);
        }
        else if(CacheConstants.CacheType.ehcache.getName().equals(cacheType)){
            CacheUtils.remove(cacheName,key);
        }
    }

    /**
     * 从缓存中移除所有
     *
     * @param cacheName
     */
    public static void removeAll(final String cacheName)
    {
        if(CacheConstants.CacheType.redis.getName().equals(cacheType)){
            Collection<String> keys = SpringUtils.getBean(RedisCache.class).keys( CacheConstants.CacheKey.map.get(cacheName)+ "*");
            SpringUtils.getBean(RedisCache.class).deleteObject(keys);
        }
        else if(CacheConstants.CacheType.ehcache.getName().equals(cacheType)){
            CacheUtils.removeAll(cacheName);
        }
    }

    public static void setCaptcha(final String uuid, final String code){
        if(CacheConstants.CacheType.redis.getName().equals(cacheType)){
            // 保存验证码信息
            String verifyKey = Constants.CAPTCHA_CODE_KEY + uuid;
            SpringUtils.getBean(RedisCache.class).setCacheObject(verifyKey, code, Constants.CAPTCHA_EXPIRATION, TimeUnit.MINUTES);
        }
        else if(CacheConstants.CacheType.ehcache.getName().equals(cacheType)){
            CacheUtils.put(Constants.CAPTCHA_CODE_CACHE, Constants.CAPTCHA_CODE_KEY+uuid, code);
        }
    }
    public static String getCaptcha(final String uuid){
        Object obj = null;
        String code = "";
        if(CacheConstants.CacheType.redis.getName().equals(cacheType)){
            String verifyKey = Constants.CAPTCHA_CODE_KEY + uuid;
            obj = SpringUtils.getBean(RedisCache.class).getCacheObject(verifyKey);
        }
        else if(CacheConstants.CacheType.ehcache.getName().equals(cacheType)){
            obj = CacheUtils.get(Constants.CAPTCHA_CODE_CACHE, Constants.CAPTCHA_CODE_KEY+uuid);
        }
        code = String.valueOf(obj != null ? obj : "");
        return code;
    }
}
