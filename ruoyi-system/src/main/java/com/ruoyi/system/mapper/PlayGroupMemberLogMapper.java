package com.ruoyi.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ruoyi.system.domain.dto.play.PlayGroupMemberLog;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

public interface PlayGroupMemberLogMapper extends BaseMapper<PlayGroupMemberLog> {
    List<PlayGroupMemberLog> selectGroupLogByPlayIdAll(@Param("playId")String playId,@Param("groupId")String groupId);

    PlayGroupMemberLog selectGroupLogByCode(@Param("code")String code);

    List<PlayGroupMemberLog> selectGroupLogByState();

    Integer updateTaskByOutTime(@Param("outTime")Date outTime);

    Integer updatePlayGroupMemberLogState(@Param("id")String id,@Param("state")Integer state);
}
