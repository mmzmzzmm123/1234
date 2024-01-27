package com.ruoyi.system.components.movie;

import com.ruoyi.common.core.delayqueue.DelayTrigger;
import com.ruoyi.common.core.delayqueue.RedissonFastDelayQueueClient;
import lombok.Data;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;

@RedissonFastDelayQueueClient
@Slf4j
public class DelayQueueInvoker {

	@Data
	@Accessors(chain = true)
	public static class Entry {

		private final String chatroomId;
		private final String playId;

		private final String traceId;

		/**
		 * 重试逻辑有这个值
		 */
		private final String firstSerialNo;
	}

	@DelayTrigger
	public void accept(Entry entry) {
		log.info("延时队列消费数据 {}", entry);
		// 导演 开始下一场
		try {
			PlayDirector.tgInstance().callMessage(entry);
		} catch (Exception e) {
			log.error("延时队列消费数据错误 {} ", entry, e);
		}
	}
}
