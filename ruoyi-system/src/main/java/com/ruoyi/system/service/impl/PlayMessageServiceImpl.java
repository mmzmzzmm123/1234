package com.ruoyi.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruoyi.common.core.domain.entity.play.PlayMessage;
import com.ruoyi.system.mapper.PlayMessageMapper;
import com.ruoyi.system.service.PlayMessageService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 *
 */
@Service
public class PlayMessageServiceImpl extends ServiceImpl<PlayMessageMapper, PlayMessage>
        implements PlayMessageService {

    @Override
    public List<PlayMessage> listByPlayId(String playId) {
        if (playId == null) {
            return new ArrayList<>();
        }
        LambdaQueryWrapper<PlayMessage> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(PlayMessage::getPlayId, playId);
        lambdaQueryWrapper.orderByAsc(PlayMessage::getMessageSort);
        return super.list(lambdaQueryWrapper);
    }

    @Override
    public void deleteByPlayId(String playId) {
        if (playId == null) {
            return;
        }
        LambdaQueryWrapper<PlayMessage> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(PlayMessage::getPlayId, playId);
        super.remove(queryWrapper);

    }
}




