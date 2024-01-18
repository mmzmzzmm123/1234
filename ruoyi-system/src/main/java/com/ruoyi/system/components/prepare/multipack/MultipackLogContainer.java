package com.ruoyi.system.components.prepare.multipack;

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
import com.ruoyi.common.core.domain.entity.play.Play;
import com.ruoyi.common.core.domain.entity.play.PlayRobotPackLog;
import com.ruoyi.common.core.redis.RedisLock;
import com.ruoyi.common.core.thread.AsyncTask;
import com.ruoyi.common.core.thread.TaskerCallbackNoneResult;
import com.ruoyi.common.utils.DelayAcquireTools;
import com.ruoyi.common.utils.Strings;
import com.ruoyi.common.utils.spi.ServiceLoader;
import com.ruoyi.common.utils.spring.SpringUtils;
import com.ruoyi.system.components.RedisTemplateTools;
import com.ruoyi.system.mapper.PlayMapper;
import com.ruoyi.system.service.PlayRobotPackLogService;
import lombok.Data;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;

@Component
@SuppressWarnings("unchecked")
@Slf4j
public class MultipackLogContainer implements InitializingBean {

	@SuppressWarnings("rawtypes")
	@Autowired
	RedisTemplate redisTemplate;
	@Autowired
	RedisLock redisLock;

	@Autowired(required = false)
	List<OnPackMonitor> onPackMonitors;

	@Autowired(required = false)
	List<OnRadioPackMonitor> radioPackMonitors;

	@Autowired
	PlayRobotPackLogService robotPackLogService;

	@Autowired
	PlayMapper playMapper;

	@Override
	public void afterPropertiesSet() throws Exception {
	}

	/**
	 * 扫描 回调数据处理
	 */
	public void jobScan() {
		// 加锁
		RLock lock = redisLock.getRLock("ruoyi:MultipackLogContainer");
		if (lock.isLocked()) {
			log.info("MultipackLogContainer_jobScan_lock");
			return;
		}
		final LogJobProcessor stateJobProcessor = ServiceLoader.load(LogJobProcessor.class, "StateJobProcessor");
		final LogJobProcessor retryJobProcessor = ServiceLoader.load(LogJobProcessor.class, "RetryJobProcessor");
		final LogJobProcessor logPostJobProcessor = ServiceLoader.load(LogJobProcessor.class, "LogPostJobProcessor");

		try {
			lock.lock(5, TimeUnit.SECONDS);
			// 查询所有人设包装状态的剧本 剧本执行进度：0未开始 1调度修改群人设中 2 调用入群中 3 入群等待中 4混淆中 5号分配 6人设包装 7剧本发送
			List<Play> datas = playMapper.selectList(
					new QueryWrapper<Play>().lambda().eq(Play::getScanProgress, 6).eq(Play::getIsDelete, 0));
			if (CollectionUtils.isEmpty(datas)) {
				return;
			}
			AsyncTask.newMultiTasker().map(datas, 5, curs -> {
				for (Play play : curs) {
					// 各个 定时器 执行
					try {
						long s = System.currentTimeMillis();
						stateJobProcessor.handle(play);
						log.info("stateJobProcessor执行 {} {}", (System.currentTimeMillis() - s), play);
					} catch (Exception e) {
						log.error("stateJobProcessor执行异常 {}", play, e);
					}

					try {
						long s = System.currentTimeMillis();
						retryJobProcessor.handle(play);
						log.info("retryJobProcessor执行 {} {}", (System.currentTimeMillis() - s), play);
					} catch (Exception e) {
						log.error("retryJobProcessor执行异常 {}", play, e);
					}

					try {
						long s = System.currentTimeMillis();
						logPostJobProcessor.handle(play);
						log.info("logPostJobProcessor执行 {} {}", (System.currentTimeMillis() - s), play);
					} catch (Exception e) {
						log.error("logPostJobProcessor执行异常 {}", play, e);
					}

				}
			});
		} finally {
			lock.unlock();
		}

	}

	public void submit(List<PlayRobotPackLog> pckList) {
		if (CollectionUtils.isEmpty(pckList)) {
			return;
		}
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
