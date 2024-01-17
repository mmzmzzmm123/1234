package com.ruoyi.system.components.prepare.multipack;

import com.ruoyi.common.core.domain.entity.play.PlayRobotPackLog;

public interface OnPackMonitor {

	/**
	 * 单个 回调 执行 成功
	 * 
	 * @param pack
	 */
	void onPackSucceed(PlayRobotPackLog log);

	/**
	 * 单个回调执行失败
	 * 
	 * @param pack
	 * @param error
	 */
	void onPackFailed(PlayRobotPackLog log, String error);
}
