package com.ruoyi.system.components.movie;

import java.util.Date;
import org.springframework.util.StringUtils;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.common.core.domain.entity.play.PlayMessage;
import com.ruoyi.common.core.domain.entity.play.PlayMessagePushDetailTrack;
import com.ruoyi.common.utils.spi.ServiceLoader;
import com.ruoyi.common.utils.spring.SpringUtils;
import com.ruoyi.system.components.PlayInfoTools;
import com.ruoyi.system.components.movie.spi.MessageSupport;
import com.ruoyi.system.components.movie.spi.PlayRunner;
import com.ruoyi.system.components.movie.spi.ProgressPuller;
import com.ruoyi.system.components.movie.spi.RobotSpeakAllocator;
import com.ruoyi.system.mapper.PlayMessagePushDetailTrackMapper;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public abstract class AbstractPlayer implements Player {

	private final PlayBook book;
	private final MessageSupport messageSupport;
	private final RobotSpeakAllocator speakAllocator;
	private final PlayRunner playRunner;

	public AbstractPlayer(PlayBook book) {
		this.book = book;
		this.messageSupport = book.getMessageSupport();
		this.speakAllocator = book.getRobotSpeakAllocator();
		this.playRunner = book.getPlayRunner();
	}

	@SuppressWarnings("deprecation")
	public void sendOne(String chatroomId, int currentIndex) {
		log.info("sendOne {} {} {}", chatroomId, currentIndex, book.getPlay());
		if (currentIndex > book.getMessageList().size()) {
			// 已经结束
			playRunner.onFinish(book.getPlay().getId(), chatroomId);
			return;
		}
		final String playId = book.getPlay().getId();
		// 下标是从1开始， 数组从0开始
		final PlayMessage playMessage = book.current(currentIndex).getMessage();
		// 失败是否 停止发送
		String opt = null;
		String robotId = null;
		boolean isFail = true;
		// 获取发言人
		try {
			robotId = speakAllocator.allocate(playMessage.getRobotNickname(), chatroomId, book.getPlay().getId());
			log.info("speakAllocator.allocate {} {} {} {}", playMessage, chatroomId, playId, robotId);
			if (StringUtils.isEmpty(robotId)) {
				playRunner.onItemFailure(null, playId, "发言人不足", chatroomId, robotId, currentIndex, null);
			}
		} catch (Exception e) {
			playRunner.onItemFailure(null, playId, "发言人获取失败", chatroomId, robotId, currentIndex, e);
		}
		// 发送消息
		try {
			if (!StringUtils.isEmpty(robotId)) {
				R<String> ret = messageSupport.sendChatroomMessage(chatroomId, robotId, playMessage, currentIndex);
				if (StringUtils.isEmpty(ret.getData())) {
					playRunner.onItemFailure(ret.getData(), playId, ret.getMsg(), chatroomId, robotId, currentIndex,
							null);
				}
				opt = ret.getData();
				isFail = false;
				// 插入 发送轨迹
				PlayMessagePushDetailTrack track = new PlayMessagePushDetailTrack();
				track.setCreateTime(new Date());
				track.setGroupId(chatroomId);
				track.setMessageSort(currentIndex);
				track.setPlayId(playId);
				track.setRobotId(robotId);
				SpringUtils.getBean(PlayMessagePushDetailTrackMapper.class).insert(track);
			}
		} catch (Exception e) {
			playRunner.onItemFailure(opt, playId, "发送消息失败", chatroomId, robotId, currentIndex, e);
		}

		if (PlayInfoTools.failStoped(book.getPlay()) && StringUtils.isEmpty(opt)) {
			// 发送失败， 且 失败停止
			playRunner.onFinish(playId, chatroomId);
			return;
		}
		if (StringUtils.isEmpty(opt)) {
			// 发送失败， 且 不停止，继续发送0
			if (book.getMessageList().size() <= currentIndex) {
				// 没有下一个了
				playRunner.onFinish(playId, chatroomId);
				return;
			}
			final PlayMessageWapper nextMessageWapper = book.getMessageList().get(currentIndex);
			ServiceLoader.load(ProgressPuller.class).nextPull(nextMessageWapper.getMessage(), chatroomId);
		}
		// 等待回调，然后继续下一条
		SendMsgOptTempRedis.set(opt, chatroomId, playId, currentIndex, playMessage.getRobotNickname(), robotId);
		// 回调发送中
		if (!StringUtils.isEmpty(opt) && !isFail) {
			playRunner.onItemSending(opt, playId, chatroomId, robotId, currentIndex);
		}
	}

	public PlayBook getBook() {
		return book;
	}
}
