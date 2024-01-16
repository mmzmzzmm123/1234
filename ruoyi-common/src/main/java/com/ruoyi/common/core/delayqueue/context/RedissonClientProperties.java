package com.ruoyi.common.core.delayqueue.context;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.BeanFactory;

public class RedissonClientProperties {

	public static final String PartitionsKey = "PartitionsKey";
	public static final String PollEventNumberKey = "pollEventNumberKey";
	/***
	 * Ĭ�� 20������������
	 */
	public static final int DefaultPartitions = 20;

	/**
	 * ҵ��key
	 */
	private String businessKey;

	private String redisKey;

	private final java.util.Properties inner = new java.util.Properties();

	private RedissonClient redissonClient = null;

	private BeanFactory beanFactory = null;

	private Method invokeTrigger = null;

	private Class<?> clientClass;

	/***
	 * ÿ���ͻ��� ��Ӧ�� poll�̳߳�����
	 */
	private final Map<String, Integer> pollEventNumberConfigure = new HashMap<>();

	public RedissonClientProperties(RedissonClient redissonClient, String businessKey, BeanFactory beanFactory) {
		Objects.requireNonNull(redissonClient);
		Objects.requireNonNull(beanFactory);
		Objects.requireNonNull(businessKey);
		this.redissonClient = redissonClient;
		this.beanFactory = beanFactory;
		this.businessKey = businessKey;
	}

	public void setRedisKey(int partition) {
		this.redisKey = businessKey + partition;
	}

	public String getRedisKey() {
		return redisKey;
	}

	public int getInt(String key, int defaultVal) {

		Object object = inner.get(key);
		if (null == object) {
			return defaultVal;
		}
		return (int) object;
	}

	public void put(String key, Object val) {
		inner.put(key, val);
	}

	public void addPollEventNumberConfigure(String businessKey, int num) {
		pollEventNumberConfigure.put(businessKey, num);
	}

	public String getBusinessKey() {
		return businessKey;
	}

	public RedissonClient getRedissonClient() {
		return redissonClient;
	}

	public BeanFactory getBeanFactory() {
		return beanFactory;
	}

	public void setInvokeTrigger(Method invokeTrigger) {
		this.invokeTrigger = invokeTrigger;
	}

	public Method getInvokeTrigger() {
		return invokeTrigger;
	}

	public void setClientClass(Class<?> clientClass) {
		this.clientClass = clientClass;
	}

	public Class<?> getClientClass() {
		return clientClass;
	}
}
