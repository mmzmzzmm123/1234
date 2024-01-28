package com.ruoyi;

import com.alibaba.fastjson2.support.spring.http.converter.FastJsonHttpMessageConverter;
import com.ruoyi.common.config.dymic.EnableRedisConfigure;
import com.ruoyi.common.core.delayqueue.EnableRedissonFastDelayQueue;
import com.ruoyi.common.utils.spring.SpringUtils;
import com.ruoyi.system.service.impl.IntoGroupService;
import com.ruoyi.system.service.limit.WarningRobotLimitService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.Import;
import org.springframework.data.redis.core.BoundValueOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.util.concurrent.TimeUnit;

/**
 * 启动程序
 *
 * @author ruoyi
 */
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
@EnableRedisConfigure
@Import(FastJsonHttpMessageConverter.class)
@EnableScheduling
@EnableRedissonFastDelayQueue
public class RuoYiApplication implements ApplicationRunner {
    @Autowired
    IntoGroupService intoGroupService;

    @Autowired
    WarningRobotLimitService warningRobotLimitService;

    public static void main(String[] args) {
        // System.setProperty("spring.devtools.restart.enabled", "false");
        SpringApplication.run(RuoYiApplication.class, args);
        System.out.println("(♥◠‿◠)ﾉﾞ  若依启动成功   ლ(´ڡ`ლ)ﾞ  \n" +
                " .-------.       ____     __        \n" +
                " |  _ _   \\      \\   \\   /  /    \n" +
                " | ( ' )  |       \\  _. /  '       \n" +
                " |(_ o _) /        _( )_ .'         \n" +
                " | (_,_).' __  ___(_ o _)'          \n" +
                " |  |\\ \\  |  ||   |(_,_)'         \n" +
                " |  | \\ `'   /|   `-'  /           \n" +
                " |  |  \\    /  \\      /           \n" +
                " ''-'   `'-'    `-..-'              ");
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        RedisTemplate<String, String> redisTemplate = SpringUtils.getBean("redisTemplate");
        BoundValueOperations<String, String> valueOperations =
                redisTemplate.boundValueOps("ut-buleprint-backend:OpenApiToken");
        String token = valueOperations.get();
        if (StringUtils.isBlank(token)) {
            valueOperations.expire(1, TimeUnit.MILLISECONDS);
        }
    }
}
