package com.ruoyi.system.job;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.ruoyi.common.constant.PlayConstants;
import com.ruoyi.common.core.domain.entity.play.PlayBackRobot;
import com.ruoyi.common.core.domain.entity.play.PlayBackRobotLog;
import com.ruoyi.common.core.redis.RedisLock;
import com.ruoyi.common.utils.Times;
import com.ruoyi.system.service.IPlayBackRobotLogService;
import com.ruoyi.system.service.IPlayBackRobotService;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service("backRobotJobService")
public class BackRobotJobService {
    @Resource
    RedisLock redisLock;
    @Resource
    IPlayBackRobotService playBackRobotService;
    @Resource
    IPlayBackRobotLogService playBackRobotLogService;

    public void doBackRobotJob(){
        String lockKey = PlayConstants.serviceName + ":doBackRobotJob";
        RLock rLock = redisLock.getRLock(lockKey);
        if (rLock.isLocked()) {
            log.info("doBackRobotJob locked");
            return;
        }
        rLock.lock(180, TimeUnit.SECONDS);
        try {
            final List<PlayBackRobot> playBackRobotList = playBackRobotService.list(new LambdaQueryWrapper<PlayBackRobot>()
                    .eq(PlayBackRobot::getIsFinish, 0)
                    .gt(PlayBackRobot::getCreateTime, Times.getDay(new Date(), 3)));
            log.info("doBackRobotJob:{}", playBackRobotList.size());
            playBackRobotList.forEach(robot -> {
                final long count = playBackRobotLogService.count(new LambdaQueryWrapper<PlayBackRobotLog>()
                        .eq(PlayBackRobotLog::getPlayId, robot.getPlayId())
                        .eq(PlayBackRobotLog::getGroupId, robot.getGroupId())
                        .eq(PlayBackRobotLog::getRobotId, robot.getRobotId()));
                if (count == 0) {
                    //开始包装
                    log.info("doBackRobotJob-开始包装:{}", robot);
                    List<PlayBackRobotLog> playBackRobotLogList = new ArrayList<>();

                }
            });
        }
        catch (Exception e){
            e.printStackTrace();
        }
        finally {
            rLock.unlock();
        }
    }
}
