package com.baoli.sysmanage.mapper;

import java.util.List;
import com.baoli.sysmanage.domain.BaoliBizBonusRule;

/**
 * 提成规则Mapper接口
 * 
 * @author niujs
 * @date 2024-05-03
 */
public interface BaoliBizBonusRuleMapper 
{
    /**
     * 查询提成规则
     * 
     * @param id 提成规则主键
     * @return 提成规则
     */
    public BaoliBizBonusRule selectBaoliBizBonusRuleById(Long id);

    /**
     * 查询提成规则列表
     * 
     * @param baoliBizBonusRule 提成规则
     * @return 提成规则集合
     */
    public List<BaoliBizBonusRule> selectBaoliBizBonusRuleList(BaoliBizBonusRule baoliBizBonusRule);

    /**
     * 新增提成规则
     * 
     * @param baoliBizBonusRule 提成规则
     * @return 结果
     */
    public int insertBaoliBizBonusRule(BaoliBizBonusRule baoliBizBonusRule);

    /**
     * 修改提成规则
     * 
     * @param baoliBizBonusRule 提成规则
     * @return 结果
     */
    public int updateBaoliBizBonusRule(BaoliBizBonusRule baoliBizBonusRule);

    /**
     * 删除提成规则
     * 
     * @param id 提成规则主键
     * @return 结果
     */
    public int deleteBaoliBizBonusRuleById(Long id);

    /**
     * 批量删除提成规则
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteBaoliBizBonusRuleByIds(Long[] ids);
}
