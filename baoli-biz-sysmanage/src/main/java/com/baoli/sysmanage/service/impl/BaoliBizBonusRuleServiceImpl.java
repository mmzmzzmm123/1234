package com.baoli.sysmanage.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.baoli.sysmanage.mapper.BaoliBizBonusRuleMapper;
import com.baoli.sysmanage.domain.BaoliBizBonusRule;
import com.baoli.sysmanage.service.IBaoliBizBonusRuleService;

/**
 * 提成规则Service业务层处理
 * 
 * @author niujs
 * @date 2024-05-03
 */
@Service
public class BaoliBizBonusRuleServiceImpl implements IBaoliBizBonusRuleService 
{
    @Autowired
    private BaoliBizBonusRuleMapper baoliBizBonusRuleMapper;

    /**
     * 查询提成规则
     * 
     * @param id 提成规则主键
     * @return 提成规则
     */
    @Override
    public BaoliBizBonusRule selectBaoliBizBonusRuleById(Long id)
    {
        return baoliBizBonusRuleMapper.selectBaoliBizBonusRuleById(id);
    }

    /**
     * 查询提成规则列表
     * 
     * @param baoliBizBonusRule 提成规则
     * @return 提成规则
     */
    @Override
    public List<BaoliBizBonusRule> selectBaoliBizBonusRuleList(BaoliBizBonusRule baoliBizBonusRule)
    {
        return baoliBizBonusRuleMapper.selectBaoliBizBonusRuleList(baoliBizBonusRule);
    }

    /**
     * 新增提成规则
     * 
     * @param baoliBizBonusRule 提成规则
     * @return 结果
     */
    @Override
    public int insertBaoliBizBonusRule(BaoliBizBonusRule baoliBizBonusRule)
    {
        return baoliBizBonusRuleMapper.insertBaoliBizBonusRule(baoliBizBonusRule);
    }

    /**
     * 修改提成规则
     * 
     * @param baoliBizBonusRule 提成规则
     * @return 结果
     */
    @Override
    public int updateBaoliBizBonusRule(BaoliBizBonusRule baoliBizBonusRule)
    {
        return baoliBizBonusRuleMapper.updateBaoliBizBonusRule(baoliBizBonusRule);
    }

    /**
     * 批量删除提成规则
     * 
     * @param ids 需要删除的提成规则主键
     * @return 结果
     */
    @Override
    public int deleteBaoliBizBonusRuleByIds(Long[] ids)
    {
        return baoliBizBonusRuleMapper.deleteBaoliBizBonusRuleByIds(ids);
    }

    /**
     * 删除提成规则信息
     * 
     * @param id 提成规则主键
     * @return 结果
     */
    @Override
    public int deleteBaoliBizBonusRuleById(Long id)
    {
        return baoliBizBonusRuleMapper.deleteBaoliBizBonusRuleById(id);
    }
}
