package com.ruoyi.system.components.movie;

import com.ruoyi.common.core.domain.entity.play.PlayMessage;

public class PlayMessageWapper {

	private final PlayMessage message;

	public PlayMessageWapper(PlayMessage message) {
		super();
		this.message = message;
	}

	public PlayMessage getMessage() {
		return message;
	}

}
