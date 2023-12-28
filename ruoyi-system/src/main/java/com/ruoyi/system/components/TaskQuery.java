package com.ruoyi.system.components;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.util.CollectionUtils;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ruoyi.common.utils.spring.SpringUtils;
import com.ruoyi.system.domain.Task;
import com.ruoyi.system.extend.UtTouchJoinRoomClient;
import com.ruoyi.system.extend.UtTouchResult;
import com.ruoyi.system.extend.data.GetChatRoomJoinTaskDetailInfoListInput;
import com.ruoyi.system.extend.data.GetChatRoomJoinTaskDetailInfoListOutput;
import com.ruoyi.system.extend.data.GetChatRoomJoinTaskPageInput;
import com.ruoyi.system.extend.data.GetChatRoomJoinTaskPageOutput;
import com.ruoyi.system.extend.data.UtTouchPage;
import com.ruoyi.system.mapper.TaskMapper;

import lombok.Data;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;

public abstract class TaskQuery {

	static Map<Integer, TaskQuery> INSTANCE_CACHE = new ConcurrentHashMap<>();

	static {
		INSTANCE_CACHE.put(0, new PullInGroupTaskQuery());
	}

	public static TaskQuery newQuery(int orderType) {
		return INSTANCE_CACHE.get(orderType);
	}

	/**
	 * 获取 任务 详情成功的 个数
	 * 
	 * @param taskId
	 * @return
	 */
	public abstract int getSuccessCountOfTaskDetail(String taskId);

	/**
	 * 通过 订单id查询 任务
	 * 
	 * @param orderIds
	 * @return
	 */
	public abstract List<TaskAdapter> listByOrder(List<String> orderIds);

	/**
	 * -1待执行 0-进行中 1-已完成 2-已取消
	 * 
	 * @param taskId
	 * @return
	 */
	public abstract int getStatus(String taskId);

	@Slf4j
	public static class PullInGroupTaskQuery extends TaskQuery {
		@Override
		public int getStatus(String taskId) {
			GetChatRoomJoinTaskPageInput input = new GetChatRoomJoinTaskPageInput();
			input.setId(taskId);
			input.setName("");
			UtTouchResult<UtTouchPage<GetChatRoomJoinTaskPageOutput>> taskRet = UtTouchJoinRoomClient
					.getChatRoomJoinTaskPage(input);
			log.info("UtTouchJoinRoomClient.getChatRoomJoinTaskPage {} {}", input, taskRet);
			if (taskRet == null || taskRet.getData() == null
					|| CollectionUtils.isEmpty(taskRet.getData().getDataList())) {
				throw new TaskQueryCallException("返回数据为空， taskId:" + taskId);
			}
			GetChatRoomJoinTaskPageOutput output = taskRet.getData().getDataList().get(0);
			// 0待执行、1执行中、2执行完成,-1已取消、-2已暂停
			int source = output.getExecuteStatus();
			if (source == 1) {
				return 0;
			}
			if (source == 2) {
				return 1;
			}
			if (source == 0) {
				return -1;
			}
			if (source == -1) {
				return 2;
			}
			return 0;
		}

		@Override
		public List<TaskAdapter> listByOrder(List<String> orderIds) {
			if (CollectionUtils.isEmpty(orderIds)) {
				return Collections.emptyList();
			}
			final TaskMapper taskMapper = SpringUtils.getBean(TaskMapper.class);
			List<Task> datas = taskMapper.selectList(new QueryWrapper<Task>().lambda().in(Task::getOrderId, orderIds));
			final List<TaskAdapter> adapters = new ArrayList<TaskQuery.TaskAdapter>();
			for (Task task : datas) {
				adapters.add(new TaskAdapter(task.getTaskId(), task.getTaskName(), task.getOrderId()));
			}
			return adapters;
		}

		@Override
		public int getSuccessCountOfTaskDetail(String taskId) {
			GetChatRoomJoinTaskDetailInfoListInput input = new GetChatRoomJoinTaskDetailInfoListInput();
			input.setTaskId(taskId);
			input.setRunStatus(1);
			input.setPageSize(100000);
			UtTouchResult<UtTouchPage<GetChatRoomJoinTaskDetailInfoListOutput>> details = UtTouchJoinRoomClient
					.getChatRoomJoinTaskDetailInfoList(input);
			log.info("getChatRoomJoinTaskDetailInfoList {} {}", input, details);
			if (details.getData() != null && !CollectionUtils.isEmpty(details.getData().getDataList())) {
				if (!CollectionUtils.isEmpty(details.getData().getDataList().get(0).getDetails())) {
					return details.getData().getDataList().get(0).getDetails().size();
				}
			}
			return 0;
		}
	}

	@SuppressWarnings("serial")
	public static class TaskQueryCallException extends RuntimeException {

		public TaskQueryCallException(String msg) {
			super(msg);
		}

		public TaskQueryCallException(String msg, Throwable e) {
			super(msg, e);
		}
	}

	@Data
	@Accessors(chain = true)
	public static class TaskAdapter {
		private final String taskId;
		private final String taskName;
		private final String orderId;
	}

}
