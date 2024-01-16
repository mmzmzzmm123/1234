package com.ruoyi.system.components.movie.spi;

import com.ruoyi.common.core.domain.entity.play.PlayMessage;

public interface ProgressPuller {

	public void nextPull(PlayMessage message, String chatroomId);

}
