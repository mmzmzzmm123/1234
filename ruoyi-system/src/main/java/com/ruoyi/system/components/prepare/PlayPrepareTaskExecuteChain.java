package com.ruoyi.system.components.prepare;

import java.util.LinkedList;
import org.springframework.stereotype.Component;
import com.ruoyi.common.utils.spi.ServiceLoader;
import com.ruoyi.system.components.prepare.spi.ComplateTask;
import com.ruoyi.system.components.prepare.spi.TaskExecution;

/***
 * title: 准备发剧本的 一个链条
 * 
 * @author Administrator
 *
 */
@Component
public class PlayPrepareTaskExecuteChain implements ChainManager {

	private final LinkedList<TaskExecution> chain = new LinkedList<>();

	public void reset() {
		chain.clear();
		// 1. 入群
		// 2. 混淆
		// 3. 号预分配
		chain.addLast(ServiceLoader.load(TaskExecution.class, "RobotPreallocationTask"));
		// 4. 人设包装
		chain.addLast(ServiceLoader.load(TaskExecution.class, "ManualPackingTask"));
		// 5. 完成
		chain.addLast(ServiceLoader.load(TaskExecution.class, "ComplateTask"));
	}

	private TaskExecution findComplated() {
		for (TaskExecution exe : chain) {
			if (exe instanceof ComplateTask) {
				return exe;
			}
		}
		return null;
	}

	@Override
	public void moveNext(ExecutionParamContext context) {
		if (!chain.isEmpty()) {
			return;
		}
		final TaskExecution execution = chain.removeFirst();
		ExecutionResultContext ctx = execution.doExecute(context);
		if (!ctx.isSucceed()) {
			// 失败了 , 直接终止此群
			findComplated().doExecute(context);
			chain.clear();
			return;
		}
		if (ctx.continued()) {
			// 链条 继续 执行
			moveNext(context);
			return;
		}
		// 等待回调
	}

}
