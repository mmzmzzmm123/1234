package com.ruoyi.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ruoyi.system.domain.dto.play.PlayModifierGroupLog;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface PlayModifierGroupLogMapper  extends BaseMapper<PlayModifierGroupLog> {


    List<PlayModifierGroupLog> selectGroupLogByPlayId(@Param("playId")String playId);

    PlayModifierGroupLog selectGroupLogByCode(@Param("optSerNo")String optSerNo);

    List<PlayModifierGroupLog> selectGroupLogByPlayIdAll(@Param("playId")String playId);

}
