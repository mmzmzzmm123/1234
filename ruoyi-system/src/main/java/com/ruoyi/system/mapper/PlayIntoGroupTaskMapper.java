package com.ruoyi.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ruoyi.system.domain.dto.play.PlayIntoGroupTask;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

public interface PlayIntoGroupTaskMapper extends BaseMapper<PlayIntoGroupTask> {

    /**
     * 批量插入
     * @param personIntoGroupTasks
     * @return
     */
    public int batchInsert(@Param("list") List<PlayIntoGroupTask> personIntoGroupTasks);


    /**
     *
     */
    public List<PlayIntoGroupTask> selectTaskList();

    /**
     * 根据操作编码查询入群记录
     *
     */
    public PlayIntoGroupTask selectTaskByCode(@Param("code")String code);


    Integer updateTaskByOutTime(@Param("outTime") Date outTime, @Param("failCause")String failCause);


    public Integer selectTaskByRetCount(@Param("retId")String retId);

    public Integer selectIsErrorCount(@Param("groupUrl")String groupUrl);


    public Integer updateTaskByErrorGroupId(@Param("groupUrl")String groupUrl);
}
