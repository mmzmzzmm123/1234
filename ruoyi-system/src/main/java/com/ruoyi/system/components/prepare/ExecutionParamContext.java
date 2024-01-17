package com.ruoyi.system.components.prepare;

import java.util.List;
import com.ruoyi.common.core.domain.entity.play.Play;
import com.ruoyi.common.core.domain.entity.play.PlayMessagePush;
import com.ruoyi.common.core.domain.entity.play.PlayMessagePushDetail;
import com.ruoyi.common.core.domain.entity.play.PlayRobotPack;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class ExecutionParamContext {

	private final Play play;

	private final String chatroomId;

	private final PlayMessagePush messagePush;

	private final List<PlayMessagePushDetail> pushDetails;

//	private final List<PlayGroupPack> playGroupPackList;

	private final List<PlayRobotPack> playRobotPackList;
}
