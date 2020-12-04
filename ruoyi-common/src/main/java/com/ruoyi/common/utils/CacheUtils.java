package com.ruoyi.common.utils;

import com.ruoyi.common.utils.spring.SpringUtils;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Iterator;
import java.util.Set;

/**
 * Cache工具类
 *
 * @author ruoyi
 */
public class CacheUtils
{
    private static Logger logger = LoggerFactory.getLogger(CacheUtils.class);

    private static CacheManager cacheManager = SpringUtils.getBean(CacheManager.class);

    private static final String SYS_CACHE = "sys-cache";

    /**
     * 获取SYS_CACHE缓存
     *
     * @param key
     * @return
     */
    public static <T> T get(final String key)
    {
        return get(SYS_CACHE, key);
    }

    /**
     * 获取SYS_CACHE缓存
     *
     * @param key
     * @param defaultValue
     * @return
     */
    public static <T> T get(final String key,final T defaultValue)
    {
        T value = get(key);
        return value != null ? value : defaultValue;
    }

    /**
     * 写入SYS_CACHE缓存
     *
     * @param key
     * @return
     */
    public static <T> void put(final String key, final T value)
    {
        put(SYS_CACHE, key, value);
    }

    /**
     * 从SYS_CACHE缓存中移除
     *
     * @param key
     * @return
     */
    public static void remove(final String key)
    {
        remove(SYS_CACHE, key);
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
        return (T) getCache(cacheName).get(getKey(key));
    }

    /**
     * 获取缓存
     *
     * @param cacheName
     * @param key
     * @param defaultValue
     * @return
     */
    public static <T> T get(final String cacheName, final String key, final T defaultValue)
    {
        T value = get(cacheName, getKey(key));
        return value != null ? value : defaultValue;
    }

    /**
     * 写入缓存
     *
     * @param cacheName
     * @param key
     * @param value
     */
    public static <T> void put(final String cacheName, final String key, final T value)
    {
        getCache(cacheName).put(getKey(key), value);
    }

    /**
     * 从缓存中移除
     *
     * @param cacheName
     * @param key
     */
    public static void remove(final String cacheName, final String key)
    {
        getCache(cacheName).remove(getKey(key));
    }

    /**
     * 从缓存中移除所有
     *
     * @param cacheName
     */
    public static <T> void removeAll(final String cacheName)
    {
        Cache<String, T> cache = getCache(cacheName);
        Set<String> keys = cache.keys();
        for (Iterator<String> it = keys.iterator(); it.hasNext();)
        {
            cache.remove(it.next());
        }
        logger.info("清理缓存： {} => {}", cacheName, keys);
    }

    /**
     * 从缓存中移除指定key
     *
     * @param keys
     */
    public static void removeByKeys(final Set<String> keys)
    {
        removeByKeys(SYS_CACHE, keys);
    }

    /**
     * 从缓存中移除指定key
     *
     * @param cacheName
     * @param keys
     */
    public static void removeByKeys(final String cacheName, final Set<String> keys)
    {
        for (Iterator<String> it = keys.iterator(); it.hasNext();)
        {
            remove(it.next());
        }
        logger.info("清理缓存： {} => {}", cacheName, keys);
    }

    /**
     * 获取缓存键名
     *
     * @param key
     * @return
     */
    private static String getKey(final String key)
    {
        return key;
    }

    /**
     * 获得一个Cache，没有则显示日志。
     *
     * @param cacheName
     * @return
     */
    public static <T> Cache<String, T> getCache(final String cacheName)
    {
        Cache<String, T> cache = cacheManager.getCache(cacheName);
        if (cache == null)
        {
            throw new RuntimeException("当前系统中没有定义“" + cacheName + "”这个缓存。");
        }
        return cache;
    }

}
