package com.ruoyi.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ruoyi.common.core.domain.entity.play.PlayRobotPack;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface PlayRobotPackMapper extends BaseMapper<PlayRobotPack> {

    /**
     * 根据剧本ID查询演员配置
     */
    List<PlayRobotPack> selectPackByPlayId(@Param("playId")String playId);
}
