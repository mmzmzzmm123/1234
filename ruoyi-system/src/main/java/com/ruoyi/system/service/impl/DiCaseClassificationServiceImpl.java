package com.ruoyi.system.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.system.mapper.DiCaseClassificationMapper;
import com.ruoyi.system.domain.DiCaseClassification;
import com.ruoyi.system.service.IDiCaseClassificationService;

/**
 * 案例分类Service业务层处理
 * 
 * @author ruoyi
 * @date 2024-05-17
 */
@Service
public class DiCaseClassificationServiceImpl implements IDiCaseClassificationService 
{
    @Autowired
    private DiCaseClassificationMapper diCaseClassificationMapper;

    /**
     * 查询案例分类
     * 
     * @param id 案例分类主键
     * @return 案例分类
     */
    @Override
    public DiCaseClassification selectDiCaseClassificationById(Long id)
    {
        return diCaseClassificationMapper.selectDiCaseClassificationById(id);
    }

    /**
     * 查询案例分类列表
     * 
     * @param diCaseClassification 案例分类
     * @return 案例分类
     */
    @Override
    public List<DiCaseClassification> selectDiCaseClassificationList(DiCaseClassification diCaseClassification)
    {
        return diCaseClassificationMapper.selectDiCaseClassificationList(diCaseClassification);
    }

    /**
     * 新增案例分类
     * 
     * @param diCaseClassification 案例分类
     * @return 结果
     */
    @Override
    public int insertDiCaseClassification(DiCaseClassification diCaseClassification)
    {
        return diCaseClassificationMapper.insertDiCaseClassification(diCaseClassification);
    }

    /**
     * 修改案例分类
     * 
     * @param diCaseClassification 案例分类
     * @return 结果
     */
    @Override
    public int updateDiCaseClassification(DiCaseClassification diCaseClassification)
    {
        return diCaseClassificationMapper.updateDiCaseClassification(diCaseClassification);
    }

    /**
     * 批量删除案例分类
     * 
     * @param ids 需要删除的案例分类主键
     * @return 结果
     */
    @Override
    public int deleteDiCaseClassificationByIds(Long[] ids)
    {
        return diCaseClassificationMapper.deleteDiCaseClassificationByIds(ids);
    }

    /**
     * 删除案例分类信息
     * 
     * @param id 案例分类主键
     * @return 结果
     */
    @Override
    public int deleteDiCaseClassificationById(Long id)
    {
        return diCaseClassificationMapper.deleteDiCaseClassificationById(id);
    }
}
