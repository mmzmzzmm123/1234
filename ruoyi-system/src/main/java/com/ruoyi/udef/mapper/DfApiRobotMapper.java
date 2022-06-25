package com.ruoyi.udef.mapper;

import java.util.List;
import com.ruoyi.udef.domain.DfApiRobot;
import com.ruoyi.udef.domain.DfEscapeSchedule;

/**
 * 机器人Mapper接口
 * 
 * @author ruoyi
 * @date 2022-06-08
 */
public interface DfApiRobotMapper 
{
    /**
     * 查询机器人
     * 
     * @param id 机器人主键
     * @return 机器人
     */
    public DfApiRobot selectDfApiRobotById(Long id);

    /**
     * 查询机器人列表
     * 
     * @param dfApiRobot 机器人
     * @return 机器人集合
     */
    public List<DfApiRobot> selectDfApiRobotList(DfApiRobot dfApiRobot);

    /**
     * 新增机器人
     * 
     * @param dfApiRobot 机器人
     * @return 结果
     */
    public int insertDfApiRobot(DfApiRobot dfApiRobot);

    /**
     * 修改机器人
     * 
     * @param dfApiRobot 机器人
     * @return 结果
     */
    public int updateDfApiRobot(DfApiRobot dfApiRobot);

    /**
     * 删除机器人
     * 
     * @param id 机器人主键
     * @return 结果
     */
    public int deleteDfApiRobotById(Long id);

    /**
     * 批量删除机器人
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteDfApiRobotByIds(Long[] ids);

    /**
     * 批量删除计划
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteDfEscapeScheduleByRobotIds(Long[] ids);
    
    /**
     * 批量新增计划
     * 
     * @param dfEscapeScheduleList 计划列表
     * @return 结果
     */
    public int batchDfEscapeSchedule(List<DfEscapeSchedule> dfEscapeScheduleList);
    

    /**
     * 通过机器人主键删除计划信息
     * 
     * @param id 机器人ID
     * @return 结果
     */
    public int deleteDfEscapeScheduleByRobotId(Long id);
}
