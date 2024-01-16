package com.ruoyi.system.components.multipack;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import com.alibaba.fastjson.JSON;
import com.ruoyi.common.core.redis.RedisLock;
import com.ruoyi.common.utils.DelayAcquireTools;
import com.ruoyi.common.utils.Ids;
import com.ruoyi.common.utils.ListTools;

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

	public void onCalled(String opt) {
		// 根据操作码 拿到 数据
		DelayAcquireTools.acquire(() -> redisTemplate.opsForValue().get("ruoyi:Container:pck:" + opt), val -> {
			Pack pack = JSON.parseObject(val.toString(), Pack.class);
			// 拿到 组数据 , 判断是否满了
			redisLock.waitLock("ruoyi:Container:radiolock:" + pack.getRadioId(), 60);
			try {
				PckRadio radio = waitRadio(pack);
				if (radio == null) {
					log.error("无法获取组数据 {}", pack);
					return;
				}
				// 判断 数量
				if (radio.isFull()) {
					// 已经完成
					// 拿到数据
					List<Pack> packs = new ArrayList<>();
					for (String optItem : radio.getPckOpt()) {
						Object obj = redisTemplate.opsForValue().get("ruoyi:Container:pck:" + optItem);
						if (obj == null) {
							continue;
						}
						packs.add(JSON.parseObject(obj.toString(), Pack.class));
					}
//					redisTemplate.delete("ruoyi:Container:pckradio:" + radio.getRadioId());
					// 通知 回调 已完成
					
				}
				radio.setCurrent(radio.getCurrent() + 1);
				redisTemplate.opsForValue().set("ruoyi:Container:pckradio:" + radio.getRadioId(),
						JSON.toJSONString(radio), 60 * 60 * 24, TimeUnit.SECONDS);
				log.info("设置组数据_update {} {}", "ruoyi:Container:pckradio:" + radio.getRadioId(),
						JSON.toJSONString(radio));
			} finally {
				redisLock.unWaitLock("ruoyi:Container:radiolock:" + pack.getRadioId());
			}
		});
	}

	public PckRadio waitRadio(Pack pack) {
		int c = 50;
		while (c-- <= 0) {
			Object val = redisTemplate.opsForValue().get("ruoyi:Container:pckradio:" + pack.getRadioId());
			if (val != null) {
				return JSON.parseObject(val.toString(), PckRadio.class);
			}
			try {
				Thread.sleep(200);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	public void put(List<Pack> pckList) {
		String radioId = Ids.getId();
		PckRadio radio = new PckRadio(radioId, ListTools.extract(pckList, f -> f.getOpt()));

		for (Pack pckRadio : pckList) {
			pckRadio.setRadioId(radioId);
			redisTemplate.opsForValue().set("ruoyi:Container:pck:" + pckRadio.getOpt(), JSON.toJSONString(pckRadio),
					60 * 60 * 24, TimeUnit.SECONDS);
			log.info("设置组元数据 {} {}", "ruoyi:Container:pck:" + pckRadio.getOpt(), JSON.toJSONString(pckRadio));

		}
		redisTemplate.opsForValue().set("ruoyi:Container:pckradio:" + radioId, JSON.toJSONString(radio), 60 * 60 * 24,
				TimeUnit.SECONDS);
		log.info("设置组数据 {} {}", "ruoyi:Container:pckradio:" + radioId, JSON.toJSONString(radio));
	}

	@Override
	public void afterPropertiesSet() throws Exception {

	}

	@Override
	public void destroy() throws Exception {
		// TODO Auto-generated method stub

	}

}
