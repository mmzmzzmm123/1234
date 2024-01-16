package com.ruoyi.system.components.multipack;

import java.util.List;

public interface OnRadioPackMonitor {

	/**
	 * 单个 回调 执行 成功
	 * 
	 * @param pack
	 */
	void onRadioPackSucceed(List<Pack> pack);

	/**
	 * 单个回调执行失败
	 * 
	 * @param pack
	 * @param error
	 */
	void onRadioPackFailed(Pack pack, String error);
}
