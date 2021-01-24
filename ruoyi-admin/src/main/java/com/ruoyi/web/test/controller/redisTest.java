package com.ruoyi.web.test.controller;

import com.ruoyi.common.core.redis.RedisUtil;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @Auther: Wang
 * @Date: 2021/01/24 14:04
 * 功能描述:
 */
public class redisTest extends BaseSpringBootTest{

    @Autowired
    RedisUtil redisUtil;

    @Test
    public void redisdemo(){
        redisUtil.sAdd("user:follow:1","1","2");
    }
}
