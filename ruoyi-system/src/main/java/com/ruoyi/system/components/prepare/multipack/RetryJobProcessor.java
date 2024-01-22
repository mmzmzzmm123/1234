package com.ruoyi.system.components.prepare.multipack;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ruoyi.system.service.ISysConfigService;
import com.ruoyi.system.service.impl.SysConfigServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.CollectionUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ruoyi.common.core.domain.entity.play.Play;
import com.ruoyi.common.core.domain.entity.play.PlayMessagePushDetail;
import com.ruoyi.common.core.domain.entity.play.PlayRobotPack;
import com.ruoyi.common.core.domain.entity.play.PlayRobotPackLog;
import com.ruoyi.common.utils.Objects;
import com.ruoyi.common.utils.Times;
import com.ruoyi.common.utils.spi.SPI;
import com.ruoyi.common.utils.spi.ServiceLoader;
import com.ruoyi.common.utils.spring.SpringUtils;
import com.ruoyi.system.components.spi.Settings;
import com.ruoyi.system.mapper.PlayMessagePushDetailMapper;
import com.ruoyi.system.mapper.PlayRobotPackLogMapper;
import com.ruoyi.system.mapper.PlayRobotPackMapper;
import com.ruoyi.system.service.PlayExecutionLogService;
import lombok.extern.slf4j.Slf4j;

/**
 * 重试
 * 
 * @author Administrator
 *
 */
@Slf4j
@SPI("RetryJobProcessor")
public class RetryJobProcessor implements LogJobProcessor {

	private static final int MaxRetryCount = 10;

	/**
	 * 4分钟
	 */
	private static final int timeoutSecond = 60 * 4;

	@Override
	public void handle(Play play) {
		final PlayMessagePushDetailMapper mapper = SpringUtils.getBean(PlayMessagePushDetailMapper.class);
		// 查询 一直在等回调 且 超过了 4分钟的 数据
		List<PlayRobotPackLog> datas = SpringUtils.getBean(PlayRobotPackLogMapper.class)
				.selectList(new QueryWrapper<PlayRobotPackLog>().lambda().eq(PlayRobotPackLog::getStatus, 0)
						.eq(PlayRobotPackLog::getIsFinish, 0)
						.le(PlayRobotPackLog::getCreateTime, Times.getSecond(new Date(), -timeoutSecond))
						.le(PlayRobotPackLog::getRetryCount, MaxRetryCount).eq(PlayRobotPackLog::getRetryMaxFlag, 0)
						.eq(PlayRobotPackLog::getPlayId, play.getId()));

		if (!CollectionUtils.isEmpty(datas)) {
			log.info("RetryJobProcessor {}", play);
			for (PlayRobotPackLog data : datas) {
				// 再次调用
				try {
					PlayMessagePushDetail detail = mapper.selectById(data.getPushDetailId());
					retryRequest(data, detail);
				} catch (Exception e) {
					log.info("RetryJobProcessor_error {}", data, e);
				}

			}
		}
		// 查询 错误的数据重试
		datas = SpringUtils.getBean(PlayRobotPackLogMapper.class)
				.selectList(new QueryWrapper<PlayRobotPackLog>().lambda().eq(PlayRobotPackLog::getStatus, 2)
						.eq(PlayRobotPackLog::getIsFinish, 0)
						.le(PlayRobotPackLog::getCreateTime, Times.getSecond(new Date(), -10))
						.le(PlayRobotPackLog::getRetryCount, MaxRetryCount).eq(PlayRobotPackLog::getRetryMaxFlag, 0)
						.eq(PlayRobotPackLog::getPlayId, play.getId()));
		
		if (!CollectionUtils.isEmpty(datas)) {
			log.info("RetryJobProcessor2 {}", play);
			for (PlayRobotPackLog data : datas) {
				// 再次调用
				try {
					PlayMessagePushDetail detail = mapper.selectById(data.getPushDetailId());
					retryRequest(data, detail);
				} catch (Exception e) {
					log.info("RetryJobProcessor_error {}", data, e);
				}

			}
		}

	}

	private void retryRequest(PlayRobotPackLog data, PlayMessagePushDetail detail) {
		// 查找对应的 配置
		final PlayRobotPackLogMapper robotPackLogMapper = SpringUtils.getBean(PlayRobotPackLogMapper.class);
		final PlayRobotPackMapper playRobotPackMapper = SpringUtils.getBean(PlayRobotPackMapper.class);

		if (data.getRetryCount().intValue() >= MaxRetryCount) {
			// 超过了 最大重试次数
			PlayRobotPackLog update = new PlayRobotPackLog();
			update.setOpt(data.getOpt());
			update.setRetryMaxFlag(1);
			robotPackLogMapper.updateById(update);
			return;
		}

		PlayRobotPack conf = playRobotPackMapper.selectOne(
				new QueryWrapper<PlayRobotPack>().lambda().eq(PlayRobotPack::getMessageSort, detail.getMessageSort())
						.eq(PlayRobotPack::getPlayId, data.getPlayId()).last(" limit 1 "));

		final String chatroomId = data.getChatroomId();
		final String robotId = data.getRobotId();
		// 请求 设置 机器人头像 ，昵称等
		Map<String, Object> param = new HashMap<>();
		param.put(Settings.Key_PlayId, data.getPlayId());
		// 机器人id
		param.put(Settings.Key_RobotId, robotId);
		// 群id
		param.put(Settings.Key_GroupId, chatroomId);
		// 姓名
		param.put(Settings.Key_Names1, Objects.wrapNull(conf.getName(), ""));
		// 姓氏
		param.put(Settings.Key_Names2, Objects.wrapNull(conf.getSurname(), ""));
		// 管理员
		param.put(Settings.Key_Admin_Flag,
				Objects.wrapNull(conf.getIsAdmin(), new Integer(0)).intValue() == 0 ? false : true);
		// 头像
		param.put(Settings.Key_Img, Objects.wrapNull(conf.getPic(), ""));
		// 备用号
		param.put(Settings.Key_Backup_Flag, data.getIsBackup().intValue() == 0 ? false : true);

		final Settings tgRobotImgSettings = ServiceLoader.load(Settings.class, "TgRobotImgSettings");
		final Settings tgRobotNameSettings = ServiceLoader.load(Settings.class, "TgRobotNameSettings");
		final Settings tgGroupHashSettings = ServiceLoader.load(Settings.class, "TgGroupHashSettings");
		final Settings tgRobotAdminSettings = ServiceLoader.load(Settings.class, "TgRobotAdminSettings");
		final Settings tgKpRobotAdminSettings = ServiceLoader.load(Settings.class, "TgKpRobotAdminSettings");

		data.setRetryCount(data.getRetryCount() + 1);
		// 判断操作 1-设置机器人姓名，姓氏 2-设置机器人头像 3-设置群hash值 4-设置管理员 5-获取群成员
		if (data.getOp().intValue() == 1) {
			// 设置机器人姓名
			PlayRobotPackLog ret = tgRobotNameSettings.set(param);
			if (StringUtils.isEmpty(ret.getOpt())) {
				PlayExecutionLogService.robotPackLog(data.getPlayId(), data.getChatroomId(), robotId, ret.getErrMsg(),
						null, "姓名-重试" + data.getRetryCount() + "次", true);
				// 更新 失败
				data.setErrMsg(ret.getErrMsg());
				data.setCreateTime(new Date());
				data.setStatus(2);
				robotPackLogMapper.updateById(data);
				log.info("log重试更新 {}", data);
			} else {
				PlayExecutionLogService.robotPackLog(data.getPlayId(), data.getChatroomId(), robotId, null,
						ret.getOpt(), "姓名-重试" + data.getRetryCount() + "次", true);
				// 删除之前的操作码
				robotPackLogMapper.deleteById(data.getOpt());
				// 重新插入
				data.setOpt(ret.getOpt());
				data.setCreateTime(new Date());
				data.setStatus(0);
				data.setErrMsg("");
				robotPackLogMapper.insert(data);
				log.info("log重试插入 {}", data);
			}
		}

		if (data.getOp().intValue() == 2) {
			// 设置机器人头像
			PlayRobotPackLog ret = tgRobotImgSettings.set(param);
			if (StringUtils.isEmpty(ret.getOpt())) {
				PlayExecutionLogService.robotPackLog(data.getPlayId(), data.getChatroomId(), robotId, ret.getErrMsg(),
						null, "头像-重试" + data.getRetryCount() + "次", true);
				// 更新 失败
				data.setErrMsg(ret.getErrMsg());
				data.setCreateTime(new Date());
				data.setStatus(2);
				robotPackLogMapper.updateById(data);
				log.info("log重试更新 {}", data);
			} else {
				PlayExecutionLogService.robotPackLog(data.getPlayId(), data.getChatroomId(), robotId, null,
						ret.getOpt(), "头像-重试" + data.getRetryCount() + "次", true);

				// 删除之前的操作码
				robotPackLogMapper.deleteById(data.getOpt());
				// 重新插入
				data.setOpt(ret.getOpt());
				data.setErrMsg("");
				data.setCreateTime(new Date());
				data.setStatus(0);
				robotPackLogMapper.insert(data);
				log.info("log重试插入 {}", data);
			}
		}

		if (data.getOp().intValue() == 3) {
			// 设置群hash值
			PlayRobotPackLog ret = tgGroupHashSettings.set(param);
			if (StringUtils.isEmpty(ret.getOpt())) {
				PlayExecutionLogService.robotPackLog(data.getPlayId(), data.getChatroomId(), robotId, ret.getErrMsg(),
						null, "管理员（获取hash值）-重试" + data.getRetryCount() + "次", true);
				// 更新 失败
				data.setErrMsg(ret.getErrMsg());
				data.setCreateTime(new Date());
				data.setStatus(2);
				robotPackLogMapper.updateById(data);
				log.info("log重试更新 {}", data);
			} else {
				PlayExecutionLogService.robotPackLog(data.getPlayId(), data.getChatroomId(), robotId, null,
						ret.getOpt(), "管理员（获取hash值）-重试" + data.getRetryCount() + "次", true);
				// 修改后置opt
				PlayRobotPackLog condition = robotPackLogMapper.selectOne(new QueryWrapper<PlayRobotPackLog>().lambda()
						.eq(PlayRobotPackLog::getWaitOpt, data.getOpt()).last(" limit 1 "));
				if (condition != null) {
					condition.setWaitOpt(ret.getOpt());
					robotPackLogMapper.updateById(condition);
				}
				// 删除之前的操作码
				robotPackLogMapper.deleteById(data.getOpt());
				// 重新插入
				data.setOpt(ret.getOpt());
				data.setErrMsg("");
				data.setCreateTime(new Date());
				data.setStatus(0);
				robotPackLogMapper.insert(data);
				log.info("log重试插入 {}", data);
			}
		}

		if (data.getOp().intValue() == 4) {
			// 设置管理员
			ISysConfigService sysConfigService = SpringUtils.getBean(SysConfigServiceImpl.class);
			final String botAdminPara = sysConfigService.selectConfigByKey("setBotAdmin:para");
			if("0".equals(botAdminPara)) {
				PlayRobotPackLog ret = tgRobotAdminSettings.set(param);
				if (ret.getStatus().intValue() == 1) {
					// 直接设置成功
					PlayExecutionLogService.robotPackLog(data.getPlayId(), data.getChatroomId(), robotId,
							String.format("【发言人包装-%s】 群%s 号%s 重试成功", "管理员", data.getChatroomId(), robotId), null);
					data.setErrMsg("");
					data.setCreateTime(new Date());
					data.setStatus(1);
					robotPackLogMapper.updateById(data);
					log.info("log重试更新 {}", data);
				} else {
					PlayExecutionLogService.robotPackLog(data.getPlayId(), data.getChatroomId(), robotId,
							String.format("【发言人包装-%s】 群%s 号%s 重试" + data.getRetryCount() + "次失败，原因：%s", "管理员", data.getChatroomId(), robotId,
									ret.getErrMsg()),
							ret.getErrMsg());
					// 更新 失败
					data.setErrMsg(ret.getErrMsg());
					data.setCreateTime(new Date());
					data.setStatus(2);
					robotPackLogMapper.updateById(data);
					log.info("log重试更新 {}", data);
				}
			}
			else {
				//走开平的逻辑
				PlayRobotPackLog ret = tgKpRobotAdminSettings.set(param);
				if (StringUtils.isEmpty(ret.getOpt())) {
					PlayExecutionLogService.robotPackLog(data.getPlayId(), data.getChatroomId(), robotId, ret.getErrMsg(),
							null, String.format("【发言人包装-%s】 群%s 号%s 请求失败", "管理员", data.getChatroomId(), data.getRobotId()), true);
					// 更新 失败
					data.setErrMsg(ret.getErrMsg());
					data.setCreateTime(new Date());
					data.setStatus(2);
					robotPackLogMapper.updateById(data);
					log.info("log重试更新 {}", data);
				} else {
					PlayExecutionLogService.robotPackLog(data.getPlayId(), data.getChatroomId(), robotId, null,
							ret.getOpt(), String.format("【发言人包装-%s】 群%s 号%s 请求成功", "管理员", data.getChatroomId(), data.getRobotId()), true);
					// 删除之前的操作码
					robotPackLogMapper.deleteById(data.getOpt());
					// 重新插入
					data.setOpt(ret.getOpt());
					data.setCreateTime(new Date());
					data.setStatus(0);
					data.setErrMsg("");
					robotPackLogMapper.insert(data);
					log.info("log重试插入 {}", data);
				}
			}
		}

	}

}
