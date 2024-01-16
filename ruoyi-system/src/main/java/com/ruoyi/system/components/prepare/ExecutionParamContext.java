package com.ruoyi.system.components.prepare;

import java.util.List;
import com.ruoyi.common.core.domain.entity.play.PlayGroupPack;
import com.ruoyi.common.core.domain.entity.play.PlayMessagePush;
import com.ruoyi.common.core.domain.entity.play.PlayMessagePushDetail;
import com.ruoyi.common.core.domain.entity.play.PlayRobotPack;
import com.ruoyi.common.enums.PlayLogTyper;
import com.ruoyi.common.utils.spring.SpringUtils;
import com.ruoyi.system.domain.mongdb.PlayExecutionLog;
import com.ruoyi.system.service.PlayExecutionLogService;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class ExecutionParamContext {

	private final String chatroomId;

	private final PlayMessagePush messagePush;

	private final List<PlayMessagePushDetail> pushDetails;

	private final List<PlayGroupPack> playGroupPackList;

	private final List<PlayRobotPack> playRobotPackList;

	public void log(ExecutionParamContext context, PlayLogTyper logTyper, boolean success, String content) {
		PlayExecutionLog log = new PlayExecutionLog();
		log.setGroupId(context.getChatroomId());
		log.setPlayId(context.getMessagePush().getPlayId());
		log.setState(success ? 0 : 1);
		log.setType(logTyper);
		log.setContent(content);
		SpringUtils.getBean(PlayExecutionLogService.class).saveLog(log);
	}

}
