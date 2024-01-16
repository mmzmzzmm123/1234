package com.ruoyi.system.components.movie;

import java.util.List;
import com.ruoyi.common.core.domain.entity.play.Play;
import com.ruoyi.system.components.movie.spi.MessageSupport;
import com.ruoyi.system.components.movie.spi.PlayRunner;
import com.ruoyi.system.components.movie.spi.RobotSpeakAllocator;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class PlayBook {

	private final Play play;

	private final List<PlayMessageWapper> messageList;

	private final List<String> chatroomList;

	private MessageSupport messageSupport;

	private RobotSpeakAllocator robotSpeakAllocator;

	private PlayRunner playRunner;

	public PlayMessageWapper current(int sort) {
		return messageList.get(sort - 1);
	}
}
