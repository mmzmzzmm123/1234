package com.ruoyi.system.components.prepare.multipack;

import java.util.List;
import java.util.Map;

import org.springframework.util.CollectionUtils;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.ruoyi.common.core.domain.entity.play.Play;
import com.ruoyi.common.core.domain.entity.play.PlayRobotPackLog;
import com.ruoyi.common.utils.ListTools;
import com.ruoyi.common.utils.spi.SPI;
import com.ruoyi.common.utils.spring.SpringUtils;
import com.ruoyi.system.components.prepare.multipack.MultipackLogContainer.CallValue;
import com.ruoyi.system.components.prepare.multipack.MultipackLogContainer.CallValueStore;
import com.ruoyi.system.mapper.PlayRobotPackLogMapper;

import lombok.extern.slf4j.Slf4j;

/**
 * 处理 状态变动
 * 
 * @author Administrator
 *
 */
@SPI("StateJobProcessor")
@Slf4j
public class StateJobProcessor implements LogJobProcessor {

	@Override
	public void handle(Play play) {
		final PlayRobotPackLogMapper robotPackLogMapper = SpringUtils.getBean(PlayRobotPackLogMapper.class);
		final OnPackMonitor onPackMonitor = SpringUtils.getBean(OnPackMonitor.class);
		final OnRadioPackMonitor onRadioPackMonitor = SpringUtils.getBean(OnRadioPackMonitor.class);

		List<PlayRobotPackLog> datas = robotPackLogMapper
				.selectList(new QueryWrapper<PlayRobotPackLog>().lambda().eq(PlayRobotPackLog::getIsFinish, 0)
						.eq(PlayRobotPackLog::getStatus, 0).eq(PlayRobotPackLog::getPlayId, play.getId()));
		
		if(CollectionUtils.isEmpty(datas)) {
			return ;
		}
		
		log.info("StateJobProcessor {}" , play);

		
		for (PlayRobotPackLog data : datas) {
			CallValue ret = CallValueStore.get(data.getOpt());

			if (ret == null) {
				// 还没有回调
				continue;
			}
			if (ret.isSuccess()) {
				// 请求成功
				data.setStatus(1);
				PlayRobotPackLog update = new PlayRobotPackLog();
				update.setOpt(data.getOpt());
				update.setStatus(1);
				update.setAttchContent(ret.getAttchContent());
				robotPackLogMapper.updateById(update);
				// 单个回调 成功
				onPackMonitor.onPackSucceed(data);
				continue;
			}

			// 单个请求失败
			data.setStatus(2);
			data.setErrMsg(ret.toString());

			PlayRobotPackLog update = new PlayRobotPackLog();
			update.setStatus(2);
			update.setOpt(data.getOpt());
			update.setErrMsg(ret.toString());
			update.setAttchContent(ret.getAttchContent());
			robotPackLogMapper.updateById(update);
			// 单个回调 失败
			onPackMonitor.onPackFailed(data, ret.toString());
		}
		// 是否执行完成 刷新
		datas = robotPackLogMapper
				.selectList(new QueryWrapper<PlayRobotPackLog>().lambda().eq(PlayRobotPackLog::getIsFinish, 0));
		
		if(CollectionUtils.isEmpty(datas)) {
			return ;
		}
		
		// 分组
		Map<String, List<PlayRobotPackLog>> group = ListTools.group(datas, f -> f.getRadioId());
		for (String radioId : group.keySet()) {
			List<PlayRobotPackLog> logs = group.get(radioId);
			
			log.info("StateJobProcessor_j {} {}" , radioId , logs);
			if (logs.size() < logs.get(0).getTotal().intValue()) {
				// 数据还没有插满
				continue;
			}
			if (PlayRobotPackLog.unFinished(logs)) {
				// 如果有一个 还在进行中（没有达到最大重试次数） 则 就还没有完成
				continue;
			}
			// 回调完成
			// 把这一组 都更新完成
			PlayRobotPackLog update = new PlayRobotPackLog();
			update.setIsFinish(1);
			UpdateWrapper<PlayRobotPackLog> wrapper = new UpdateWrapper<>();
			wrapper.lambda().eq(PlayRobotPackLog::getRadioId, radioId);
			robotPackLogMapper.update(update, wrapper);
			log.info("PlayRobotPackLog_更新完成 {}", radioId);
			List<PlayRobotPackLog> failList = PlayRobotPackLog.listByError(logs);
			if (CollectionUtils.isEmpty(failList)) {
				// 全部都是成功， 代表成功
				onRadioPackMonitor.onRadioPackSucceed(logs);
				continue;
			}
			// 回调错误
			onRadioPackMonitor.onRadioPackFailed(logs);
		}

	}

}
