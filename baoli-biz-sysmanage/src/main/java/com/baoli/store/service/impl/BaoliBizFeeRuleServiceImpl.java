package com.baoli.store.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.baoli.store.mapper.BaoliBizFeeRuleMapper;
import com.baoli.store.domain.BaoliBizFeeRule;
import com.baoli.store.service.IBaoliBizFeeRuleService;

/**
 * 费用规则Service业务层处理
 * 
 * @author niujs
 * @date 2024-05-23
 */
@Service
public class BaoliBizFeeRuleServiceImpl implements IBaoliBizFeeRuleService 
{
    @Autowired
    private BaoliBizFeeRuleMapper baoliBizFeeRuleMapper;

    /**
     * 查询费用规则
     * 
     * @param id 费用规则主键
     * @return 费用规则
     */
    @Override
    public BaoliBizFeeRule selectBaoliBizFeeRuleById(Long id)
    {
        return baoliBizFeeRuleMapper.selectBaoliBizFeeRuleById(id);
    }

    /**
     * 查询费用规则列表
     * 
     * @param baoliBizFeeRule 费用规则
     * @return 费用规则
     */
    @Override
    public List<BaoliBizFeeRule> selectBaoliBizFeeRuleList(BaoliBizFeeRule baoliBizFeeRule)
    {
        return baoliBizFeeRuleMapper.selectBaoliBizFeeRuleList(baoliBizFeeRule);
    }

    /**
     * 新增费用规则
     * 
     * @param baoliBizFeeRule 费用规则
     * @return 结果
     */
    @Override
    public int insertBaoliBizFeeRule(BaoliBizFeeRule baoliBizFeeRule)
    {
        return baoliBizFeeRuleMapper.insertBaoliBizFeeRule(baoliBizFeeRule);
    }

    /**
     * 修改费用规则
     * 
     * @param baoliBizFeeRule 费用规则
     * @return 结果
     */
    @Override
    public int updateBaoliBizFeeRule(BaoliBizFeeRule baoliBizFeeRule)
    {
        return baoliBizFeeRuleMapper.updateBaoliBizFeeRule(baoliBizFeeRule);
    }

    /**
     * 批量删除费用规则
     * 
     * @param ids 需要删除的费用规则主键
     * @return 结果
     */
    @Override
    public int deleteBaoliBizFeeRuleByIds(Long[] ids)
    {
        return baoliBizFeeRuleMapper.deleteBaoliBizFeeRuleByIds(ids);
    }

    /**
     * 删除费用规则信息
     * 
     * @param id 费用规则主键
     * @return 结果
     */
    @Override
    public int deleteBaoliBizFeeRuleById(Long id)
    {
        return baoliBizFeeRuleMapper.deleteBaoliBizFeeRuleById(id);
    }
}
