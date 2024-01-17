package com.ruoyi.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ruoyi.common.core.domain.entity.play.PlayMessagePushDetail;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface PlayMessagePushDetailMapper extends BaseMapper<PlayMessagePushDetail> {

	List<PlayMessagePushDetail> listByPlayIdStatistics(@Param("playId") String playId);

	void updateFailure(String playId, String chatroomId, int msgSort, String opt, String msg, String robotId);

	void updateSuccess(String playId, String chatroomId, int msgSort, String opt, String robotId);

	void updateSending(String playId, String chatroomId, int msgSort, String opt, String robotId);
}
