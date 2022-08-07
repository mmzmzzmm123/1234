package com.ruoyi.system.mapper;

import java.util.List;
import com.ruoyi.system.domain.WorkExtraction;

/**
 * 【请填写功能名称】Mapper接口
 * 
 * @author ruoyi
 * @date 2022-08-05
 */
public interface WorkExtractionMapper 
{
    /**
     * 查询【请填写功能名称】
     * 
     * @param extractionId 【请填写功能名称】主键
     * @return 【请填写功能名称】
     */
    public WorkExtraction selectWorkExtractionByExtractionId(Long extractionId);

    /**
     * 查询【请填写功能名称】列表
     * 
     * @param workExtraction 【请填写功能名称】
     * @return 【请填写功能名称】集合
     */
    public List<WorkExtraction> selectWorkExtractionList(WorkExtraction workExtraction);

    /**
     * 新增【请填写功能名称】
     * 
     * @param workExtraction 【请填写功能名称】
     * @return 结果
     */
    public int insertWorkExtraction(WorkExtraction workExtraction);

    /**
     * 修改【请填写功能名称】
     * 
     * @param workExtraction 【请填写功能名称】
     * @return 结果
     */
    public int updateWorkExtraction(WorkExtraction workExtraction);

    /**
     * 删除【请填写功能名称】
     * 
     * @param extractionId 【请填写功能名称】主键
     * @return 结果
     */
    public int deleteWorkExtractionByExtractionId(Long extractionId);

    /**
     * 批量删除【请填写功能名称】
     * 
     * @param extractionIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteWorkExtractionByExtractionIds(Long[] extractionIds);
}
