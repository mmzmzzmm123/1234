package com.ruoyi.system.components.movie.spi;

/***
 * 群维度 风控止损
 * 
 * @author Administrator
 *
 */
public interface GroupCtrlStopper {

	void doSetting(String groupId);

	boolean isStoped(String playId, String groupId);

}
