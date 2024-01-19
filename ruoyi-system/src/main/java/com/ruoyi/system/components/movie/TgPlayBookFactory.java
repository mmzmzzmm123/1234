package com.ruoyi.system.components.movie;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.springframework.util.CollectionUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ruoyi.common.core.domain.entity.play.Play;
import com.ruoyi.common.core.domain.entity.play.PlayMessage;
import com.ruoyi.common.core.domain.entity.play.PlayMessagePush;
import com.ruoyi.common.utils.ListTools;
import com.ruoyi.common.utils.spi.SPI;
import com.ruoyi.common.utils.spi.ServiceLoader;
import com.ruoyi.common.utils.spring.SpringUtils;
import com.ruoyi.system.components.movie.spi.MessageSupport;
import com.ruoyi.system.components.movie.spi.PlayRunner;
import com.ruoyi.system.components.movie.spi.RobotSpeakAllocator;
import com.ruoyi.system.mapper.PlayMapper;
import com.ruoyi.system.mapper.PlayMessageMapper;
import com.ruoyi.system.mapper.PlayMessagePushMapper;

@SPI("TgPlayBookFactory")
public class TgPlayBookFactory implements PlayBookFactory {
	@Override
	public PlayBook newInstance(String alias, String playId) {
		return newInstance(alias, SpringUtils.getBean(PlayMapper.class).selectById(playId));
	}

	@Override
	public PlayBook newInstance(String alias, Play play) {
		// 解析剧本
		List<PlayMessageWapper> messageList = parsePlayMessage(play.getId());
		// 解析得到群id
		List<String> chatroomIdList = parseChatroom(play.getId());

		final MessageSupport messageSupport = ServiceLoader.load(MessageSupport.class, "TgMessageSupport");
		final RobotSpeakAllocator robotSpeakAllocator = ServiceLoader.load(RobotSpeakAllocator.class);
		final PlayRunner playRunner = ServiceLoader.load(PlayRunner.class);

		return new PlayBook(play, messageList, chatroomIdList)
				// 底层消息提供者
				.setMessageSupport(messageSupport)
				// 剧本相关事件回调
				.setPlayRunner(playRunner)
				// 发言人分配器
				.setRobotSpeakAllocator(robotSpeakAllocator);
	}

	private List<PlayMessageWapper> parsePlayMessage(String playId) {
		// 消息
		List<PlayMessage> messageList = SpringUtils.getBean(PlayMessageMapper.class)
				.selectList(new QueryWrapper<PlayMessage>().lambda().eq(PlayMessage::getPlayId, playId)
						.eq(PlayMessage::getIsDelete, 0).orderByAsc(PlayMessage::getMessageSort));
		final List<PlayMessageWapper> resultList = new ArrayList<>();
		for (PlayMessage msg : messageList) {
			resultList.add(new PlayMessageWapper(msg));
		}
		return resultList;
	}

	private List<String> parseChatroom(String playId) {
		List<PlayMessagePush> gs = SpringUtils.getBean(PlayMessagePushMapper.class)
				.selectList(new QueryWrapper<PlayMessagePush>().lambda().eq(PlayMessagePush::getPlayId, playId)
						.eq(PlayMessagePush::getRobotAllocationFlag, 1).eq(PlayMessagePush::getRobotPackFlag, 1));
		if (CollectionUtils.isEmpty(gs)) {
			return Collections.emptyList();
		}
		return ListTools.extract(gs, f -> f.getGroupId());
	}

}
