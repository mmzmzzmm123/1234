package com.baoli.sysmanage.mapper;

import java.util.List;
import com.baoli.sysmanage.domain.BaoliBizBonus;

/**
 * 提成Mapper接口
 * 
 * @author niujs
 * @date 2024-04-25
 */
public interface BaoliBizBonusMapper 
{
    /**
     * 查询提成
     * 
     * @param id 提成主键
     * @return 提成
     */
    public BaoliBizBonus selectBaoliBizBonusById(Long id);

    /**
     * 查询提成列表
     * 
     * @param baoliBizBonus 提成
     * @return 提成集合
     */
    public List<BaoliBizBonus> selectBaoliBizBonusList(BaoliBizBonus baoliBizBonus);

    /**
     * 新增提成
     * 
     * @param baoliBizBonus 提成
     * @return 结果
     */
    public int insertBaoliBizBonus(BaoliBizBonus baoliBizBonus);

    /**
     * 修改提成
     * 
     * @param baoliBizBonus 提成
     * @return 结果
     */
    public int updateBaoliBizBonus(BaoliBizBonus baoliBizBonus);

    /**
     * 删除提成
     * 
     * @param id 提成主键
     * @return 结果
     */
    public int deleteBaoliBizBonusById(Long id);

    /**
     * 批量删除提成
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteBaoliBizBonusByIds(Long[] ids);
}
