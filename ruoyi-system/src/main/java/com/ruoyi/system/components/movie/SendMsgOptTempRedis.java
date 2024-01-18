package com.ruoyi.system.components.movie;

import java.util.concurrent.TimeUnit;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.StringUtils;

import com.alibaba.fastjson2.JSON;
import com.ruoyi.common.utils.spring.SpringUtils;
import com.ruoyi.system.components.RedisTemplateTools;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SendMsgOptTempRedis {

	public static void set(String opt, String chatroomId, String playId, int msgSort, String robotNickName,
			String robotId) {
		final SendMsgOptTempEntry entry = new SendMsgOptTempEntry();
		entry.setChatroomId(chatroomId);
		entry.setMsgSort(msgSort);
		entry.setRobotId(robotId);
		entry.setPlayId(playId);
		entry.setRobotNickName(robotNickName);
		RedisTemplateTools.get().opsForValue().set("ruoyi:SendMsgOptTempRedis:" + opt, JSON.toJSONString(entry),
				60 * 60 * 24 * 5, TimeUnit.SECONDS);
		log.info("SendMsgOptTempRedis {} {} {} {} {} {}", opt, chatroomId, playId, msgSort, robotNickName, robotId);
	}

	@SuppressWarnings("deprecation")
	public static SendMsgOptTempEntry get(String opt) {
		String v = (String) RedisTemplateTools.get().opsForValue().get("ruoyi:SendMsgOptTempRedis:" + opt);
		log.info("SendMsgOptTempRedis_get {} {}", opt, v);
		if (StringUtils.isEmpty(v)) {
			return null;
		}
		return JSON.parseObject(v, SendMsgOptTempEntry.class);
	}

	@Data
	public static class SendMsgOptTempEntry {
		String chatroomId;
		String playId;
		int msgSort;
		String robotNickName;
		String robotId;
	}
}
