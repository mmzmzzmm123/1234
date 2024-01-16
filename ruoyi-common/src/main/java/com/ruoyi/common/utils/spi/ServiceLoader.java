package com.ruoyi.common.utils.spi;

import java.io.IOException;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.util.StringUtils;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ServiceLoader {

	private static Set<Class<?>> implSet = new HashSet<>();

	private static final Map<Class<?>, Object> implInstanceCache = new ConcurrentHashMap<>();

	static {
		try {
			implSet = PackageScans.findPackageClass("com.ruoyi", SPI.class);
			log.info("加载到 个%d spi类", implSet.size());
		} catch (ClassNotFoundException | IOException e) {
		}

	}

	public static <T> T load(Class<T> interfaceClass, String alias) {
		if (interfaceClass == null) {
			return null;
		}

		if (!interfaceClass.isInterface()) {
			throw new UnsupportedOperationException("非接口不支持操作");
		}
		return findSPIImpl(interfaceClass, alias);

	}

	public static <T> T load(Class<T> interfaceClass) {
		if (interfaceClass == null) {
			return null;
		}

		if (!interfaceClass.isInterface()) {
			throw new UnsupportedOperationException("非接口不支持操作");
		}
		return findSPIImpl(interfaceClass, null);

	}

	@SuppressWarnings("unchecked")
	private static synchronized <T> T findSPIImpl(Class<T> interfaceClass, String alias) {
		T impl = (T) implInstanceCache.get(interfaceClass);

		if (impl != null) {
			return impl;
		}
		Class<?> implClass = null;
		for (Class<?> cls : implSet) {
			if (interfaceClass.isAssignableFrom(cls)) {
				if (StringUtils.isEmpty(alias)) {
					implClass = cls;
					break;
				}
				if (!StringUtils.isEmpty(alias) && alias.equals(cls.getAnnotation(SPI.class).value())) {
					implClass = cls;
					break;
				}
			}
		}
		if (implClass == null) {
			throw new RuntimeException("SPI实现类未找到，interface:" + interfaceClass.getName());
		}
		// 初始化
		T obj = (T) Classes.newInstance(implClass);
		implInstanceCache.put(interfaceClass, obj);
		return obj;
	}

}
