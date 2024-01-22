package com.ruoyi.system.job;

import cn.hutool.core.date.DateUtil;
import com.alibaba.fastjson2.JSON;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.ruoyi.common.constant.PlayConstants;
import com.ruoyi.common.core.domain.entity.play.Play;
import com.ruoyi.common.core.redis.RedisLock;
import com.ruoyi.common.core.thread.AsyncTask;
import com.ruoyi.system.domain.PlayMessageConfound;
import com.ruoyi.system.domain.vo.PlayConfusionStateVO;
import com.ruoyi.system.domain.vo.PlayMessageConfusionStateVO;
import com.ruoyi.system.mapper.PlayMapper;
import com.ruoyi.system.mapper.PlayMessageConfoundMapper;
import com.ruoyi.system.service.PlayExecutionLogService;
import com.ruoyi.system.service.PlayMessageConfoundLogService;
import com.ruoyi.system.service.PlayMessagePushService;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @Author : XGF（徐桂烽）
 * @create 2024/1/18/018 13:51
 * @Description :
 */
@Slf4j
@Service("confoundJobService")
public class ConfoundJobService {

    @Resource
    private PlayMapper playInfoMapper;

    @Resource
    private PlayMessagePushService playMessagePushService;

    @Resource
    private PlayMessageConfoundMapper playMessageConfoundMapper;

    @Resource
    private PlayMessageConfoundLogService playMessageConfoundLogService;

    @Resource
    private RedisLock redisLock;

    /**
     * 混淆任务重试JOB
     */
    public void retryingConfusion() {
        String lockKey = PlayConstants.serviceName + ":retryingConfusion";
        RLock rLock = redisLock.getRLock(lockKey);
        if (rLock.isLocked()) {
            log.info("retryingConfusion lock");
            return;
        }
        rLock.lock(90, TimeUnit.SECONDS);
        String uuid = IdWorker.getIdStr();
        log.info("retryingConfusion start {}", uuid);
        playMessageConfoundLogService.retryingConfusion(lockKey);
        log.info("retryingConfusion end {}", uuid);
    }

    /**
     * 更新剧本的混淆状态
     */
    public void refreshPlayConfusionState() {
        String lockKey = PlayConstants.serviceName + ":refreshPlayConfusionState";
        RLock rLock = redisLock.getRLock(lockKey);
        if (rLock.isLocked()) {
            log.info("refreshPlayConfusionState lock");
            return;
        }
        rLock.lock(180, TimeUnit.SECONDS);
        List<PlayConfusionStateVO> list = playInfoMapper.selectConfusionStateStatistics();
        String uuid = IdWorker.getIdStr();
        log.info("refreshPlayConfusionState start {} {}", uuid, JSON.toJSONString(list));
        try {
            if (CollectionUtils.isEmpty(list)) {
                return;
            }
            for (PlayConfusionStateVO vo : list) {
                // 剧本标记失败
                if (vo.getFailCount() > 0) {
                    Play playInfo = playInfoMapper.selectById(vo.getPlayId());
                    if (playInfo == null) {
                        continue;
                    }
                    playInfo.setState(3);//标记剧本暂停
                    playInfo.setConfoundState(2);
                    playInfo.setFailReason("剧本混淆失败");
                    playInfoMapper.updateById(playInfo);

                    // 记录混淆日志
                    PlayExecutionLogService.playConfoundLog(playInfo.getId(), "混淆剧本失败", 1);
                    continue;
                }

                // 总条数 和成功条数 一致 则混淆完成
                if (!vo.getTotal().equals(vo.getSuccessCount())) {
                    continue;
                }
                Play playInfo = playInfoMapper.selectById(vo.getPlayId());
                if (playInfo == null) {
                    continue;
                }
                playInfo.setConfoundState(1);
                playInfoMapper.updateById(playInfo);
                // 剧本混淆完成 去创建t_play_message_push_detail 的混淆数据
                AsyncTask.execute(() -> {
                    try {
                        playMessagePushService.createPushDetail(playInfo.getId());
                    } catch (Exception e) {
                        log.error("refreshPlayConfusionState createPushDetail error {}", playInfo.getId(), e);
                    }
                });
            }
        } catch (Exception e) {
            log.error("refreshPlayConfusionState error", e);
        } finally {
            redisLock.unlock(lockKey);
            log.info("refreshPlayConfusionState end {}", uuid);
        }
    }

    /**
     * 更新剧本消息混淆状态
     */
    public void refreshPlayMessageConfusionState() {
        String lockKey = PlayConstants.serviceName + ":refreshPlayMessageConfusionState";
        RLock rLock = redisLock.getRLock(lockKey);
        if (rLock.isLocked()) {
            log.info("refreshPlayMessageConfusionState lock");
            return;
        }
        rLock.lock(180, TimeUnit.SECONDS);
        List<PlayMessageConfusionStateVO> list = playMessageConfoundMapper.selectConfusionStateStatistics();
        String uuid = IdWorker.getIdStr();
        log.info("refreshPlayMessageConfusionState start {} {}", uuid, list.size());
        try {
            if (CollectionUtils.isEmpty(list)) {
                return;
            }
            Date date = DateUtil.offsetMinute(new Date(), -10); //10分钟前
            for (PlayMessageConfusionStateVO vo : list) {
                PlayMessageConfound confound = playMessageConfoundMapper.selectById(vo.getConfoundId());
                if (confound == null) {
                    continue;
                }
                // 总条数 和成功条数 一致 则混淆完成
                if (vo.getTotal() == vo.getSuccessCount()) {
                    confound.setState(1);
                    playMessageConfoundMapper.updateById(confound);
                    continue;
                }
                // 超过10分钟 自动失败
                if (date.after(confound.getCreateTime())) {
                    confound.setState(2);
                    playMessageConfoundMapper.updateById(confound);
                }
            }
        } catch (Exception e) {
            log.info("refreshPlayMessageConfusionState error", e);
        } finally {
            redisLock.unlock(lockKey);
            log.info("refreshPlayMessageConfusionState end {}", uuid);
        }
    }

    /**
     * 拿剧本去跑混淆的 JOB
     */
    public void playConfusionJob() {
        String lockKey = PlayConstants.serviceName + ":playConfusionJob";
        RLock rLock = redisLock.getRLock(lockKey);
        if (rLock.isLocked()){
            log.info("playConfusionJob lock");
            return;
        }
        rLock.lock(180,TimeUnit.SECONDS);

        String uuid = IdWorker.getIdStr();
        log.info("playConfusionJob start {}", uuid);
        try {
            List<Play> playList = playInfoMapper.selectConfusionList();
            for (Play play : playList) {
                String playLockKey = PlayConstants.serviceName + ":playConfusion:" + play.getId();
                // 1个小时内不能再次触发
                RLock playLock = redisLock.getRLock(playLockKey);
                if (playLock.isLocked()) {
                    log.info("playConfusionJob lock playLock {}", play.getId());
                    continue;
                }
                playLock.lock(1, TimeUnit.HOURS);
                playMessagePushService.createPush(play.getId());
            }
        } catch (Exception e) {
            log.info("playConfusionJob error", e);
        } finally {
            redisLock.unlock(lockKey);
            log.info("playConfusionJob end {}", uuid);
        }
    }

}
