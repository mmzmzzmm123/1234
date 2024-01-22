package com.ruoyi.system.components.prepare.multipack;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson2.JSON;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.CollectionUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ruoyi.common.core.domain.entity.play.Play;
import com.ruoyi.common.core.domain.entity.play.PlayRobotPackLog;
import com.ruoyi.common.utils.ListTools;
import com.ruoyi.common.utils.Objects;
import com.ruoyi.common.utils.spi.SPI;
import com.ruoyi.common.utils.spi.ServiceLoader;
import com.ruoyi.common.utils.spring.SpringUtils;
import com.ruoyi.system.components.spi.Settings;
import com.ruoyi.system.mapper.PlayRobotPackLogMapper;
import com.ruoyi.system.service.PlayExecutionLogService;
import lombok.extern.slf4j.Slf4j;

/**
 * 处理后置条件的 log
 *
 * @author Administrator
 *
 */
@SPI("LogPostJobProcessor")
@Slf4j
public class LogPostJobProcessor implements LogJobProcessor {

	@Override
	public void handle(Play play) {
		final PlayRobotPackLogMapper robotPackLogMapper = SpringUtils.getBean(PlayRobotPackLogMapper.class);

		List<PlayRobotPackLog> datas = robotPackLogMapper.selectList(new QueryWrapper<PlayRobotPackLog>().lambda()
				.eq(PlayRobotPackLog::getIsFinish, 0).eq(PlayRobotPackLog::getStatus, -1).eq(PlayRobotPackLog::getPlayId, play.getId()));

		if(CollectionUtils.isEmpty(datas)) {
			return ;
		}

		// 找出等待条件的log
		List<PlayRobotPackLog> waitList = robotPackLogMapper
				.selectBatchIds(ListTools.extract(datas, f -> f.getWaitOpt()));

		Map<String, List<PlayRobotPackLog>> waitOptMap = ListTools.group(waitList, f -> f.getOpt());

		for (PlayRobotPackLog data : datas) {
			// 找出 等待 的log
			List<PlayRobotPackLog> ls = waitOptMap.get(data.getWaitOpt());
			if (CollectionUtils.isEmpty(ls)) {
				continue;
			}
            log.info("查询后置任务 {} {}",data.getOpt(), JSON.toJSONString(ls));
			if (ls.get(0).getStatus().intValue() == 0) {
				// 前置条件的 log 没有 回调完成
				continue;
			}
			if (ls.get(0).getStatus().intValue() == 1) {
				// 前置条件的 log 回调成功
				handleCurrentLog(data, ls.get(0).getAttchContent());
			}

		}
	}

	private void handleCurrentLog(PlayRobotPackLog data, String attchContent) {
		final PlayRobotPackLogMapper robotPackLogMapper = SpringUtils.getBean(PlayRobotPackLogMapper.class);

		if (data.getOp().intValue() == 4) {
			// 1-设置机器人姓名，姓氏 2-设置机器人头像 3-设置群hash值 4-设置管理员 5-获取群成员
			Settings settings = ServiceLoader.load(Settings.class, "TgKpRobotAdminSettings");
			// 请求 设置 机器人头像 ，昵称等
			Map<String, Object> param = new HashMap<>();
			param.put(Settings.Key_PlayId, data.getPlayId());
			// 机器人id
			param.put(Settings.Key_RobotId, data.getRobotId());
			// 群id
			param.put(Settings.Key_GroupId, data.getChatroomId());
			// 管理员
			param.put(Settings.Key_Admin_Flag, true);
			// 备用号
			param.put(Settings.Key_Backup_Flag, data.getIsBackup().intValue() == 1 ? true : false);
			// hash 值
			param.put(Settings.Key_AttachContent, Objects.wrapNull(attchContent, ""));
			// 请求 设置管理员
			PlayRobotPackLog ret = settings.set(param);

			if (StringUtils.isEmpty(ret.getOpt())) {
				PlayExecutionLogService.robotPackLog(data.getPlayId(), data.getChatroomId(),  data.getRobotId(), ret.getErrMsg(), null, String.format("【发言人包装-%s】 群%s 号%s 设置失败", "管理员", data.getChatroomId(), data.getRobotId()), true);
				// 更新 状态
				PlayRobotPackLog update = new PlayRobotPackLog();
				update.setStatus(ret.getStatus());
				update.setErrMsg(ret.getErrMsg());
				update.setOpt(data.getOpt());
				robotPackLogMapper.updateById(update);
				log.info("后置log更新 {}", data);
			} else {
				PlayExecutionLogService.robotPackLog(data.getPlayId(), data.getChatroomId(),  data.getRobotId(), null, ret.getOpt(), String.format("【发言人包装-%s】 群%s 号%s 设置成功", "管理员", data.getChatroomId(), data.getRobotId()), true);
				// 删除之前的
				robotPackLogMapper.deleteById(data);
				// 新增当前的
				data.setStatus(ret.getStatus());
				data.setErrMsg(ret.getErrMsg());
				data.setOpt(ret.getOpt());
				robotPackLogMapper.insert(data);
				log.info("后置log覆盖插入 {}", data);
			}
		}
	}

}
