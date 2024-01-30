package com.ruoyi.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruoyi.common.core.domain.entity.play.PlayRobotMessage;
import com.ruoyi.system.mapper.PlayRobotMessageMapper;
import org.springframework.stereotype.Service;

@Service
public class PlayRobotMessageService extends ServiceImpl<PlayRobotMessageMapper, PlayRobotMessage> {

    public PlayRobotMessage selectByPlayId(String playId, int offset) {
        LambdaQueryWrapper<PlayRobotMessage> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(PlayRobotMessage::getPlayId, playId);
        queryWrapper.last("limit " + offset + " , 1");
        return this.getOne(queryWrapper);
    }


    public Long countByPlayId(String playId) {
        LambdaQueryWrapper<PlayRobotMessage> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(PlayRobotMessage::getPlayId, playId);
        return this.count(queryWrapper);
    }
}
