package com.ruoyi.system.components;

import com.ruoyi.common.core.domain.entity.play.Play;
import com.ruoyi.common.utils.spring.SpringUtils;
import com.ruoyi.system.mapper.PlayMapper;

import java.util.Objects;

public class PlayInfoTools {

	public static boolean isDeletedOrCancel(String playId) {
		Play playInfo = SpringUtils.getBean(PlayMapper.class).selectById(playId);
		if (playInfo == null) {
			return true;
		}
		if (playInfo.getIsDelete() == 1 || Objects.equals(playInfo.getState(), 4)) {
			return true;
		}
		return false;
	}

	public static boolean failStoped(String playId) {
		int playErrType = SpringUtils.getBean(PlayMapper.class).selectById(playId).convertSendMechanismStr()
				.getSendErrorType();
        return playErrType == 2;
	}

	public static boolean failStoped(Play play) {
		int playErrType = play.convertSendMechanismStr().getSendErrorType();
        return playErrType == 2;
	}

}
