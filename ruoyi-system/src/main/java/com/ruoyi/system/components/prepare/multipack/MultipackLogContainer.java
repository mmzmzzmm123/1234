package com.ruoyi.system.components.prepare.multipack;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;
import org.apache.commons.lang3.StringUtils;
import org.redisson.api.RLock;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import com.alibaba.fastjson2.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.ruoyi.common.core.domain.entity.play.Play;
import com.ruoyi.common.core.domain.entity.play.PlayRobotPackLog;
import com.ruoyi.common.core.redis.RedisLock;
import com.ruoyi.common.core.thread.AsyncTask;
import com.ruoyi.common.enums.ScanProgressEnum;
import com.ruoyi.common.utils.DelayAcquireTools;
import com.ruoyi.common.utils.Strings;
import com.ruoyi.common.utils.spi.ServiceLoader;
import com.ruoyi.system.components.RedisTemplateTools;
import com.ruoyi.system.mapper.PlayMapper;
import com.ruoyi.system.service.PlayRobotPackLogService;
import lombok.Data;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class MultipackLogContainer implements InitializingBean {

	@SuppressWarnings("rawtypes")
	@Autowired
	RedisTemplate redisTemplate;
	@Autowired
	RedisLock redisLock;
	@Autowired
	PlayRobotPackLogService robotPackLogService;

	@Autowired
	PlayMapper playMapper;

	@Override
	public void afterPropertiesSet() throws Exception {
		try {
			RedisTemplateTools.get().delete("ruoyi:MultipackLogContainerLock");
		} catch (Exception e) {
		}
	}

	/**
	 * 扫描 回调数据处理
	 */
	public void jobScan() {
		// 加锁
		RLock lock = redisLock.getRLock("ruoyi:MultipackLogContainerLock");
		if (lock.isLocked()) {
			log.info("MultipackLogContainer_jobScan_lock");
			return;
		}
		final StartupJobProcessor startupJobProcessor = (StartupJobProcessor) ServiceLoader.load(LogJobProcessor.class,
				"StartupJobProcessor");
		final StateJobProcessor stateJobProcessor = (StateJobProcessor) ServiceLoader.load(LogJobProcessor.class,
				"StateJobProcessor");
		final RetryJobProcessor retryJobProcessor = (RetryJobProcessor) ServiceLoader.load(LogJobProcessor.class,
				"RetryJobProcessor");
//		final LogPostJobProcessor logPostJobProcessor = (LogPostJobProcessor) ServiceLoader.load(LogJobProcessor.class,
//				"LogPostJobProcessor");
		final SendConditionJobProcessor sendConditionJobProcessor = (SendConditionJobProcessor) ServiceLoader
				.load(LogJobProcessor.class, "SendConditionJobProcessor");
		final SendPlayJobProcessor sendPlayJobProcessor = (SendPlayJobProcessor) ServiceLoader
				.load(LogJobProcessor.class, "SendPlayJobProcessor");
		final MessageTimeoutJobProcessor messageTimeoutJobProcessor = (MessageTimeoutJobProcessor) ServiceLoader
				.load(LogJobProcessor.class, "MessageTimeoutJobProcessor");

		try {
			lock.lock(60 * 10, TimeUnit.SECONDS);
			// 0未开始 1调度修改群人设中 2 调用入群中 3 入群等待中 4 混淆 5 号分配,人设 6等待超群条件 7发剧本
			List<Play> datas = playMapper
					.selectList(
							new QueryWrapper<Play>().lambda()
									.in(Play::getScanProgress, Arrays.asList(ScanProgressEnum.Robot.getVal(),
											ScanProgressEnum.Send_Wait.getVal()))
									.eq(Play::getIsDelete, 0).in(Play::getState, Arrays.asList(1, 2, 3)));
			if (CollectionUtils.isEmpty(datas)) {
				return;
			}
			AsyncTask.newMultiTasker().map(datas, 5, curs -> {
				for (Play play : curs) {
					if (play.getScanProgress().intValue() == ScanProgressEnum.Send_Wait.getVal()) {
						// 炒群 条件 定时器 , 时间达到&人数达到
						try {
							long s = System.currentTimeMillis();
							sendConditionJobProcessor.handle(play);
							log.info("sendConditionJobProcessor执行 {} {}", (System.currentTimeMillis() - s), play);
						} catch (Exception e) {
							log.error("sendConditionJobProcessor执行异常 {}", play, e);
						}
						try {
							long s = System.currentTimeMillis();
							sendPlayJobProcessor.handle(play);
							log.info("sendPlayJobProcessor执行 {} {}", (System.currentTimeMillis() - s), play);
						} catch (Exception e) {
							log.error("sendPlayJobProcessor执行异常 {}", play, e);
						}
						try {
							long s = System.currentTimeMillis();
							messageTimeoutJobProcessor.handle(play);
							log.info("messageTimeoutJobProcessor执行 {} {}", (System.currentTimeMillis() - s), play);
						} catch (Exception e) {
							log.error("messageTimeoutJobProcessor执行异常 {}", play, e);
						}
						continue;
					}

					// 开始 启动人设 包装
					try {
						long s = System.currentTimeMillis();
						startupJobProcessor.handle(play);
						log.info("startupJobProcessor执行 {} {}", (System.currentTimeMillis() - s), play);
					} catch (Exception e) {
						log.error("startupJobProcessor执行异常 {}", play, e);
					}

					// 包装执行日志的 状态扭转
					try {
						long s = System.currentTimeMillis();
						stateJobProcessor.handle(play);
						log.info("stateJobProcessor执行 {} {}", (System.currentTimeMillis() - s), play);
					} catch (Exception e) {
						log.error("stateJobProcessor执行异常 {}", play, e);
					}

					// 包装执行日志的重试执行
					try {
						long s = System.currentTimeMillis();
						retryJobProcessor.handle(play);
						log.info("retryJobProcessor执行 {} {}", (System.currentTimeMillis() - s), play);
					} catch (Exception e) {
						log.error("retryJobProcessor执行异常 {}", play, e);
					}

					// 包装执行日志的 后置处理 (设置管理员)
//					try {
//						long s = System.currentTimeMillis();
//						logPostJobProcessor.handle(play);
//						log.info("logPostJobProcessor执行 {} {}", (System.currentTimeMillis() - s), play);
//					} catch (Exception e) {
//						log.error("logPostJobProcessor执行异常 {}", play, e);
//					}
				}
			});
		} finally {
			try {
				lock.unlock();
			} catch (Exception e2) {
			}
		}

	}

	public void submit(List<PlayRobotPackLog> pckList) {
		if (CollectionUtils.isEmpty(pckList)) {
			return;
		}
		// 数据是否存在， 存在则删除重新提交
		robotPackLogService.remove(new UpdateWrapper<PlayRobotPackLog>().lambda()
				.eq(PlayRobotPackLog::getPlayId, pckList.get(0).getPlayId())
				.eq(PlayRobotPackLog::getChatroomId, pckList.get(0).getChatroomId()));
		for (PlayRobotPackLog item : pckList) {
			item.setCreateTime(new Date());
			item.setTotal(pckList.size());
			log.info("设置组元数据 {} ", item);
		}
		robotPackLogService.saveBatch(pckList);
	}

	public void onfail(String opt, String error) {
		String err = Strings.subMax(error, 500);
		DelayAcquireTools.acquire(() -> robotPackLogService.getById(opt), data -> {
			// 修改 状态
			CallValueStore.store(opt, null, err);
		});
	}

	public void onSucceed(String opt, String attchContent) {
		// 根据操作码 拿到 数据
		DelayAcquireTools.acquire(() -> robotPackLogService.getById(opt), data -> {
			CallValueStore.store(opt, attchContent, null);
		});
	}

	@Data
	@Accessors(chain = true)
	public static class CallValue {

		private final String opt;

		private String error;

		private String attchContent;

		private boolean success;

	}

	public static class CallValueStore {

		public static void store(String opt, String attchContent, String error) {
			if (StringUtils.isEmpty(error)) {
				RedisTemplateTools.get().opsForValue().set("ruoyi:CallValueStore:" + opt,
						JSON.toJSONString(new CallValue(opt).setSuccess(true).setAttchContent(attchContent)),
						60 * 60 * 24 * 5, TimeUnit.SECONDS);

				return;
			}
			RedisTemplateTools.get().opsForValue().set("ruoyi:CallValueStore:" + opt,
					JSON.toJSONString(
							new CallValue(opt).setSuccess(false).setAttchContent(attchContent).setError(error)),
					60 * 60 * 24 * 5, TimeUnit.SECONDS);

			log.info("CallValueStore {} {} {}", opt, attchContent, error);

		}

		public static CallValue get(String opt) {
			Object val = RedisTemplateTools.get().opsForValue().get("ruoyi:CallValueStore:" + opt);
			if (val == null) {
				return null;
			}
			return JSON.parseObject(val.toString(), CallValue.class);
		}

	}

}
