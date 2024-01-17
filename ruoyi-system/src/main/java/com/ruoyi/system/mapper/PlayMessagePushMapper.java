package com.ruoyi.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ruoyi.common.core.domain.entity.play.PlayMessagePush;
import com.ruoyi.system.domain.dto.play.QueryRobotDetailDTO;
import com.ruoyi.system.domain.vo.play.QueryRobotDetailVO;
import com.ruoyi.system.domain.vo.play.RobotStatisticsVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface PlayMessagePushMapper extends BaseMapper<PlayMessagePush> {

	RobotStatisticsVO getRobotStatisticsVO(@Param("playId") String playId);

	List<QueryRobotDetailVO> robotDetails(@Param("dto") QueryRobotDetailDTO dto);

	void updateFailure(String playId, String chatroomId, String msg);

	void updateStarted(String playId, String chatroomId);

	void updateSuccess(String playId, String chatroomId);
}
