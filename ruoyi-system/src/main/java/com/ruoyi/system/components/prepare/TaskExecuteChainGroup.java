package com.ruoyi.system.components.prepare;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson2.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ruoyi.common.core.domain.entity.play.Play;
import com.ruoyi.common.core.domain.entity.play.PlayMessagePush;
import com.ruoyi.common.core.domain.entity.play.PlayMessagePushDetail;
import com.ruoyi.common.core.domain.entity.play.PlayRobotPack;
import com.ruoyi.common.utils.ListTools;
import com.ruoyi.common.utils.spring.SpringUtils;
import com.ruoyi.system.mapper.PlayMapper;
import com.ruoyi.system.mapper.PlayMessagePushDetailMapper;
import com.ruoyi.system.mapper.PlayMessagePushMapper;
import com.ruoyi.system.mapper.PlayRobotPackMapper;

import lombok.extern.slf4j.Slf4j;
import springfox.documentation.spring.web.json.Json;

@Component
@Slf4j
public class TaskExecuteChainGroup {

	@Autowired
	private PlayPrepareTaskExecuteChain executeChain;

	@Autowired
	private PlayMessagePushMapper playMessagePushMapper;
	@Autowired
	private PlayRobotPackMapper playRobotPackMapper;

	@Autowired
	private PlayMapper playMapper;

	public void execute(String playId) {
		log.info("TaskExecuteChainGroup_execute {}", playId);
		// 查询剧本
		Play play = playMapper.selectById(playId);
		// 查询 详情
		List<PlayMessagePush> messages = playMessagePushMapper
				.selectList(new QueryWrapper<PlayMessagePush>().lambda().eq(PlayMessagePush::getPlayId, playId));

		Map<String, List<PlayMessagePush>> msgMap = ListTools.group(messages, f -> f.getGroupId());

		List<PlayRobotPack> robotPackList = playRobotPackMapper
				.selectList(new QueryWrapper<PlayRobotPack>().lambda().eq(PlayRobotPack::getPlayId, playId));
//		List<PlayGroupPack> playGroupPackList = playGroupPackMapper
//				.selectList(new QueryWrapper<PlayGroupPack>().lambda().eq(PlayGroupPack::getPlayId, playId));
		// 每个群 ， 一个执行器链条
		for (String chatroomId : ListTools.extract(messages, f -> f.getGroupId())) {
			try {
				executeChain.reset();
				PlayMessagePush push = msgMap.get(chatroomId).get(0);
				List<PlayMessagePushDetail> details = SpringUtils.getBean(PlayMessagePushDetailMapper.class)
						.selectList(new QueryWrapper<PlayMessagePushDetail>().lambda()
								.eq(PlayMessagePushDetail::getPlayMsgPushId, push.getId()));
				executeChain.doExecute(new ExecutionParamContext(play, chatroomId, push, details, robotPackList));
			} catch (Exception e) {
				log.error("TaskExecuteChainGroup_doExecute {} {}", playId, chatroomId, e);
			}
		}
	}

}
