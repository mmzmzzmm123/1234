package com.baoli.company.mapper;

import java.util.List;
import com.baoli.company.domain.BaoliBizCompanyFeeRateRule;

/**
 * 对司费率规则Mapper接口
 * 
 * @author niujs
 * @date 2024-05-10
 */
public interface BaoliBizCompanyFeeRateRuleMapper 
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
     * 删除对司费率规则
     * 
     * @param id 对司费率规则主键
     * @return 结果
     */
    public int deleteBaoliBizCompanyFeeRateRuleById(Long id);

    /**
     * 批量删除对司费率规则
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteBaoliBizCompanyFeeRateRuleByIds(Long[] ids);
}
