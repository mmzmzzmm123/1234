package com.ruoyi.system.components.movie;

import java.util.concurrent.TimeUnit;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.StringUtils;
import com.ruoyi.common.utils.spring.SpringUtils;
import com.ruoyi.system.components.RedisTemplateTools;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class GlobalIndexContext {

	@SuppressWarnings("unchecked")
	public static void setIndex(String chatroomId, String playId, int index) {
		RedisTemplateTools.get().opsForValue().set(
				"ruoyi:GlobalIndexContext:" + playId + ":" + chatroomId, index + "", 60 * 60 * 24 * 5,
				TimeUnit.SECONDS);
		log.info("GlobalIndexContext.set {} {} {}", chatroomId, playId, index);
	}

	public static int getIndex(String chatroomId, String playId) {
		String v = (String) RedisTemplateTools.get().opsForValue()
				.get("ruoyi:GlobalIndexContext:" + playId + ":" + chatroomId);
		if (StringUtils.isEmpty(v)) {
			return -1;
		}
		return Integer.parseInt(v);
	}

	public static int next(String chatroomId, String playId) {
		try {
			int index = getIndex(chatroomId, playId);
			index++;
			setIndex(chatroomId, playId, index);
			return index;
		} finally {
		}
	}

}
