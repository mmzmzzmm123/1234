package com.ruoyi.system.components.prepare.multipack;

import java.util.List;
import com.ruoyi.common.core.domain.entity.play.PlayRobotPackLog;

public interface OnRadioPackMonitor {

	/**
	 * 一组 回调 完成
	 * 
	 * @param pack
	 */
	void onRadioPackSucceed(List<PlayRobotPackLog> succesList);

	/**
	 * 一组回调失败（只要有一个回调失败就是失败的）
	 * 
	 * @param pack
	 * @param error
	 */
	void onRadioPackFailed(List<PlayRobotPackLog> failList);
}
