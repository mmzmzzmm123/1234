package com.ruoyi.udef.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.udef.mapper.DfRobotAlarmMapper;
import com.ruoyi.udef.domain.DfRobotAlarm;
import com.ruoyi.udef.service.IDfRobotAlarmService;

/**
 * 报警Service业务层处理
 * 
 * @author ruoyi
 * @date 2022-06-25
 */
@Service
public class DfRobotAlarmServiceImpl implements IDfRobotAlarmService 
{
    @Autowired
    private DfRobotAlarmMapper dfRobotAlarmMapper;

    /**
     * 查询报警
     * 
     * @param id 报警主键
     * @return 报警
     */
    @Override
    public DfRobotAlarm selectDfRobotAlarmById(Long id)
    {
        return dfRobotAlarmMapper.selectDfRobotAlarmById(id);
    }

    /**
     * 查询报警列表
     * 
     * @param dfRobotAlarm 报警
     * @return 报警
     */
    @Override
    public List<DfRobotAlarm> selectDfRobotAlarmList(DfRobotAlarm dfRobotAlarm)
    {
        return dfRobotAlarmMapper.selectDfRobotAlarmList(dfRobotAlarm);
    }

    /**
     * 新增报警
     * 
     * @param dfRobotAlarm 报警
     * @return 结果
     */
    @Override
    public int insertDfRobotAlarm(DfRobotAlarm dfRobotAlarm)
    {
        return dfRobotAlarmMapper.insertDfRobotAlarm(dfRobotAlarm);
    }

    /**
     * 修改报警
     * 
     * @param dfRobotAlarm 报警
     * @return 结果
     */
    @Override
    public int updateDfRobotAlarm(DfRobotAlarm dfRobotAlarm)
    {
        return dfRobotAlarmMapper.updateDfRobotAlarm(dfRobotAlarm);
    }

    /**
     * 批量删除报警
     * 
     * @param ids 需要删除的报警主键
     * @return 结果
     */
    @Override
    public int deleteDfRobotAlarmByIds(Long[] ids)
    {
        return dfRobotAlarmMapper.deleteDfRobotAlarmByIds(ids);
    }

    /**
     * 删除报警信息
     * 
     * @param id 报警主键
     * @return 结果
     */
    @Override
    public int deleteDfRobotAlarmById(Long id)
    {
        return dfRobotAlarmMapper.deleteDfRobotAlarmById(id);
    }
}
