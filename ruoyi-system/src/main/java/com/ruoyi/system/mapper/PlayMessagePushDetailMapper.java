package com.ruoyi.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ruoyi.common.core.domain.entity.play.PlayMessagePushDetail;

public interface PlayMessagePushDetailMapper extends BaseMapper<PlayMessagePushDetail> {

	void updateFailure(String playId, String chatroomId, int msgSort, String opt, String msg, String robotId);

	void updateSuccess(String playId, String chatroomId, int msgSort, String opt, String robotId);

	void updateSending(String playId, String chatroomId, int msgSort, String opt, String robotId);
}
