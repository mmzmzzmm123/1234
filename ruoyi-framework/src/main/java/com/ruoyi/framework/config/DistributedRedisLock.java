package com.ruoyi.framework.config;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * 分布式Redis锁
 */
@Slf4j
@Component
public class DistributedRedisLock {


    @Autowired
    private RedissionConfig redissionConfig;

    // 加锁
    public Boolean lock(String lockName) {
        try {
            if (redissionConfig == null) {
                log.info("DistributedRedisLock redissonClient is null");
                return false;
            }

            RLock lock = redissionConfig.redissonClient().getLock(lockName);
            // 锁10秒后自动释放，防止死锁
            lock.lock(10, TimeUnit.SECONDS);

            log.info("Thread [{}] DistributedRedisLock lock [{}] success", Thread.currentThread().getName(), lockName);
            // 加锁成功
            return true;
        } catch (Exception e) {
            log.error("DistributedRedisLock lock [{}] Exception:", lockName, e);
            return false;
        }
    }

    // 释放锁
    public Boolean unlock(String lockName) {
        try {
            if (redissionConfig == null) {
                log.info("DistributedRedisLock redissonClient is null");
                return false;
            }

            RLock lock = redissionConfig.redissonClient().getLock(lockName);
            lock.unlock();
            log.info("Thread [{}] DistributedRedisLock unlock [{}] success", Thread.currentThread().getName(), lockName);
            // 释放锁成功
            return true;
        } catch (Exception e) {
            log.error("DistributedRedisLock unlock [{}] Exception:", lockName, e);
            return false;
        }
    }

    /*

    @RestController
@RequestMapping("/lock")
public class LockTestController {

    @Autowired
    private DistributedRedisLock distributedRedisLock;

    // 测试分布式锁
    @GetMapping("/testLock")
    public void testLock() {
        for (int i = 0; i < 5; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    Boolean lockFlag = distributedRedisLock.lock("LOCK");
                }
            }).start();
        }
    }

}

     */

}
