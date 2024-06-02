package com.baoli.store.mapper;

import java.util.List;
import com.baoli.store.domain.BaoliBizFeeRule;

/**
 * 费用规则Mapper接口
 * 
 * @author niujs
 * @date 2024-05-23
 */
public interface BaoliBizFeeRuleMapper 
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
     * 删除费用规则
     * 
     * @param id 费用规则主键
     * @return 结果
     */
    public int deleteBaoliBizFeeRuleById(Long id);

    /**
     * 批量删除费用规则
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteBaoliBizFeeRuleByIds(Long[] ids);
}
