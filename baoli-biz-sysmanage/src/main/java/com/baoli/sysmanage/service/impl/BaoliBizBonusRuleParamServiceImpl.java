package com.baoli.sysmanage.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.baoli.sysmanage.mapper.BaoliBizBonusRuleParamMapper;
import com.baoli.sysmanage.domain.BaoliBizBonusRuleParam;
import com.baoli.sysmanage.service.IBaoliBizBonusRuleParamService;

/**
 * 提成规则参数Service业务层处理
 * 
 * @author niujs
 * @date 2024-05-03
 */
@Service
public class BaoliBizBonusRuleParamServiceImpl implements IBaoliBizBonusRuleParamService 
{
    @Autowired
    private BaoliBizBonusRuleParamMapper baoliBizBonusRuleParamMapper;

    /**
     * 查询提成规则参数
     * 
     * @param id 提成规则参数主键
     * @return 提成规则参数
     */
    @Override
    public BaoliBizBonusRuleParam selectBaoliBizBonusRuleParamById(Long id)
    {
        return baoliBizBonusRuleParamMapper.selectBaoliBizBonusRuleParamById(id);
    }

    /**
     * 查询提成规则参数列表
     * 
     * @param baoliBizBonusRuleParam 提成规则参数
     * @return 提成规则参数
     */
    @Override
    public List<BaoliBizBonusRuleParam> selectBaoliBizBonusRuleParamList(BaoliBizBonusRuleParam baoliBizBonusRuleParam)
    {
        return baoliBizBonusRuleParamMapper.selectBaoliBizBonusRuleParamList(baoliBizBonusRuleParam);
    }

    /**
     * 新增提成规则参数
     * 
     * @param baoliBizBonusRuleParam 提成规则参数
     * @return 结果
     */
    @Override
    public int insertBaoliBizBonusRuleParam(BaoliBizBonusRuleParam baoliBizBonusRuleParam)
    {
        return baoliBizBonusRuleParamMapper.insertBaoliBizBonusRuleParam(baoliBizBonusRuleParam);
    }

    /**
     * 修改提成规则参数
     * 
     * @param baoliBizBonusRuleParam 提成规则参数
     * @return 结果
     */
    @Override
    public int updateBaoliBizBonusRuleParam(BaoliBizBonusRuleParam baoliBizBonusRuleParam)
    {
        return baoliBizBonusRuleParamMapper.updateBaoliBizBonusRuleParam(baoliBizBonusRuleParam);
    }

    /**
     * 批量删除提成规则参数
     * 
     * @param ids 需要删除的提成规则参数主键
     * @return 结果
     */
    @Override
    public int deleteBaoliBizBonusRuleParamByIds(Long[] ids)
    {
        return baoliBizBonusRuleParamMapper.deleteBaoliBizBonusRuleParamByIds(ids);
    }

    /**
     * 删除提成规则参数信息
     * 
     * @param id 提成规则参数主键
     * @return 结果
     */
    @Override
    public int deleteBaoliBizBonusRuleParamById(Long id)
    {
        return baoliBizBonusRuleParamMapper.deleteBaoliBizBonusRuleParamById(id);
    }
}
