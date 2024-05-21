package com.ruoyi.system.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import com.ruoyi.common.utils.StringUtils;
import org.springframework.transaction.annotation.Transactional;
import com.ruoyi.system.domain.DiCaseClassification;
import com.ruoyi.system.mapper.DiClassicCaseMapper;
import com.ruoyi.system.domain.DiClassicCase;
import com.ruoyi.system.service.IDiClassicCaseService;

/**
 * 典型案例Service业务层处理
 * 
 * @author ruoyi
 * @date 2024-05-17
 */
@Service
public class DiClassicCaseServiceImpl implements IDiClassicCaseService 
{
    @Autowired
    private DiClassicCaseMapper diClassicCaseMapper;

    /**
     * 查询典型案例
     * 
     * @param id 典型案例主键
     * @return 典型案例
     */
    @Override
    public DiClassicCase selectDiClassicCaseById(Long id)
    {
        return diClassicCaseMapper.selectDiClassicCaseById(id);
    }

    /**
     * 查询典型案例列表
     * 
     * @param diClassicCase 典型案例
     * @return 典型案例
     */
    @Override
    public List<DiClassicCase> selectDiClassicCaseList(DiClassicCase diClassicCase)
    {
        return diClassicCaseMapper.selectDiClassicCaseList(diClassicCase);
    }

    /**
     * 新增典型案例
     * 
     * @param diClassicCase 典型案例
     * @return 结果
     */
    @Transactional
    @Override
    public int insertDiClassicCase(DiClassicCase diClassicCase)
    {
        int rows = diClassicCaseMapper.insertDiClassicCase(diClassicCase);
        insertDiCaseClassification(diClassicCase);
        return rows;
    }

    /**
     * 修改典型案例
     * 
     * @param diClassicCase 典型案例
     * @return 结果
     */
    @Transactional
    @Override
    public int updateDiClassicCase(DiClassicCase diClassicCase)
    {
        diClassicCaseMapper.deleteDiCaseClassificationById(diClassicCase.getId());
        insertDiCaseClassification(diClassicCase);
        return diClassicCaseMapper.updateDiClassicCase(diClassicCase);
    }

    /**
     * 批量删除典型案例
     * 
     * @param ids 需要删除的典型案例主键
     * @return 结果
     */
    @Transactional
    @Override
    public int deleteDiClassicCaseByIds(Long[] ids)
    {
        diClassicCaseMapper.deleteDiCaseClassificationByIds(ids);
        return diClassicCaseMapper.deleteDiClassicCaseByIds(ids);
    }

    /**
     * 删除典型案例信息
     * 
     * @param id 典型案例主键
     * @return 结果
     */
    @Transactional
    @Override
    public int deleteDiClassicCaseById(Long id)
    {
        diClassicCaseMapper.deleteDiCaseClassificationById(id);
        return diClassicCaseMapper.deleteDiClassicCaseById(id);
    }

    /**
     * 新增案例分类信息
     * 
     * @param diClassicCase 典型案例对象
     */
    public void insertDiCaseClassification(DiClassicCase diClassicCase)
    {
        List<DiCaseClassification> diCaseClassificationList = diClassicCase.getDiCaseClassificationList();
        Long id = diClassicCase.getId();
        if (StringUtils.isNotNull(diCaseClassificationList))
        {
            List<DiCaseClassification> list = new ArrayList<DiCaseClassification>();
            for (DiCaseClassification diCaseClassification : diCaseClassificationList)
            {
                diCaseClassification.setId(id);
                list.add(diCaseClassification);
            }
            if (list.size() > 0)
            {
                diClassicCaseMapper.batchDiCaseClassification(list);
            }
        }
    }
}
