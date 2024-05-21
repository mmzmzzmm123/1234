package com.ruoyi.system.mapper;

import java.util.List;
import com.ruoyi.system.domain.DiCaseClassification;

/**
 * 案例分类Mapper接口
 * 
 * @author ruoyi
 * @date 2024-05-17
 */
public interface DiCaseClassificationMapper 
{
    /**
     * 查询案例分类
     * 
     * @param id 案例分类主键
     * @return 案例分类
     */
    public DiCaseClassification selectDiCaseClassificationById(Long id);

    /**
     * 查询案例分类列表
     * 
     * @param diCaseClassification 案例分类
     * @return 案例分类集合
     */
    public List<DiCaseClassification> selectDiCaseClassificationList(DiCaseClassification diCaseClassification);

    /**
     * 新增案例分类
     * 
     * @param diCaseClassification 案例分类
     * @return 结果
     */
    public int insertDiCaseClassification(DiCaseClassification diCaseClassification);

    /**
     * 修改案例分类
     * 
     * @param diCaseClassification 案例分类
     * @return 结果
     */
    public int updateDiCaseClassification(DiCaseClassification diCaseClassification);

    /**
     * 删除案例分类
     * 
     * @param id 案例分类主键
     * @return 结果
     */
    public int deleteDiCaseClassificationById(Long id);

    /**
     * 批量删除案例分类
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteDiCaseClassificationByIds(Long[] ids);
}
