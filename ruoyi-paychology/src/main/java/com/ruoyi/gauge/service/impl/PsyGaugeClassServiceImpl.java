package com.ruoyi.gauge.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.gauge.mapper.PsyGaugeClassMapper;
import com.ruoyi.gauge.domain.PsyGaugeClass;
import com.ruoyi.gauge.service.IPsyGaugeClassService;

/**
 * 测评分类Service业务层处理
 * 
 * @author ruoyi
 * @date 2022-08-30
 */
@Service
public class PsyGaugeClassServiceImpl implements IPsyGaugeClassService 
{
    @Autowired
    private PsyGaugeClassMapper psyGaugeClassMapper;

    /**
     * 查询测评分类
     * 
     * @param id 测评分类主键
     * @return 测评分类
     */
    @Override
    public PsyGaugeClass selectPsyGaugeClassById(Integer id)
    {
        return psyGaugeClassMapper.selectPsyGaugeClassById(id);
    }

    /**
     * 查询测评分类列表
     * 
     * @param psyGaugeClass 测评分类
     * @return 测评分类
     */
    @Override
    public List<PsyGaugeClass> selectPsyGaugeClassList(PsyGaugeClass psyGaugeClass)
    {
        return psyGaugeClassMapper.selectPsyGaugeClassList(psyGaugeClass);
    }

    /**
     * 新增测评分类
     * 
     * @param psyGaugeClass 测评分类
     * @return 结果
     */
    @Override
    public int insertPsyGaugeClass(PsyGaugeClass psyGaugeClass)
    {
        return psyGaugeClassMapper.insertPsyGaugeClass(psyGaugeClass);
    }

    /**
     * 修改测评分类
     * 
     * @param psyGaugeClass 测评分类
     * @return 结果
     */
    @Override
    public int updatePsyGaugeClass(PsyGaugeClass psyGaugeClass)
    {
        return psyGaugeClassMapper.updatePsyGaugeClass(psyGaugeClass);
    }

    /**
     * 批量删除测评分类
     * 
     * @param ids 需要删除的测评分类主键
     * @return 结果
     */
    @Override
    public int deletePsyGaugeClassByIds(Integer[] ids)
    {
        return psyGaugeClassMapper.deletePsyGaugeClassByIds(ids);
    }

    /**
     * 删除测评分类信息
     * 
     * @param id 测评分类主键
     * @return 结果
     */
    @Override
    public int deletePsyGaugeClassById(Integer id)
    {
        return psyGaugeClassMapper.deletePsyGaugeClassById(id);
    }
}
