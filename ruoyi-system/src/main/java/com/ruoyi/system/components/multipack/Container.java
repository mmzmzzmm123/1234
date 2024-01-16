package com.ruoyi.system.components.multipack;

import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ruoyi.common.core.domain.entity.play.PlayRobotPackLog;
import com.ruoyi.common.core.redis.RedisLock;
import com.ruoyi.common.utils.DelayAcquireTools;
import com.ruoyi.common.utils.Ids;
import com.ruoyi.system.mapper.PlayRobotPackLogMapper;
import lombok.extern.slf4j.Slf4j;

@Component
@SuppressWarnings("unchecked")
@Slf4j
public class Container implements InitializingBean, DisposableBean {

	@SuppressWarnings("rawtypes")
	@Autowired
	RedisTemplate redisTemplate;
	@Autowired
	RedisLock redisLock;

	@Autowired(required = false)
	List<OnPackMonitor> onPackMonitors;
//
//	@Autowired
//	List<OnRadioPackMonitor> radioPackMonitors;

	@Autowired
	PlayRobotPackLogMapper robotPackLogMapper;

	public void onfail(String opt, String error) {
		DelayAcquireTools.acquire(() -> robotPackLogMapper.selectById(opt), data -> {
			// 单个回调
			onPackMonitors.forEach(item -> item.onPackFailed(data, error));
			// 修改 状态
			redisTemplate.opsForValue().set("ruoyi:Containercall:" + opt, error, 60 * 60 * 24 * 5, TimeUnit.SECONDS);

			log.info("状态更新失败 {} {}", opt, error);

		});
	}

	public void onSucceed(String opt) {
		// 根据操作码 拿到 数据
		DelayAcquireTools.acquire(() -> robotPackLogMapper.selectById(opt), data -> {
			// 单个回调
			onPackMonitors.forEach(item -> item.onPackSucceed(data));
			// 修改 状态
			redisTemplate.opsForValue().set("ruoyi:Containercall:" + opt, "ok", 60 * 60 * 24 * 5, TimeUnit.SECONDS);
			log.info("状态更新成功 {}", opt);
		});
	}

	public List<PlayRobotPackLog> waitFull(PlayRobotPackLog log) {
		int c = 50;
		while (c-- <= 0) {
			List<PlayRobotPackLog> datas = robotPackLogMapper.selectList(
					new QueryWrapper<PlayRobotPackLog>().lambda().eq(PlayRobotPackLog::getRadioId, log.getRadioId()));
			if (datas.size() >= log.getTotal().intValue()) {
				// full
				return datas;
			}
			try {
				Thread.sleep(200);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	public void put(List<PlayRobotPackLog> pckList) {
		if (CollectionUtils.isEmpty(pckList)) {
			return;
		}
		String radioId = Ids.getId();
		for (PlayRobotPackLog item : pckList) {
			item.setRadioId(radioId);
			item.setCreateTime(new Date());
			item.setTotal(pckList.size());
			robotPackLogMapper.insert(item);
			log.info("设置组元数据 {} ", item);
		}
	}

	@Override
	public void afterPropertiesSet() throws Exception {

	}

	@Override
	public void destroy() throws Exception {
		// TODO Auto-generated method stub

	}

}
