package com.ruoyi.system.service.business;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.alibaba.fastjson2.TypeReference;
import com.ruoyi.common.core.domain.entity.play.PlayBackRobot;
import com.ruoyi.common.core.domain.entity.play.PlayMessage;
import com.ruoyi.common.core.domain.entity.play.PlayMessagePushDetail;
import com.ruoyi.common.enums.PlayLogTyper;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.spi.ServiceLoader;
import com.ruoyi.common.utils.spring.SpringUtils;
import com.ruoyi.system.components.RedisTemplateTools;
import com.ruoyi.system.components.movie.PlayDirector;
import com.ruoyi.system.components.movie.SendMsgOptTempRedis;
import com.ruoyi.system.components.movie.spi.ProgressPuller;
import com.ruoyi.system.domain.mongdb.OpenApiRequestLog;
import com.ruoyi.system.domain.mongdb.PlayExecutionLog;
import com.ruoyi.system.mapper.PlayMessageMapper;
import com.ruoyi.system.openapi.OpenApiEnum;
import com.ruoyi.system.openapi.OpenApiResult;
import com.ruoyi.system.openapi.model.input.ThirdTgSendGroupMessageInputDTO;
import com.ruoyi.system.openapi.model.output.TgBaseOutputDTO;
import com.ruoyi.system.service.IPlayBackRobotService;
import com.ruoyi.system.service.PlayExecutionLogService;
import com.ruoyi.system.service.PlayMessagePushDetailService;
import com.ruoyi.system.service.PlayMessageService;
import com.ruoyi.system.service.impl.SysConfigServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * 重试Service
 *
 * @author Sharly
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class RetryService {

    private final MongoTemplate mongoTemplate;
    private final PlayMessageMapper playMessageMapper;
    private final PlayMessageService playMessageService;
    private final IPlayBackRobotService playBackRobotService;
    private final PlayExecutionLogService playExecutionLogService;
    private final PlayMessagePushDetailService playMessagePushDetailService;

    public boolean retry(SendMsgOptTempRedis.SendMsgOptTempEntry entry, String optSerialNo, String errMsg) {

        // 更新并查询本次请求记录
        OpenApiRequestLog requestLog = this.updateErrorMessage(optSerialNo, errMsg);

        // 更新错误次数
        String firstRequestId = requestLog.getLastTimeRequestId();
        OpenApiRequestLog firstLog = null;
        if (StringUtils.isNotBlank(firstRequestId)) {
            firstLog = this.incErrorTimes(firstRequestId);
        } else {
            firstRequestId = optSerialNo;
        }

        // save retry mongo log
        this.saveRetryExecutionLog(entry, optSerialNo, errMsg, firstLog);

        // 是否达到阈值
        String retryTimes = SpringUtils.getBean(SysConfigServiceImpl.class).selectConfigByKey("message-retry-times");
        Integer times = Optional.ofNullable(retryTimes).map(Integer::parseInt).orElse(10);
        if (firstLog != null && firstLog.getRequestTimes() >= times) {
            // 已经达到阈值无需重试
            log.info("重试达到次数不再继续重试 {} {} {}", entry, optSerialNo, errMsg);
            return false;
        }

        // 获取当前消息
        PlayMessage playMessage = playMessageMapper.getBySort(entry.getMsgSort(), entry.getPlayId());

        // 群Id
        String chatroomId = entry.getChatroomId();
        ServiceLoader.load(ProgressPuller.class).continuePull(playMessage, chatroomId, firstRequestId);
        log.info("推送至重试延时队列进行处理 {} {} {}", entry, optSerialNo, errMsg);
        return true;
    }

    /**
     * 保存页面上的重试日志
     *
     * @param entry
     * @param optSerialNo
     * @param errMsg
     * @param firstRequestLog
     */
    private void saveRetryExecutionLog(SendMsgOptTempRedis.SendMsgOptTempEntry entry,
                                       String optSerialNo,
                                       String errMsg,
                                       OpenApiRequestLog firstRequestLog) {
        PlayExecutionLog oneLog = new PlayExecutionLog();
        oneLog.setOpt(optSerialNo)
                .setState(0)
                .setType(PlayLogTyper.Group_Send)
                .setPlayId(entry.getPlayId())
                .setGroupId(entry.getChatroomId())
                .setCreateTime(new Date())
                .setRobotId(entry.getRobotId());
        String content = StrUtil.format("群消息发送失败开始重试,原因:{},操作编码:{},机器人:{},当前第{}次",
                errMsg, optSerialNo, entry.getRobotId(), firstRequestLog == null ? 1 : firstRequestLog.getRequestTimes());
        oneLog.setContent(content);
        playExecutionLogService.saveLog(oneLog);
    }

    public OpenApiRequestLog getRequestLog(String optSerialNo) {
        return mongoTemplate.findOne(Query.query(Criteria.where("id").is(optSerialNo)), OpenApiRequestLog.class);
    }

    public OpenApiRequestLog updateErrorMessage(String optSerialNo, String errorMessage) {
        Update update = Update.update("errorMessage", errorMessage);
        mongoTemplate.updateFirst(Query.query(Criteria.where("id").is(optSerialNo)), update, OpenApiRequestLog.class);
        return getRequestLog(optSerialNo);
    }


    public OpenApiRequestLog incErrorTimes(String optSerialNo) {
        Update update = new Update().inc("requestTimes", 1L);
        mongoTemplate.updateFirst(Query.query(Criteria.where("id").is(optSerialNo)), update, OpenApiRequestLog.class);
        return this.getRequestLog(optSerialNo);
    }

    public void saveRetrySendMessageLog(JSONObject body, String response) {

        if (body == null) {
            return;
        }
        log.info("保存发送消息请求日志 {} {}", body.toJSONString(), response);
        OpenApiResult<TgBaseOutputDTO> result = JSON.parseObject(response, new TypeReference<OpenApiResult<TgBaseOutputDTO>>(TgBaseOutputDTO.class) {
        });

        String currentOptSerialNo = Optional.ofNullable(result).map(OpenApiResult::getData).map(TgBaseOutputDTO::getOptSerNo).orElse(null);
        String errorMessage = Optional.ofNullable(result).map(OpenApiResult::getMessage).orElse(null);
        ThirdTgSendGroupMessageInputDTO input = body.toJavaObject(ThirdTgSendGroupMessageInputDTO.class);
        OpenApiRequestLog oneLog = new OpenApiRequestLog();
        oneLog.setId(currentOptSerialNo);
        oneLog.setChatRoomId(input.getChatroomSerialNo());
        oneLog.setRequestParams(body.toString());
        oneLog.setRequestMethod(OpenApiEnum.THIRD_KP_TG_SEND_GROUP_MESSAGE.name());
        oneLog.setResponseParams(response);
        oneLog.setLastTimeRequestId(input.getFirstOptSerialNo());
        oneLog.setErrorMessage(errorMessage);
        oneLog.setRequestTimes(0);
        oneLog.setCreateTime(LocalDateTime.now());

        mongoTemplate.save(oneLog);
    }

    public void scanPlayRobotPackReady() {
        List<PlayBackRobot> playBackRobots = playBackRobotService.queryReadyRobot();
        log.info("扫描到已经包装完成的备用号 {}", playBackRobots);

        if (CollectionUtils.isEmpty(playBackRobots)) {
            return;
        }

        for (PlayBackRobot playBackRobot : playBackRobots) {

            // 恢复剧本的发送
            String playId = playBackRobot.getPlayId();
            String groupId = playBackRobot.getGroupId();
            PlayDirector.tgInstance().resume(playId, groupId);

            // 更新待发送消息 - 把剩余数据都更新为新号
            List<PlayMessagePushDetail> playMessagePushDetails =
                    playMessagePushDetailService.selectOldRobotMessage(playId,
                            playBackRobot.getSpokesmanNickname(),
                            playBackRobot.getGroupId());
            playMessagePushDetails = CollectionUtils.emptyIfNull(playMessagePushDetails).stream()
                    .peek(it -> it.setRobotId(playBackRobot.getRobotId()))
                    .collect(Collectors.toList());

            RedisTemplateTools.get().opsForHash().put(
                    "ruoyiadmin:firegroup:DefaultRobotSpeakAllocator:" + playId + ":" + groupId, playBackRobot.getSpokesmanNickname(), playBackRobot.getRobotId());


            log.info("更新发送详情中的发言人信息 {}", playMessagePushDetails);
            playMessagePushDetailService.updateBatchById(playMessagePushDetails, 1000);
            PlayMessage playMessage = playMessageMapper.getBySort(playBackRobot.getMessageSort(), playId);
            ServiceLoader.load(ProgressPuller.class).continuePull(playMessage, groupId, null);

        }

    }
}
