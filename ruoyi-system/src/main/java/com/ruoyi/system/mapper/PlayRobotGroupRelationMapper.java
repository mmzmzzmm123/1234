package com.ruoyi.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ruoyi.system.domain.dto.play.PlayRobotGroupRelation;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

public interface PlayRobotGroupRelationMapper extends BaseMapper<PlayRobotGroupRelation> {


    /**
     * 查询机器人是否在群内
     * @param groupId
     * @param robotId
     * @return
     */
    Integer selectRobotGroup(@Param("groupId")String groupId,@Param("robotId")String robotId);


    Integer selectRobotGroupCount(@Param("groupId")String groupId);

    List<PlayRobotGroupRelation> selectRobotByGroup(@Param("groupId")String groupId);

    public  Integer removeRobotByGroup(@Param("groupId")String groupId,@Param("list")List<String> robots);


    Integer updateRobotState(@Param("groupId")String groupId);

    List<PlayRobotGroupRelation> selectWaitOutGroup(@Param("time") Date time);

    Integer updateRobotOutState(@Param("groupId")String groupId,@Param("robotId")String robotId,@Param("state")Integer state);

    List<PlayRobotGroupRelation> selectWaitOutGroupByGroupId(@Param("groupId") String groupId);
}
