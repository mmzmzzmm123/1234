package com.ruoyi.system.components.movie.spi;

public interface PlayRunner {

	/**
	 * 单个消息发送失败
	 * 
	 * @param playInfo
	 * @param msg
	 * @param chatroomId
	 * @param robotId
	 * @param e
	 */
	void onItemFailure(String opt, String playId, String msg, String chatroomId, String robotId, int msgSort,
			Throwable e);

	/**
	 * 单个消息发送中
	 * 
	 * @param playInfo
	 * @param msg
	 * @param chatroomId
	 * @param robotId
	 * @param e
	 */
	void onItemSending(String opt, String playId, String chatroomId, String robotId, int msgSort);

	/**
	 * 单个消息发送成功
	 * 
	 * @param playId
	 * @param chatroomId
	 * @param robotId
	 * @param msgSort
	 */
	void onItemSendSuccess(String opt, String playId, String chatroomId, int msgSort, String robotId);

	/**
	 * 某个群 完成
	 * 
	 * @param playInfo
	 */
	void onFinish(String playId, String chatroomId);

	/**
	 * title: 剧本开始
	 * 
	 * @param playId
	 */
	void onStart(String playId);
}
