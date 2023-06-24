package com.ruoyi.web.controller.monitor;

import com.ruoyi.common.constant.CacheConstants;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.utils.CacheUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.system.domain.SysCache;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * 缓存监控
 *
 * @author ruoyi
 */
@RestController
@RequestMapping("/monitor/cache")
public class CacheController {
    private final static List<SysCache> caches = new ArrayList<SysCache>();

    {
        caches.add(new SysCache(CacheConstants.LOGIN_TOKEN_CACHENAME, "用户信息"));
        caches.add(new SysCache(CacheConstants.SYS_CONFIG_CACHENAME, "配置信息"));
        caches.add(new SysCache(CacheConstants.SYS_DICT_CACHENAME, "数据字典"));
        caches.add(new SysCache(CacheConstants.CAPTCHA_CODE_CACHENAME, "验证码"));
        caches.add(new SysCache(CacheConstants.REPEAT_SUBMIT_CACHENAME, "防重提交"));
        caches.add(new SysCache(CacheConstants.RATE_LIMIT_CACHENAME, "限流处理"));
        caches.add(new SysCache(CacheConstants.PWD_ERR_CNT_CACHENAME, "密码错误次数"));
    }

    @PreAuthorize("@ss.hasPermi('monitor:cache:list')")
    @GetMapping()
    public AjaxResult getInfo() throws Exception {
        Map<String, Object> result = new HashMap<>(3);
        if (CacheUtils.getCacheManager() instanceof RedisCacheManager) {
            Properties info = (Properties) CacheUtils.getRedisTemplate().execute((RedisCallback<Object>) connection -> connection.info());
            Properties commandStats = (Properties) CacheUtils.getRedisTemplate().execute((RedisCallback<Object>) connection -> connection.info("commandstats"));
            Object dbSize = CacheUtils.getRedisTemplate().execute((RedisCallback<Object>) connection -> connection.dbSize());
            result.put("info", info);
            result.put("dbSize", dbSize);
            List<Map<String, String>> pieList = new ArrayList<>();
            commandStats.stringPropertyNames().forEach(key -> {
                Map<String, String> data = new HashMap<>(2);
                String property = commandStats.getProperty(key);
                data.put("name", StringUtils.removeStart(key, "cmdstat_"));
                data.put("value", StringUtils.substringBetween(property, "calls=", ",usec"));
                pieList.add(data);
            });
            result.put("commandStats", pieList);
        }
        return AjaxResult.success(result);
    }

    @PreAuthorize("@ss.hasPermi('monitor:cache:list')")
    @GetMapping("/getNames")
    public AjaxResult cache() {
        return AjaxResult.success(caches);
    }

    @PreAuthorize("@ss.hasPermi('monitor:cache:list')")
    @GetMapping("/getKeys/{cacheName}")
    public AjaxResult getCacheKeys(@PathVariable String cacheName) {
        Set<String> keyset = CacheUtils.getkeys(cacheName);
        return AjaxResult.success(keyset);
    }

    @PreAuthorize("@ss.hasPermi('monitor:cache:list')")
    @GetMapping("/getValue/{cacheName}/{cacheKey}")
    public AjaxResult getCacheValue(@PathVariable String cacheName, @PathVariable String cacheKey) {
        String cacheValue = CacheUtils.get(cacheName, cacheKey).get().toString();
        SysCache sysCache = new SysCache(cacheName, cacheKey, cacheValue);
        return AjaxResult.success(sysCache);
    }

    @PreAuthorize("@ss.hasPermi('monitor:cache:list')")
    @DeleteMapping("/clearCacheName/{cacheName}")
    public AjaxResult clearCacheName(@PathVariable String cacheName) {
        CacheUtils.clear(cacheName);
        return AjaxResult.success();
    }

    @PreAuthorize("@ss.hasPermi('monitor:cache:list')")
    @DeleteMapping("/clearCacheKey/{cacheKey}")
    public AjaxResult clearCacheKey(@PathVariable String cacheKey) {
        CacheUtils.evictIfPresent(cacheKey.split(":")[0], cacheKey.split(":")[1]);
        return AjaxResult.success();
    }

    @PreAuthorize("@ss.hasPermi('monitor:cache:list')")
    @DeleteMapping("/clearCacheAll")
    public AjaxResult clearCacheAll() {
        for (String cacheName : CacheUtils.getCacheManager().getCacheNames()) {
            CacheUtils.clear(cacheName);
        }
        return AjaxResult.success();
    }
}
