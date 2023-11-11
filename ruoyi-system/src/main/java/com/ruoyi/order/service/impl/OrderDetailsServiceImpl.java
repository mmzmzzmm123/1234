package com.ruoyi.order.service.impl;

import java.util.List;
import lombok.RequiredArgsConstructor;
import com.ruoyi.common.utils.SecurityUtils;
import org.springframework.stereotype.Service;
import com.ruoyi.order.mapper.OrderDetailsMapper;
import com.ruoyi.order.domain.OrderDetails;
import com.ruoyi.order.service.IOrderDetailsService;

/**
 * 订单明细Service业务层处理
 *
 * @author Lam
 * @date 2023-09-15
 */
@Service
@RequiredArgsConstructor
public class OrderDetailsServiceImpl implements IOrderDetailsService {

    private final OrderDetailsMapper orderDetailsMapper;

    /**
     * 查询订单明细
     *
     * @param id 订单明细主键
     * @return 订单明细
     */
    @Override
    public OrderDetails selectOrderDetailsById(Long id) {
        return orderDetailsMapper.selectOrderDetailsById(id);
    }

    /**
     * 查询订单明细列表
     *
     * @param orderDetails 订单明细
     * @return 订单明细
     */
    @Override
    public List<OrderDetails> selectOrderDetailsList(OrderDetails orderDetails) {
        return orderDetailsMapper.selectOrderDetailsList(orderDetails);
    }

    /**
     * 新增订单明细
     *
     * @param orderDetails 订单明细
     * @return 结果
     */
    @Override
    public int insertOrderDetails(OrderDetails orderDetails) {
        return orderDetailsMapper.insertOrderDetails(orderDetails);
    }

    /**
     * 修改订单明细
     *
     * @param orderDetails 订单明细
     * @return 结果
     */
    @Override
    public int updateOrderDetails(OrderDetails orderDetails) {
        return orderDetailsMapper.updateOrderDetails(orderDetails);
    }

    /**
     * 批量删除订单明细
     *
     * @param ids 需要删除的订单明细主键
     * @return 结果
     */
    @Override
    public int deleteOrderDetailsByIds(Long[] ids) {
        return orderDetailsMapper.deleteOrderDetailsByIds(ids);
    }

    /**
     * 删除订单明细信息
     *
     * @param id 订单明细主键
     * @return 结果
     */
    @Override
    public int deleteOrderDetailsById(Long id) {
        return orderDetailsMapper.deleteOrderDetailsById(id);
    }
}
