package com.ruoyi.common.utils;

import org.springframework.util.StringUtils;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class DelayAcquireTools {
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private static ExecutorService executorService = new ThreadPoolExecutor(1000, 1000, 90L, TimeUnit.SECONDS,
			new ArrayBlockingQueue(512), new ThreadPoolExecutor.AbortPolicy());

	public static interface IGet<T> {
		T get();
	}

	public static interface IDo<T> {
		void handle(T t);
	}

	public static <T> void acquire(IGet<T> get, IDo<T> ido) {
		acquire(get, ido, 6);
	}

	@SuppressWarnings("deprecation")
	public static <T> void acquire(IGet<T> get, IDo<T> ido, int second) {
		T t = get.get();
		if (t == null || StringUtils.isEmpty(t)) {
			// 延迟执行
			executorService.execute(() -> {
				try {
					Thread.sleep(second * 1000);
				} catch (InterruptedException e) {
				}
				T newVal = get.get();
				if (newVal == null || StringUtils.isEmpty(newVal)) {
					return;
				}
				ido.handle(t);
			});
			return;
		}
		// 直接执行
		ido.handle(t);
	}

}
