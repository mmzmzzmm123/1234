package com.ruoyi.system.components.movie.spi.impl;

import java.math.BigDecimal;
import com.ruoyi.common.utils.spi.SPI;
import com.ruoyi.common.utils.spring.SpringUtils;
import com.ruoyi.system.components.movie.spi.DelaySpeedController;
import com.ruoyi.system.mapper.PlayMapper;

@SPI
public class DefaultDelaySpeedController implements DelaySpeedController {

	@Override
	public int calculate(int sourceVal, String playId) {
		BigDecimal bigDecimal = SpringUtils.getBean(PlayMapper.class).selectById(playId).getSpeed();
		if (bigDecimal == null || bigDecimal.doubleValue() == 0d) {
			return sourceVal;
		}
		final double speed = bigDecimal.doubleValue();
		if (speed > 0) {
			// 加速 ， 剧本要快
			int v = (int) ((double) sourceVal - (double) sourceVal * speed);
			if (v == 0) {
				return sourceVal;
			}
			if (v < 0) {
				return Math.abs(v);
			}
			return v;
		}

		if (speed < 0) {
			// 减速 ， 剧本要慢
			int v = (int) ((double) sourceVal + (double) sourceVal * Math.abs(speed));
			if (v == 0) {
				return sourceVal;
			}
			if (v < 0) {
				return Math.abs(v);
			}
			return v;
		}

		return 0;
	}
}
