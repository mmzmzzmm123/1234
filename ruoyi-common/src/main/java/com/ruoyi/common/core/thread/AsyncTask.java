package com.ruoyi.common.core.thread;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;
import org.springframework.util.CollectionUtils;

import com.ruoyi.common.utils.Strings;

import lombok.extern.slf4j.Slf4j;

/***
 * 异步任务工具，使用条件<br>
 * 
 * @author HadLuo
 * @since JDK1.7
 * @history 2018年10月26日 新建
 */
@Slf4j
public class AsyncTask {

	/***
	 * 异步执行单个任务
	 * 
	 * @param task
	 * @author HadLuo 2020年2月20日 新建
	 */
	public static void executeCallerRuns(Runnable task) {
		if (task == null) {
			return;
		}
		CompletableFuture.supplyAsync(new Supplier<Void>() {
			@Override
			public Void get() {
				try {
					task.run();
				} catch (Throwable e) {
					log.error("线程池执行异常 {} ", Strings.getStack(500), e);
					throw e;
				}
				return null;
			}
		}, ThreadPoolCallerRunsUtils.getExecutorService());
	}

	/***
	 * 异步执行单个任务
	 * 
	 * @param task
	 * @author HadLuo 2020年2月20日 新建
	 */
	public static void execute(Runnable task) {
		if (task == null) {
			return;
		}
		// 拿到堆栈
		ThreadPools.getThreadPools().execute(() -> {
			try {
				long s = System.currentTimeMillis();
				task.run();
				s = System.currentTimeMillis() - s;
				if (s > 8000) {
					log.info("线程池执行耗时过长 {}  堆栈: \r\n {}", s, Strings.getStack(500));
				}
			} catch (Throwable e) {
				log.error("线程池执行异常 ", e);
				throw e;
			}
		});
	}

	/***
	 * 构造多线程任务 工具
	 * 
	 * @return
	 * @author HadLuo 2019年5月7日 新建
	 */
	public static MultiTasker newMultiTasker() {
		return new MultiTaskerCore();
	}

	public static ParallelTools parallel() {
		return new ParallelTools();
	}

	public static class ParallelTools {
		private final List<Runnable> taskList = new ArrayList<>();

		public ParallelTools put(Runnable task) {
			taskList.add(task);
			return this;
		}

		public void sync() {
			if (CollectionUtils.isEmpty(taskList)) {
				return;
			}
			int nThread = taskList.size();
			if (nThread > 20) {
				nThread = 20;
			}
			newMultiTasker().map(taskList, nThread, new TaskerCallbackNoneResult<Runnable>() {
				@Override
				public void run(List<Runnable> curDatas) {
					for (Runnable r : curDatas) {
						try {
							r.run();
						} catch (Exception e) {
							log.error("并发执行业务错误" , e);
						}
					}
				}
			});
		}
	}
}
