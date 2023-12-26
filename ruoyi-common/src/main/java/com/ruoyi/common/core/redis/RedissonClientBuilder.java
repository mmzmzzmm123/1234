package com.ruoyi.common.core.redis;

import java.util.ArrayList;
import java.util.List;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.codec.JsonJacksonCodec;
import org.redisson.config.ClusterServersConfig;
import org.redisson.config.Config;
import org.redisson.config.TransportMode;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.util.StringUtils;

import io.lettuce.core.dynamic.annotation.CommandNaming;
import lombok.extern.slf4j.Slf4j;

@Configuration
@Slf4j
public class RedissonClientBuilder implements EnvironmentAware {

	private static Environment environment;

	@SuppressWarnings("deprecation")
	public RedissonClient build() {
		try {
			String host = RedissonClientBuilder.environment.getProperty("spring.redis.host");
			String port = RedissonClientBuilder.environment.getProperty("spring.redis.port");
			String password = RedissonClientBuilder.environment.getProperty("spring.redis.password");
			// 单机
			Config config = new Config();
			config.setTransportMode(TransportMode.NIO);
			config.setCodec(JsonJacksonCodec.INSTANCE);
			config.useSingleServer().setAddress("redis://" + host + ":" + port)
					// 这里一定要处理一下无密码问题
					.setPassword(StringUtils.isEmpty(password) ? null : password).setDatabase(0);

			RedissonClient redission = Redisson.create(config);
			log.info("[发现redis单机] 地址： nodes:{} , port: {} , pwd:{}", host, port, password);
			return redission;
		} catch (Exception e) {
			// 使用集群
			String nodes = RedissonClientBuilder.environment.getProperty("spring.redis.cluster.nodes");
			String password = RedissonClientBuilder.environment.getProperty("spring.redis.password");
			// redisson版本是3.5，集群的imp前面要加上“redis://”，不然会报错，3.2版本可不加
			String[] addrs = nodes.split(",");
			if (addrs == null || addrs.length == 0) {
				addrs = nodes.split(";");
			}
			if (addrs == null || addrs.length == 0) {
				throw new IllegalArgumentException("redis集群地址不正确:" + nodes);
			}

			List<String> clusterNodes = new ArrayList<>();
			for (int i = 0; i < addrs.length; i++) {
				clusterNodes.add("redis://" + addrs[i]);
			}
			Config config = new Config();
			// 添加集群地址
			ClusterServersConfig clusterServersConfig = config.useClusterServers()
					.addNodeAddress(clusterNodes.toArray(new String[clusterNodes.size()]));
			// 设置密码
			clusterServersConfig.setPassword(password);
			log.info("[发现redis集群] 地址： nodes:{} , pwd:{}", nodes, password);
			return Redisson.create(config);
		}
	}

	@Override
	public void setEnvironment(Environment environment) {

		RedissonClientBuilder.environment = environment;

	}
}
