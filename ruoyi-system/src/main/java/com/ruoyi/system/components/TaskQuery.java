package com.ruoyi.system.components;

import org.springframework.util.CollectionUtils;
import com.ruoyi.system.extend.UtTouchJoinRoomClient;
import com.ruoyi.system.extend.UtTouchResult;
import com.ruoyi.system.extend.data.GetChatRoomJoinTaskPageInput;
import com.ruoyi.system.extend.data.GetChatRoomJoinTaskPageOutput;
import com.ruoyi.system.extend.data.UtTouchPage;
import lombok.extern.slf4j.Slf4j;

public interface TaskQuery {

//	public static TaskQuery getQuery(int orderType) {
//		if (orderType == 0) {
//			return new PullInGroupTaskQuery();
//		}
//	}

	/**
	 * -1待执行 0-进行中 1-已完成 2-已取消
	 * 
	 * @param taskId
	 * @return
	 */
	int getStatus(String taskId);

	@Slf4j
	public static class PullInGroupTaskQuery implements TaskQuery {
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

}
