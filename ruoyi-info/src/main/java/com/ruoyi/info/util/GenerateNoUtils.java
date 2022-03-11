package com.ruoyi.info.util;

import com.ruoyi.common.core.redis.RedisCache;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.concurrent.TimeUnit;

@Component
public class GenerateNoUtils {

    @Autowired
    private RedisCache redisCache;

    public String tradeNo() {
        String yyyyMMdd = DateFormatUtils.format(new Date(), "yyyyMMdd");
        String incrKey = "trade:" + yyyyMMdd;
        Long increment = redisCache.redisTemplate.opsForValue().increment(incrKey, 1);
        redisCache.expire(incrKey, 1L, TimeUnit.DAYS);
        return yyyyMMdd + paddingFour(increment);
    }

    private static String paddingFour(long l) {
        String s = String.valueOf(l);
        int length = s.length();
        if (length > 3) {
            return s;
        }
        for (; length < 4; length++) {
            s = "0" + s;
        }
        return s;
    }

}
