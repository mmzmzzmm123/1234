package com.ruoyi.system.components.movie;

import com.ruoyi.common.core.domain.entity.play.Play;

public interface PlayBookFactory {
	PlayBook newInstance(String alias, Play play);

	PlayBook newInstance(String alias, String playId);
}
