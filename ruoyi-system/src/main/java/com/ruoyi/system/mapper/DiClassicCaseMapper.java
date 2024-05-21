package com.ruoyi.system.mapper;

import java.util.List;
import com.ruoyi.system.domain.DiClassicCase;
import com.ruoyi.system.domain.DiCaseClassification;

/**
 * 典型案例Mapper接口
 * 
 * @author ruoyi
 * @date 2024-05-17
 */
public interface DiClassicCaseMapper 
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
     * 删除典型案例
     * 
     * @param id 典型案例主键
     * @return 结果
     */
    public int deleteDiClassicCaseById(Long id);

    /**
     * 批量删除典型案例
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteDiClassicCaseByIds(Long[] ids);

    /**
     * 批量删除案例分类
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteDiCaseClassificationByIds(Long[] ids);
    
    /**
     * 批量新增案例分类
     * 
     * @param diCaseClassificationList 案例分类列表
     * @return 结果
     */
    public int batchDiCaseClassification(List<DiCaseClassification> diCaseClassificationList);
    

    /**
     * 通过典型案例主键删除案例分类信息
     * 
     * @param id 典型案例ID
     * @return 结果
     */
    public int deleteDiCaseClassificationById(Long id);
}
