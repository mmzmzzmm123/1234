package com.ruoyi.system.service;

import java.util.List;

import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.portal.form.PayForm;
import com.ruoyi.system.domain.BusPostOrder;

/**
 * 订单Service接口
 * 
 * @author ruoyi
 * @date 2024-06-18
 */
public interface IBusPostOrderService 
{
    /**
     * 查询订单
     * 
     * @param orderId 订单主键
     * @return 订单
     */
    public BusPostOrder selectBusPostOrderByOrderId(Long orderId);

    /**
     * 查询订单列表
     * 
     * @param busPostOrder 订单
     * @return 订单集合
     */
    public List<BusPostOrder> selectBusPostOrderList(BusPostOrder busPostOrder);

    /**
     * 新增订单
     * 
     * @param busPostOrder 订单
     * @return 结果
     */
    public int insertBusPostOrder(BusPostOrder busPostOrder);

    /**
     * 修改订单
     * 
     * @param busPostOrder 订单
     * @return 结果
     */
    public int updateBusPostOrder(BusPostOrder busPostOrder);

    /**
     * 批量删除订单
     * 
     * @param orderIds 需要删除的订单主键集合
     * @return 结果
     */
    public int deleteBusPostOrderByOrderIds(Long[] orderIds);

    /**
     * 删除订单信息
     * 
     * @param orderId 订单主键
     * @return 结果
     */
    public int deleteBusPostOrderByOrderId(Long orderId);

}
