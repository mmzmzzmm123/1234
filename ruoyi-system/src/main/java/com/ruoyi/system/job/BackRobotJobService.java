package com.ruoyi.system.job;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson2.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.ruoyi.common.constant.PlayConstants;
import com.ruoyi.common.core.domain.entity.play.PlayBackRobot;
import com.ruoyi.common.core.domain.entity.play.PlayBackRobotLog;
import com.ruoyi.common.core.domain.entity.play.PlayRobotPack;
import com.ruoyi.common.core.domain.entity.play.PlayRobotPackLog;
import com.ruoyi.common.core.redis.RedisLock;
import com.ruoyi.common.enums.PlayLogTyper;
import com.ruoyi.common.utils.Ids;
import com.ruoyi.common.utils.MD5Utils;
import com.ruoyi.common.utils.Times;
import com.ruoyi.common.utils.bean.BeanUtils;
import com.ruoyi.common.utils.spi.ServiceLoader;
import com.ruoyi.common.utils.spring.SpringUtils;
import com.ruoyi.system.components.movie.PlayDirector;
import com.ruoyi.system.components.prepare.multipack.MultipackLogContainer;
import com.ruoyi.system.components.spi.Settings;
import com.ruoyi.system.service.IPlayBackRobotLogService;
import com.ruoyi.system.service.IPlayBackRobotService;
import com.ruoyi.system.service.ISysConfigService;
import com.ruoyi.system.service.PlayExecutionLogService;
import com.ruoyi.system.service.impl.PlayRobotPackService;
import com.ruoyi.system.service.impl.SysConfigServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.redisson.api.RLock;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static com.ruoyi.common.utils.Objects.wrapNull;

@Slf4j
@Service("backRobotJobService")
public class BackRobotJobService {
    @Resource
    RedisLock redisLock;
    @Resource
    IPlayBackRobotService playBackRobotService;
    @Resource
    IPlayBackRobotLogService playBackRobotLogService;
    @Resource
    PlayRobotPackService playRobotPackService;

    /**
     * 扫描任务开始包装
     */
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
                    .gt(PlayBackRobot::getCreateTime, Times.getDay(new Date(), -3)));
            log.info("doBackRobotJob:{}", playBackRobotList.size());
            playBackRobotList.forEach(robot -> {
                final List<PlayBackRobotLog> playBackRobotLogList = playBackRobotLogService.list(new LambdaQueryWrapper<PlayBackRobotLog>()
                        .eq(PlayBackRobotLog::getPlayId, robot.getPlayId())
                        .eq(PlayBackRobotLog::getChatroomId, robot.getGroupId())
                        .eq(PlayBackRobotLog::getRobotId, robot.getRobotId())
                );
                if (CollectionUtils.isEmpty(playBackRobotLogList)) {
                    //开始包装
                    log.info("doBackRobotJob-开始包装:{}", robot);
                    doPackRobot(robot);
                }
                else {
                    //已处理过
                    //将所有已处理过的Log进行统计，如果全部处理成功，则更新主库
                    //如果全部处理失败，则换号再来
                    final long successCount = playBackRobotLogList.stream().filter(it -> it.getStatus().compareTo(1) == 0).count();
                    final long failCount = playBackRobotLogList.stream().filter(it -> it.getStatus().compareTo(2) == 0).count();
                    if(playBackRobotLogList.size() == successCount){
                        robot.setIsFinish(1);
                        playBackRobotService.updateById(robot);
                        PlayExecutionLogService.savePackLog(PlayLogTyper.Robot_Settings, robot.getPlayId(),robot.getGroupId(),robot.getRobotId(), StrUtil.format("群{}-备用机器人{} 备用号人设包装已完成", robot.getGroupId(), robot.getRobotId()), null);
                    }
                    if(failCount > 0){
                        robot.setIsFinish(2);
                        playBackRobotService.updateById(robot);
                        PlayExecutionLogService.savePackLog(PlayLogTyper.Robot_Settings, robot.getPlayId(),robot.getGroupId(),robot.getRobotId(), StrUtil.format("群{}-备用机器人{} 备用号人设包装失败，准备换号继续", robot.getGroupId(), robot.getRobotId()), 1);
                        //启动新备用机器人
                        PlayDirector.tgInstance().doProcessBackRobot(robot.getPlayId(),robot.getGroupId(),robot.getSpokesmanNickname(),robot.getMessageSort());
                    }
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

    /**
     * 进行备用号人设包装操作
     * @param robot
     */
    private void doPackRobot(PlayBackRobot robot) {
        final Settings tgRobotImgSettings = ServiceLoader.load(Settings.class, "TgRobotImgSettings");
        final Settings tgRobotNameSettings = ServiceLoader.load(Settings.class, "TgRobotNameSettings");
        final Settings tgRobotAdminSettings = ServiceLoader.load(Settings.class, "TgRobotAdminSettings");
        final Settings tgGroupHashSettings = ServiceLoader.load(Settings.class, "TgGroupHashSettings");
        final PlayRobotPack robotPck = playRobotPackService.getOne(new LambdaQueryWrapper<PlayRobotPack>()
                .eq(PlayRobotPack::getPlayId, robot.getPlayId())
                .eq(PlayRobotPack::getRobotNickname, robot.getSpokesmanNickname()));
        log.info("开始备用号人设包装 {} {} " , robot, JSON.toJSONString(robotPck));
        // 请求 设置 机器人头像 ，昵称等
        Map<String, Object> param = new HashMap<>();
        param.put(Settings.Key_PlayId, robot.getPlayId());
        // 机器人id
        param.put(Settings.Key_RobotId, robot.getRobotId());
        // 群id
        param.put(Settings.Key_GroupId, robot.getGroupId());
        // 姓名
        param.put(Settings.Key_Names1, wrapNull(robotPck.getName(), ""));
        // 姓氏
        param.put(Settings.Key_Names2, wrapNull(robotPck.getSurname(), ""));
        // 管理员
        param.put(Settings.Key_Admin_Flag,
                wrapNull(robotPck.getIsAdmin(), new Integer(0)).intValue() == 0 ? false : true);
        // 头像
        param.put(Settings.Key_Img, wrapNull(robotPck.getPic(), ""));
        // 备用号
        param.put(Settings.Key_Backup_Flag, true);

        boolean isChange = false;
        // 设置头像
        if (!StringUtils.isEmpty(robotPck.getPic())) {
            isChange = true;
            PlayBackRobotLog playBackRobotLog = new PlayBackRobotLog();
            PlayRobotPackLog ret = tgRobotImgSettings.set(param);
            if (StringUtils.isEmpty(ret.getOpt())) {
                PlayExecutionLogService.robotPackLog(robot.getPlayId(), robot.getGroupId(), robot.getRobotId(), ret.getErrMsg(),
                        null, "备用号-头像", true);
            } else {
                PlayExecutionLogService.robotPackLog(robot.getPlayId(), robot.getGroupId(), robot.getRobotId(), null, ret.getOpt(),
                        "备用号-头像", true);
            }
            log.info("备用号-同步设置头像-orgin {} " , ret);
            boolean b = false;
            try {
                BeanUtils.copyProperties(ret, playBackRobotLog);
                playBackRobotLog.setId(MD5Utils.md5Hex(robot.getRobotId()+ robot.getGroupId()+ robot.getRobotId()+ret.getOp().toString(),"utf-8"));
                b = playBackRobotLogService.saveOrUpdate(playBackRobotLog);
            } catch (Exception e) {
                log.error("设置头像发生异常 {}", e.getMessage(), e);
            }

            log.info("备用号-同步设置头像 {}, {} " , playBackRobotLog, b);
        }

        // 设置 昵称
        if (!StringUtils.isEmpty(robotPck.getName()) || !StringUtils.isEmpty(robotPck.getSurname())) {
            isChange = true;
            PlayBackRobotLog playBackRobotLog = new PlayBackRobotLog();
            PlayRobotPackLog ret = tgRobotNameSettings.set(param);
            if (StringUtils.isEmpty(ret.getOpt())) {
                PlayExecutionLogService.robotPackLog(robot.getPlayId(), robot.getGroupId(), robot.getRobotId(), ret.getErrMsg(),
                        null, "备用号-姓名", true);
            } else {
                PlayExecutionLogService.robotPackLog(robot.getPlayId(), robot.getGroupId(), robot.getRobotId(), null, ret.getOpt(),
                        "备用号-姓名", true);
            }
            log.info("备用号-同步设置昵称-orgin {} " , ret);
            BeanUtils.copyProperties(ret, playBackRobotLog);
            playBackRobotLog.setId(MD5Utils.md5Hex(robot.getRobotId()+ robot.getGroupId()+ robot.getRobotId()+ret.getOp().toString(),"utf-8"));
            final boolean b = playBackRobotLogService.saveOrUpdate(playBackRobotLog);

            log.info("备用号-同步设置昵称 {} ,{}" , playBackRobotLog, b);
        }

        // 同步设置 管理员
        if (robotPck.getIsAdmin() != null && robotPck.getIsAdmin().intValue() == 1) {
            isChange = true;
            PlayBackRobotLog playBackRobotLog = new PlayBackRobotLog();
            log.info("备用号-设置管理员 {} " , JSON.toJSONString(robotPck));
            ISysConfigService sysConfigService = SpringUtils.getBean(SysConfigServiceImpl.class);
            final String botAdminPara = sysConfigService.selectConfigByKey("setBotAdmin:para");
            if("0".equals(botAdminPara)) {
                log.info("走bot的逻辑 {} " , JSON.toJSONString(robotPck));
                //走bot的逻辑
                PlayRobotPackLog ret = tgRobotAdminSettings.set(param);
                if (ret.getStatus().intValue() == 1) {
                    // 直接设置成功
                    PlayExecutionLogService.robotPackLog(robot.getPlayId(), robot.getGroupId(), robot.getRobotId(),
                            String.format("【发言人包装-%s】 群%s 号%s 设置成功", "备用号-管理员", robot.getGroupId(), robot), null);
                } else {
                    PlayExecutionLogService.robotPackLog(robot.getPlayId(), robot.getGroupId(), robot.getRobotId(),
                            String.format("【发言人包装-%s】 群%s 号%s 设置失败，原因：%s", "备用号-管理员", robot.getGroupId(), robot,
                                    ret.getErrMsg()),
                            ret.getErrMsg());
                }
                BeanUtils.copyProperties(ret, playBackRobotLog);
                playBackRobotLog.setId(MD5Utils.md5Hex(robot.getRobotId()+ robot.getGroupId()+ robot.getRobotId()+ret.getOp().toString(),"utf-8"));
                playBackRobotLogService.saveOrUpdate(playBackRobotLog);
                log.info("备用号-同步设置管理员 {} ", ret);
            }
            else{
                //走开平的逻辑
                log.info("备用号-走开平的逻辑 {} " , JSON.toJSONString(robotPck));
                final PlayRobotPackLog ret = tgGroupHashSettings.set(param);
                if (StringUtils.isEmpty(ret.getOpt())) {
                    PlayExecutionLogService.robotPackLog(robot.getPlayId(), robot.getGroupId(), robot.getRobotId(), ret.getErrMsg(), null, "备用号-管理员（获取hash值）", true);
                } else {
                    PlayExecutionLogService.robotPackLog(robot.getPlayId(), robot.getGroupId(), robot.getRobotId(), null, ret.getOpt(), "备用号-管理员（获取hash值）", true);
                }
                log.info("备用号-同步设置管理员(获取hash值)-orgin {} " , ret);
                final String opt = ret.wrapOpt().getOpt();
                BeanUtils.copyProperties(ret, playBackRobotLog);
                playBackRobotLog.setId(MD5Utils.md5Hex(robot.getRobotId()+ robot.getGroupId()+ robot.getRobotId()+ret.getOp().toString(),"utf-8"));
                final boolean b = playBackRobotLogService.saveOrUpdate(playBackRobotLog);
                log.info("备用号-同步设置管理员(获取hash值) {},{} ", ret,b);

                if (!StringUtils.isEmpty(ret.getOpt())) {
                    // 请求成功后，插入一条 后置 请求
                    PlayBackRobotLog postposition = new PlayBackRobotLog();
                    postposition.setChatroomId(robot.getGroupId());
                    postposition.setCreateTime(new Date());
                    // 1-设置机器人姓名，姓氏 2-设置机器人头像 3-设置群hash值 4-设置管理员 5-获取群成员
                    postposition.setOp(4);
                    postposition.setOpt("wait_" + Ids.getId());
                    postposition.setPlayId(robot.getPlayId());
                    postposition.setRetryCount(0);
                    postposition.setRobotId(robot.getRobotId());
                    postposition.setStatus(-1);
                    // 等待某个操作码执行完成才能开始调用
                    postposition.setWaitOpt(opt);
                    playBackRobotLog.setId(MD5Utils.md5Hex(robot.getRobotId()+ robot.getGroupId()+ robot.getRobotId()+postposition.getOp().toString(),"utf-8"));
                    playBackRobotLogService.saveOrUpdate(postposition);
                }
            }
        }
        if(!isChange){
            robot.setIsFinish(1);
            playBackRobotService.updateById(robot);
        }
    }

    /**
     * 扫描任务状态判断
     */
    public void doBackRobotStateJob(){
        int timeOut = 60;//1分钟算超时
        final List<PlayBackRobotLog> playBackRobotLogList = playBackRobotLogService.list(new LambdaQueryWrapper<PlayBackRobotLog>()
                .eq(PlayBackRobotLog::getStatus, 0)
                .gt(PlayBackRobotLog::getCreateTime, Times.getDay(new Date(), -3)));
        if(CollectionUtils.isEmpty(playBackRobotLogList)){
            log.info("doBackRobotStateJob:没有需要包装的备用号");
            return;
        }
        log.info("doBackRobotStateJob:{}", playBackRobotLogList.size());
        playBackRobotLogList.forEach(data -> {
            MultipackLogContainer.CallValue ret = MultipackLogContainer.CallValueStore.get(data.getOpt());
            if (ret != null) {
                //已有回调
                if (ret.isSuccess()) {
                    // 请求成功
                    data.setStatus(1);
                    data.setAttchContent(ret.getAttchContent());
                    playBackRobotLogService.updateById(data);
                    // 单个回调 成功
                    saveLog(data, null);

                    //处理后置动作请求
                    final PlayBackRobotLog waitData = playBackRobotLogService.getOne(new LambdaQueryWrapper<PlayBackRobotLog>()
                            .eq(PlayBackRobotLog::getWaitOpt, data.getOpt())
                            .last(" limit 1 ")
                    );
                    if (waitData.getOp().intValue() == 4) {
                        // 1-设置机器人姓名，姓氏 2-设置机器人头像 3-设置群hash值 4-设置管理员 5-获取群成员
                        Settings settings = ServiceLoader.load(Settings.class, "TgKpRobotAdminSettings");
                        // 请求 设置 机器人头像 ，昵称等
                        Map<String, Object> param = new HashMap<>();
                        param.put(Settings.Key_PlayId, data.getPlayId());
                        // 机器人id
                        param.put(Settings.Key_RobotId, data.getRobotId());
                        // 群id
                        param.put(Settings.Key_GroupId, data.getChatroomId());
                        // 管理员
                        param.put(Settings.Key_Admin_Flag, true);
                        // 备用号
                        param.put(Settings.Key_Backup_Flag, true);
                        // hash 值
                        param.put(Settings.Key_AttachContent, wrapNull(data.getAttchContent(), ""));
                        // 请求 设置管理员
                        PlayRobotPackLog retAdmin = settings.set(param);
                        waitData.setStatus(retAdmin.getStatus());
                        waitData.setErrMsg(retAdmin.getErrMsg());
                        waitData.setOpt(retAdmin.getOpt());

                        if (StringUtils.isEmpty(retAdmin.getOpt())) {
                            saveLog(waitData, JSON.toJSONString(ret), true);
                        } else {
                            saveLog(waitData, null, true);
                        }
                        playBackRobotLogService.saveOrUpdate(waitData);
                        log.info("后置log更新 {}", data);
                    }
                }
                else {
                    // 单个请求失败
                    data.setStatus(2);
                    data.setErrMsg(JSON.toJSONString(ret));
                    data.setAttchContent(ret.getAttchContent());
                    playBackRobotLogService.updateById(data);
                    // 单个回调 失败
                    saveLog(data, JSON.toJSONString(ret));
                }
            }
            else{
                //没有回调
                if(Times.isMoreThanSeconds(data.getUpdateTime(), new Date(), timeOut)){
                    //超过60秒设置为超时
                    data.setStatus(2);
                    data.setErrMsg("回调超时"+timeOut+"秒");
                    playBackRobotLogService.updateById(data);
                    saveLog(data, data.getErrMsg());
                }
            }
        });
    }

    public void saveLog(PlayBackRobotLog data, String errMsg, boolean isSync) {
        log.info("onPackLog {}", data);

        // 单个 回调 执行 成功 1-设置机器人姓名，姓氏 2-设置机器人头像 3-设置群hash值 4-设置管理员 5-获取群成员
        if (data.getOp().intValue() == 1) {
            PlayExecutionLogService.robotPackLog(data.getPlayId(), data.getChatroomId(), data.getRobotId(), errMsg,
                    data.getOpt(), "姓名", isSync);
        }
        if (data.getOp().intValue() == 2) {
            PlayExecutionLogService.robotPackLog(data.getPlayId(), data.getChatroomId(), data.getRobotId(), errMsg,
                    data.getOpt(), "头像", isSync);
        }
        if (data.getOp().intValue() == 3) {
            PlayExecutionLogService.robotPackLog(data.getPlayId(), data.getChatroomId(), data.getRobotId(), errMsg,
                    data.getOpt(), "管理员（获取hash值）", isSync);
        }
        if (data.getOp().intValue() == 4) {
            PlayExecutionLogService.robotPackLog(data.getPlayId(), data.getChatroomId(), data.getRobotId(), errMsg,
                    data.getOpt(), "管理员", isSync);
        }
    }
    public void saveLog(PlayBackRobotLog data, String errMsg) {
        saveLog(data, errMsg, false);
    }
}
