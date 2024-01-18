package com.ruoyi.common.core.thread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 线程池管理工具类
 */
public class ThreadPoolCallerRunsUtils {
	/**
	 * 核心 20 最大 200 存活90秒 512队列的线程池
	 */
	private static ExecutorService executorService = new ThreadPoolExecutor(80, 80, 90L, TimeUnit.SECONDS,
//            new ArrayBlockingQueue(512),
//            new ThreadPoolExecutor.AbortPolicy());
			new SynchronousQueue(), new ThreadPoolExecutor.CallerRunsPolicy());

	/**
	 * 执行任务
	 * 
	 * @param task
	 */
	public static void execute(Runnable task) {
		executorService.execute(task);
	}

	/**
	 * 返回池服务对象
	 * 
	 * @return
	 */
	public static ExecutorService getExecutorService() {
		return executorService;
	}

}
