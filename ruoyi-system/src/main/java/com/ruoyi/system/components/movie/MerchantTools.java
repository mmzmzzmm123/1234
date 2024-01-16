//package com.ruoyi.system.components.movie;
//
//import com.linkmaster.firegroup.api.pojo.PlayInfo;
//import com.uc.framework.App;
//import com.uc.framework.redis.RedisHandler;
//import com.uc.linkmaster.firegroup.mapper.PlayInfoMapper;
//
//public class MerchantTools {
//
//	public static String getByPlay(int playId) {
//		PlayInfo playInfo = RedisHandler.fromCache("lkmaster:firegroup:getByPlay:" + playId, 60 * 10,
//				() -> App.getBean(PlayInfoMapper.class).selectById(playId), true);
//
//		if (playInfo == null) {
//			return null;
//		}
//		return playInfo.getMerchantId();
//	}
//
//}
