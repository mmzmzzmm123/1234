package com.ruoyi.common.config.dymic;

import java.util.ArrayList;
import java.util.List;
import org.springframework.core.env.Environment;

import io.lettuce.core.AbstractRedisClient;
import io.lettuce.core.RedisURI;
import io.lettuce.core.cluster.RedisClusterClient;
import io.lettuce.core.pubsub.RedisPubSubListener;
import io.lettuce.core.pubsub.StatefulRedisPubSubConnection;
import io.lettuce.core.pubsub.api.async.RedisPubSubAsyncCommands;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ClusterRedisConfigurePublisher extends LettuceRedisConfigurePublisher {

	public ClusterRedisConfigurePublisher(Environment environment) {
		super(environment);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void registerConsumer(RedisPubSubListener<String, String> listener, String topic) {
		RedisClusterClient client = (RedisClusterClient) getRedisClient();
		StatefulRedisPubSubConnection<String, String> connection = client.connectPubSub();
		connection.addListener(listener);
		RedisPubSubAsyncCommands<String, String> async = connection.async();
		async.subscribe(topic);
		log.info("redis配置订阅topic:%s成功", topic);
	}

	@Override
	public void doSend(String message, String topic) {
		RedisClusterClient client = (RedisClusterClient) getRedisClient();
		StatefulRedisPubSubConnection<String, String> connection = client.connectPubSub();
		RedisPubSubAsyncCommands<String, String> async = connection.async();
		async.publish(topic, message);

		log.info("redis配置发布成功 topic:%s, message: %s", topic, message);

	}

	@Override
	AbstractRedisClient initRedisClient() {
		final Environment environment = getEnvironment();
		// 集群
		String nodes = environment.getProperty("spring.redis.cluster.nodes");
		String password = environment.getProperty("spring.redis.password");
		// 步骤1：节点信息
		List<RedisURI> servers = new ArrayList<>();

		String[] addrs = nodes.split(",");
		if (addrs == null || addrs.length == 0) {
			addrs = nodes.split(";");
		}
		for (int i = 0; i < addrs.length; i++) {
			@SuppressWarnings("deprecation")
			RedisURI uri = RedisURI.builder().withHost(addrs[i].split(":")[0])
					.withPort(Integer.parseInt(addrs[i].split(":")[1])).withPassword(password).build();
			servers.add(uri);
		}
		RedisClusterClient client = RedisClusterClient.create(servers);
		log.info("redis动态配置初始化成功 ...");
		return client;
	}

}
