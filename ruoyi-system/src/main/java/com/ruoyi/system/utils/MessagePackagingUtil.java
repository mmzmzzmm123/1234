package com.ruoyi.system.utils;

import com.alibaba.fastjson2.JSONObject;
import com.ruoyi.common.constant.PlayConstants;
import com.ruoyi.common.core.domain.dto.play.ContentJson;
import com.ruoyi.common.core.domain.entity.robot.Robot;
import com.ruoyi.common.utils.ListTools;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.spi.ServiceLoader;
import com.ruoyi.system.components.movie.spi.impl.PreRobotSpeakAllocator;
import com.ruoyi.system.components.spi.RobotInfoQuery;
import com.ruoyi.system.domain.dto.out.InsertEventOutputDTO;
import com.ruoyi.system.openapi.OpenApiClient;
import com.ruoyi.system.openapi.OpenApiResult;
import com.ruoyi.system.openapi.model.input.ThirdTgInsertEventInputDTO;
import com.ruoyi.system.openapi.model.input.ThirdTgMessageDTO;
import com.ruoyi.system.openapi.model.output.TgBaseOutputDTO;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Optional;

/**
 * @author Sharly
 */
public class MessagePackagingUtil {

    public static List<ThirdTgMessageDTO> packageMessage(List<ContentJson> messageContentList, boolean enableConfound) {

        int msgNum = 1;
        // 先把链接提取出来
        // 提取@人
        String link = "";
        link = extractLink2005(messageContentList, enableConfound);
        // 不混淆的 文本
        link = link + extractNoLikeText2017(messageContentList);
        link = link + extractNoLikeText2018(messageContentList);
        List<ThirdTgMessageDTO> dataList = ListTools.newArrayList();
        boolean linkAppend = false;
        boolean hadImg = false;
        final ContentJson contentJson = messageContentList.stream().filter(it -> it.getMomentTypeId() == 2002).findFirst().orElse(null);
        if(contentJson != null){
            hadImg = true;
        }
        if(hadImg){
            ThirdTgMessageDTO dto = new ThirdTgMessageDTO();
            dto.setMsgType(2002);
            dto.setMsgNum(msgNum);
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
            dto.setMsgNum(msgNum);
            // 消息类型（2001 文本消息, 2002 图片 2003 语音（目前仅支持.ogg和 .mp3文件后缀） 2004 视频 2005 2007
            // emoji表情 链接 2010 文件 2015 贴图消息 2034 超文本链接 2035 postbot消息）

            // 消息类型: 2001 文字 2002 图片 2003 语音(只支持amr格式) 2004 视频 2005 链接 2006 好友名片 2010 文件
            // 2013 小程序 2016 音乐 201姓名 2017接粉号 2018自定义
            dto.setMsgContent(extractLink2001(messageContentList) + " " + link);
            dataList.add(dto);
        }
        return dataList;
    }

    private static void insetAt(List<ContentJson> messageContentList, String at) {
        if (StringUtils.isEmpty(at)) {
            return;
        }
        ContentJson json = new ContentJson();
        json.setSMateContent("@" + at);
        json.setMomentTypeId(2001);
        messageContentList.add(0, json);
    }

    private static String extractAt(String playId, String chatroomId, String callRobotNickname) {
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

    private static String extractLink2001(List<ContentJson> messageContentList) {
        StringBuilder sb = new StringBuilder();
        for (ContentJson item : messageContentList) {
            if (item.getMomentTypeId() == 2001) {
                sb.append(" ").append(item.getSMateContent());
            }
        }
        if (StringUtils.isEmpty(sb.toString().trim())) {
            return "";
        }
        return sb.toString();
    }

    private static String extractLink2005(List<ContentJson> messageContentList, boolean enableConfound) {

        StringBuilder sb = new StringBuilder();
        for (ContentJson item : messageContentList) {
            if (item.getMomentTypeId() == 2005) {
                if (enableConfound) {

                    // 如果混淆失败 会发送原链接
                    sb.append(" [").append(MessagePackagingUtil.confoundLink(item.getSMateContent())).append("]");
                    continue;
                }
                sb.append(" [").append(item.getSMateContent()).append("]");
            }
        }
        if (StringUtils.isEmpty(sb.toString().trim())) {
            return "";
        }
        return sb.toString();
    }

    private static String extractNoLikeText2017(List<ContentJson> messageContentList) {
        StringBuilder sb = new StringBuilder();
        for (ContentJson item : messageContentList) {
            if (item.getMomentTypeId() == 2017) {
                sb.append(" [").append(item.getSMateContent()).append("]");
            }
        }
        if (StringUtils.isEmpty(sb.toString().trim())) {
            return "";
        }
        return sb.toString();
    }

    private static String extractNoLikeText2018(List<ContentJson> messageContentList) {
        StringBuilder sb = new StringBuilder();
        for (ContentJson item : messageContentList) {
            if (item.getMomentTypeId() == 2018) {
                sb.append(" ").append(item.getSMateContent());
            }
        }
        if (StringUtils.isEmpty(sb.toString().trim())) {
            return "";
        }
        return sb.toString();
    }


    public static String confoundLink(String activityLink) {
        ThirdTgInsertEventInputDTO dto = new ThirdTgInsertEventInputDTO();
        dto.setActivityRule(activityLink);
        dto.setUnioUserNo(PlayConstants.unioUserNo);
        //域名分组传10
        dto.setDomainGroupTypeId(PlayConstants.domainGroupTypeId);
        //10：跳转落地URL 20：直接跳转原始URL
        dto.setRedirectWay(PlayConstants.redirectWa);

        OpenApiResult<TgBaseOutputDTO> result = OpenApiClient.insertEventByThirdKpTg(dto);

        // 如果混淆结果为空,则发送原链接
        return Optional.ofNullable(result)
                .map(OpenApiResult::getData)
                .map(TgBaseOutputDTO::getData)
                .map(Object::toString)
                .map(it -> JSONObject.parseObject(it, InsertEventOutputDTO.class))
                .map(InsertEventOutputDTO::getActivityLinkAddress)
                .orElse(activityLink);
    }
}
