package com.baoli.company.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.baoli.company.mapper.BaoliBizCompanyFeeRateRuleMapper;
import com.baoli.company.domain.BaoliBizCompanyFeeRateRule;
import com.baoli.company.service.IBaoliBizCompanyFeeRateRuleService;

/**
 * 对司费率规则Service业务层处理
 * 
 * @author niujs
 * @date 2024-05-10
 */
@Service
public class BaoliBizCompanyFeeRateRuleServiceImpl implements IBaoliBizCompanyFeeRateRuleService 
{
    @Autowired
    private BaoliBizCompanyFeeRateRuleMapper baoliBizCompanyFeeRateRuleMapper;

    /**
     * 查询对司费率规则
     * 
     * @param id 对司费率规则主键
     * @return 对司费率规则
     */
    @Override
    public BaoliBizCompanyFeeRateRule selectBaoliBizCompanyFeeRateRuleById(Long id)
    {
        return baoliBizCompanyFeeRateRuleMapper.selectBaoliBizCompanyFeeRateRuleById(id);
    }

    /**
     * 查询对司费率规则列表
     * 
     * @param baoliBizCompanyFeeRateRule 对司费率规则
     * @return 对司费率规则
     */
    @Override
    public List<BaoliBizCompanyFeeRateRule> selectBaoliBizCompanyFeeRateRuleList(BaoliBizCompanyFeeRateRule baoliBizCompanyFeeRateRule)
    {
        return baoliBizCompanyFeeRateRuleMapper.selectBaoliBizCompanyFeeRateRuleList(baoliBizCompanyFeeRateRule);
    }

    /**
     * 新增对司费率规则
     * 
     * @param baoliBizCompanyFeeRateRule 对司费率规则
     * @return 结果
     */
    @Override
    public int insertBaoliBizCompanyFeeRateRule(BaoliBizCompanyFeeRateRule baoliBizCompanyFeeRateRule)
    {
        return baoliBizCompanyFeeRateRuleMapper.insertBaoliBizCompanyFeeRateRule(baoliBizCompanyFeeRateRule);
    }

    /**
     * 修改对司费率规则
     * 
     * @param baoliBizCompanyFeeRateRule 对司费率规则
     * @return 结果
     */
    @Override
    public int updateBaoliBizCompanyFeeRateRule(BaoliBizCompanyFeeRateRule baoliBizCompanyFeeRateRule)
    {
        return baoliBizCompanyFeeRateRuleMapper.updateBaoliBizCompanyFeeRateRule(baoliBizCompanyFeeRateRule);
    }

    /**
     * 批量删除对司费率规则
     * 
     * @param ids 需要删除的对司费率规则主键
     * @return 结果
     */
    @Override
    public int deleteBaoliBizCompanyFeeRateRuleByIds(Long[] ids)
    {
        return baoliBizCompanyFeeRateRuleMapper.deleteBaoliBizCompanyFeeRateRuleByIds(ids);
    }

    /**
     * 删除对司费率规则信息
     * 
     * @param id 对司费率规则主键
     * @return 结果
     */
    @Override
    public int deleteBaoliBizCompanyFeeRateRuleById(Long id)
    {
        return baoliBizCompanyFeeRateRuleMapper.deleteBaoliBizCompanyFeeRateRuleById(id);
    }
}
