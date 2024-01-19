package com.ruoyi.system.components;

import com.ruoyi.common.core.domain.entity.play.Play;
import com.ruoyi.common.utils.spring.SpringUtils;
import com.ruoyi.system.mapper.PlayMapper;

public class PlayInfoTools {

	public static boolean isDeleted(String playId) {
		Play playInfo = SpringUtils.getBean(PlayMapper.class).selectById(playId);
		if (playInfo == null) {
			return true;
		}
		if (playInfo.getIsDelete().intValue() == 1 || playInfo.getIsDelete().intValue() == 4) {
			return true;
		}
		return false;
	}

	public static boolean failStoped(String playId) {
		int playErrType = SpringUtils.getBean(PlayMapper.class).selectById(playId).convertSendMechanismStr()
				.getSendErrorType();
		boolean failStoped = (playErrType == 2) ? true : false;
		return failStoped;
	}

	public static boolean failStoped(Play play) {
		int playErrType = play.convertSendMechanismStr().getSendErrorType();
		boolean failStoped = (playErrType == 2) ? true : false;
		return failStoped;
	}

}
