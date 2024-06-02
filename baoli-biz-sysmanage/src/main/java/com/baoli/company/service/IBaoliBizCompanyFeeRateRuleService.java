package com.baoli.company.service;

import java.util.List;
import com.baoli.company.domain.BaoliBizCompanyFeeRateRule;

/**
 * 对司费率规则Service接口
 * 
 * @author niujs
 * @date 2024-05-10
 */
public interface IBaoliBizCompanyFeeRateRuleService 
{
    /**
     * 查询对司费率规则
     * 
     * @param id 对司费率规则主键
     * @return 对司费率规则
     */
    public BaoliBizCompanyFeeRateRule selectBaoliBizCompanyFeeRateRuleById(Long id);

    /**
     * 查询对司费率规则列表
     * 
     * @param baoliBizCompanyFeeRateRule 对司费率规则
     * @return 对司费率规则集合
     */
    public List<BaoliBizCompanyFeeRateRule> selectBaoliBizCompanyFeeRateRuleList(BaoliBizCompanyFeeRateRule baoliBizCompanyFeeRateRule);

    /**
     * 新增对司费率规则
     * 
     * @param baoliBizCompanyFeeRateRule 对司费率规则
     * @return 结果
     */
    public int insertBaoliBizCompanyFeeRateRule(BaoliBizCompanyFeeRateRule baoliBizCompanyFeeRateRule);

    /**
     * 修改对司费率规则
     * 
     * @param baoliBizCompanyFeeRateRule 对司费率规则
     * @return 结果
     */
    public int updateBaoliBizCompanyFeeRateRule(BaoliBizCompanyFeeRateRule baoliBizCompanyFeeRateRule);

    /**
     * 批量删除对司费率规则
     * 
     * @param ids 需要删除的对司费率规则主键集合
     * @return 结果
     */
    public int deleteBaoliBizCompanyFeeRateRuleByIds(Long[] ids);

    /**
     * 删除对司费率规则信息
     * 
     * @param id 对司费率规则主键
     * @return 结果
     */
    public int deleteBaoliBizCompanyFeeRateRuleById(Long id);
}
