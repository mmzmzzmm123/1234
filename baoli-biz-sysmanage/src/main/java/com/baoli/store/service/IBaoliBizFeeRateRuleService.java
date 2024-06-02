package com.baoli.store.service;

import java.util.List;
import com.baoli.store.domain.BaoliBizFeeRateRule;

/**
 * 对店费率规则Service接口
 * 
 * @author niujs
 * @date 2024-05-10
 */
public interface IBaoliBizFeeRateRuleService 
{
    /**
     * 查询对店费率规则
     * 
     * @param id 对店费率规则主键
     * @return 对店费率规则
     */
    public BaoliBizFeeRateRule selectBaoliBizFeeRateRuleById(Long id);

    /**
     * 查询对店费率规则列表
     * 
     * @param baoliBizFeeRateRule 对店费率规则
     * @return 对店费率规则集合
     */
    public List<BaoliBizFeeRateRule> selectBaoliBizFeeRateRuleList(BaoliBizFeeRateRule baoliBizFeeRateRule);

    /**
     * 新增对店费率规则
     * 
     * @param baoliBizFeeRateRule 对店费率规则
     * @return 结果
     */
    public int insertBaoliBizFeeRateRule(BaoliBizFeeRateRule baoliBizFeeRateRule);

    /**
     * 修改对店费率规则
     * 
     * @param baoliBizFeeRateRule 对店费率规则
     * @return 结果
     */
    public int updateBaoliBizFeeRateRule(BaoliBizFeeRateRule baoliBizFeeRateRule);

    /**
     * 批量删除对店费率规则
     * 
     * @param ids 需要删除的对店费率规则主键集合
     * @return 结果
     */
    public int deleteBaoliBizFeeRateRuleByIds(Long[] ids);

    /**
     * 删除对店费率规则信息
     * 
     * @param id 对店费率规则主键
     * @return 结果
     */
    public int deleteBaoliBizFeeRateRuleById(Long id);
}
