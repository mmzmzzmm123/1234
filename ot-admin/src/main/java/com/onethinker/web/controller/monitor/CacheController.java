package com.onethinker.web.controller.monitor;

import com.onethinker.common.constant.ServicePathConstant;
import com.onethinker.common.core.domain.AjaxResult;
import com.onethinker.common.enums.CacheEnum;
import com.onethinker.common.utils.StringUtils;
import com.onethinker.system.domain.SysCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * 缓存监控
 *
 * @author yangyouqi
 */
@RestController
@RequestMapping("/monitor/cache")
public class CacheController {
    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    private final static List<SysCache> CACHES = new ArrayList<SysCache>();

    {
        for (CacheEnum value : CacheEnum.values()) {
            CACHES.add(new SysCache(value.getCode(), value.getMsg()));
        }
    }

    @PreAuthorize("@ss.hasPermi('monitor:cache:list')")
    @GetMapping(ServicePathConstant.PREFIX_SERVICE_PATH)
    public AjaxResult getInfo() throws Exception {
        Properties info = (Properties) redisTemplate.execute((RedisCallback<Object>) connection -> connection.info());
        Properties commandStats = (Properties) redisTemplate.execute((RedisCallback<Object>) connection -> connection.info("commandstats"));
        Object dbSize = redisTemplate.execute((RedisCallback<Object>) connection -> connection.dbSize());

        Map<String, Object> result = new HashMap<>(3);
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
        return AjaxResult.success(result);
    }

    @PreAuthorize("@ss.hasPermi('monitor:cache:list')")
    @GetMapping(ServicePathConstant.PREFIX_SERVICE_PATH + "/getNames")
    public AjaxResult cache() {
        return AjaxResult.success(CACHES);
    }

    @PreAuthorize("@ss.hasPermi('monitor:cache:list')")
    @GetMapping(ServicePathConstant.PREFIX_SERVICE_PATH + "/getKeys/{cacheName}")
    public AjaxResult getCacheKeys(@PathVariable String cacheName) {
        Set<String> cacheKeys = redisTemplate.keys(cacheName + "*");
        return AjaxResult.success(cacheKeys);
    }

    @PreAuthorize("@ss.hasPermi('monitor:cache:list')")
    @GetMapping(ServicePathConstant.PREFIX_SERVICE_PATH + "/getValue/{cacheName}/{cacheKey}")
    public AjaxResult getCacheValue(@PathVariable String cacheName, @PathVariable String cacheKey) {
        String cacheValue = redisTemplate.opsForValue().get(cacheKey);
        SysCache sysCache = new SysCache(cacheName, cacheKey, cacheValue);
        return AjaxResult.success(sysCache);
    }

    @PreAuthorize("@ss.hasPermi('monitor:cache:list')")
    @DeleteMapping( ServicePathConstant.PREFIX_SERVICE_PATH + "/clearCacheName/{cacheName}")
    public AjaxResult clearCacheName(@PathVariable String cacheName) {
        Collection<String> cacheKeys = redisTemplate.keys(cacheName + "*");
        redisTemplate.delete(cacheKeys);
        return AjaxResult.success();
    }

    @PreAuthorize("@ss.hasPermi('monitor:cache:list')")
    @DeleteMapping( ServicePathConstant.PREFIX_SERVICE_PATH + "/clearCacheKey/{cacheKey}")
    public AjaxResult clearCacheKey(@PathVariable String cacheKey) {
        redisTemplate.delete(cacheKey);
        return AjaxResult.success();
    }

    @PreAuthorize("@ss.hasPermi('monitor:cache:list')")
    @DeleteMapping( ServicePathConstant.PREFIX_SERVICE_PATH + "/clearCacheAll")
    public AjaxResult clearCacheAll() {
        Collection<String> cacheKeys = redisTemplate.keys("*");
        redisTemplate.delete(cacheKeys);
        return AjaxResult.success();
    }
}
