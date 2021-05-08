package com.stdiet.custom.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.stdiet.custom.domain.SysCommision;
import com.stdiet.custom.domain.SysCustomer;
import com.stdiet.custom.domain.SysOrder;
import com.stdiet.custom.dto.response.EveryMonthTotalAmount;
import org.apache.ibatis.annotations.Param;

/**
 * 销售订单Service接口
 *
 * @author wonder
 * @date 2020-09-24
 */
public interface ISysOrderService
{
    /**
     * 查询销售订单
     *
     * @param orderId 销售订单ID
     * @return 销售订单
     */
    public SysOrder selectSysOrderById(Long orderId);

    /**
     * 查询销售订单列表
     *
     * @param sysOrder 销售订单
     * @return 销售订单集合
     */
    public List<SysOrder> selectSysOrderList(SysOrder sysOrder);

    /**
     * 新增销售订单
     *
     * @param sysOrder 销售订单
     * @return 结果
     */
    public int insertSysOrder(SysOrder sysOrder);

    /**
     * 修改销售订单
     *
     * @param sysOrder 销售订单
     * @return 结果
     */
    public int updateSysOrder(SysOrder sysOrder);

    /**
     * 批量删除销售订单
     *
     * @param orderIds 需要删除的销售订单ID
     * @return 结果
     */
    public int deleteSysOrderByIds(Long[] orderIds);

    /**
     * 删除销售订单信息
     *
     * @param orderId 销售订单ID
     * @return 结果
     */
    public int deleteSysOrderById(Long orderId);

    /**
     * 计算所有订单总额
     * @param sysOrder
     * @return
     */
    public BigDecimal selectAllOrderAmount(SysOrder sysOrder);

    /**
     * 暂停记录发生变更的时候需要更新该用户对应所有订单的开始时间、结束时间
     * @param cusId
     * @return
     */
    int updateOrderServerStartEndDate(Long cusId);

    /**
     * 查询客户订单数量
     * @param sysCustomer
     * @return
     */
    int getOrderCountByCustomer(SysCustomer sysCustomer);

    /**
     * 查询每年每月的总金额
     * @return
     */
    List<EveryMonthTotalAmount> getTotalAmountByUserId(SysCommision sysCommision);

    /**
     * 获取订单数量（按天提成计算）
     * @param sysCommision
     * @return
     */
    int selectSimpleOrderMessageCount(SysCommision sysCommision);

    /**
     * 更新订单的开始时间以及提成计算开始时间
     * @param sysOrder
     * @param date
     */
    void updateOrderStartTime(SysOrder sysOrder, LocalDate date);

    /**
     * 根据客户ID查询对应所有订单
     * @return
     */
    List<SysOrder> getAllOrderByCusId(Long cusId);

    /**
     * 根据openid查询订单数量
     * @param openid
     * @return
     */
    int getOrderCountByOpenId(String openid);
}