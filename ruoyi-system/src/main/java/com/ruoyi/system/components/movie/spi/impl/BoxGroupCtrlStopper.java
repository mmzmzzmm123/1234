package com.ruoyi.system.components.movie.spi.impl;

import com.ruoyi.common.enums.PlayLogTyper;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.spi.SPI;
import com.ruoyi.common.utils.spi.ServiceLoader;
import com.ruoyi.system.components.movie.spi.GroupCtrlStopper;
import com.ruoyi.system.service.PlayExecutionLogService;

@SPI("BoxGroupCtrlStopper")
public class BoxGroupCtrlStopper implements GroupCtrlStopper {

	@Override
	public void doSetting(String groupId) {
		ServiceLoader.load(GroupCtrlStopper.class, "BothwayGroupCtrlStopper").doSetting(groupId);

		ServiceLoader.load(GroupCtrlStopper.class, "BanRobotGroupCtrlStopper").doSetting(groupId);

		ServiceLoader.load(GroupCtrlStopper.class, "SendFailGroupCtrlStopper").doSetting(groupId);

	}

	@Override
	public boolean isStoped(String playId, String groupId) {
		boolean stop = ServiceLoader.load(GroupCtrlStopper.class, "BothwayGroupCtrlStopper").isStoped(playId, groupId);
		if (stop) {
			return true;
		}
		stop = ServiceLoader.load(GroupCtrlStopper.class, "BanRobotGroupCtrlStopper").isStoped(playId, groupId);
		if (stop) {
			return true;
		}
		return ServiceLoader.load(GroupCtrlStopper.class, "SendFailGroupCtrlStopper").isStoped(playId, groupId);
	}

}
