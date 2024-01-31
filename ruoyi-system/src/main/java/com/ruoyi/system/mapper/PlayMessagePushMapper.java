package com.ruoyi.system.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ruoyi.common.core.domain.entity.play.PlayMessagePush;
import com.ruoyi.system.domain.dto.play.QueryRobotAccountDetailDTO;
import com.ruoyi.system.domain.dto.play.QueryRobotDetailDTO;
import com.ruoyi.system.domain.vo.play.QueryRobotAccountDetailVO;
import com.ruoyi.system.domain.vo.play.QueryRobotDetailVO;
import com.ruoyi.system.domain.vo.play.RobotStatisticsVO;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

public interface PlayMessagePushMapper extends BaseMapper<PlayMessagePush> {

	RobotStatisticsVO getRobotStatisticsVO(@Param("playId") String playId);

	List<QueryRobotDetailVO> robotDetails(@Param("dto") QueryRobotDetailDTO dto);
	List<QueryRobotAccountDetailVO> robotAccountDetails(Page<QueryRobotAccountDetailVO> page, @Param("dto") QueryRobotAccountDetailVO dto);

	public default PlayMessagePush getOne(String playId, String chatroomId) {
		return selectOne(new QueryWrapper<PlayMessagePush>().lambda().eq(PlayMessagePush::getGroupId, chatroomId)
				.eq(PlayMessagePush::getPlayId, playId).last(" limit 1 "));
	}

	public default void updateSuccess(String playId, String chatroomId) {
		PlayMessagePush item = getOne(playId, chatroomId);
		if (item == null) {
			return;
		}
		// 推送状态:1:待发送 2:进行中 3:已结束 4:人工暂停 5:推送失败 6:取消
		PlayMessagePush update = new PlayMessagePush();
		update.setPushState(3);
		update.setId(item.getId());
		updateById(update);
	}

	public default void updateFailure(String playId, String chatroomId, String err) {
		PlayMessagePush item = getOne(playId, chatroomId);
		if (item == null) {
			return;
		}
		PlayMessagePush update = new PlayMessagePush();
		update.setPushState(5);
		update.setId(item.getId());
		update.setPauseReason(err);
		updateById(update);
	}

	public default void updateStarted(String playId, String chatroomId) {
		PlayMessagePush item = getOne(playId, chatroomId);
		if (item == null) {
			return;
		}
		PlayMessagePush update = new PlayMessagePush();
		update.setPushState(2);
		update.setPushTime(new Date());
		update.setId(item.getId());
		updateById(update);
	}
}
