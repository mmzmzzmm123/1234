package com.ruoyi.system.components.prepare.multipack;

import java.util.List;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.ruoyi.common.core.domain.entity.play.PlayRobotPackLog;
import com.ruoyi.common.utils.ListTools;
import com.ruoyi.common.utils.spi.SPI;
import com.ruoyi.common.utils.spring.SpringUtils;
import com.ruoyi.system.components.prepare.multipack.MultipackLogContainer.CallValue;
import com.ruoyi.system.components.prepare.multipack.MultipackLogContainer.CallValueStore;
import com.ruoyi.system.mapper.PlayRobotPackLogMapper;

/**
 * 处理 状态变动
 * 
 * @author Administrator
 *
 */
@SPI("StateJobProcessor")
public class StateJobProcessor implements LogJobProcessor {

	@Override
	public void handle() {
		final PlayRobotPackLogMapper robotPackLogMapper = SpringUtils.getBean(PlayRobotPackLogMapper.class);
		final OnPackMonitor onPackMonitor = SpringUtils.getBean(OnPackMonitor.class);
		final OnRadioPackMonitor onRadioPackMonitor = SpringUtils.getBean(OnRadioPackMonitor.class);

		List<PlayRobotPackLog> datas = robotPackLogMapper.selectList(new QueryWrapper<PlayRobotPackLog>().lambda()
				.eq(PlayRobotPackLog::getIsFinish, 0).eq(PlayRobotPackLog::getStatus, 0));
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
		// 分组
		Map<String, List<PlayRobotPackLog>> group = ListTools.group(datas, f -> f.getRadioId());
		for (String radioId : group.keySet()) {
			List<PlayRobotPackLog> logs = group.get(radioId);
			if (logs.size() < logs.get(0).getTotal().intValue()) {
				// 还没有满
				continue;
			}
			// 判断是否都完成
			if (!PlayRobotPackLog.finished(logs)) {
				// 还有没有回调的，继续等待
				continue;
			}
			// 回调完成
			PlayRobotPackLog data = PlayRobotPackLog.findError(logs);
			if (data == null) {
				// 回调 成功
				onRadioPackMonitor.onRadioPackSucceed(logs);
				// 把这一组 都更新完成
				PlayRobotPackLog update = new PlayRobotPackLog();
				update.setIsFinish(1);
				UpdateWrapper<PlayRobotPackLog> wrapper = new UpdateWrapper<>();
				wrapper.lambda().eq(PlayRobotPackLog::getRadioId, radioId);
				robotPackLogMapper.update(update, wrapper);
				continue;
			}
			// 回调错误
			onRadioPackMonitor.onRadioPackFailed(data);
		}

	}

}
