package com.ruoyi.system.components.movie;

import org.springframework.util.StringUtils;
import com.ruoyi.system.components.RedisTemplateTools;

public class PlaybackContext {

	public static void pause(String playId, String chatroomId) {
		RedisTemplateTools.get().opsForValue().set("ruoyi:PlaybackContext:" + playId + ":" + chatroomId, "1",
				60 * 60 * 24 * 30);
	}

	public static void resume(String playId, String chatroomId) {
		RedisTemplateTools.get().delete("ruoyi:PlaybackContext:" + playId + ":" + chatroomId);
	}

	@SuppressWarnings("deprecation")
	public static boolean isPause(String playId, String chatroomId) {
		String v = RedisTemplateTools.get().opsForValue()
				.get("ruoyi:PlaybackContext:" + playId + ":" + chatroomId);

		if (StringUtils.isEmpty(v)) {
			return false;
		}
		return "1".equals(v);
	}
}
