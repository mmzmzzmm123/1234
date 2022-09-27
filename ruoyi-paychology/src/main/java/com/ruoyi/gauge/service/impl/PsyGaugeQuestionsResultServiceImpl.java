package com.ruoyi.gauge.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.gauge.mapper.PsyGaugeQuestionsResultMapper;
import com.ruoyi.gauge.domain.PsyGaugeQuestionsResult;
import com.ruoyi.gauge.service.IPsyGaugeQuestionsResultService;

/**
 * 心理测评问题结果Service业务层处理
 * 
 * @author ruoyi
 * @date 2022-09-10
 */
@Service
public class PsyGaugeQuestionsResultServiceImpl implements IPsyGaugeQuestionsResultService 
{
    @Autowired
    private PsyGaugeQuestionsResultMapper psyGaugeQuestionsResultMapper;

    /**
     * 查询心理测评问题结果
     * 
     * @param id 心理测评问题结果主键
     * @return 心理测评问题结果
     */
    @Override
    public PsyGaugeQuestionsResult selectPsyGaugeQuestionsResultById(Long id)
    {
        return psyGaugeQuestionsResultMapper.selectPsyGaugeQuestionsResultById(id);
    }

    /**
     * 查询心理测评问题结果列表
     * 
     * @param psyGaugeQuestionsResult 心理测评问题结果
     * @return 心理测评问题结果
     */
    @Override
    public List<PsyGaugeQuestionsResult> selectPsyGaugeQuestionsResultList(PsyGaugeQuestionsResult psyGaugeQuestionsResult)
    {
        return psyGaugeQuestionsResultMapper.selectPsyGaugeQuestionsResultList(psyGaugeQuestionsResult);
    }

    /**
     * 新增心理测评问题结果
     * 
     * @param psyGaugeQuestionsResult 心理测评问题结果
     * @return 结果
     */
    @Override
    public int insertPsyGaugeQuestionsResult(PsyGaugeQuestionsResult psyGaugeQuestionsResult)
    {
        psyGaugeQuestionsResult.setCreateTime(DateUtils.getNowDate());
        return psyGaugeQuestionsResultMapper.insertPsyGaugeQuestionsResult(psyGaugeQuestionsResult);
    }

    /**
     * 修改心理测评问题结果
     * 
     * @param psyGaugeQuestionsResult 心理测评问题结果
     * @return 结果
     */
    @Override
    public int updatePsyGaugeQuestionsResult(PsyGaugeQuestionsResult psyGaugeQuestionsResult)
    {
        return psyGaugeQuestionsResultMapper.updatePsyGaugeQuestionsResult(psyGaugeQuestionsResult);
    }

    /**
     * 批量删除心理测评问题结果
     * 
     * @param ids 需要删除的心理测评问题结果主键
     * @return 结果
     */
    @Override
    public int deletePsyGaugeQuestionsResultByIds(Long[] ids)
    {
        return psyGaugeQuestionsResultMapper.deletePsyGaugeQuestionsResultByIds(ids);
    }

    /**
     * 删除心理测评问题结果信息
     * 
     * @param id 心理测评问题结果主键
     * @return 结果
     */
    @Override
    public int deletePsyGaugeQuestionsResultById(Long id)
    {
        return psyGaugeQuestionsResultMapper.deletePsyGaugeQuestionsResultById(id);
    }
}
