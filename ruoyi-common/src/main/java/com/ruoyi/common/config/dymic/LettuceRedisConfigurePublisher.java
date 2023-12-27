package com.ruoyi.common.config.dymic;

import org.springframework.core.env.Environment;

import io.lettuce.core.AbstractRedisClient;

public abstract class LettuceRedisConfigurePublisher implements RedisConfigurePublisher {

	private AbstractRedisClient redisClient;

	private final Environment environment;

	public LettuceRedisConfigurePublisher(Environment environment) {
		this.environment = environment;

		this.redisClient = initRedisClient();
	}

	abstract AbstractRedisClient initRedisClient();

	public void setRedisClient(AbstractRedisClient redisClient) {
		this.redisClient = redisClient;
	}

	public Environment getEnvironment() {
		return environment;
	}

	public AbstractRedisClient getRedisClient() {
		return redisClient;
	}
}
