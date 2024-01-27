package com.ruoyi.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruoyi.common.core.domain.entity.play.PlayBackRobot;
import com.ruoyi.system.mapper.PlayBackRobotMapper;
import com.ruoyi.system.service.IPlayBackRobotService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PlayBackRobotServiceImpl extends ServiceImpl<PlayBackRobotMapper, PlayBackRobot> implements IPlayBackRobotService {

    @Override
    public List<PlayBackRobot> queryReadyRobot() {
        LambdaQueryWrapper<PlayBackRobot> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(PlayBackRobot::getIsFinish, 1)
                .isNotNull(PlayBackRobot::getMessageSort);
        return this.list(queryWrapper);
    }

}
