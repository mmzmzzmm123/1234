package com.ruoyi.system.components.prepare.multipack;

import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import com.alibaba.fastjson2.JSON;
import com.ruoyi.common.core.domain.entity.play.PlayRobotPackLog;
import com.ruoyi.common.core.redis.RedisLock;
import com.ruoyi.common.utils.DelayAcquireTools;
import com.ruoyi.common.utils.Strings;
import com.ruoyi.common.utils.spring.SpringUtils;
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

	@Override
	public void afterPropertiesSet() throws Exception {
	}

	/**
	 * 扫描 回调数据处理
	 */
	public void jobScan() {

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
				SpringUtils.getBean(RedisTemplate.class).opsForValue().set("ruoyi:CallValueStore:" + opt,
						JSON.toJSONString(new CallValue(opt).setSuccess(true).setAttchContent(attchContent)),
						60 * 60 * 24 * 5, TimeUnit.SECONDS);

				return;
			}
			SpringUtils.getBean(RedisTemplate.class).opsForValue().set("ruoyi:CallValueStore:" + opt,
					JSON.toJSONString(
							new CallValue(opt).setSuccess(false).setAttchContent(attchContent).setError(error)),
					60 * 60 * 24 * 5, TimeUnit.SECONDS);

			log.info("CallValueStore {} {} {}", opt, attchContent, error);

		}

		public static CallValue get(String opt) {
			Object val = SpringUtils.getBean(RedisTemplate.class).opsForValue().get("ruoyi:CallValueStore:" + opt);
			if (val == null) {
				return null;
			}
			return JSON.parseObject(val.toString(), CallValue.class);
		}

	}

}
