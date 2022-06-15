package com.ruoyi.quartz.task;

import com.ruoyi.robot.MacdTaRobot;
import com.ruoyi.udef.service.impl.RobotServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component("ryRobot")
public class RyRobot {
    @Value("${spring.profiles.active}")
    String profilesActive;

    @Autowired
    MacdTaRobot macdTaRobot;

    @Autowired
    RobotServiceImpl robotService;

    @Scheduled(cron = "0/30 * * * * ?")
    public void pearlMacdRobot() {
        try {
            log.info("pearlMacdRobot .... ");
            if("divine".equals(profilesActive)){
                macdTaRobot.execute();
            } else {
                log.warn("当前开发环境,不运行机器人!!!");
            }
        } catch (Exception e) {
            log.warn("macd建议机器");
            e.printStackTrace();
        }
    }

    @Scheduled(cron = "0/20 * * * * ?")
    public void checkPositionTrigger() {
        try {
            log.info("checkPositionTrigger .... ");
            if("divine".equals(profilesActive)){
                robotService.checkPositionTriggerFromConfig();
            } else {
                log.warn("当前开发环境，不运行触发器监控!!!");
            }
        } catch (Exception e) {
            log.warn("checkPositionTrigger");
            e.printStackTrace();
        }
    }

    @Scheduled(cron = "0 0/5 * * * ?")
    public void syncOrder() {
        try {
            log.info("syncOrder .... ");
            if("divine".equals(profilesActive)){
                robotService.syncOrder();
            } else {
                log.warn("当前开发环境，不运行触发器监控!!!");
            }
        } catch (Exception e) {
            log.warn("checkPositionTrigger");
            e.printStackTrace();
        }
    }


}
