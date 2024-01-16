package com.ruoyi.system.components.multipack;

public interface OnPackMonitor {

	/**
	 * 单个 回调 执行 成功
	 * 
	 * @param pack
	 */
	void onPackSucceed(Pack pack);

	/**
	 * 单个回调执行失败
	 * 
	 * @param pack
	 * @param error
	 */
	void onPackFailed(Pack pack, String error);
}
