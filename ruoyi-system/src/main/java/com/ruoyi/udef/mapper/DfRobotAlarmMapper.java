package com.ruoyi.udef.mapper;

import java.util.List;
import com.ruoyi.udef.domain.DfRobotAlarm;

/**
 * 报警Mapper接口
 * 
 * @author ruoyi
 * @date 2022-06-25
 */
public interface DfRobotAlarmMapper 
{
    /**
     * 查询报警
     * 
     * @param id 报警主键
     * @return 报警
     */
    public DfRobotAlarm selectDfRobotAlarmById(Long id);

    /**
     * 查询报警列表
     * 
     * @param dfRobotAlarm 报警
     * @return 报警集合
     */
    public List<DfRobotAlarm> selectDfRobotAlarmList(DfRobotAlarm dfRobotAlarm);

    /**
     * 新增报警
     * 
     * @param dfRobotAlarm 报警
     * @return 结果
     */
    public int insertDfRobotAlarm(DfRobotAlarm dfRobotAlarm);

    /**
     * 修改报警
     * 
     * @param dfRobotAlarm 报警
     * @return 结果
     */
    public int updateDfRobotAlarm(DfRobotAlarm dfRobotAlarm);

    /**
     * 删除报警
     * 
     * @param id 报警主键
     * @return 结果
     */
    public int deleteDfRobotAlarmById(Long id);

    /**
     * 批量删除报警
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteDfRobotAlarmByIds(Long[] ids);
}
