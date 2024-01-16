package com.ruoyi.system.components.movie.spi.impl;

import java.util.concurrent.TimeUnit;
import org.springframework.data.redis.core.RedisTemplate;
import com.ruoyi.common.utils.spi.SPI;
import com.ruoyi.common.utils.spring.SpringUtils;
import com.ruoyi.system.components.movie.spi.RobotSpeakAllocator;

import lombok.extern.slf4j.Slf4j;

@SPI
public class PreRobotSpeakAllocator implements RobotSpeakAllocator {

	@Override
	public String allocate(String nick, String chatroomId, String playId) {
		// 直接 找分配好的剧本
		return PreRobotSpeakAllocator.Cache.get(playId, chatroomId, nick);
	}

	@Slf4j
	public static class Cache {

		@SuppressWarnings("unchecked")
		public static void set(String playId, String groupId, String nickName, String robotId) {

			SpringUtils.getBean(RedisTemplate.class).opsForHash().put(
					"ruoyiadmin:firegroup:DefaultRobotSpeakAllocator:" + playId + ":" + groupId, nickName, robotId);

			SpringUtils.getBean(RedisTemplate.class).expire(
					"ruoyiadmin:firegroup:DefaultRobotSpeakAllocator:" + playId + ":" + groupId, 60 * 60 * 24 * 5,
					TimeUnit.SECONDS);

			log.info("DefaultRobotSpeakAllocator_cache {} {} {} {}", playId, groupId, nickName, robotId);
		}

		@SuppressWarnings("unchecked")
		public static String get(String playId, String groupId, String nickName) {
			return (String) SpringUtils.getBean(RedisTemplate.class).opsForHash()
					.get("ruoyiadmin:firegroup:DefaultRobotSpeakAllocator:" + playId + ":" + groupId, nickName);
		}

	}

}
