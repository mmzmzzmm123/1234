package com.ruoyi.system.components.prepare;

import java.util.LinkedList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ruoyi.common.core.domain.entity.play.PlayMessagePush;
import com.ruoyi.common.core.domain.entity.play.PlayRobotPackLog;
import com.ruoyi.common.utils.spi.ServiceLoader;
import com.ruoyi.common.utils.spring.SpringUtils;
import com.ruoyi.system.components.prepare.multipack.OnPackMonitor;
import com.ruoyi.system.components.prepare.multipack.OnRadioPackMonitor;
import com.ruoyi.system.components.prepare.spi.ManualPackingTask;
import com.ruoyi.system.components.prepare.spi.RobotPreallocationTask;
import com.ruoyi.system.components.prepare.spi.TaskExecution;
import com.ruoyi.system.mapper.PlayMessagePushMapper;
import com.ruoyi.system.service.PlayExecutionLogService;
import lombok.extern.slf4j.Slf4j;

/***
 * title: 准备发剧本的 一个链条
 * 
 * @author Administrator
 *
 */
@Component
@Slf4j
public class PlayPrepareTaskExecuteChain implements OnPackMonitor, OnRadioPackMonitor {

	private final LinkedList<TaskExecution> chain = new LinkedList<>();

	@Autowired
	private PlayMessagePushMapper playMessagePushMapper;

	public void reset() {
		chain.clear();
		// 1. 入群
		// 2. 混淆
		// 3. 号预分配
		chain.addLast(ServiceLoader.load(TaskExecution.class, "RobotPreallocationTask"));
		// 4. 人设包装
		chain.addLast(ServiceLoader.load(TaskExecution.class, "ManualPackingTask"));
	}

	public void doExecute(ExecutionParamContext context) {
		if (chain.isEmpty()) {
			return;
		}
		final TaskExecution execution = chain.removeFirst();
		ExecutionResultContext ctx = execution.doExecute(context);

		if ((execution instanceof RobotPreallocationTask) && !ctx.isNeedWait()) {
			// 更新 推送 业务进度
			PlayMessagePush update = new PlayMessagePush();
			update.setId(context.getMessagePush().getId());
			update.setRobotAllocationFlag(ctx.isSucceed() ? 1 : 2);
			SpringUtils.getBean(PlayMessagePushMapper.class).updateById(update);
		}

		if ((execution instanceof ManualPackingTask) && !ctx.isNeedWait()) {
			// 更新 推送 业务进度
			updatePrepareProgress(context.getMessagePush().getId(), ctx.isSucceed());
		}

		if (ctx.continued()) {
			// 链条 继续 执行
			doExecute(context);
			return;
		}
		// 等待回调
	}

	private void updatePrepareProgress(int messagePushId, boolean succeed) {
		PlayMessagePush update = new PlayMessagePush();
		update.setId(messagePushId);
		update.setRobotPackFlag(succeed ? 1 : 2);
		SpringUtils.getBean(PlayMessagePushMapper.class).updateById(update);
	}

	@Override
	public void onRadioPackSucceed(List<PlayRobotPackLog> dataList) {
		// 群维度 的 准备完成
		PlayMessagePush push = playMessagePushMapper.selectOne(
				new QueryWrapper<PlayMessagePush>().lambda().eq(PlayMessagePush::getPlayId, dataList.get(0).getPlayId())
						.eq(PlayMessagePush::getGroupId, dataList.get(0).getChatroomId()).last(" limit 1 "));
		log.info("onRadioPackSucceed {} {}", push, dataList);
		if (push == null) {
			return;
		}
		updatePrepareProgress(push.getId(), true);
	}

	@Override
	public void onRadioPackFailed(List<PlayRobotPackLog> failList) {
		// 群维度 的 准备 失败
		PlayMessagePush push = playMessagePushMapper.selectOne(
				new QueryWrapper<PlayMessagePush>().lambda().eq(PlayMessagePush::getPlayId, failList.get(0).getPlayId())
						.eq(PlayMessagePush::getGroupId, failList.get(0).getChatroomId()).last(" limit 1 "));
		log.info("onRadioPackFailed {} {}", push, failList);
		if (push == null) {
			return;
		}
		updatePrepareProgress(push.getId(), false);
	}

	@Override
	public void onPackSucceed(PlayRobotPackLog data) {
		log.info("onPackSucceed {}", data);

		// 单个 回调 执行 成功  1-设置机器人姓名，姓氏 2-设置机器人头像 3-设置群hash值 4-设置管理员
		if (data.getOp().intValue() == 1) {
			PlayExecutionLogService.robotPackLog(data.getPlayId(), data.getChatroomId(), data.getRobotId(), null, data.getOpt(), "姓名", false);
		}
		if (data.getOp().intValue() == 2) {
			PlayExecutionLogService.robotPackLog(data.getPlayId(), data.getChatroomId(), data.getRobotId(), null, data.getOpt(), "头像", false);
		}
		if (data.getOp().intValue() == 3) {
			PlayExecutionLogService.robotPackLog(data.getPlayId(), data.getChatroomId(), data.getRobotId(), null, data.getOpt(), "管理员（获取hash值）", false);
		}
		if (data.getOp().intValue() == 4) {
			PlayExecutionLogService.robotPackLog(data.getPlayId(), data.getChatroomId(), data.getRobotId(), null, data.getOpt(), "管理员", false);
		}
	}

	@Override
	public void onPackFailed(PlayRobotPackLog data, String error) {
		log.info("onPackSucceed {} {}", data ,error);
		// 单个 回调 执行 失败
		if (data.getOp().intValue() == 1) {
			PlayExecutionLogService.robotPackLog(data.getPlayId(), data.getChatroomId(), data.getRobotId(), error, data.getOpt(), "姓名", false);
		}
		if (data.getOp().intValue() == 2) {
			PlayExecutionLogService.robotPackLog(data.getPlayId(), data.getChatroomId(), data.getRobotId(), error, data.getOpt(), "头像", false);
		}
		if (data.getOp().intValue() == 3) {
			PlayExecutionLogService.robotPackLog(data.getPlayId(), data.getChatroomId(), data.getRobotId(), error, data.getOpt(), "管理员（获取hash值）", false);
		}
		if (data.getOp().intValue() == 4) {
			PlayExecutionLogService.robotPackLog(data.getPlayId(), data.getChatroomId(), data.getRobotId(), error, data.getOpt(), "管理员", false);
		}
	}

}
