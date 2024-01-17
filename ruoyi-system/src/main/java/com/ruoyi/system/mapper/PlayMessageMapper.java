package com.ruoyi.system.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ruoyi.common.core.domain.entity.play.PlayMessage;

import java.util.List;

public interface PlayMessageMapper extends BaseMapper<PlayMessage> {

    public default PlayMessage getBySort(int msgSort, String playId) {
        return selectOne(new QueryWrapper<PlayMessage>().lambda().eq(PlayMessage::getPlayId, playId)
                .eq(PlayMessage::getMessageSort, msgSort).last(" limit 1 "));
    }

    List<PlayMessage> selectListByPlayId(String playId);

}
