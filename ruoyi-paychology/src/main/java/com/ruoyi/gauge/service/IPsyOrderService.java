package com.ruoyi.gauge.service;

import com.ruoyi.common.core.domain.dto.LoginDTO;
import com.ruoyi.gauge.domain.PsyOrder;
import com.ruoyi.gauge.dto.OrderQueryDTO;

import java.util.List;

/**
 * 心理测评订单信息Service接口
 * 
 * @author ruoyi
 * @date 2022-10-12
 */
public interface IPsyOrderService 
{
    /**
     * 查询心理测评订单信息
     * 
     * @param id 心理测评订单信息主键
     * @return 心理测评订单信息
     */
    public PsyOrder selectPsyOrderById(Integer id);

    /**
     * 查询心理测评订单信息列表
     * 
     * @param psyOrder 心理测评订单信息
     * @return 心理测评订单信息集合
     */
    public List<PsyOrder> selectPsyOrderList(PsyOrder psyOrder);

    public List<PsyOrder> queryOrderList(OrderQueryDTO psyOrder);

    public List<PsyOrder> getOrderByCancel(int num);

    /**
     * 新增心理测评订单信息
     * 
     * @param psyOrder 心理测评订单信息
     * @return 结果
     */
    public int insertPsyOrder(PsyOrder psyOrder);

    /**
     * 修改心理测评订单信息
     * 
     * @param psyOrder 心理测评订单信息
     * @return 结果
     */
    public int updatePsyOrder(PsyOrder psyOrder);

    /**
     * 修改心理测评订单信息  by  orderId
     *
     * @param psyOrder 心理测评订单信息
     * @return 结果
     */
    public int updatePsyOrderByOrderId(PsyOrder psyOrder);
    /**
     * 批量删除心理测评订单信息
     * 
     * @param ids 需要删除的心理测评订单信息主键集合
     * @return 结果
     */
    public int deletePsyOrderByIds(Integer[] ids);

    /**
     * 删除心理测评订单信息信息
     * 
     * @param id 心理测评订单信息主键
     * @return 结果
     */
    public int deletePsyOrderById(Integer id);

    List<PsyOrder> queryOrderInfo(PsyOrder psyOrder);

    int getMyReportNum(LoginDTO loginUser);

    int getOrderNumByGaugeId(Integer gaugeId);

    /**
     * 查询测评订单
     *
     * @param orderId 测评订单主键
     * @return 课程订单
     */
    public PsyOrder selectPsyOrderByOrderId(String orderId);

    /**
     * 生成测评订单
     *
     * @param psyOrder 测评订单
     * @return 生成的订单对象
     */
    public PsyOrder generatePsyOrder(PsyOrder psyOrder);

    /**
     * 根据测评ID查询测评是否已经购买
     *
     * @param userId 用户ID
     * @param gaugeId 测评ID
     * @return 测评是否已经购买
     */
    public Integer getGaugeIsBuy(Integer userId, Integer gaugeId);

    /**
     * 根据用户ID和测评ID查询测评订单信息
     *
     * @param userId 用户ID
     * @param gaugeId 测评ID
     * @return 订单信息
     */
    public List<PsyOrder> getPsyOrder(Integer userId, Integer gaugeId);
}
