package com.ruoyi.system.components.movie;

import java.util.List;
import java.util.concurrent.TimeUnit;
import org.redisson.api.RLock;
import com.ruoyi.common.core.domain.entity.play.Play;
import com.ruoyi.common.core.domain.entity.play.PlayMessage;
import com.ruoyi.common.core.domain.entity.play.PlayMessagePushDetail;
import com.ruoyi.common.core.redis.RedisLock;
import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.common.utils.DelayAcquireTools;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.spi.ServiceLoader;
import com.ruoyi.common.utils.spring.SpringUtils;
import com.ruoyi.system.components.PlayInfoTools;
import com.ruoyi.system.components.movie.SendMsgOptTempRedis.SendMsgOptTempEntry;
import com.ruoyi.system.components.movie.spi.PlayRunner;
import com.ruoyi.system.components.movie.spi.ProgressPuller;
import com.ruoyi.system.mapper.PlayMessageMapper;
import com.ruoyi.system.mapper.PlayMessagePushDetailMapper;
import com.ruoyi.system.mapper.PlayMessagePushDetailTrackMapper;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class PlayDirector implements CallBackProcessor {

	private final String alias;

	private static final PlayDirector tgInstance = new PlayDirector("tg");

	public static PlayDirector tgInstance() {
		return tgInstance;
	}

	private PlayDirector(String alias) {
		this.alias = alias;
	}

	public ManualExecutivePlayer getManualExecutivePlayer(Play play) {
		PlayBookFactory factory = ServiceLoader.load(PlayBookFactory.class, "TgPlayBookFactory");
		final PlayBook playBook = factory.newInstance(alias, play);
		return new ManualExecutivePlayer(playBook);
	}

	/**
	 * 开始定时剧本
	 * 
	 * @param play
	 */
	public void timeup(Play play) {
		PlayBookFactory factory = ServiceLoader.load(PlayBookFactory.class, "TgPlayBookFactory");
		// 构建 电影 本子
		final PlayBook playBook = factory.newInstance(alias, play);
		// 定时剧本
		new ScheduleExecutivePlayer(playBook).timeup();
	}

	/**
	 * 继续 发消息
	 * 
	 * @param playId
	 * @param chatroomId
	 */
	public void callMessage(String playId, String chatroomId) {
		PlayBookFactory factory = ServiceLoader.load(PlayBookFactory.class, "TgPlayBookFactory");
		// 构建 电影 本子
		final PlayBook playBook = factory.newInstance(alias, playId);
		new NextExecutivePlayer(playBook).sendOne(chatroomId, GlobalIndexContext.getIndex(chatroomId, playId));
	}

	@Override
	public void onMessageSuccess(String opt) {
		DelayAcquireTools.acquire(() -> SendMsgOptTempRedis.get(opt), (val) -> success0(val, opt));
	}

	private void success0(SendMsgOptTempEntry entry, String opt) {
		log.info("play_success0 {} {} {}" , entry , opt);

		final PlayRunner playRunner = ServiceLoader.load(PlayRunner.class);
		playRunner.onItemSendSuccess(opt, entry.getPlayId(), entry.getChatroomId(), entry.getMsgSort(),
				entry.getRobotId());
		// 查询下一个消息的配置
		PlayMessage playMsg = SpringUtils.getBean(PlayMessageMapper.class).getBySort(entry.getMsgSort() + 1,
				entry.getPlayId());
		log.info("parseIntervalTime {} {} {}", opt, entry, playMsg);
		if (playMsg == null) {
			// 没有下一个消息了 ， 剧本完成
			playRunner.onFinish(entry.getPlayId(), entry.getChatroomId());
			return;
		}
		log.info("DelayQueueProgressPuller {} {} {}", opt, entry, playMsg);
		// 放到延时队列里面去 .继续 下第一条
		ServiceLoader.load(ProgressPuller.class).nextPull(playMsg, entry.getChatroomId());
	}

	@Override
	public void onMessageFailure(String opt, String errMsg) {
		DelayAcquireTools.acquire(() -> SendMsgOptTempRedis.get(opt), (val) -> fail0(val, opt, errMsg));
	}

	void fail0(SendMsgOptTempEntry entry, String opt, String errMsg) {
		log.info("play_fail0 {} {} {}" , entry , opt , errMsg);
		final PlayRunner playRunner = ServiceLoader.load(PlayRunner.class);

		// 如果启用了 备用号， 则需要重试发送这条消息
		PlayMessagePushDetail detail = SpringUtils.getBean(PlayMessagePushDetailMapper.class).getOne(entry.getPlayId(),
				entry.getChatroomId(), entry.getMsgSort());
		if (!StringUtils.isEmpty(detail.getSpareRobot())) {
			// 有备用号
			// 有发过消息的号
			List<String> sendRobotList = SpringUtils.getBean(PlayMessagePushDetailTrackMapper.class)
					.getOne(entry.getPlayId(), entry.getChatroomId(), entry.getMsgSort());
			for (String backRobot : org.apache.commons.lang3.StringUtils.split(detail.getSpareRobot(), ",")) {
				if (sendRobotList.contains(backRobot)) {
					// 有发过的
					continue;
				}
				// 直接取这个号继续发送
				PlayMessage playMsg = SpringUtils.getBean(PlayMessageMapper.class).getBySort(entry.getMsgSort(),
						entry.getPlayId());
				ServiceLoader.load(ProgressPuller.class).continuePull(playMsg, entry.getChatroomId());
				log.info("切换备用号发送 {} {}" , entry , opt);
				return;
			}
		}
		// 备用号 都用完了 , 直接失败
		playRunner.onItemFailure(opt, entry.getPlayId(), errMsg, entry.getChatroomId(), entry.getRobotId(),
				entry.getMsgSort(), null);
		// 查询下一个消息的配置
		PlayMessage playMsg = SpringUtils.getBean(PlayMessageMapper.class).getBySort(entry.getMsgSort() + 1,
				entry.getPlayId());
		if (playMsg == null) {
			// 没有下一个消息了 ， 剧本完成
			playRunner.onFinish(entry.getPlayId(), entry.getChatroomId());
			return;
		}
		// 失败是否 停止发送
		if (PlayInfoTools.failStoped(entry.getPlayId())) {
			// 失败完成
			playRunner.onFinish(entry.getPlayId(), entry.getChatroomId());
			return;
		}
		// 放到延时队列里面去 .继续 下第一条
		ServiceLoader.load(ProgressPuller.class).nextPull(playMsg, entry.getChatroomId());
	}

	/**
	 * 暂停 某个 剧本的某个群 的 推送
	 * 
	 * @param playId
	 * @param chatroomId
	 */
	public void pause(String playId, String chatroomId) {
		RLock lock = SpringUtils.getBean(RedisLock.class).getRLock("ruoyi:pauselock:" + playId + ":" + chatroomId);
		if (lock.isLocked()) {
			throw new ServiceException("操作频繁");
		}
		try {
			lock.lock(5, TimeUnit.SECONDS);
			PlaybackContext.pause(playId, chatroomId);
		} finally {
			lock.unlock();
		}
	}

	/**
	 * 恢复 某个剧本的某个群的 推送
	 * 
	 * @param playId
	 * @param chatroomId
	 */
	public void resume(String playId, String chatroomId) {
		RLock lock = SpringUtils.getBean(RedisLock.class).getRLock("ruoyi:pauselock:" + playId + ":" + chatroomId);
		if (lock.isLocked()) {
			throw new ServiceException("操作频繁");
		}
		final PlayRunner playRunner = ServiceLoader.load(PlayRunner.class);

		try {
			lock.lock(10, TimeUnit.SECONDS);
			PlaybackContext.resume(playId, chatroomId);
			int index = GlobalIndexContext.getIndex(chatroomId, playId);
			// 拿下一条
			PlayMessage playMsg = SpringUtils.getBean(PlayMessageMapper.class).getBySort(index + 1, playId);
			log.info("剧本恢复 {} {} ", chatroomId, playMsg);
			if (playMsg == null) {
				playRunner.onFinish(playId, chatroomId);
				return;
			}
			ServiceLoader.load(ProgressPuller.class).nextPull(playMsg, chatroomId);
		} finally {
			lock.unlock();
		}
	}

}
