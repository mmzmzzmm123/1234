package com.ruoyi.system.components.movie.spi.impl;

import java.util.List;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ruoyi.common.core.domain.entity.play.Play;
import com.ruoyi.common.core.domain.entity.play.PlayMessage;
import com.ruoyi.common.core.domain.entity.play.PlayMessagePush;
import com.ruoyi.common.utils.spi.SPI;
import com.ruoyi.common.utils.spring.SpringUtils;
import com.ruoyi.system.components.movie.spi.PlayRunner;
import com.ruoyi.system.mapper.PlayMapper;
import com.ruoyi.system.mapper.PlayMessageMapper;
import com.ruoyi.system.mapper.PlayMessagePushDetailMapper;
import com.ruoyi.system.mapper.PlayMessagePushMapper;
import com.ruoyi.system.service.PlayExecutionLogService;
import com.ruoyi.system.service.impl.IntoGroupService;

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
		// 打日志
		PlayExecutionLogService.robotSendMassageErrLog(playId, chatroomId, robotId, msg, opt);
		if (finished(playId, msgSort)) {
			// 单个群的 已经发送结束了
			SpringUtils.getBean(PlayMessagePushMapper.class).updateFailure(playId, chatroomId, msg);
			String c = String.format("【剧本群推送完成】 群%s 剧本:%s" , chatroomId , playId) ;
			PlayExecutionLogService.robotPackLog(playId, chatroomId, robotId, c, null);
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

		// 剧本完成
		boolean finishFlag = true;
		List<PlayMessagePush> groups = SpringUtils.getBean(PlayMessagePushMapper.class)
				.selectList(new QueryWrapper<PlayMessagePush>().lambda().eq(PlayMessagePush::getPlayId, playId)
						.select(PlayMessagePush::getPushState));
		for (PlayMessagePush push : groups) {
			if (push.getPushState().intValue() == 1 || push.getPushState().intValue() == 2) {
				// 推送状态:1:待发送 2:进行中 3:已结束 4:人工暂停 5:推送失败 6:系统暂停 7:取消
				finishFlag = false;
				break;
			}
		}
		if (!finishFlag) {
			return;
		}
		// 更新剧本完成
		Play play = SpringUtils.getBean(PlayMapper.class).selectById(playId);
		if (play != null && play.getState().intValue() == 2) {
			String c = String.format("【剧本推送完成】剧本:%s" , playId) ;
			PlayExecutionLogService.robotPackLog(playId, null, null, c, null);
			Play update = new Play();
			update.setState(5);
			update.setId(playId);
			SpringUtils.getBean(PlayMapper.class).updateById(update);
			// 退群
			try {
				SpringUtils.getBean(IntoGroupService.class).outGroup(play);
				log.info("outGroup {}" , play);
			} catch (Exception e) {
				log.error("IntoGroupService_outGroup {}" , play);
			}
		}
	}

	@Override
	public void onStart(String playId) {
		log.info("DefaultPlayRunner_onStart {}", playId);
		Play play = SpringUtils.getBean(PlayMapper.class).selectById(playId);
		if (play != null && play.getState().intValue() == 1) {
			// 改成 炒群中
			Play update = new Play();
			update.setState(2);
			update.setId(playId);
			String c = String.format("【剧本开始炒群】剧本:%s" , playId) ;
			PlayExecutionLogService.robotPackLog(playId, null, null, c, null);
			SpringUtils.getBean(PlayMapper.class).updateById(update);
		}
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
