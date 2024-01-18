package com.ruoyi.system.components.movie.spi.impl;

import com.ruoyi.common.core.domain.R;
import com.ruoyi.common.core.domain.entity.play.PlayMessage;
import com.ruoyi.common.utils.spi.SPI;
import com.ruoyi.system.components.movie.spi.MessageSupport;
import com.ruoyi.system.openapi.OpenApiClient;

import lombok.extern.slf4j.Slf4j;

@SPI("tg")
@Slf4j
public class TgMessageSupport implements MessageSupport {

//	private List<ContentJson> getLikeMsgList(int playId, String chatroomId, int sort) {
//		PlayMessagePush push = App.getBean(PlayMessagePushMapper.class).getOne(playId, chatroomId);
//		PlayMessagePushDetail detail = App.getBean(PlayMessagePushDetailMapper.class).getOne(push.getId(), sort);
//		return JSON.parseArray(detail.getMessageContent(), ContentJson.class);
//	}

	@SuppressWarnings("unchecked")
	@Override
	public R<String> sendChatroomMessage(String chatroomId, String robotId, PlayMessage message, int msgSort) {
//		
//		
//		OpenApiClient.batchRobotSimpInfoByThirdKpTg(data)
		
		
		
		return null;

//		final RobotSendPrivateMessageDTO dto = new RobotSendPrivateMessageDTO();
//		dto.setMerchantId(MerchantTools.getByPlay(message.getPlayId()));
//		dto.setRobotId(robotId);
//		dto.setPlatform("telegram");
//		dto.setFreId(chatroomId);
//		// 好友类型不传默认0：0-好友 1-群
//		dto.setType(1);
//		// 获取混淆后的内容
//		List<ContentJson> messageContentList = getLikeMsgList(message.getPlayId(), chatroomId, msgSort);
//		List<MessageInfo> messages = ListTools.newArrayList();
//		int msgNum = 1;
//
//		// 先把链接提取出来
//		String link = extractLink2005(messageContentList);
//		// 不混淆的 文本
//		link = link + extractNoLikeText2017(messageContentList);
//
//		boolean linkAppend = false;
//		for (ContentJson item : messageContentList) {
//			if (item.getMomentTypeId().intValue() == 2005 || item.getMomentTypeId().intValue() == 2017) {
//				continue;
//			}
//			MessageInfo msg = new MessageInfo();
//			msg.setMsgType(item.getMomentTypeId());
//			msg.setMsgNum(msgNum++);
//			if (msg.getMsgType().intValue() == 2002) {
//				msg.setMsgContent(item.getSMateImgUrl());
//			} else {
//				if (msg.getMsgType().intValue() == 2001 && !linkAppend && !StringUtils.isEmpty(link)) {
//					msg.setMsgContent(item.getSMateContent() + link);
//					linkAppend = true;
//				} else {
//					msg.setMsgContent(item.getSMateContent());
//				}
//			}
//			messages.add(msg);
//		}
//		dto.setMessages(messages);
//		String trace = Ids.getId();
//		try {
//			ResultBody<ChannelOutputDTO<Void>> ret = App.getBean(FnRobotMessageService.class).sendPrivateMessage(dto);
//			log.info("FnRobotMessageService.sendPrivateMessage {} {} {}", trace, JSON.toJSONString(dto),
//					JSON.toJSONString(ret));
//			if (ret != null && ret.getData() != null && !StringUtils.isEmpty(ret.getData().getSerialNo())) {
//				return ResultBody.okT(ret.getData().getSerialNo());
//			}
//			if (ret != null && ret.getData() != null && !StringUtils.isEmpty(ret.getData().getMessage())) {
//				return ResultBody.commonfailed(ret.getData().getMessage());
//			}
//		} catch (Exception e) {
//			log.error("FnRobotMessageService.sendPrivateMessage_error {} {}", trace, dto, e);
//		}
//		return ResultBody.commonfailed("底层消息发送失败，trace: " + trace);
	}

//	private String extractLink2005(List<ContentJson> messageContentList) {
//		StringBuilder sb = new StringBuilder("");
//		for (ContentJson item : messageContentList) {
//			if (item.getMomentTypeId().intValue() == 2005) {
//				sb.append(" ").append(item.getSMateContent());
//			}
//		}
//		if (StringUtils.isEmpty(sb.toString().trim())) {
//			return "";
//		}
//		return sb.toString();
//	}
//
//	private String extractNoLikeText2017(List<ContentJson> messageContentList) {
//		StringBuilder sb = new StringBuilder("");
//		for (ContentJson item : messageContentList) {
//			if (item.getMomentTypeId().intValue() == 2017) {
//				sb.append(" ").append(item.getSMateContent());
//			}
//		}
//		if (StringUtils.isEmpty(sb.toString().trim())) {
//			return "";
//		}
//		return sb.toString();
//	}

}
