package com.ruoyi.udef.mapper;

import java.util.List;
import com.ruoyi.udef.domain.DfEscapeSchedule;

/**
 * 计划Mapper接口
 * 
 * @author ruoyi
 * @date 2022-06-08
 */
public interface DfEscapeScheduleMapper 
{
    /**
     * 查询计划
     * 
     * @param id 计划主键
     * @return 计划
     */
    public DfEscapeSchedule selectDfEscapeScheduleById(Long id);

    /**
     * 查询计划列表
     * 
     * @param dfEscapeSchedule 计划
     * @return 计划集合
     */
    public List<DfEscapeSchedule> selectDfEscapeScheduleList(DfEscapeSchedule dfEscapeSchedule);

    /**
     * 新增计划
     * 
     * @param dfEscapeSchedule 计划
     * @return 结果
     */
    public int insertDfEscapeSchedule(DfEscapeSchedule dfEscapeSchedule);

    /**
     * 修改计划
     * 
     * @param dfEscapeSchedule 计划
     * @return 结果
     */
    public int updateDfEscapeSchedule(DfEscapeSchedule dfEscapeSchedule);

    /**
     * 删除计划
     * 
     * @param id 计划主键
     * @return 结果
     */
    public int deleteDfEscapeScheduleById(Long id);

    /**
     * 批量删除计划
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteDfEscapeScheduleByIds(Long[] ids);
}
