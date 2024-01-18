package com.ruoyi.common.core.thread;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Executor;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import com.ruoyi.common.utils.Ids;

import lombok.extern.slf4j.Slf4j;

/***
 * 线程池工具,优先获取spring配置的线程池
 * 
 * @author HadLuo
 * @since JDK1.7
 * @history 2020年2月20日 新建
 */

@Slf4j
public class ThreadPools {

	// 核心*2
	public static int Default_CorePoolSize = Runtime.getRuntime().availableProcessors() * 6;

	private static volatile Executor EXECUTOR = null;
	private final static AtomicInteger atomic = new AtomicInteger();

//	private static Executor fromSpringPools() {
//		try {
//			return App.getBean(ThreadPoolTaskExecutor.class);
//		} catch (Throwable e) {
//			return null;
//		}
//	}
	public synchronized static Executor getThreadPools() {
		if (EXECUTOR == null) {
			if (Default_CorePoolSize > 80) {
				Default_CorePoolSize = 80;
			}
			EXECUTOR = new ThreadPoolExecutor(Default_CorePoolSize, 80, 12000, TimeUnit.MILLISECONDS,
					new ArrayBlockingQueue<>(10), new ThreadFactory() {
						public Thread newThread(Runnable r) {
							return new Thread(Thread.currentThread().getThreadGroup(), r,
									"HThreadPool-" + atomic.getAndIncrement());
						}
					}, new RejectedExecutionHandler() {
						@Override
						public void rejectedExecution(Runnable var1, ThreadPoolExecutor executor) {
							String trace = Ids.getId();
							// 有 任务队列满了 ， 线程满了 ，无法执行新任务
							log.error("[线程池溢出] thread:{} , trace: {} , dump:{}", Thread.currentThread().getName(),
									trace, dump());
//							AlertContext.robot()
//									.alert("[线程出溢出，无法执行新任务，请尽快查看] , trace: " + trace + " , app: " + App.getAppName());

						}
					});
		}
		return EXECUTOR;
	}

	public static String dump() {
		ThreadPoolExecutor executor = (ThreadPoolExecutor) EXECUTOR;
		return "CorePoolSize=" + executor.getCorePoolSize() + ",LargestPoolSize()=" + executor.getLargestPoolSize()
				+ ",MaximumPoolSize=" + executor.getMaximumPoolSize() + ",ActiveCount=" + executor.getActiveCount()
				+ ",PoolSize=" + executor.getPoolSize() + ",Queue=" + executor.getQueue();
	}
}
