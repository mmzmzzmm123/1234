package com.ruoyi.order.service.impl;

import java.util.Date;
import java.util.List;

import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.rocketmq.RocketMqService;
import com.ruoyi.common.rocketmq.constants.MqConstants;
import com.ruoyi.common.utils.DateUtils;
import lombok.RequiredArgsConstructor;
import com.ruoyi.common.utils.SecurityUtils;
import lombok.extern.log4j.Log4j;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import com.ruoyi.order.mapper.OrderInfoMapper;
import com.ruoyi.order.domain.OrderInfo;
import com.ruoyi.order.service.IOrderInfoService;

/**
 * 订单信息Service业务层处理
 *
 * @author Lam
 * @date 2023-09-15
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class OrderInfoServiceImpl implements IOrderInfoService {

    private final OrderInfoMapper orderInfoMapper;
    private final RocketMqService rocketMqService;

    /**
     * 查询订单信息
     *
     * @param id 订单信息主键
     * @return 订单信息
     */
    @Override
    public OrderInfo selectOrderInfoById(Long id) {
        return orderInfoMapper.selectOrderInfoById(id);
    }

    /**
     * 查询订单信息列表
     *
     * @param orderInfo 订单信息
     * @return 订单信息
     */
    @Override
    public List<OrderInfo> selectOrderInfoList(OrderInfo orderInfo) {
        return orderInfoMapper.selectOrderInfoList(orderInfo);
    }

    /**
     * 新增订单信息
     *
     * @param orderInfo 订单信息
     * @return 结果
     */
    @Override
    public int insertOrderInfo(OrderInfo orderInfo) {
        Date now = DateUtils.getNowDate();
        orderInfo.setCreateTime(now)
                .setUpdateTime(now);
        return orderInfoMapper.insertOrderInfo(orderInfo);
    }

    /**
     * 修改订单信息
     *
     * @param orderInfo 订单信息
     * @return 结果
     */
    @Override
    public int updateOrderInfo(OrderInfo orderInfo) {
        orderInfo.setUpdateTime(DateUtils.getNowDate());
        return orderInfoMapper.updateOrderInfo(orderInfo);
    }

    /**
     * 批量删除订单信息
     *
     * @param ids 需要删除的订单信息主键
     * @return 结果
     */
    @Override
    public int deleteOrderInfoByIds(Long[] ids) {
        return orderInfoMapper.deleteOrderInfoByIds(ids);
    }

    /**
     * 删除订单信息信息
     *
     * @param id 订单信息主键
     * @return 结果
     */
    @Override
    public int deleteOrderInfoById(Long id) {
        return orderInfoMapper.deleteOrderInfoById(id);
    }

    /**
     * 订单自动完成
     *
     * @param id 订单标识
     * @return 结果
     * */
    @Override
    public AjaxResult orderFinish(Long id) {
        log.info("admin-订单自动完成：开始，参数：{}", id);
        rocketMqService.asyncSend(MqConstants.TOPIC_ORDER_AUTO_SUCCESS, id);
        log.info("admin-订单自动完成：完成");
        return AjaxResult.success();
    }
}
