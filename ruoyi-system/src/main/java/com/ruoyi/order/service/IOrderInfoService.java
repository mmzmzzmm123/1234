package com.ruoyi.order.service;

import java.util.List;
import com.ruoyi.order.domain.OrderInfo;

/**
 * 订单信息Service接口
 *
 * @author Lam
 * @date 2023-09-15
 */
public interface IOrderInfoService {

    /**
     * 查询订单信息
     *
     * @param id 订单信息主键
     * @return 订单信息
     */
    public OrderInfo selectOrderInfoById(Long id);

    /**
     * 查询订单信息列表
     *
     * @param orderInfo 订单信息
     * @return 订单信息集合
     */
    public List<OrderInfo> selectOrderInfoList(OrderInfo orderInfo);

    /**
     * 新增订单信息
     *
     * @param orderInfo 订单信息
     * @return 结果
     */
    public int insertOrderInfo(OrderInfo orderInfo);

    /**
     * 修改订单信息
     *
     * @param orderInfo 订单信息
     * @return 结果
     */
    public int updateOrderInfo(OrderInfo orderInfo);

    /**
     * 批量删除订单信息
     *
     * @param ids 需要删除的订单信息主键集合
     * @return 结果
     */
    public int deleteOrderInfoByIds(Long[] ids);

    /**
     * 删除订单信息信息
     *
     * @param id 订单信息主键
     * @return 结果
     */
    public int deleteOrderInfoById(Long id);
}
