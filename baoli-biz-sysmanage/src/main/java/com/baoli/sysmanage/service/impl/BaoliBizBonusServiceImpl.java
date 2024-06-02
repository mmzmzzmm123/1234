package com.baoli.sysmanage.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.baoli.sysmanage.mapper.BaoliBizBonusMapper;
import com.baoli.sysmanage.domain.BaoliBizBonus;
import com.baoli.sysmanage.service.IBaoliBizBonusService;

/**
 * 提成Service业务层处理
 * 
 * @author niujs
 * @date 2024-04-25
 */
@Service
public class BaoliBizBonusServiceImpl implements IBaoliBizBonusService 
{
    @Autowired
    private BaoliBizBonusMapper baoliBizBonusMapper;

    /**
     * 查询提成
     * 
     * @param id 提成主键
     * @return 提成
     */
    @Override
    public BaoliBizBonus selectBaoliBizBonusById(Long id)
    {
        return baoliBizBonusMapper.selectBaoliBizBonusById(id);
    }

    /**
     * 查询提成列表
     * 
     * @param baoliBizBonus 提成
     * @return 提成
     */
    @Override
    public List<BaoliBizBonus> selectBaoliBizBonusList(BaoliBizBonus baoliBizBonus)
    {
        return baoliBizBonusMapper.selectBaoliBizBonusList(baoliBizBonus);
    }

    /**
     * 新增提成
     * 
     * @param baoliBizBonus 提成
     * @return 结果
     */
    @Override
    public int insertBaoliBizBonus(BaoliBizBonus baoliBizBonus)
    {
        baoliBizBonus.setCreateTime(DateUtils.getNowDate());
        return baoliBizBonusMapper.insertBaoliBizBonus(baoliBizBonus);
    }

    /**
     * 修改提成
     * 
     * @param baoliBizBonus 提成
     * @return 结果
     */
    @Override
    public int updateBaoliBizBonus(BaoliBizBonus baoliBizBonus)
    {
        baoliBizBonus.setUpdateTime(DateUtils.getNowDate());
        return baoliBizBonusMapper.updateBaoliBizBonus(baoliBizBonus);
    }

    /**
     * 批量删除提成
     * 
     * @param ids 需要删除的提成主键
     * @return 结果
     */
    @Override
    public int deleteBaoliBizBonusByIds(Long[] ids)
    {
        return baoliBizBonusMapper.deleteBaoliBizBonusByIds(ids);
    }

    /**
     * 删除提成信息
     * 
     * @param id 提成主键
     * @return 结果
     */
    @Override
    public int deleteBaoliBizBonusById(Long id)
    {
        return baoliBizBonusMapper.deleteBaoliBizBonusById(id);
    }
}
