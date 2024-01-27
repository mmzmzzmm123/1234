package com.ruoyi.system.components.movie;

public interface Player {

	/***
	 * 开始剧本
	 * 
	 * @param playInfo
	 */
	void timeup();

	/***
	 * 发送当前消息
	 * 
	 * @param chatroom
	 * @param messageWapper
	 * @param currentIndex  从1开始
	 */
	void sendOne(String chatroom, int currentIndex, String firstOptSerialNo);

}
