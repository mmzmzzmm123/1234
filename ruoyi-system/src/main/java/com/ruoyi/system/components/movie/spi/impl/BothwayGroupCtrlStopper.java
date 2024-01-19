package com.ruoyi.system.components.movie.spi.impl;

import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.spi.SPI;
import com.ruoyi.system.components.RedisTemplateTools;
import com.ruoyi.system.components.movie.spi.GroupCtrlStopper;
import lombok.extern.slf4j.Slf4j;

/**
 * title: 单个群累计双向数上限
 * 
 * @author Administrator
 *
 */
@SPI("BothwayGroupCtrlStopper")
@Slf4j
public class BothwayGroupCtrlStopper implements GroupCtrlStopper {

	@Override
	public void doSetting(String groupId, String robotId) {
		RedisTemplateTools.get().opsForValue().set("ruoyi:BothwayGroupCtrlStopper:" + robotId, "1");
		log.info("BothwayGroupCtrlStopper_set {} ", robotId);
	}

	@Override
	public boolean isStoped(String groupId, String robotId) {
		String v = RedisTemplateTools.get().opsForValue().get("ruoyi:BothwayGroupCtrlStopper:" + robotId);
		if (StringUtils.isEmpty(v)) {
			return false;
		}
		return true;
	}

}
