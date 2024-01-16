package com.ruoyi.system.components.movie.spi.impl;

import com.ruoyi.common.core.delayqueue.context.FastDelayQueueContext;
import com.ruoyi.common.core.domain.entity.play.PlayMessage;
import com.ruoyi.common.utils.Ids;
import com.ruoyi.common.utils.spi.SPI;
import com.ruoyi.system.components.PlayInfoTools;
import com.ruoyi.system.components.movie.DelayQueueInvoker;
import com.ruoyi.system.components.movie.GlobalIndexContext;
import com.ruoyi.system.components.movie.PlaybackContext;
import com.ruoyi.system.components.movie.spi.ProgressPuller;
import lombok.extern.slf4j.Slf4j;

@SPI
@Slf4j
public class DelayQueueProgressPuller implements ProgressPuller {

	@Override
	public void nextPull(PlayMessage message, String chatroomId) {
		log.info("nextPull {} {}", chatroomId, message);

		// 剧本被暂停了
		if (PlaybackContext.isPause(message.getPlayId(), chatroomId)) {
			// 剧本被暂停了.
			log.info("剧本暂停了 {} {}", message, chatroomId);
			return;
		}
		// 剧本被删除了
		if (PlayInfoTools.isDeleted(message.getPlayId())) {
			log.info("剧本删除了 {} {}", message, chatroomId);
			return;
		}
		Integer delayTime = message.getIntervalTime();
		// 先移动游标 ，
		GlobalIndexContext.next(chatroomId, message.getPlayId());
		DelayQueueInvoker.Entry e = new DelayQueueInvoker.Entry(chatroomId, message.getPlayId(), Ids.getId());
		// 放到延时队列里面继续下一条消息
		FastDelayQueueContext.send(DelayQueueInvoker.class, e, delayTime);
		log.info("延时队列生产 {} {} ", e, delayTime);
	}

}
