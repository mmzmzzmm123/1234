package com.ruoyi.system.components.movie.spi.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ruoyi.common.core.domain.entity.play.PlayMessage;
import com.ruoyi.common.utils.spi.SPI;
import com.ruoyi.common.utils.spring.SpringUtils;
import com.ruoyi.system.components.movie.spi.PlayRunner;
import com.ruoyi.system.mapper.PlayMessageMapper;
import com.ruoyi.system.mapper.PlayMessagePushDetailMapper;
import com.ruoyi.system.mapper.PlayMessagePushMapper;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@SPI
public class DefaultPlayRunner implements PlayRunner {

	public static void info(String format, Object arg) {
		log.info(format, arg);
	}

	@Override
	public void onItemFailure(String opt, String playId, String msg, String chatroomId, String robotId, int msgSort,
			Throwable e) {
		if (e != null) {
			log.error("DefaultPlayRunner_onItemFailure {} {} {} {} {} {}", opt, playId, msg, chatroomId, robotId,
					msgSort, e);
		} else {
			log.info("DefaultPlayRunner_onItemFailure {} {} {} {} {} {}", opt, playId, msg, chatroomId, robotId,
					msgSort);
		}
		if (finished(playId, msgSort)) {
			// 单个群的 已经发送结束了
			SpringUtils.getBean(PlayMessagePushMapper.class).updateFailure(playId, chatroomId, msg);
		}
		// 单条数据
		SpringUtils.getBean(PlayMessagePushDetailMapper.class).updateFailure(playId, chatroomId, msgSort, opt, msg,
				robotId);
	}

	@Override
	public void onItemSending(String opt, String playId, String chatroomId, String robotId, int msgSort) {
		log.info("DefaultPlayRunner_onItemSending {} {} {} {} {}", opt, playId, chatroomId, robotId, msgSort);
		if (msgSort == 1) {
			// 单个群 开始
			SpringUtils.getBean(PlayMessagePushMapper.class).updateStarted(playId, chatroomId);
		}
		SpringUtils.getBean(PlayMessagePushDetailMapper.class).updateSending(playId, chatroomId, msgSort, opt, robotId);
	}

	@Override
	public void onItemSendSuccess(String opt, String playId, String chatroomId, int msgSort, String robotId) {
		log.info("DefaultPlayRunner_onItemSendSuccess {} {} {} {} {}", opt, playId, chatroomId, robotId, msgSort);

		if (finished(playId, msgSort)) {
			// 单个群的 已经发送结束了
			log.info("DefaultPlayRunner_onItemSendSuccess_end {} {} {} {} {}", opt, playId, chatroomId, robotId,
					msgSort);
			SpringUtils.getBean(PlayMessagePushMapper.class).updateSuccess(playId, chatroomId);
		}
		SpringUtils.getBean(PlayMessagePushDetailMapper.class).updateSuccess(playId, chatroomId, msgSort, opt, robotId);
	}

	@Override
	public void onFinish(String playId, String chatroomId) {
		log.info("DefaultPlayRunner_onFinish {} {}", playId, chatroomId);
//		App.getBean(PlayInfoMapper.class).updateFinish(playId);
		SpringUtils.getBean(PlayMessagePushMapper.class).updateSuccess(playId, chatroomId);
	}

	@Override
	public void onStart(String playId) {
		log.info("DefaultPlayRunner_onStart {}", playId);
	}

	private boolean finished(String playId, int curSort) {
		PlayMessage playmsg = SpringUtils.getBean(PlayMessageMapper.class)
				.selectOne(new QueryWrapper<PlayMessage>().lambda().eq(PlayMessage::getPlayId, playId)
						.orderByDesc(PlayMessage::getMessageSort).last(" limit 1 "));
		if (playmsg == null) {
			return false;
		}
		return curSort >= playmsg.getMessageSort().intValue();
	}

}
