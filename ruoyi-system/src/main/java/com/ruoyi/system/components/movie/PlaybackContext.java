package com.ruoyi.system.components.movie;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.StringUtils;

import com.ruoyi.common.utils.spring.SpringUtils;

public class PlaybackContext {

	@SuppressWarnings("unchecked")
	public static void pause(String playId, String chatroomId) {
		SpringUtils.getBean(RedisTemplate.class).opsForValue().set("ruoyi:PlaybackContext:" + playId + ":" + chatroomId,
				60 * 60 * 24 * 30);
	}

	@SuppressWarnings("unchecked")
	public static void resume(String playId, String chatroomId) {
		SpringUtils.getBean(RedisTemplate.class).delete("ruoyi:PlaybackContext:" + playId + ":" + chatroomId);
	}

	@SuppressWarnings("deprecation")
	public static boolean isPause(String playId, String chatroomId) {
		String v = (String) SpringUtils.getBean(RedisTemplate.class).opsForValue()
				.get("ruoyi:PlaybackContext:" + playId + ":" + chatroomId);

		if (StringUtils.isEmpty(v)) {
			return false;
		}
		return "1".equals(v);
	}
}
