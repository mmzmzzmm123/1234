package com.ruoyi.system.components.movie;

public class ScheduleExecutivePlayer extends AbstractPlayer {

	public ScheduleExecutivePlayer(PlayBook book) {
		super(book);
	}

	@Override
	public void timeup() {
		final PlayBook book = getBook();
		getBook().getPlayRunner().onStart(book.getPlay().getId());
		for (String chatroomId : book.getChatroomList()) {
			// 遍历群， 发送第一条消息
			GlobalIndexContext.setIndex(chatroomId, book.getPlay().getId(), 1);
			// 发送 消息
			sendOne(chatroomId, 1);
		}
	}

}
