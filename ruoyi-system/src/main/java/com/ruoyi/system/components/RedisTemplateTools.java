package com.ruoyi.system.components;

import org.springframework.data.redis.core.RedisTemplate;
import com.ruoyi.common.utils.spring.SpringUtils;

public class RedisTemplateTools {

	public static RedisTemplate<String, String> get() {
		return SpringUtils.getBean("stringRedisTemplate");
	}
}
