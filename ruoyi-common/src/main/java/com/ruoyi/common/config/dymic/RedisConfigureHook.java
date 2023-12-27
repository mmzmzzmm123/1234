package com.ruoyi.common.config.dymic;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.env.Environment;
import org.springframework.util.StringUtils;
import com.alibaba.fastjson2.JSON;
import com.ruoyi.common.config.dymic.RedisConfigureRegistrar.Configure;
import io.lettuce.core.pubsub.RedisPubSubListener;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class RedisConfigureHook
		implements ApplicationRunner, DisposableBean, BeanPostProcessor, RedisPubSubListener<String, String> {

	private final Map<Class<?>, List<Field>> confCache = new ConcurrentHashMap<>();

	@Override
	public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
		if (bean.getClass().getAnnotation(RedisRefreshScope.class) == null) {
			return bean;
		}
		confCache.put(bean.getClass(), new ArrayList<Field>());
		// 解析 所有字段
		for (Field field : bean.getClass().getDeclaredFields()) {
			confCache.get(bean.getClass()).add(field);
		}
		return bean;
	}

	@Override
	public void run(ApplicationArguments args) throws Exception {
		final Configure configure = RedisConfigureRegistrar.getConfigure();
		log.info("开始初始化RedisConfigure ...  {}", JSON.toJSON(configure));
		// 注册订阅器
		configure.getRedisConfigurePublisher().registerConsumer(this, RedisConfigureRegistrar.Topic);

		// 数据初始化
		initData();
	}

	@SuppressWarnings("deprecation")
	private void initData() {
		for (Class<?> clazz : confCache.keySet()) {
			Object conf = RedisConfigureRegistrar.beanFactory.getBean(clazz);
			for (Field field : clazz.getDeclaredFields()) {
				field.setAccessible(true);
				String curValue = RedisConfigureRegistrar.fromHash(clazz, field);
				if (StringUtils.isEmpty(curValue)) {
					continue;
				}
				// 注入当前值
				injectVal(field, conf, curValue);
			}
		}
	}

	private void injectVal(Field field, Object conf, String curValue) {
		try {
			if (field.getType().equals(int.class) || field.getType().equals(Integer.class)) {
				field.setInt(conf, Integer.parseInt(curValue));
			} else if (field.getType().equals(boolean.class) || field.getType().equals(Boolean.class)) {
				if (curValue.trim().equals("1")) {
					field.setBoolean(conf, true);

				} else if (curValue.trim().equals("0")) {
					field.setBoolean(conf, false);
				} else {
					field.setBoolean(conf, Boolean.parseBoolean(curValue));
				}
			} else if (field.getType().equals(double.class) || field.getType().equals(Double.class)) {
				field.setDouble(conf, Double.parseDouble(curValue));
			} else if (field.getType().equals(float.class) || field.getType().equals(Float.class)) {
				field.setFloat(conf, Float.parseFloat(curValue));
			} else if (field.getType().equals(long.class) || field.getType().equals(Long.class)) {
				field.setLong(conf, Long.parseLong(curValue));
			} else if (field.getType().equals(byte.class) || field.getType().equals(Byte.class)) {
				field.setByte(conf, Byte.parseByte(curValue));
			} else if (field.getType().equals(char.class) || field.getType().equals(Character.class)) {
				field.setChar(conf, curValue.charAt(0));
			} else {
				field.set(conf, curValue);
			}
			log.info("【redis配置监听变化 设置值成功】 key:%s , val:%s" + (conf.getClass().getName() + ":" + field.getName()),
					curValue);

		} catch (Exception e) {
			log.error("redis配置类型错误， key:%s hash key: %s", RedisConfigureRegistrar.Key,
					(conf.getClass().getName() + ":" + field.getName()), e);
		}
	}

	@Override
	public void destroy() throws Exception {
	}

	@SuppressWarnings("deprecation")
	@Override
	public void message(String channel, String message) {
		if (StringUtils.isEmpty(message)) {
			return;
		}
		Message entry = null;
		try {
			entry = JSON.parseObject(message, Message.class);
		} catch (Exception e) {
			return;
		}
		String hkey = entry.getHashKey();
		Class<?> cls = null;
		try {
			cls = Class.forName(hkey.split(":")[0]);
		} catch (ClassNotFoundException e) {
			return;
		}
		String fName = hkey.split(":")[1];
		Object conf = RedisConfigureRegistrar.beanFactory.getBean(cls);
		for (Field field : cls.getDeclaredFields()) {
			if (!field.getName().equals(fName)) {
				continue;
			}
			field.setAccessible(true);
			String curValue = RedisConfigureRegistrar.fromHash(cls, field);
			if (StringUtils.isEmpty(curValue)) {
				continue;
			}
			// 注入当前值
			injectVal(field, conf, curValue);
		}
	}

	@Override
	public void message(String pattern, String channel, String message) {
		log.info("msg2={} in channel={}", message, channel);
	}

	@Override
	public void subscribed(String channel, long count) {
		// TODO Auto-generated method stub
	}

	@Override
	public void psubscribed(String pattern, long count) {
		// TODO Auto-generated method stub
	}

	@Override
	public void unsubscribed(String channel, long count) {
		// TODO Auto-generated method stub
	}

	@Override
	public void punsubscribed(String pattern, long count) {
		// TODO Auto-generated method stub
	}

}
