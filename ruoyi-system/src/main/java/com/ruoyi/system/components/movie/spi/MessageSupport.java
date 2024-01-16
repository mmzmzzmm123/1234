package com.ruoyi.system.components.movie.spi;

import com.ruoyi.common.core.domain.R;
import com.ruoyi.common.core.domain.entity.play.PlayMessage;

public interface MessageSupport {

	/**
	 * 发送群消息
	 * 
	 * @param chatroomId
	 * @param robotId
	 * @return
	 */
	R<String> sendChatroomMessage(String chatroomId, String robotId, PlayMessage message, int msgSort);

}
