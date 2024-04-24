package com.baoli.order.service;

import java.util.List;
import java.util.Map;

import com.baoli.order.domain.BaoliBizOrder;

/**
 * 订单Service接口
 * 
 * @author niujs
 * @date 2024-04-07
 */
public interface IBaoliBizOrderService 
{
    /**
     * 查询订单
     * 
     * @param id 订单主键
     * @return 订单
     */
    public BaoliBizOrder selectBaoliBizOrderById(Long id);

    /**
     * 查询订单列表
     * 
     * @param baoliBizOrder 订单
     * @return 订单集合
     */
    public List<BaoliBizOrder> selectBaoliBizOrderList(BaoliBizOrder baoliBizOrder);
    public List<BaoliBizOrder> selectMyOrderList(BaoliBizOrder baoliBizOrder);
    public List<Map<String,Object>> selectMyOrder(BaoliBizOrder baoliBizOrder);

    /**
     * 新增订单
     * 
     * @param baoliBizOrder 订单
     * @return 结果
     */
    public int insertBaoliBizOrder(BaoliBizOrder baoliBizOrder);

    /**
     * 修改订单
     * 
     * @param baoliBizOrder 订单
     * @return 结果
     */
    public int updateBaoliBizOrder(BaoliBizOrder baoliBizOrder);

    /**
     * 批量删除订单
     * 
     * @param ids 需要删除的订单主键集合
     * @return 结果
     */
    public int deleteBaoliBizOrderByIds(Long[] ids);

    /**
     * 删除订单信息
     * 
     * @param id 订单主键
     * @return 结果
     */
    public int deleteBaoliBizOrderById(Long id);
}
