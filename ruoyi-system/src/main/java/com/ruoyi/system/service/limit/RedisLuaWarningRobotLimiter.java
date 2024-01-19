package com.ruoyi.system.service.limit;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.scripting.support.ResourceScriptSource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Collections;

/***
 *
 */
@Service
public class RedisLuaWarningRobotLimiter implements InitializingBean, WarningRobotLimitService {
    @Autowired
    //private RedisTemplate<String, Object> redisTemplate;
    private RedisTemplate redisTemplate;

    DefaultRedisScript<Long> script;

    @Override
    public void afterPropertiesSet() throws Exception {
        // 加载 lua
        script = new DefaultRedisScript<>();
        script.setScriptSource(new ResourceScriptSource(new ClassPathResource("limit.lua")));
        script.setResultType(Long.class);

    }

    @Override
    public Long getWarningRobotLimit(String key,Integer interval,Integer limitCount) {
        if (StringUtils.isEmpty(key) ) {
            //return 0l;
            return 0L;
        }
        //return redisTemplate.execute(script, Collections.singletonList(key),interval+"",limitCount+"");
            return (Long) redisTemplate.execute(script, Collections.singletonList(key),interval,limitCount);
    }



}
