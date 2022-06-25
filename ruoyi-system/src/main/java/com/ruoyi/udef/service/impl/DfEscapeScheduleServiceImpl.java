package com.ruoyi.udef.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.udef.mapper.DfEscapeScheduleMapper;
import com.ruoyi.udef.domain.DfEscapeSchedule;
import com.ruoyi.udef.service.IDfEscapeScheduleService;

/**
 * 计划Service业务层处理
 * 
 * @author ruoyi
 * @date 2022-06-08
 */
@Service
public class DfEscapeScheduleServiceImpl implements IDfEscapeScheduleService 
{
    @Autowired
    private DfEscapeScheduleMapper dfEscapeScheduleMapper;

    /**
     * 查询计划
     * 
     * @param id 计划主键
     * @return 计划
     */
    @Override
    public DfEscapeSchedule selectDfEscapeScheduleById(Long id)
    {
        return dfEscapeScheduleMapper.selectDfEscapeScheduleById(id);
    }

    /**
     * 查询计划列表
     * 
     * @param dfEscapeSchedule 计划
     * @return 计划
     */
    @Override
    public List<DfEscapeSchedule> selectDfEscapeScheduleList(DfEscapeSchedule dfEscapeSchedule)
    {
        return dfEscapeScheduleMapper.selectDfEscapeScheduleList(dfEscapeSchedule);
    }

    /**
     * 新增计划
     * 
     * @param dfEscapeSchedule 计划
     * @return 结果
     */
    @Override
    public int insertDfEscapeSchedule(DfEscapeSchedule dfEscapeSchedule)
    {
        return dfEscapeScheduleMapper.insertDfEscapeSchedule(dfEscapeSchedule);
    }

    /**
     * 修改计划
     * 
     * @param dfEscapeSchedule 计划
     * @return 结果
     */
    @Override
    public int updateDfEscapeSchedule(DfEscapeSchedule dfEscapeSchedule)
    {
        return dfEscapeScheduleMapper.updateDfEscapeSchedule(dfEscapeSchedule);
    }

    /**
     * 批量删除计划
     * 
     * @param ids 需要删除的计划主键
     * @return 结果
     */
    @Override
    public int deleteDfEscapeScheduleByIds(Long[] ids)
    {
        return dfEscapeScheduleMapper.deleteDfEscapeScheduleByIds(ids);
    }

    /**
     * 删除计划信息
     * 
     * @param id 计划主键
     * @return 结果
     */
    @Override
    public int deleteDfEscapeScheduleById(Long id)
    {
        return dfEscapeScheduleMapper.deleteDfEscapeScheduleById(id);
    }
}
