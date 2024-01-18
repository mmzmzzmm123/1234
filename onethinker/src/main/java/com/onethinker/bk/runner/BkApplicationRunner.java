package com.onethinker.bk.runner;

import com.ruoyi.common.core.redis.RedisCache;
import com.ruoyi.common.enums.CacheEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.Collection;

/**
 * @author : yangyouqi
 * @date : 2024/1/18 9:47
 */
@Component
public class BkApplicationRunner implements ApplicationRunner {

    @Autowired
    private RedisCache redisCache;

    private static final Logger LOG = LoggerFactory.getLogger(BkApplicationRunner.class);


    /**
     * 删除博客相关缓存内容
     * @param args incoming application arguments
     * @throws Exception
     */
    @Override
    public void run(ApplicationArguments args) throws Exception {
        // 获取缓存相关key
        Collection<String> cacheKeys = redisCache.keys(CacheEnum.WEB_INFO.getCode() + "*");
        if (cacheKeys.isEmpty()) {
            LOG.info("博客本次启动暂无相关缓存信息");
            return;
        }
        // 删除网站缓存信息
        boolean b = redisCache.deleteObject(cacheKeys);
        LOG.info("博客相关缓存删除：{}",b);

    }
}
