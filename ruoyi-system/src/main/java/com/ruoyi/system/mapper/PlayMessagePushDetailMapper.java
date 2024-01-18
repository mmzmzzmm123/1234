package com.ruoyi.system.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ruoyi.common.core.domain.entity.play.PlayMessagePush;
import com.ruoyi.common.core.domain.entity.play.PlayMessagePushDetail;
import com.ruoyi.common.utils.ListTools;
import com.ruoyi.common.utils.Strings;
import com.ruoyi.common.utils.spi.ServiceLoader;
import com.ruoyi.common.utils.spring.SpringUtils;
import com.ruoyi.system.components.movie.spi.impl.DefaultPlayRunner;
import com.ruoyi.system.components.spi.RobotInfoQuery;
import com.ruoyi.system.openapi.model.output.ExtTgSelectRobotInfoListVO;
import org.apache.ibatis.annotations.Param;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import java.util.Date;
import java.util.List;

public interface PlayMessagePushDetailMapper extends BaseMapper<PlayMessagePushDetail> {

	List<PlayMessagePushDetail> listByPlayIdStatistics(@Param("playId") String playId);

	public default PlayMessagePushDetail getOne(String playId, String chatroomId, int sort) {
		PlayMessagePush item = SpringUtils.getBean(PlayMessagePushMapper.class)
				.selectOne(new QueryWrapper<PlayMessagePush>().lambda().eq(PlayMessagePush::getGroupId, chatroomId)
						.eq(PlayMessagePush::getPlayId, playId).last(" limit 1 "));
		if (item == null) {
			return null;
		}
		return selectOne(new QueryWrapper<PlayMessagePushDetail>().lambda()
				.eq(PlayMessagePushDetail::getPlayMsgPushId, item.getId())
				.eq(PlayMessagePushDetail::getMessageSort, sort).last(" limit 1 "));
	}

	@SuppressWarnings("deprecation")
	public default void updateFailure(String playId, String chatroomId, int sort, String opt, String err,
			String robotId) {
		PlayMessagePushDetail detail = getOne(playId, chatroomId, sort);
		if (detail == null) {
			return;
		}
		PlayMessagePushDetail update = new PlayMessagePushDetail();
		update.setSendState(2);
		update.setId(detail.getId());
		if (!StringUtils.isEmpty(opt)) {
			update.setOptSerialNo(opt);
		}
		update.setPushFailReason(Strings.subMax(err, 900));
		if (!StringUtils.isEmpty(robotId)) {
			update.setRobotId(robotId);
			RobotInfoQuery robotInfoQuery = ServiceLoader.load(RobotInfoQuery.class, "TgRobotInfoQuery");
			List<ExtTgSelectRobotInfoListVO> info = robotInfoQuery.listById(ListTools.newArrayList(robotId));
			if (!CollectionUtils.isEmpty(info)) {
				update.setRobotNickname(info.get(0).getUserName());
				update.setRobotImgUrl(info.get(0).getHeadImgUrl());
				update.setRobotAcct(info.get(0).getAccount());
			}
		}
		updateById(update);
		DefaultPlayRunner.info("PlayMessagePushDetail_updateFailure {}", update);
	}

	@SuppressWarnings("deprecation")
	public default void updateSuccess(String playId, String chatroomId, int sort, String opt, String robotId) {
		PlayMessagePushDetail detail = getOne(playId, chatroomId, sort);
		PlayMessagePushDetail update = new PlayMessagePushDetail();
		update.setSendState(1);
		update.setId(detail.getId());
		if (!StringUtils.isEmpty(opt)) {
			update.setOptSerialNo(opt);
		}
		if (!StringUtils.isEmpty(robotId)) {
			update.setRobotId(robotId);
			RobotInfoQuery robotInfoQuery = ServiceLoader.load(RobotInfoQuery.class, "TgRobotInfoQuery");
			List<ExtTgSelectRobotInfoListVO> info = robotInfoQuery.listById(ListTools.newArrayList(robotId));
			if (!CollectionUtils.isEmpty(info)) {
				update.setRobotNickname(info.get(0).getUserName());
				update.setRobotImgUrl(info.get(0).getHeadImgUrl());
				update.setRobotAcct(info.get(0).getAccount());
			}
		}
		updateById(update);
		DefaultPlayRunner.info("PlayMessagePushDetail_updateSuccess {}", update);
	}

	@SuppressWarnings("deprecation")
	public default void updateSending(String playId, String chatroomId, int sort, String opt, String robotId) {
		PlayMessagePushDetail detail = getOne(playId, chatroomId, sort);
		PlayMessagePushDetail update = new PlayMessagePushDetail();
		update.setSendState(3);
		update.setId(detail.getId());
		update.setModifyTime(new Date());
		if (!StringUtils.isEmpty(opt)) {
			update.setOptSerialNo(opt);
		}
		if (!StringUtils.isEmpty(robotId)) {
			update.setRobotId(robotId);
			RobotInfoQuery robotInfoQuery = ServiceLoader.load(RobotInfoQuery.class, "TgRobotInfoQuery");
			List<ExtTgSelectRobotInfoListVO> info = robotInfoQuery.listById(ListTools.newArrayList(robotId));
			if (!CollectionUtils.isEmpty(info)) {
				update.setRobotNickname(info.get(0).getUserName());
				update.setRobotImgUrl(info.get(0).getHeadImgUrl());
				update.setRobotAcct(info.get(0).getAccount());
			}
		}
		updateById(update);

		DefaultPlayRunner.info("PlayMessagePushDetail_updateSending {}", update);

	}
}
