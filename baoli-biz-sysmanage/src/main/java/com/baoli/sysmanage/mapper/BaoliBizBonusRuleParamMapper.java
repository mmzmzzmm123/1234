package com.baoli.sysmanage.mapper;

import java.util.List;
import com.baoli.sysmanage.domain.BaoliBizBonusRuleParam;

/**
 * 提成规则参数Mapper接口
 * 
 * @author niujs
 * @date 2024-05-03
 */
public interface BaoliBizBonusRuleParamMapper 
{
    /**
     * 查询提成规则参数
     * 
     * @param id 提成规则参数主键
     * @return 提成规则参数
     */
    public BaoliBizBonusRuleParam selectBaoliBizBonusRuleParamById(Long id);

    /**
     * 查询提成规则参数列表
     * 
     * @param baoliBizBonusRuleParam 提成规则参数
     * @return 提成规则参数集合
     */
    public List<BaoliBizBonusRuleParam> selectBaoliBizBonusRuleParamList(BaoliBizBonusRuleParam baoliBizBonusRuleParam);

    /**
     * 新增提成规则参数
     * 
     * @param baoliBizBonusRuleParam 提成规则参数
     * @return 结果
     */
    public int insertBaoliBizBonusRuleParam(BaoliBizBonusRuleParam baoliBizBonusRuleParam);

    /**
     * 修改提成规则参数
     * 
     * @param baoliBizBonusRuleParam 提成规则参数
     * @return 结果
     */
    public int updateBaoliBizBonusRuleParam(BaoliBizBonusRuleParam baoliBizBonusRuleParam);

    /**
     * 删除提成规则参数
     * 
     * @param id 提成规则参数主键
     * @return 结果
     */
    public int deleteBaoliBizBonusRuleParamById(Long id);

    /**
     * 批量删除提成规则参数
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteBaoliBizBonusRuleParamByIds(Long[] ids);
}
