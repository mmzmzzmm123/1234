package com.baoli.store.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.baoli.store.mapper.BaoliBizFeeRateRuleMapper;
import com.baoli.store.domain.BaoliBizFeeRateRule;
import com.baoli.store.service.IBaoliBizFeeRateRuleService;

/**
 * 对店费率规则Service业务层处理
 * 
 * @author niujs
 * @date 2024-05-10
 */
@Service
public class BaoliBizFeeRateRuleServiceImpl implements IBaoliBizFeeRateRuleService 
{
    @Autowired
    private BaoliBizFeeRateRuleMapper baoliBizFeeRateRuleMapper;

    /**
     * 查询对店费率规则
     * 
     * @param id 对店费率规则主键
     * @return 对店费率规则
     */
    @Override
    public BaoliBizFeeRateRule selectBaoliBizFeeRateRuleById(Long id)
    {
        return baoliBizFeeRateRuleMapper.selectBaoliBizFeeRateRuleById(id);
    }

    /**
     * 查询对店费率规则列表
     * 
     * @param baoliBizFeeRateRule 对店费率规则
     * @return 对店费率规则
     */
    @Override
    public List<BaoliBizFeeRateRule> selectBaoliBizFeeRateRuleList(BaoliBizFeeRateRule baoliBizFeeRateRule)
    {
        return baoliBizFeeRateRuleMapper.selectBaoliBizFeeRateRuleList(baoliBizFeeRateRule);
    }

    /**
     * 新增对店费率规则
     * 
     * @param baoliBizFeeRateRule 对店费率规则
     * @return 结果
     */
    @Override
    public int insertBaoliBizFeeRateRule(BaoliBizFeeRateRule baoliBizFeeRateRule)
    {
        return baoliBizFeeRateRuleMapper.insertBaoliBizFeeRateRule(baoliBizFeeRateRule);
    }

    /**
     * 修改对店费率规则
     * 
     * @param baoliBizFeeRateRule 对店费率规则
     * @return 结果
     */
    @Override
    public int updateBaoliBizFeeRateRule(BaoliBizFeeRateRule baoliBizFeeRateRule)
    {
        return baoliBizFeeRateRuleMapper.updateBaoliBizFeeRateRule(baoliBizFeeRateRule);
    }

    /**
     * 批量删除对店费率规则
     * 
     * @param ids 需要删除的对店费率规则主键
     * @return 结果
     */
    @Override
    public int deleteBaoliBizFeeRateRuleByIds(Long[] ids)
    {
        return baoliBizFeeRateRuleMapper.deleteBaoliBizFeeRateRuleByIds(ids);
    }

    /**
     * 删除对店费率规则信息
     * 
     * @param id 对店费率规则主键
     * @return 结果
     */
    @Override
    public int deleteBaoliBizFeeRateRuleById(Long id)
    {
        return baoliBizFeeRateRuleMapper.deleteBaoliBizFeeRateRuleById(id);
    }
}
