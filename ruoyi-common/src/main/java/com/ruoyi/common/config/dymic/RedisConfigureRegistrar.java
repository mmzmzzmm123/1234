package com.ruoyi.common.config.dymic;

import java.lang.reflect.Field;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.env.Environment;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.StringUtils;
import com.alibaba.fastjson2.JSON;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class RedisConfigureRegistrar implements BeanFactoryAware, ImportBeanDefinitionRegistrar, EnvironmentAware {
	public static final String Topic = "ruoyi_redis_conf_topic";
	public static final String Key = "ruoyi_redis_conf_key";

	static BeanFactory beanFactory;

	private Environment environment;

	private static final Configure CONFIGURE = new Configure();

	static class Configure {

		private RedisConfigurePublisher redisConfigurePublisher;

		public RedisConfigurePublisher getRedisConfigurePublisher() {
			return redisConfigurePublisher;
		}

		public void setRedisConfigurePublisher(RedisConfigurePublisher redisConfigurePublisher) {
			this.redisConfigurePublisher = redisConfigurePublisher;
		}

	}

	public static Configure getConfigure() {
		return CONFIGURE;
	}

	@Override
	public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
		ImportBeanDefinitionRegistrar.super.registerBeanDefinitions(importingClassMetadata, registry);

		// 初始化 redis
		initlettuce();

		registerBeanDefinition(RedisConfigureHook.class, beanFactory);
	}

	private void initlettuce() {
		if (environment.containsProperty("spring.redis.cluster.nodes")) {
			// 集群
			RedisConfigureRegistrar.CONFIGURE
					.setRedisConfigurePublisher(new ClusterRedisConfigurePublisher(environment));
			return;
		}
		// 单机
		RedisConfigureRegistrar.CONFIGURE.setRedisConfigurePublisher(new SingleRedisConfigurePublisher(environment));
	}

	public static <T> T registerBeanDefinition(Class<T> clazz, BeanFactory _beanFactory) {
		BeanDefinition beanDefinition = new GenericBeanDefinition();
		beanDefinition.setBeanClassName(clazz.getName());
		((DefaultListableBeanFactory) _beanFactory).registerBeanDefinition(clazz.getName(), beanDefinition);
		return _beanFactory.getBean(clazz);
	}

	@Override
	public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
		RedisConfigureRegistrar.beanFactory = beanFactory;
	}

	@Override
	public void setEnvironment(Environment environment) {
		this.environment = environment;
	}

	@SuppressWarnings("unchecked")
	public static String fromHash(Class<?> cls, Field field) {
		@SuppressWarnings("rawtypes")
		RedisTemplate redisTemplate = getRestTemplate();

		Object obj = redisTemplate.opsForHash().get(Key, cls.getName() + ":" + field.getName());
		if (obj == null) {
			return null;
		}
		return obj.toString();
	}

	@SuppressWarnings("unchecked")
	public static void doChange(String className, String fieldName, String value) {
		if (StringUtils.isEmpty(value)) {
			return;
		}
		@SuppressWarnings("rawtypes")
		RedisTemplate redisTemplate = getRestTemplate();

		Message message = new Message();
		message.setKey(Key);
		message.setHashKey(className + ":" + fieldName);
		redisTemplate.opsForHash().put(Key, className + ":" + fieldName, value);
		// 发布通知
		CONFIGURE.getRedisConfigurePublisher().doSend(JSON.toJSONString(message), Topic);
		log.info("【redis配置发布】  key:%s , val: %s", (className + ":" + fieldName), value);
	}

	private static RedisTemplate getRestTemplate() {
		try {
			return RedisConfigureRegistrar.beanFactory.getBean(RedisTemplate.class);
		} catch (Exception e) {
			try {
				return (RedisTemplate) RedisConfigureRegistrar.beanFactory.getBean("stringRedisTemplate");
			} catch (Exception e2) {
				return (RedisTemplate) RedisConfigureRegistrar.beanFactory.getBean("redisTemplate");
			}
		}
	}
}