package com.ruoyi.common.config.dymic;

import org.springframework.core.env.Environment;
import org.springframework.util.StringUtils;

import io.lettuce.core.AbstractRedisClient;
import io.lettuce.core.RedisClient;
import io.lettuce.core.RedisURI;
import io.lettuce.core.cluster.RedisClusterClient;
import io.lettuce.core.pubsub.RedisPubSubListener;
import io.lettuce.core.pubsub.StatefulRedisPubSubConnection;
import io.lettuce.core.pubsub.api.async.RedisPubSubAsyncCommands;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SingleRedisConfigurePublisher extends LettuceRedisConfigurePublisher {

	public SingleRedisConfigurePublisher(Environment environment) {
		super(environment);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void registerConsumer(RedisPubSubListener<String, String> listener, String topic) {
		RedisClient client = (RedisClient) getRedisClient();
		StatefulRedisPubSubConnection<String, String> connection = client.connectPubSub();
		connection.addListener(listener);
		RedisPubSubAsyncCommands<String, String> async = connection.async();
		async.subscribe(topic);

		log.info("redis配置订阅topic:%s成功", topic);
	}

	@Override
	public void doSend(String message, String topic) {
		RedisClient client = (RedisClient) getRedisClient();

		StatefulRedisPubSubConnection<String, String> connection = client.connectPubSub();
		RedisPubSubAsyncCommands<String, String> async = connection.async();
		async.publish(topic, message);

		log.info("redis配置发布成功 topic:%s, message: %s", topic, message);
	}

	@Override
	AbstractRedisClient initRedisClient() {
		final Environment environment = getEnvironment();
		// 单机
		String host = getEnvironment().getProperty("spring.redis.host");
		String port = environment.getProperty("spring.redis.port");
		String password = environment.getProperty("spring.redis.password");

		io.lettuce.core.RedisURI.Builder build = RedisURI.Builder.redis(host);
		if (!StringUtils.isEmpty(password)) {
			build.withPassword(password);
		}
		build.withPort(Integer.parseInt(port));
		build.withDatabase(0);
		RedisURI redisUri = build.build();
		RedisClient redisClient = RedisClient.create(redisUri);
		log.info("redis动态配置初始化成功 ...");
		return redisClient;
	}

}
