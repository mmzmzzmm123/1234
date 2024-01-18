package com.ruoyi.system.components.movie;

/**
 * 手动剧本
 * 
 * @author Administrator
 *
 */
public class ManualExecutivePlayer extends AbstractPlayer {

	public ManualExecutivePlayer(PlayBook book) {
		super(book);
	}

	@Override
	public void timeup() {
		throw new UnsupportedOperationException();
	}

	public void startGroup(String chatroomId) {
		final PlayBook book = getBook();
		getBook().getPlayRunner().onStart(book.getPlay().getId());
		// 遍历群， 发送第一条消息
		GlobalIndexContext.setIndex(chatroomId, book.getPlay().getId(), 1);
		// 发送 消息
		sendOne(chatroomId, 1);
	}

}
