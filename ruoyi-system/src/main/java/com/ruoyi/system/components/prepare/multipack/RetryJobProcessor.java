package com.ruoyi.system.components.prepare.multipack;

import java.util.*;

import com.alibaba.fastjson2.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.common.core.domain.entity.play.*;
import com.ruoyi.common.utils.Ids;
import com.ruoyi.common.utils.ListTools;
import com.ruoyi.common.utils.bean.BeanUtils;
import com.ruoyi.system.components.movie.PlayDirector;
import com.ruoyi.system.components.prepare.ExecutionParamContext;
import com.ruoyi.system.mapper.*;
import com.ruoyi.system.service.ISysConfigService;
import com.ruoyi.system.service.impl.SysConfigServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.CollectionUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ruoyi.common.utils.Objects;
import com.ruoyi.common.utils.Times;
import com.ruoyi.common.utils.spi.SPI;
import com.ruoyi.common.utils.spi.ServiceLoader;
import com.ruoyi.common.utils.spring.SpringUtils;
import com.ruoyi.system.components.spi.Settings;
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

	private static final int MaxRetryCount = 4;

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
			update.setStatus(2);
			robotPackLogMapper.updateById(update);

			//切换备用号继续包装
			//先判断是否已有备用号在包装中（心累的判断，可能会出现多个备用号都在包装的情况）
			final List<PlayRobotPackLog> playRobotPackLogs = SpringUtils.getBean(PlayRobotPackLogMapper.class).selectList(new LambdaQueryWrapper<PlayRobotPackLog>()
					.eq(PlayRobotPackLog::getPlayId, data.getPlayId())
					.eq(PlayRobotPackLog::getChatroomId, data.getChatroomId())
					.eq(PlayRobotPackLog::getPushDetailId, data.getPushDetailId())
			);
			Map<String, List<PlayRobotPackLog>> group = ListTools.group(playRobotPackLogs, f -> f.getRobotId());
			for (List<PlayRobotPackLog> logList : group.values()) {
				final long failCount = logList.stream().filter(it -> it.getStatus().intValue() == 2).count();
				if(failCount == 0L){
					//有未失败的机器人包装记录，暂时不能再分配备用号
					return;
				}
			}
			//开始分配备用号逻辑
			final PlayBackRobot processBackRobot = PlayDirector.tgInstance().getProcessBackRobot(data.getPlayId(), data.getChatroomId(), detail.getSpokesmanNickname(), null);
			if(processBackRobot == null){
				//没有可用的备用号
				return;
			}
			//todo 备用号包装逻辑 需要检查
			Play play = SpringUtils.getBean(PlayMapper.class).selectById(data.getPlayId());
			PlayMessagePush push = SpringUtils.getBean(PlayMessagePushMapper.class).selectOne(new LambdaQueryWrapper<PlayMessagePush>()
					.eq(PlayMessagePush::getPlayId,data.getPlayId())
					.eq(PlayMessagePush::getGroupId, data.getChatroomId())
					.last(" limit 1 ")
			);
			//更新为新的备用机器人
			detail.setRobotId(processBackRobot.getRobotId());
			SpringUtils.getBean(PlayMessagePushDetailMapper.class).updateById(detail);

			List<PlayMessagePushDetail> details = ListTools.newArrayList(detail);
			List<PlayRobotPack> robotPackList = playRobotPackMapper.selectList(
					new QueryWrapper<PlayRobotPack>().lambda().eq(PlayRobotPack::getRobotNickname,detail.getSpokesmanNickname())
							.eq(PlayRobotPack::getPlayId, data.getPlayId()).last(" limit 1 "));
			packBackRobot(new ExecutionParamContext(play, data.getChatroomId(), push, details, robotPackList), data.getRadioId());
			return;
		}

		PlayRobotPack conf = playRobotPackMapper.selectOne(
				new QueryWrapper<PlayRobotPack>().lambda().eq(PlayRobotPack::getRobotNickname, detail.getSpokesmanNickname())
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
		// hash 值
		param.put(Settings.Key_AttachContent, Objects.wrapNull(data.getAttchContent(), ""));

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
						.eq(PlayRobotPackLog::getWaitOpt, data.getOpt())
						.eq(PlayRobotPackLog::getStatus, -1)
						.eq(PlayRobotPackLog::getPlayId, data.getPlayId())
						.last(" limit 1 "));
				if (condition != null) {
					condition.setWaitOpt(ret.getOpt());
					robotPackLogMapper.updateById(condition);
					log.info("后置log更新opt {}", condition);
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
							null, String.format("【发言人包装-%s】 群%s 号%s 重试" + data.getRetryCount() + "次", "管理员", data.getChatroomId(), data.getRobotId()), true);
					// 更新 失败
					data.setErrMsg(ret.getErrMsg());
					data.setCreateTime(new Date());
					data.setStatus(2);
					robotPackLogMapper.updateById(data);
					log.info("log重试更新 {}", data);
				} else {
					PlayExecutionLogService.robotPackLog(data.getPlayId(), data.getChatroomId(), robotId, null,
							ret.getOpt(), String.format("【发言人包装-%s】 群%s 号%s 重试" + data.getRetryCount() + "次", "管理员", data.getChatroomId(), data.getRobotId()), true);
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

	/**
	 * 提交备用号包装任务
	 * @param context
	 * @param radioId
	 * @return
	 */
	private R<String> packBackRobot(ExecutionParamContext context, String radioId) {
		if (CollectionUtils.isEmpty(context.getPlayRobotPackList())) {
			return null;
		}
		final String playId = context.getPlay().getId();

		final Settings tgRobotImgSettings = ServiceLoader.load(Settings.class, "TgRobotImgSettings");

		final Settings tgRobotNameSettings = ServiceLoader.load(Settings.class, "TgRobotNameSettings");

		final Settings tgRobotAdminSettings = ServiceLoader.load(Settings.class, "TgRobotAdminSettings");

		final Settings tgGroupHashSettings = ServiceLoader.load(Settings.class, "TgGroupHashSettings");
		// 总共提交的数据
		// 包装 组id
		final List<PlayRobotPackLog> submitList = new ArrayList<>();
		for (PlayRobotPack robotPck : context.getPlayRobotPackList()) {
			// 查找 对应的发言 机器人数据
			PlayMessagePushDetail detail = getByNickname(context.getPushDetails(), robotPck.getRobotNickname());
			if (detail == null || StringUtils.isEmpty(detail.getRobotId())) {
				log.error("数据错误 {} {}", robotPck.getRobotNickname(), context.getPushDetails());
				continue;
			}
			List<String> robotList = new ArrayList<>();
			robotList.add(detail.getRobotId());
			// 每个机器人都要请求人设
			for (String robot : robotList) {
				// 请求 设置 机器人头像 ，昵称等
				Map<String, Object> param = new HashMap<>();
				param.put(Settings.Key_PlayId, context.getPlay().getId());
				// 机器人id
				param.put(Settings.Key_RobotId, robot);
				// 群id
				param.put(Settings.Key_GroupId, context.getChatroomId());
				// 姓名
				param.put(Settings.Key_Names1, Objects.wrapNull(robotPck.getName(), ""));
				// 姓氏
				param.put(Settings.Key_Names2, Objects.wrapNull(robotPck.getSurname(), ""));
				// 管理员
				param.put(Settings.Key_Admin_Flag,
						Objects.wrapNull(robotPck.getIsAdmin(), new Integer(0)).intValue() == 0 ? false : true);
				// 头像
				param.put(Settings.Key_Img, Objects.wrapNull(robotPck.getPic(), ""));
				// 备用号
				param.put(Settings.Key_Backup_Flag, 1);
				log.info("开始备用号初始人设包装 {} {} " , robot, JSON.toJSONString(robotPck));
				// 设置头像
				if (!StringUtils.isEmpty(robotPck.getPic())) {
					PlayRobotPackLog ret = tgRobotImgSettings.set(param);
					if (StringUtils.isEmpty(ret.getOpt())) {
						PlayExecutionLogService.robotPackLog(playId, context.getChatroomId(), robot, ret.getErrMsg(),
								null, "头像", true);
					} else {
						PlayExecutionLogService.robotPackLog(playId, context.getChatroomId(), robot, null, ret.getOpt(),
								"头像", true);
					}
					submitList.add(ret.wrapOpt().wrapRadio(radioId).wrapPushDetailId(detail.getId()));

					log.info("备用号-初始-同步设置头像 {} " , ret);
				}

				// 设置 昵称
				if (!StringUtils.isEmpty(robotPck.getName()) || !StringUtils.isEmpty(robotPck.getSurname())) {
					PlayRobotPackLog ret = tgRobotNameSettings.set(param);
					if (StringUtils.isEmpty(ret.getOpt())) {
						PlayExecutionLogService.robotPackLog(playId, context.getChatroomId(), robot, ret.getErrMsg(),
								null, "姓名", true);
					} else {
						PlayExecutionLogService.robotPackLog(playId, context.getChatroomId(), robot, null, ret.getOpt(),
								"姓名", true);
					}
					submitList.add(ret.wrapOpt().wrapRadio(radioId).wrapPushDetailId(detail.getId()));

					log.info("备用号-初始-同步设置昵称 {} " , ret);
				}

				// 同步设置 管理员
				if (robotPck.getIsAdmin() != null && robotPck.getIsAdmin().intValue() == 1) {
					log.info("备用号-初始-设置管理员 {} " , JSON.toJSONString(robotPck));
					ISysConfigService sysConfigService = SpringUtils.getBean(SysConfigServiceImpl.class);
					final String botAdminPara = sysConfigService.selectConfigByKey("setBotAdmin:para");
					if("0".equals(botAdminPara)) {
						log.info("备用号-初始-走bot的逻辑 {} " , JSON.toJSONString(robotPck));
						//走bot的逻辑
						PlayRobotPackLog ret = tgRobotAdminSettings.set(param);
						if (ret.getStatus().intValue() == 1) {
							// 直接设置成功
							PlayExecutionLogService.robotPackLog(playId, context.getChatroomId(), robot,
									String.format("【发言人包装-%s】 群%s 号%s 设置成功", "管理员", context.getChatroomId(), robot), null);
						} else {
							PlayExecutionLogService.robotPackLog(playId, context.getChatroomId(), robot,
									String.format("【发言人包装-%s】 群%s 号%s 设置失败，原因：%s", "管理员", context.getChatroomId(), robot,
											ret.getErrMsg()),
									ret.getErrMsg());
						}
						submitList.add(ret.wrapRadio(radioId).wrapPushDetailId(detail.getId()).wrapOpt());
						log.info("备用号-初始-同步设置管理员 {} ", ret);
					}
					else{
						//走开平的逻辑
						log.info("备用号-初始-走开平的逻辑 {} " , JSON.toJSONString(robotPck));
						final PlayRobotPackLog ret = tgGroupHashSettings.set(param);
						if (StringUtils.isEmpty(ret.getOpt())) {
							PlayExecutionLogService.robotPackLog(playId, context.getChatroomId(), robot, ret.getErrMsg(), null, "管理员（获取hash值）", true);
						} else {
							PlayExecutionLogService.robotPackLog(playId, context.getChatroomId(), robot, null, ret.getOpt(), "管理员（获取hash值）", true);
						}
						final String opt = ret.wrapOpt().getOpt();
						submitList.add(ret.wrapRadio(radioId).wrapPushDetailId(detail.getId()));
						log.info("备用号-初始-同步设置管理员(获取hash值) {} ", ret);

						if (!StringUtils.isEmpty(ret.getOpt())) {
							// 请求成功后，插入一条 后置 请求
							PlayRobotPackLog postposition = new PlayRobotPackLog();
							postposition.setChatroomId(context.getChatroomId());
							postposition.setCreateTime(new Date());
							postposition.setIsBackup((boolean) param.get(Settings.Key_Backup_Flag) ? 1 : 0);
							postposition.setIsFinish(0);
							// 1-设置机器人姓名，姓氏 2-设置机器人头像 3-设置群hash值 4-设置管理员 5-获取群成员
							postposition.setOp(4);
							postposition.setOpt("wait_" + Ids.getId());
							postposition.setPlayId(context.getPlay().getId());
							postposition.setRadioId(radioId);
							postposition.setRetryCount(0);
							postposition.setRobotId(robot);
							postposition.setStatus(-1);
							postposition.setPushDetailId(detail.getId());
							// 等待某个操作码执行完成才能开始调佣
							postposition.setWaitOpt(opt);
							submitList.add(postposition);
						}
					}
				}
			}
		}
		if (CollectionUtils.isEmpty(submitList)) {
			return null;
		}
		// 提交到 容器
		SpringUtils.getBean(MultipackLogContainer.class).submit(submitList);
		return R.ok();
	}
	private PlayMessagePushDetail getByNickname(List<PlayMessagePushDetail> datas, String nickname) {
		for (PlayMessagePushDetail detail : datas) {
			if (detail.getSpokesmanNickname().equals(nickname)) {
				return detail;
			}
		}
		return null;
	}
}
