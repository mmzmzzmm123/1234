package com.ruoyi.system.service.business;

import cn.hutool.core.util.RandomUtil;
import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONArray;
import com.ruoyi.common.core.domain.dto.play.ContentJson;
import com.ruoyi.common.core.domain.entity.play.PlayRobotMessage;
import com.ruoyi.common.utils.spring.SpringUtils;
import com.ruoyi.system.callback.dto.Called1100910010DTO;
import com.ruoyi.system.callback.dto.Called1100910027DTO;
import com.ruoyi.system.callback.dto.CalledDTO;
import com.ruoyi.system.domain.dto.RobotGroupRelationDTO;
import com.ruoyi.system.domain.mongdb.AutoReplyLog;
import com.ruoyi.system.mapper.PlayRobotGroupRelationMapper;
import com.ruoyi.system.openapi.OpenApiClient;
import com.ruoyi.system.openapi.OpenApiResult;
import com.ruoyi.system.openapi.model.input.ThirdTgMessageDTO;
import com.ruoyi.system.openapi.model.input.ThirdTgSendFriendMessageInputDTO;
import com.ruoyi.system.openapi.model.output.TgBaseOutputDTO;
import com.ruoyi.system.service.impl.PlayRobotMessageService;
import com.ruoyi.system.service.impl.SysConfigServiceImpl;
import com.ruoyi.system.utils.MessagePackagingUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * 自动回复Service
 *
 * @author Sharly
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class AutoReplyService {

    private final PlayRobotGroupRelationMapper playRobotGroupRelationMapper;
    private final PlayRobotMessageService playRobotMessageService;
    private final MongoTemplate mongoTemplate;

    /**
     * 收到消息回调
     *
     * @param root
     * @param dto
     */
    public void messageCallback(CalledDTO root, List<Called1100910027DTO> dto) {
        log.info("机器人收到消息私聊消息回调 {} {}", root, dto);
        final String robotId = root.getRobotId();
        final String chatId = CollectionUtils.emptyIfNull(dto)
                .stream().findFirst()
                .map(Called1100910027DTO::getFrom_serial_no)
                .orElse(null);
        RobotGroupRelationDTO groupRobotRelation = playRobotGroupRelationMapper.selectLatestByRobotId(robotId);

        if (StringUtils.isBlank(chatId)) {
            log.info("未获取到发送者会话ID {}", chatId);
            return;
        }

        log.info("查询到机器人所在剧本 {} {} {}", groupRobotRelation, robotId, chatId);
        if (groupRobotRelation == null) {
            return;
        }

        // 查询商家配置的回复话术
        String playId = groupRobotRelation.getPlayId();
        Long messageCount = playRobotMessageService.countByPlayId(playId);

        if (messageCount == null || messageCount <= 0) {
            log.info("剧本Id没有配置自动回复内容,自动回复跳过 {} {}", messageCount, playId);
            return;
        }

        int offset = messageCount == 1 ? 0 :RandomUtil.randomInt(0, messageCount.intValue() - 1);
        PlayRobotMessage message = playRobotMessageService.selectByPlayId(playId, offset);

        Optional.ofNullable(message).ifPresent(it -> this.sendMessage(it, robotId, chatId));
    }


    public void retrySendMessage(CalledDTO root, Called1100910010DTO dto) {
        String optSerNo = root.getOptSerNo();

        AutoReplyLog autoReplyLog = mongoTemplate.findById(optSerNo, AutoReplyLog.class);
        String firstRequestId = Optional.ofNullable(autoReplyLog)
                .map(AutoReplyLog::getFirstRequestId)
                .orElse(optSerNo);
        log.info("自动回复MongoDB数据 {}", JSON.toJSONString(autoReplyLog));

        Update inc = new Update().inc("requestTimes", 1L);
        mongoTemplate.updateFirst(Query.query(Criteria.where("id").is(firstRequestId)), inc, AutoReplyLog.class);

        AutoReplyLog firstReplyLog = mongoTemplate.findById(firstRequestId, AutoReplyLog.class);

        if (firstReplyLog == null) {
            log.info("自动回复最初发送数据为空,不会再次发送");
            return;
        }

        Integer requestTimes = firstReplyLog.getRequestTimes();

        String retryTimes = SpringUtils.getBean(SysConfigServiceImpl.class).selectConfigByKey("auto-reply-retry-times");
        Integer times = Optional.ofNullable(retryTimes).map(Integer::parseInt).orElse(10);
        if (requestTimes >= times) {
            log.info("自动回复重试达到上限次数,不再发送消息 {} {}", requestTimes, times);
            return;
        }
        log.info("自动回复开始重试 {} {} {}", requestTimes, times, JSON.toJSONString(firstReplyLog));
        ThirdTgSendFriendMessageInputDTO input = JSON.parseObject(firstReplyLog.getRequestParams(), ThirdTgSendFriendMessageInputDTO.class);
        OpenApiResult<TgBaseOutputDTO> result = OpenApiClient.sendFriendMessageByThirdKpTg(input);

        // 记录发送日志
        this.saveRequestLog(input, result, firstRequestId);

    }

    public void sendMessage(PlayRobotMessage message, String robotId, String chatId) {
        log.info("自动回复发送消息 {} {} {}", message, robotId, chatId);

        String strMessage = message.getMessageContent();
        List<ContentJson> contentJsons = JSONArray.parseArray(strMessage, ContentJson.class);

        List<ThirdTgMessageDTO> messageInputs = MessagePackagingUtil.packageMessage(contentJsons, false);

        ThirdTgSendFriendMessageInputDTO input = new ThirdTgSendFriendMessageInputDTO();
        input.setTgRobotId(robotId);
        input.setCountryCode("-1");
        input.setFriendSerialNo(chatId);
        input.setMessageData(messageInputs);
        input.setExtendInfo("{\"level\":0}");

        OpenApiResult<TgBaseOutputDTO> result = OpenApiClient.sendFriendMessageByThirdKpTg(input);
        log.info("自动回复OpenApi发送消息 {} {}", JSON.toJSONString(result), JSON.toJSONString(input));

        // 记录发送日志
        this.saveRequestLog(input, result, null);
    }


    private void saveRequestLog(ThirdTgSendFriendMessageInputDTO input,
                                OpenApiResult<TgBaseOutputDTO> result,
                                String firstSerialNo) {
        String optSerialNo = Optional.ofNullable(result)
                .map(OpenApiResult::getData)
                .map(TgBaseOutputDTO::getOptSerNo)
                .orElse("");

        String errorMessage = Optional.ofNullable(result)
                .map(OpenApiResult::getMessage)
                .orElse("");

        AutoReplyLog replyLog = new AutoReplyLog();
        replyLog.setId(optSerialNo);
        replyLog.setRequestParams(JSON.toJSONString(input));
        replyLog.setRequestTimes(0);
        replyLog.setCreateTime(LocalDateTime.now());
        replyLog.setFirstRequestId(firstSerialNo);
        replyLog.setFailMessage(errorMessage);
        mongoTemplate.save(replyLog);
    }
}
