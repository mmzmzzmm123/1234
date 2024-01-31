package com.ruoyi.system.service.business;

import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONArray;
import com.ruoyi.common.core.domain.dto.play.ContentJson;
import com.ruoyi.common.core.domain.entity.play.PlayRobotMessage;
import com.ruoyi.common.core.redis.RedisLock;
import com.ruoyi.common.enums.PlayLogTyper;
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
import com.ruoyi.system.service.PlayExecutionLogService;
import com.ruoyi.system.service.impl.PlayRobotMessageService;
import com.ruoyi.system.service.impl.SysConfigServiceImpl;
import com.ruoyi.system.utils.MessagePackagingUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.redisson.api.RLock;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

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
    private final RedisLock redisLock;

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

        String playId = groupRobotRelation.getPlayId();
        String groupId = groupRobotRelation.getGroupId();

        // 记录发送消息日志
        this.saveAutoReplyMessage(playId, robotId, groupId, dto);

        for (Called1100910027DTO data : dto) {

            // 同一个用户多次消息仅回复一次
            String lockKey = "auto-reply-user-lock:" + data.getFrom_serial_no() + ":" + data.getTo_serial_no();
            RLock lock = redisLock.getRLock(lockKey);
            if (lock.isLocked()) {
                log.info("改用户在10分钟内收到多条消息,自动跳过 {} {}", root.getOptSerNo(), data);
                continue;
            }
            lock.lock(10, TimeUnit.MINUTES);

            // 查询商家配置的回复话术
            Long messageCount = playRobotMessageService.countByPlayId(playId);
            if (messageCount == null || messageCount <= 0) {
                log.info("剧本Id没有配置自动回复内容,自动回复跳过 {} {} {}", messageCount, playId, root.getOptSerNo());
                return;
            }

            int offset = messageCount == 1 ? 0 :RandomUtil.randomInt(0, messageCount.intValue() - 1);
            PlayRobotMessage message = playRobotMessageService.selectByPlayId(playId, offset);

            Optional.ofNullable(message).ifPresent(it -> this.sendMessage(it, robotId, data.getFrom_serial_no(), groupId));
        }
    }


    public void retrySendMessage(List<AutoReplyLog> firstReplyLogs) {

        for (AutoReplyLog firstReplyLog : firstReplyLogs) {
            String firstRequestId = firstReplyLog.getId();
            Update inc = new Update().inc("requestTimes", 1L);
            mongoTemplate.updateFirst(Query.query(Criteria.where("id").is(firstRequestId)), inc, AutoReplyLog.class);

            Integer requestTimes = firstReplyLog.getRequestTimes();

            log.info("自动回复开始重试 {} {}", requestTimes, JSON.toJSONString(firstReplyLog));
            ThirdTgSendFriendMessageInputDTO input = JSON.parseObject(firstReplyLog.getRequestParams(), ThirdTgSendFriendMessageInputDTO.class);
            OpenApiResult<TgBaseOutputDTO> result = OpenApiClient.sendFriendMessageByThirdKpTg(input);

            // 记录发送日志
            this.saveRequestLog(input, result, firstRequestId, firstReplyLog.getPlayId(), firstReplyLog.getGroupId());
        }



    }

    public void sendMessage(PlayRobotMessage message, String robotId, String chatId, String groupId) {
        log.info("自动回复发送消息 {} {} {} {}", message, robotId, chatId, groupId);

        String strMessage = message.getMessageContent();
        List<ContentJson> contentJsons = JSONArray.parseArray(strMessage, ContentJson.class);

        List<ThirdTgMessageDTO> messageInputs = MessagePackagingUtil.packageMessage(contentJsons, false);

        ThirdTgSendFriendMessageInputDTO input = new ThirdTgSendFriendMessageInputDTO();
        input.setTgRobotId(robotId);
        input.setCountryCode("-1");
        input.setFriendSerialNo(chatId);
        input.setMessageData(messageInputs);
        input.setExtendInfo("{\"level\":1}");

        OpenApiResult<TgBaseOutputDTO> result = OpenApiClient.sendFriendMessageByThirdKpTg(input);
        log.info("自动回复OpenApi发送消息 {} {}", JSON.toJSONString(result), JSON.toJSONString(input));

        this.saveAutoReplyResultLog(message.getPlayId(),groupId, robotId, chatId, result);

        // 记录发送日志
        this.saveRequestLog(input, result, null, message.getPlayId(), groupId);
    }


    private void saveRequestLog(ThirdTgSendFriendMessageInputDTO input,
                                OpenApiResult<TgBaseOutputDTO> result,
                                String firstSerialNo, String playId, String groupId) {
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
        replyLog.setPlayId(playId);
        replyLog.setGroupId(groupId);
        replyLog.setFriendId(input.getFriendSerialNo());
        replyLog.setRobotId(input.getTgRobotId());
        replyLog.setCreateTime(LocalDateTime.now());
        replyLog.setFirstRequestId(firstSerialNo);
        replyLog.setFailMessage(errorMessage);
        replyLog.setSuccess(false);
        mongoTemplate.save(replyLog);
    }

    public void sendMessageResult(CalledDTO root, Called1100910010DTO dto) {

        String optSerialNo = root.getOptSerNo();
        AutoReplyLog autoReplyLog = mongoTemplate.findById(optSerialNo, AutoReplyLog.class);
        String firstRequestId = Optional.ofNullable(autoReplyLog)
                .map(AutoReplyLog::getFirstRequestId)
                .orElse(optSerialNo);

        if (autoReplyLog == null) {
            return;
        }

        // 保存发送结果操作日志
        this.saveSendCallbackLog(autoReplyLog, root);

        // 成功更新结果 /失败更新失败原因
        Update updateResult = root.isSuccess() ?
                Update.update("isSuccess", true) : Update.update("failMessage", root.getResultMsg());

        mongoTemplate.updateFirst(Query.query(Criteria.where("id").is(optSerialNo)),
                updateResult,
                AutoReplyLog.class);

        if (root.isSuccess() && !StringUtils.equals(optSerialNo, firstRequestId)) {
            // 成功 且 不是第一次调用就成功 需要更新第一条记录的成功状态
            mongoTemplate.updateFirst(Query.query(Criteria.where("id").is(firstRequestId)),
                    updateResult,
                    AutoReplyLog.class);
        }
    }


    /**
     * 定时任务调度 - 扫描第一发送没有成功 且重试次数小于设定值
     */
    public void scanAutoMessageRetry() {

        int currentPage = 1;
        int pageSize = 100;
        List<AutoReplyLog> replyLogs = null;
        String retryTimes = SpringUtils.getBean(SysConfigServiceImpl.class).selectConfigByKey("auto-reply-retry-times");
        Integer times = Optional.ofNullable(retryTimes).map(Integer::parseInt).orElse(10);
        do {

            // 分页查询 MongoReply 数据
            Criteria criteria = Criteria.where("firstRequestId").exists(false)
                    .and("requestTimes").lt(times)
                    .and("isSuccess").is(false);
            Query query = Query.query(criteria);
            query.with(PageRequest.of(currentPage - 1, pageSize));
            replyLogs = mongoTemplate.find(query, AutoReplyLog.class);

            log.info("扫描到需要重试的数据 {}", replyLogs);

            this.retrySendMessage(replyLogs);

            // 当前页码++
            currentPage++;

        } while (replyLogs.size() == pageSize);
    }

    private void saveAutoReplyMessage(String playId, String robotId, String groupId, List<Called1100910027DTO> callbackData) {
        for (Called1100910027DTO data : callbackData) {
            String content = StrUtil.format("【自动回复-收消息】水军号：{}；群Id：{}；好友ID：{}；收到私聊消息：{}；",
                    robotId, groupId, data.getFrom_serial_no(), JSON.toJSONString(data));
            PlayExecutionLogService.savePackLog(PlayLogTyper.Auto_Reply_Receive, playId, groupId, content, 0);
        }
    }

    private void saveAutoReplyResultLog(String playId, String groupId, String robotId, String chatId, OpenApiResult<TgBaseOutputDTO> result) {
        //【自动回复-触发】2024-1-29 21:19:41， 水军号：演员1/anhshhs；接收：hhhshhs，自动回复成功
        String content = StrUtil.format("【自动回复-触发】水军号：{}；群Id：{}；好友ID：{}；触发自动回复：{}，调用结果：{}",
                robotId, groupId, chatId, result.getData().getOptSerNo(), result.getMessage());
        PlayExecutionLogService.savePackLog(PlayLogTyper.Auto_Reply_Send, playId, groupId, content, result.isSuccess() ? 0 : 1);
    }

    private void saveSendCallbackLog(AutoReplyLog replyLog, CalledDTO root) {
        String content = StrUtil.format("【自动回复-结果】水军号：{}；群Id：{}；好友ID：{}；自动回复结果：{} {}",
                replyLog.getRobotId(), replyLog.getGroupId(), replyLog.getFriendId(), root.isSuccess() ? "成功": root.getResultMsg());
        PlayExecutionLogService.savePackLog(PlayLogTyper.Auto_Reply_Send, replyLog.getPlayId(), replyLog.getGroupId(), content, root.isSuccess() ? 0 : 1);
    }
}
