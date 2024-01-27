package com.ruoyi.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ruoyi.common.core.domain.entity.play.PlayBackRobot;

import java.util.List;

public interface IPlayBackRobotService extends IService<PlayBackRobot> {

    List<PlayBackRobot> queryReadyRobot();

}
