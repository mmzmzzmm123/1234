package com.ruoyi.system.components.movie.spi.impl;

import com.ruoyi.common.core.delayqueue.context.FastDelayQueueContext;
import com.ruoyi.common.core.domain.entity.play.PlayMessage;
import com.ruoyi.common.utils.Ids;
import com.ruoyi.common.utils.spi.SPI;
import com.ruoyi.common.utils.spi.ServiceLoader;
import com.ruoyi.common.utils.spring.SpringUtils;
import com.ruoyi.system.components.PlayInfoTools;
import com.ruoyi.system.components.movie.DelayQueueInvoker;
import com.ruoyi.system.components.movie.GlobalIndexContext;
import com.ruoyi.system.components.movie.PlaybackContext;
import com.ruoyi.system.components.movie.spi.DelaySpeedController;
import com.ruoyi.system.components.movie.spi.GroupCtrlStopper;
import com.ruoyi.system.components.movie.spi.ProgressPuller;
import com.ruoyi.system.domain.GroupState;
import com.ruoyi.system.service.GroupStateService;
import com.ruoyi.system.service.impl.PlayServiceImpl;
import com.ruoyi.system.service.impl.SysConfigServiceImpl;
import lombok.extern.slf4j.Slf4j;

import java.util.Objects;
import java.util.Optional;

@SPI
@Slf4j
public class DelayQueueProgressPuller implements ProgressPuller {

    @Override
    public void nextPull(PlayMessage message, String chatroomId) {
        log.info("nextPull {} {}", chatroomId, message);

        // 剧本被暂停了
        if (PlaybackContext.isPause(message.getPlayId(), chatroomId)) {
            // 剧本被暂停了.
            log.info("剧本暂停了 {} {}", message, chatroomId);
            return;
        }
        // 剧本被删除了
        if (PlayInfoTools.isDeletedOrCancel(message.getPlayId())) {
            log.info("剧本删除了 {} {}", message, chatroomId);
            return;
        }
        // 风控限制了， 需要暂停剧本
        boolean limit = ServiceLoader.load(GroupCtrlStopper.class, "BoxGroupCtrlStopper").isStoped(message.getPlayId(), chatroomId);
        if (limit) {
            // 暂停剧本的某个群
            SpringUtils.getBean(PlayServiceImpl.class).stopPlayByGroupId(message.getPlayId(), chatroomId);
            log.info("群任务暂停 {} {}", chatroomId, message);
            return;
        }
        GroupState groupState = SpringUtils.getBean(GroupStateService.class).getById(chatroomId);
        if (groupState != null && Objects.equals(groupState.getGroupStatus(), 1)) {
            PlaybackContext.pause(message.getPlayId(),chatroomId);
            log.info("群状态异常不再发送消息 {} {} {}", groupState, message, chatroomId);
            return;
        }

        Integer delayTime = message.getIntervalTime();
        // 先移动游标 ，
        GlobalIndexContext.next(chatroomId, message.getPlayId());
        DelayQueueInvoker.Entry e = new DelayQueueInvoker.Entry(chatroomId, message.getPlayId(), Ids.getId(), null);
        // 计算 延时消息
        int val = ServiceLoader.load(DelaySpeedController.class).calculate(delayTime, message.getPlayId());
        log.info("加减计算 {} {} {}", message.getPlayId(), delayTime, val);
        // 放到延时队列里面继续下一条消息
        FastDelayQueueContext.send(DelayQueueInvoker.class, e, delayTime);
        log.info("延时队列生产 {} {} ", e, delayTime);
    }

	@Override
	public void continuePull(PlayMessage message, String chatroomId, String optSerialNo) {
        log.info("continuePull {} {}", chatroomId, message);

        // 剧本被暂停了
        if (PlaybackContext.isPause(message.getPlayId(), chatroomId)) {
            // 剧本被暂停了.
            log.info("剧本暂停了 {} {}", message, chatroomId);
            return;
        }
        // 剧本被删除了
        if (PlayInfoTools.isDeletedOrCancel(message.getPlayId())) {
            log.info("剧本删除了 {} {}", message, chatroomId);
            return;
        }

        GroupState groupState = SpringUtils.getBean(GroupStateService.class).getById(chatroomId);
        if (groupState != null && Objects.equals(groupState.getGroupStatus(), 1)) {
            PlaybackContext.pause(message.getPlayId(),chatroomId);
            log.info("群状态异常不再发送消息 {} {} {} {}", groupState, message, chatroomId, optSerialNo);
            return;
        }

        // 风控限制了， 需要暂停剧本
        boolean limit = ServiceLoader.load(GroupCtrlStopper.class, "BoxGroupCtrlStopper").isStoped(message.getPlayId(), chatroomId);
        if (limit) {
            // 暂停剧本的某个群
            SpringUtils.getBean(PlayServiceImpl.class).stopPlayByGroupId(message.getPlayId(), chatroomId);
            log.info("群任务暂停 {} {}", chatroomId, message);
            return;
        }

        String delaySecond = SpringUtils.getBean(SysConfigServiceImpl.class).selectConfigByKey("message-retry-delay-seconds");
        Integer delayTime = Optional.ofNullable(delaySecond).map(Integer::parseInt).orElse(10);

        DelayQueueInvoker.Entry e =
                new DelayQueueInvoker.Entry(chatroomId, message.getPlayId(), Ids.getId(), optSerialNo);
        // 计算 延时消息
        int val = ServiceLoader.load(DelaySpeedController.class).calculate(delayTime, message.getPlayId());
        log.info("加减计算 {} {} {}", message.getPlayId(), delayTime, val);
        // 放到延时队列里面继续发消息
        FastDelayQueueContext.send(DelayQueueInvoker.class, e, val);
        log.info("延时队列生产 {} {} ", e, delayTime);
	}

}
