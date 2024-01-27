package com.ruoyi.system.components.movie.spi.impl;

import com.alibaba.fastjson2.JSON;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.common.core.domain.dto.play.ContentJson;
import com.ruoyi.common.core.domain.entity.play.PlayMessage;
import com.ruoyi.common.core.domain.entity.play.PlayMessagePushDetail;
import com.ruoyi.common.core.domain.entity.robot.Robot;
import com.ruoyi.common.utils.ListTools;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.spi.SPI;
import com.ruoyi.common.utils.spi.ServiceLoader;
import com.ruoyi.common.utils.spring.SpringUtils;
import com.ruoyi.system.components.movie.spi.MessageSupport;
import com.ruoyi.system.components.spi.RobotInfoQuery;
import com.ruoyi.system.mapper.GroupInfoMapper;
import com.ruoyi.system.mapper.PlayMessagePushDetailMapper;
import com.ruoyi.system.openapi.OpenApiClient;
import com.ruoyi.system.openapi.OpenApiResult;
import com.ruoyi.system.openapi.model.input.ThirdTgMessageDTO;
import com.ruoyi.system.openapi.model.input.ThirdTgSendGroupMessageInputDTO;
import com.ruoyi.system.openapi.model.output.TgBaseOutputDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.CollectionUtils;

import java.util.List;

@SPI("TgMessageSupport")
@Slf4j
public class TgMessageSupport implements MessageSupport {

	private PlayMessagePushDetail getDetail(String playId, String chatroomId, int sort) {
		return SpringUtils.getBean(PlayMessagePushDetailMapper.class).getOne(playId, chatroomId, sort);
	}

	private List<ThirdTgMessageDTO> createMsgData(PlayMessage message, String chatroomId, int msgSort) {
		final PlayMessagePushDetail detail = getDetail(message.getPlayId(), chatroomId, msgSort);
		// 获取混淆后的内容
		List<ContentJson> messageContentList = JSON.parseArray(detail.getMessageContent(), ContentJson.class);

		int msgNum = 1;
		// 先把链接提取出来
		// 提取@人
		String link = "";
		String atUserName = extractAt(message.getPlayId(), chatroomId, message.getCallRobotNickname());
		if (!StringUtils.isEmpty(atUserName)) {
			link = "@" + atUserName +" ";
		}
		link = extractLink2005(messageContentList);
		// 不混淆的 文本
		link = link + extractNoLikeText2017(messageContentList);
		link = link + extractNoLikeText2018(messageContentList);
		List<ThirdTgMessageDTO> dataList = ListTools.newArrayList();
		boolean linkAppend = false;
		boolean hadImg = false;
		final ContentJson contentJson = messageContentList.stream().filter(it -> {return it.getMomentTypeId().intValue() == 2002;}).findFirst().orElse(null);
		if(contentJson != null){
			hadImg = true;
		}
		if(hadImg){
			ThirdTgMessageDTO dto = new ThirdTgMessageDTO();
			dto.setMsgType(2002);
			dto.setMsgNum(msgNum++);
			// 消息类型（2001 文本消息, 2002 图片 2003 语音（目前仅支持.ogg和 .mp3文件后缀） 2004 视频 2005 2007
			// emoji表情 链接 2010 文件 2015 贴图消息 2034 超文本链接 2035 postbot消息）

			// 消息类型: 2001 文字 2002 图片 2003 语音(只支持amr格式) 2004 视频 2005 链接 2006 好友名片 2010 文件
			// 2013 小程序 2016 音乐 201姓名 2017接粉号 2018自定义
			dto.setMsgContent(contentJson.getSMateImgUrl());
			dto.setMsgTitle(extractLink2001(messageContentList) + " " + link);
			dataList.add(dto);
		}
		else{
			ThirdTgMessageDTO dto = new ThirdTgMessageDTO();
			dto.setMsgType(2001);
			dto.setMsgNum(msgNum++);
			// 消息类型（2001 文本消息, 2002 图片 2003 语音（目前仅支持.ogg和 .mp3文件后缀） 2004 视频 2005 2007
			// emoji表情 链接 2010 文件 2015 贴图消息 2034 超文本链接 2035 postbot消息）

			// 消息类型: 2001 文字 2002 图片 2003 语音(只支持amr格式) 2004 视频 2005 链接 2006 好友名片 2010 文件
			// 2013 小程序 2016 音乐 201姓名 2017接粉号 2018自定义
			dto.setMsgContent(extractLink2001(messageContentList) + " " + link);
			dataList.add(dto);
		}
//		for (ContentJson msg : messageContentList) {
//			if (msg.getMomentTypeId().intValue() == 2005 || msg.getMomentTypeId().intValue() == 2017
//					|| msg.getMomentTypeId().intValue() == 2018) {
//				continue;
//			}
//			ThirdTgMessageDTO dto = new ThirdTgMessageDTO();
//			dto.setMsgType(msg.getMomentTypeId());
//			dto.setMsgNum(msgNum++);
//			// 消息类型（2001 文本消息, 2002 图片 2003 语音（目前仅支持.ogg和 .mp3文件后缀） 2004 视频 2005 2007
//			// emoji表情 链接 2010 文件 2015 贴图消息 2034 超文本链接 2035 postbot消息）
//
//			// 消息类型: 2001 文字 2002 图片 2003 语音(只支持amr格式) 2004 视频 2005 链接 2006 好友名片 2010 文件
//			// 2013 小程序 2016 音乐 201姓名 2017接粉号 2018自定义
//			if (msg.getMomentTypeId().intValue() == 2002) {
//				dto.setMsgContent(msg.getSMateImgUrl());
//			} else {
//				if (dto.getMsgType().intValue() == 2001 && !linkAppend && !StringUtils.isEmpty(link)) {
//					dto.setMsgContent(msg.getSMateContent() + link);
//					linkAppend = true;
//				} else {
//					dto.setMsgContent(msg.getSMateContent());
//				}
//			}
//			dataList.add(dto);
//		}
		return dataList;
	}

	private void insetAt(List<ContentJson> messageContentList, String at) {
		if (StringUtils.isEmpty(at)) {
			return;
		}
		ContentJson json = new ContentJson();
		json.setSMateContent("@" + at);
		json.setMomentTypeId(2001);
		messageContentList.add(0, json);
	}

	private String extractAt(String playId, String chatroomId, String callRobotNickname) {
		if (!StringUtils.isEmpty(callRobotNickname)) {
			// 根据发言人 查询
			String robot = PreRobotSpeakAllocator.Cache.get(playId, chatroomId, callRobotNickname);
			if (!StringUtils.isEmpty(robot)) {
				// 查询这个robot信息
				List<Robot> vos = ServiceLoader.load(RobotInfoQuery.class, "TgRobotInfoQuery")
						.listById(ListTools.newArrayList(robot));
				if (!CollectionUtils.isEmpty(vos)) {
					return vos.get(0).getUserName();
				}
			}
		}
		return null;
	}

	@Override
	public R<String> sendChatroomMessage(String chatroomId, String robotId, PlayMessage message, int msgSort, String firstOprSerialNo) {
		ThirdTgSendGroupMessageInputDTO data = new ThirdTgSendGroupMessageInputDTO();
		final String groupId = SpringUtils.getBean(GroupInfoMapper.class).selectById(chatroomId).getGroupSerialNo();
		data.setChatroomSerialNo(groupId);
		data.setTgRobotId(robotId);
		data.setMessageData(createMsgData(message, chatroomId, msgSort));
		data.setExtendInfo("{\"level\":0}");
		data.setFirstOptSerialNo(firstOprSerialNo);
		@SuppressWarnings("rawtypes")
		OpenApiResult<TgBaseOutputDTO> ret = OpenApiClient.sendGroupMessageByThirdKpTg(data);
		log.info("sendGroupMessageByThirdKpTg {} {}", data, ret);

		if (ret.isSuccess() && ret.getData() != null && !StringUtils.isEmpty(ret.getData().getOptSerNo())) {
			return R.ok(ret.getData().getOptSerNo());
		}
		return R.fail(ret.getMessage());
	}

	private String extractLink2001(List<ContentJson> messageContentList) {
		StringBuilder sb = new StringBuilder("");
		for (ContentJson item : messageContentList) {
			if (item.getMomentTypeId().intValue() == 2001) {
				sb.append(" ").append(item.getSMateContent());
			}
		}
		if (StringUtils.isEmpty(sb.toString().trim())) {
			return "";
		}
		return sb.toString();
	}

	private String extractLink2005(List<ContentJson> messageContentList) {
		StringBuilder sb = new StringBuilder("");
		for (ContentJson item : messageContentList) {
			if (item.getMomentTypeId().intValue() == 2005) {
				sb.append(" ").append(item.getSMateContent());
			}
		}
		if (StringUtils.isEmpty(sb.toString().trim())) {
			return "";
		}
		return sb.toString();
	}

	private String extractNoLikeText2017(List<ContentJson> messageContentList) {
		StringBuilder sb = new StringBuilder("");
		for (ContentJson item : messageContentList) {
			if (item.getMomentTypeId().intValue() == 2017) {
				sb.append(" ").append(item.getSMateContent());
			}
		}
		if (StringUtils.isEmpty(sb.toString().trim())) {
			return "";
		}
		return sb.toString();
	}

	private String extractNoLikeText2018(List<ContentJson> messageContentList) {
		StringBuilder sb = new StringBuilder("");
		for (ContentJson item : messageContentList) {
			if (item.getMomentTypeId().intValue() == 2018) {
				sb.append(" ").append(item.getSMateContent());
			}
		}
		if (StringUtils.isEmpty(sb.toString().trim())) {
			return "";
		}
		return sb.toString();
	}

}
