package com.ruoyi.system.components.movie.spi;

public interface RobotSpeakAllocator {

	String allocate(String nick, String chatroomId, String playId);
}
