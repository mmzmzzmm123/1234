package com.baoli.order.mapper;

import java.util.List;
import com.baoli.order.domain.BaoliBizOrderLabel;

/**
 * 订单标签管理Mapper接口
 * 
 * @author niujs
 * @date 2024-04-24
 */
public interface BaoliBizOrderLabelMapper 
{
    /**
     * 查询订单标签管理
     * 
     * @param id 订单标签管理主键
     * @return 订单标签管理
     */
    public BaoliBizOrderLabel selectBaoliBizOrderLabelById(Long id);

    /**
     * 查询订单标签管理列表
     * 
     * @param baoliBizOrderLabel 订单标签管理
     * @return 订单标签管理集合
     */
    public List<BaoliBizOrderLabel> selectBaoliBizOrderLabelList(BaoliBizOrderLabel baoliBizOrderLabel);

    /**
     * 新增订单标签管理
     * 
     * @param baoliBizOrderLabel 订单标签管理
     * @return 结果
     */
    public int insertBaoliBizOrderLabel(BaoliBizOrderLabel baoliBizOrderLabel);

    /**
     * 修改订单标签管理
     * 
     * @param baoliBizOrderLabel 订单标签管理
     * @return 结果
     */
    public int updateBaoliBizOrderLabel(BaoliBizOrderLabel baoliBizOrderLabel);

    /**
     * 删除订单标签管理
     * 
     * @param id 订单标签管理主键
     * @return 结果
     */
    public int deleteBaoliBizOrderLabelById(Long id);

    /**
     * 批量删除订单标签管理
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteBaoliBizOrderLabelByIds(Long[] ids);
}
