package com.ruoyi.system.mapper;

import java.util.List;
import com.ruoyi.system.domain.WorkConcentrate;

/**
 * 【请填写功能名称】Mapper接口
 * 
 * @author ruoyi
 * @date 2022-08-05
 */
public interface WorkConcentrateMapper 
{
    /**
     * 查询【请填写功能名称】
     * 
     * @param concentrateId 【请填写功能名称】主键
     * @return 【请填写功能名称】
     */
    public WorkConcentrate selectWorkConcentrateByConcentrateId(Long concentrateId);

    /**
     * 查询【请填写功能名称】列表
     * 
     * @param workConcentrate 【请填写功能名称】
     * @return 【请填写功能名称】集合
     */
    public List<WorkConcentrate> selectWorkConcentrateList(WorkConcentrate workConcentrate);

    /**
     * 新增【请填写功能名称】
     * 
     * @param workConcentrate 【请填写功能名称】
     * @return 结果
     */
    public int insertWorkConcentrate(WorkConcentrate workConcentrate);

    /**
     * 修改【请填写功能名称】
     * 
     * @param workConcentrate 【请填写功能名称】
     * @return 结果
     */
    public int updateWorkConcentrate(WorkConcentrate workConcentrate);

    /**
     * 删除【请填写功能名称】
     * 
     * @param concentrateId 【请填写功能名称】主键
     * @return 结果
     */
    public int deleteWorkConcentrateByConcentrateId(Long concentrateId);

    /**
     * 批量删除【请填写功能名称】
     * 
     * @param concentrateIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteWorkConcentrateByConcentrateIds(Long[] concentrateIds);
}
