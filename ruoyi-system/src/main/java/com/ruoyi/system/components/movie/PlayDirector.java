package com.ruoyi.system.components.movie;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ruoyi.common.core.domain.entity.play.Play;
import com.ruoyi.common.core.domain.entity.play.PlayBackRobot;
import com.ruoyi.common.core.domain.entity.play.PlayMessage;
import com.ruoyi.common.core.redis.RedisLock;
import com.ruoyi.common.enums.PlayLogTyper;
import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.common.utils.DelayAcquireTools;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.spi.ServiceLoader;
import com.ruoyi.common.utils.spring.SpringUtils;
import com.ruoyi.system.components.PlayInfoTools;
import com.ruoyi.system.components.movie.SendMsgOptTempRedis.SendMsgOptTempEntry;
import com.ruoyi.system.components.movie.spi.PlayRunner;
import com.ruoyi.system.components.movie.spi.ProgressPuller;
import com.ruoyi.system.mapper.PlayMessageMapper;
import com.ruoyi.system.service.PlayExecutionLogService;
import com.ruoyi.system.service.business.RetryService;
import com.ruoyi.system.service.impl.PlayBackRobotServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Slf4j
public class PlayDirector implements CallBackProcessor {

    private final String alias;

    private static final PlayDirector tgInstance = new PlayDirector("tg");

    public static PlayDirector tgInstance() {
        return tgInstance;
    }

    private PlayDirector(String alias) {
        this.alias = alias;
    }

    public ManualExecutivePlayer getManualExecutivePlayer(Play play) {
        PlayBookFactory factory = ServiceLoader.load(PlayBookFactory.class, "TgPlayBookFactory");
        final PlayBook playBook = factory.newInstance(alias, play);
        return new ManualExecutivePlayer(playBook);
    }

    /**
     * 开始定时剧本
     *
     * @param play
     */
    public void timeup(Play play) {
        PlayBookFactory factory = ServiceLoader.load(PlayBookFactory.class, "TgPlayBookFactory");
        // 构建 电影 本子
        final PlayBook playBook = factory.newInstance(alias, play);
        // 定时剧本
        new ScheduleExecutivePlayer(playBook).timeup();
    }

    /**
     * 继续 发消息
     *
     */
    public void callMessage(DelayQueueInvoker.Entry entry) {
        String playId = entry.getPlayId();
        String chatroomId = entry.getChatroomId();
        String firstSerialNo = entry.getFirstSerialNo();
        PlayBookFactory factory = ServiceLoader.load(PlayBookFactory.class, "TgPlayBookFactory");
        // 构建 电影 本子
        final PlayBook playBook = factory.newInstance(alias, playId);
        new NextExecutivePlayer(playBook).sendOne(chatroomId, GlobalIndexContext.getIndex(chatroomId, playId), firstSerialNo);
    }

    @Override
    public void onMessageSuccess(String opt) {
        DelayAcquireTools.acquire(() -> SendMsgOptTempRedis.get(opt), (val) -> success0(val, opt));
    }

    private void success0(SendMsgOptTempEntry entry, String opt) {
        log.info("play_success0 {} {} {}", entry, opt);

        final PlayRunner playRunner = ServiceLoader.load(PlayRunner.class);
        playRunner.onItemSendSuccess(opt, entry.getPlayId(), entry.getChatroomId(), entry.getMsgSort(),
                entry.getRobotId());
        // 查询下一个消息的配置
        PlayMessage playMsg = SpringUtils.getBean(PlayMessageMapper.class).getBySort(entry.getMsgSort() + 1,
                entry.getPlayId());
        log.info("parseIntervalTime {} {} {}", opt, entry, playMsg);
        if (playMsg == null) {
            // 没有下一个消息了 ， 剧本完成
            playRunner.onFinish(entry.getPlayId(), entry.getChatroomId());
            return;
        }
        log.info("DelayQueueProgressPuller {} {} {}", opt, entry, playMsg);
        // 放到延时队列里面去 .继续 下第一条
        ServiceLoader.load(ProgressPuller.class).nextPull(playMsg, entry.getChatroomId());
    }

    @Override
    public void onMessageFailure(String opt, String errMsg) {
        DelayAcquireTools.acquire(() -> SendMsgOptTempRedis.get(opt), (val) -> fail0(val, opt, errMsg));
    }

    void fail0(SendMsgOptTempEntry entry, String opt, String errMsg) {
        log.info("play_fail0 {} {} {}", entry, opt, errMsg);
        final PlayRunner playRunner = ServiceLoader.load(PlayRunner.class);

        /**
         * 		发送消息重试逻辑 true - 重试成功
         * 	重试失败 - 走备用号逻辑
         */
        if (SpringUtils.getBean(RetryService.class).retry(entry, opt, errMsg)) {
            this.pause(entry.playId, entry.getChatroomId());
            log.info("进入发消息重试流程成功 {} {} {}", entry, opt, errMsg);
            return;
        }

        if (this.doProcessBackRobot(entry.getPlayId(), entry.getChatroomId(), entry.getRobotNickName(), entry.getMsgSort())) {
            log.info("进入备用号包装流程成功 {} {} {}", entry, opt, errMsg);
            this.pause(entry.playId, entry.getChatroomId());
            return;
        }
        log.info("失败进入发送下一条消息流程 {} {} {}", entry, opt, errMsg);

        // 备用号 都用完了 , 直接失败
        playRunner.onItemFailure(opt, entry.getPlayId(), errMsg, entry.getChatroomId(), entry.getRobotId(),
                entry.getMsgSort(), null);
        // 查询下一个消息的配置
        PlayMessage playMsg = SpringUtils.getBean(PlayMessageMapper.class).getBySort(entry.getMsgSort() + 1,
                entry.getPlayId());
        if (playMsg == null) {
            // 没有下一个消息了 ， 剧本完成
            playRunner.onFinish(entry.getPlayId(), entry.getChatroomId());
            return;
        }
        if (PlayInfoTools.failStoped(entry.getPlayId())) {
            // 失败完成
            playRunner.onFinish(entry.getPlayId(), entry.getChatroomId());
            return;
        }
        // 放到延时队列里面去 .继续 下第一条
        ServiceLoader.load(ProgressPuller.class).nextPull(playMsg, entry.getChatroomId());
    }

    /**
     * 暂停 某个 剧本的某个群 的 推送
     *
     * @param playId
     * @param chatroomId
     */
    public void pause(String playId, String chatroomId) {
        RLock lock = SpringUtils.getBean(RedisLock.class).getRLock("ruoyi:pauselock:" + playId + ":" + chatroomId);
        if (lock.isLocked()) {
            throw new ServiceException("操作频繁");
        }
        try {
            lock.lock(5, TimeUnit.SECONDS);
            PlaybackContext.pause(playId, chatroomId);
        } finally {
            lock.unlock();
        }
    }

    /**
     * 恢复 某个剧本的某个群的 推送
     *
     * @param playId
     * @param chatroomId
     */
    public void resume(String playId, String chatroomId) {
        RLock lock = SpringUtils.getBean(RedisLock.class).getRLock("ruoyi:pauselock:" + playId + ":" + chatroomId);
        if (lock.isLocked()) {
            throw new ServiceException("操作频繁");
        }
        final PlayRunner playRunner = ServiceLoader.load(PlayRunner.class);

        try {
            lock.lock(10, TimeUnit.SECONDS);
            PlaybackContext.resume(playId, chatroomId);
            int index = GlobalIndexContext.getIndex(chatroomId, playId);
            // 拿下一条
            PlayMessage playMsg = SpringUtils.getBean(PlayMessageMapper.class).getBySort(index + 1, playId);
            log.info("剧本恢复 {} {} ", chatroomId, playMsg);
            if (playMsg == null) {
                playRunner.onFinish(playId, chatroomId);
                return;
            }
            ServiceLoader.load(ProgressPuller.class).nextPull(playMsg, chatroomId);
        } finally {
            lock.unlock();
        }
    }


	/**
	 * 启用备用号，走新的包装流程
	 * @param playId 剧本ID
	 * @param groupId 群ID
	 * @param spokesmanNickname 对应发言人昵称
	 * @param messageSort 暂停的发言顺序
	 */
    public boolean doProcessBackRobot(String playId, String groupId, String spokesmanNickname, Integer messageSort){
        final PlayBackRobot playBackRobot = doProcessBackRobot(playId, groupId, spokesmanNickname, messageSort, 0);
        return playBackRobot != null;
    }

    /**
     * 直接获取备用号，并且不走新的备用号包装流程
     * @param playId
     * @param groupId
     * @param spokesmanNickname
     * @param messageSort
     * @return
     */
    public PlayBackRobot doProcessBackRobot(String playId, String groupId, String spokesmanNickname, Integer messageSort, Integer finishState){
        String lockKey = StringUtils.format("ruoyi:wait:doProcessBackRobot:{}:{}", playId, groupId);
        SpringUtils.getBean(RedisLock.class).waitLock(lockKey, 60);
        try{
            final PlayBackRobotServiceImpl playBackRobotService = SpringUtils.getBean(PlayBackRobotServiceImpl.class);
            LambdaQueryWrapper<PlayBackRobot> queryWrapperIng = new LambdaQueryWrapper<PlayBackRobot>()
                    .eq(PlayBackRobot::getPlayId,playId)
                    .eq(PlayBackRobot::getGroupId,groupId)
                    .eq(PlayBackRobot::getSpokesmanNickname,spokesmanNickname)
                    .in(PlayBackRobot::getIsFinish,0,1);
            final List<PlayBackRobot> playBackRobotList = playBackRobotService.list(queryWrapperIng);
            if(!playBackRobotList.isEmpty()){
                return playBackRobotList.get(0);
            }
            LambdaQueryWrapper<PlayBackRobot> queryWrapper = new QueryWrapper<PlayBackRobot>().lambda()
                    .eq(PlayBackRobot::getPlayId, playId)
                    .eq(PlayBackRobot::getGroupId, groupId)
                    .eq(PlayBackRobot::getIsFinish, -1)
                    .last(" limit 1 ");
            final PlayBackRobot playBackRobot = playBackRobotService.getOne(queryWrapper);
            if(playBackRobot == null){
                PlayExecutionLogService.savePackLog(PlayLogTyper.Robot_Pre_Alloc, playId, groupId, "没有可以分配的备用号", 1);
                throw new ServiceException("没有可以分配的备用号");
            }
            playBackRobot.setIsFinish(finishState);
            playBackRobot.setMessageSort(messageSort);
            playBackRobot.setSpokesmanNickname(spokesmanNickname);
            playBackRobotService.updateById(playBackRobot);
            PlayExecutionLogService.savePackLog(PlayLogTyper.Robot_Pre_Alloc, playId, groupId, playBackRobot.getRobotId(),StringUtils.format("【备用号出库】 群{} 号{}，对应发言人：{}",groupId,playBackRobot.getRobotId(),spokesmanNickname), null);
            return playBackRobot;
        } catch (Exception e){
            return null;
        } finally {
            SpringUtils.getBean(RedisLock.class).unWaitLock(lockKey);
        }
    }


    /**
     * 恢复发送 - 重新发送消息(非一下条)
     *
     * @param playId     剧本Id
     * @param chatroomId 群Id
     */
    public void resumeCurrentMessage(String playId, String chatroomId) {
        RLock lock = SpringUtils.getBean(RedisLock.class).getRLock("ruoyi:pauselock:" + playId + ":" + chatroomId);
        if (lock.isLocked()) {
            throw new ServiceException("操作频繁");
        }
        try {
            lock.lock(10, TimeUnit.SECONDS);
            PlaybackContext.resume(playId, chatroomId);
            int index = GlobalIndexContext.getIndex(chatroomId, playId);
            PlayMessage playMsg = SpringUtils.getBean(PlayMessageMapper.class).getBySort(index, playId);
            log.info("当前剧本恢复发送 {} {} ", chatroomId, playMsg);
        } finally {
            lock.unlock();
        }
    }

}
