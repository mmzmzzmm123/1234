package com.ruoyi.common.config.dymic;

import io.lettuce.core.pubsub.RedisPubSubListener;

public interface RedisConfigurePublisher {

	public void registerConsumer(RedisPubSubListener<String, String> listener, String topic);

	public void doSend(String message, String topic);

}
