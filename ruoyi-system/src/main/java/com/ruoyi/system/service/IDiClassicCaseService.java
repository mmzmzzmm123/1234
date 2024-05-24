package com.ruoyi.system.service;

import java.util.List;
import com.ruoyi.system.domain.DiClassicCase;

/**
 * 典型案例Service接口
 * 
 * @author ruoyi
 * @date 2024-05-17
 */
public interface IDiClassicCaseService 
{
    /**
     * 查询典型案例
     * 
     * @param id 典型案例主键
     * @return 典型案例
     */
    public DiClassicCase selectDiClassicCaseById(Long id);

    /**
     * 查询典型案例列表
     * 
     * @param diClassicCase 典型案例
     * @return 典型案例集合
     */
    public List<DiClassicCase> selectDiClassicCaseList(DiClassicCase diClassicCase);

    /**
     * 新增典型案例
     * 
     * @param diClassicCase 典型案例
     * @return 结果
     */
    public int insertDiClassicCase(DiClassicCase diClassicCase);

    /**
     * 修改典型案例
     * 
     * @param diClassicCase 典型案例
     * @return 结果
     */
    public int updateDiClassicCase(DiClassicCase diClassicCase);

    /**
     * 批量删除典型案例
     * 
     * @param ids 需要删除的典型案例主键集合
     * @return 结果
     */
    public int deleteDiClassicCaseByIds(Long[] ids);

    /**
     * 删除典型案例信息
     * 
     * @param id 典型案例主键
     * @return 结果
     */
    public int deleteDiClassicCaseById(Long id);
}
