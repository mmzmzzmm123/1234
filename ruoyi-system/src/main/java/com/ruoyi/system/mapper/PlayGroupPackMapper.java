package com.ruoyi.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ruoyi.common.core.domain.entity.play.PlayGroupPack;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface PlayGroupPackMapper extends BaseMapper<PlayGroupPack> {

    PlayGroupPack selectGroupPackByPlayId(@Param("playId")String playId);
}
