package com.ruoyi.common.core.delayqueue;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.codec.JsonJacksonCodec;
import org.redisson.config.ClusterServersConfig;
import org.redisson.config.Config;
import org.redisson.config.TransportMode;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.boot.autoconfigure.AutoConfigurationPackages;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.env.Environment;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import com.ruoyi.common.core.delayqueue.context.DelayQueueComponent;
import com.ruoyi.common.core.delayqueue.context.FastDelayQueueContext;
import com.ruoyi.common.core.delayqueue.context.RedissonClientProperties;
import com.ruoyi.common.utils.spi.PackageScans;

public class RedissonFastQueueRegistrar implements BeanFactoryAware, ImportBeanDefinitionRegistrar, EnvironmentAware {
	private Environment environment;
	private BeanFactory beanFactory;
	private BeanDefinitionRegistry registry;

	@Override
	public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
		this.registry = registry;
		Map<String, Object> mapper = importingClassMetadata
				.getAnnotationAttributes(EnableRedissonFastDelayQueue.class.getName());
		int pollEventNumber = (int) mapper.get("poll");
		int partition = (int) mapper.get("partition");

		if (pollEventNumber <= 0 || partition <= 0) {
			throw new RuntimeException("����ֵ�д���! EnableRedissonDelayFastQueueע���pollEventNumber,partition�ֶ� �������0");
		}
		// ɨ���ж��ٸ� �ͻ���
		Set<Class<?>> clientClasses = findClient();
		// init
		init(clientClasses, pollEventNumber, partition);
	}

	private void init(Set<Class<?>> clientClasses, int pollEventNumber, int partition) {
		for (Class<?> clientClass : clientClasses) {
			RedissonFastDelayQueueClient client = clientClass.getAnnotation(RedissonFastDelayQueueClient.class);
			String businessKey = StringUtils.isEmpty(client.businessKey()) ? clientClass.getName() + "_dely_queue001"
					: client.businessKey();

			RedissonClientProperties properties = new RedissonClientProperties(getRedissonClient(), businessKey,
					beanFactory);

			properties.addPollEventNumberConfigure(businessKey,
					client.pollEventNumber() <= 0 ? pollEventNumber : client.pollEventNumber());

			properties.put(RedissonClientProperties.PartitionsKey,
					client.partition() <= 0 ? partition : client.partition());
			// ���� �ͻ���class
			properties.setClientClass(clientClass);
			// ���� ����������շ���
			properties.setInvokeTrigger(parseDelayTriggerMethod(clientClass));
			// ���ͻ��� ע��spring
			registry.registerBeanDefinition(businessKey, new RootBeanDefinition(clientClass));
			// ���ͻ��˶�Ӧ�� ���� ���� ����
			FastDelayQueueContext.putContext(clientClass, businessKey, new DelayQueueComponent(properties));
		}
	}

	private Method parseDelayTriggerMethod(Class<?> clientClass) {
		for (Method method : clientClass.getDeclaredMethods()) {
			if (!method.isAnnotationPresent(DelayTrigger.class)) {
				continue;
			}
			method.setAccessible(true);
			return method;
		}
		throw new NullPointerException(clientClass.getName() + " ����ָ��һ��������Ϊ������ʱ�����ص������ڻص�����������@DelayTriggerע��");
	}

	private RedissonClient getRedissonClient() {
		RedissonClient client = null;
		try {
			// from spring
			client = beanFactory.getBean(RedissonClient.class);
			if (null != client) {
				return client;
			}
		} catch (Exception e) {
		}
		String pwd = environment.getProperty("spring.redis.password");
		int port = Integer.parseInt(environment.getProperty("spring.redis.port", "6379"));
		if (environment.containsProperty("spring.redis.cluster.nodes")) {
			// ��Ⱥ
			String nodes = environment.getProperty("spring.redis.cluster.nodes", "127.0.0.1:6379");
			Config config = new Config();
			// redisson�汾��3.5����Ⱥ��impǰ��Ҫ���ϡ�redis://������Ȼ�ᱨ��3.2�汾�ɲ���
			String[] addrs = nodes.split(",");
			if (addrs == null || addrs.length == 0) {
				addrs = nodes.split(";");
			}
			if (addrs == null || addrs.length == 0) {
				throw new IllegalArgumentException("redis��Ⱥ��ַ����ȷ:" + nodes);
			}
			List<String> clusterNodes = new ArrayList<>();
			for (int i = 0; i < addrs.length; i++) {
				clusterNodes.add("redis://" + addrs[i]);
			}
			// ��Ӽ�Ⱥ��ַ
			ClusterServersConfig clusterServersConfig = config.useClusterServers()
					.addNodeAddress(clusterNodes.toArray(new String[clusterNodes.size()]));
			// ��������
			clusterServersConfig.setPassword(pwd).setPingConnectionInterval(3000);
			;
			return Redisson.create(config);
		}
		String host = environment.getProperty("spring.redis.host", "127.0.0.1");
		// ����
		Config config = new Config();
		config.setTransportMode(TransportMode.NIO);
		config.setCodec(JsonJacksonCodec.INSTANCE);
		config.useSingleServer().setAddress("redis://" + host + ":" + port)
				// ����һ��Ҫ����һ������������
				.setPassword(StringUtils.isEmpty(pwd) ? null : pwd).setDatabase(0).setPingConnectionInterval(3000);
		;
		return Redisson.create(config);
	}

	private Set<Class<?>> findClient() {
		List<String> packages = AutoConfigurationPackages.get(beanFactory);
		Set<Class<?>> clientClasses = new HashSet<>();
		packages.forEach(pck -> {
			try {
				Set<Class<?>> clazz = PackageScans.findPackageClass(pck, RedissonFastDelayQueueClient.class);
				if (!CollectionUtils.isEmpty(clazz)) {
					clientClasses.addAll(clazz);
				}
			} catch (ClassNotFoundException | IOException e) {
			}
		});
		return clientClasses;
	}

	@Override
	public void setEnvironment(Environment environment) {
		this.environment = environment;
	}

	@Override
	public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
		this.beanFactory = beanFactory;
	}

}
