package com.baoli.store.service;

import java.util.List;
import com.baoli.store.domain.BaoliBizFeeRule;

/**
 * 费用规则Service接口
 * 
 * @author niujs
 * @date 2024-04-17
 */
public interface IBaoliBizFeeRuleService 
{
    /**
     * 查询费用规则
     * 
     * @param id 费用规则主键
     * @return 费用规则
     */
    public BaoliBizFeeRule selectBaoliBizFeeRuleById(Long id);

    /**
     * 查询费用规则列表
     * 
     * @param baoliBizFeeRule 费用规则
     * @return 费用规则集合
     */
    public List<BaoliBizFeeRule> selectBaoliBizFeeRuleList(BaoliBizFeeRule baoliBizFeeRule);

    /**
     * 新增费用规则
     * 
     * @param baoliBizFeeRule 费用规则
     * @return 结果
     */
    public int insertBaoliBizFeeRule(BaoliBizFeeRule baoliBizFeeRule);

    /**
     * 修改费用规则
     * 
     * @param baoliBizFeeRule 费用规则
     * @return 结果
     */
    public int updateBaoliBizFeeRule(BaoliBizFeeRule baoliBizFeeRule);

    /**
     * 批量删除费用规则
     * 
     * @param ids 需要删除的费用规则主键集合
     * @return 结果
     */
    public int deleteBaoliBizFeeRuleByIds(Long[] ids);

    /**
     * 删除费用规则信息
     * 
     * @param id 费用规则主键
     * @return 结果
     */
    public int deleteBaoliBizFeeRuleById(Long id);
}
