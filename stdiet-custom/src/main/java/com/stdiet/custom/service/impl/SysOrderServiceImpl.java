package com.stdiet.custom.service.impl;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.stdiet.common.utils.DateUtils;
import com.stdiet.custom.domain.SysOrderCommisionDayDetail;
import com.stdiet.custom.domain.SysOrderPause;
import com.stdiet.custom.service.ISysCommissionDayService;
import com.stdiet.custom.service.ISysOrderPauseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.stdiet.custom.mapper.SysOrderMapper;
import com.stdiet.custom.domain.SysOrder;
import com.stdiet.custom.service.ISysOrderService;
import org.springframework.transaction.annotation.Transactional;

/**
 * 销售订单Service业务层处理
 *
 * @author wonder
 * @date 2020-09-24
 */
@Service
@Transactional
public class SysOrderServiceImpl implements ISysOrderService {
    @Autowired
    private SysOrderMapper sysOrderMapper;

    @Autowired
    private ISysCommissionDayService sysCommissionDayService;

    /**
     * 查询销售订单
     *
     * @param orderId 销售订单ID
     * @return 销售订单
     */
    @Override
    public SysOrder selectSysOrderById(Long orderId) {
        return sysOrderMapper.selectSysOrderById(orderId);
    }

    /**
     * 查询销售订单列表
     *
     * @param sysOrder 销售订单
     * @return 销售订单
     */
    @Override
    public List<SysOrder> selectSysOrderList(SysOrder sysOrder) {
        return sysOrderMapper.selectSysOrderList(sysOrder);
    }

    /**
     * 新增销售订单
     *
     * @param sysOrder 销售订单
     * @return 结果
     */
    @Override
    public int insertSysOrder(SysOrder sysOrder) {
        Date orderTime = DateUtils.getNowDate();
        sysOrder.setCreateTime(orderTime);
//        sysOrder.setOrderTime(orderTime);
        //计算服务到期时间
        setOrderServerEndDate(sysOrder);
        sysOrder.setOrderId(Long.parseLong(DateUtils.parseDateToStr(DateUtils.YYYYMMDDHHMMSS, orderTime)));
        return sysOrderMapper.insertSysOrder(sysOrder);
    }

    /**
     * 修改销售订单
     *
     * @param sysOrder 销售订单
     * @return 结果
     */
    @Override
    public int updateSysOrder(SysOrder sysOrder) {
        sysOrder.setUpdateTime(DateUtils.getNowDate());
        //计算服务到期时间
        setOrderServerEndDate(sysOrder);
        return sysOrderMapper.updateSysOrder(sysOrder);
    }

    /**
     * 批量删除销售订单
     *
     * @param orderIds 需要删除的销售订单ID
     * @return 结果
     */
    @Override
    public int deleteSysOrderByIds(Long[] orderIds) {
        return sysOrderMapper.deleteSysOrderByIds(orderIds);
    }

    /**
     * 删除销售订单信息
     *
     * @param orderId 销售订单ID
     * @return 结果
     */
    @Override
    public int deleteSysOrderById(Long orderId) {
        return sysOrderMapper.deleteSysOrderById(orderId);
    }

    /**
     * 计算所有订单总额
     * @param sysOrder
     * @return
     */
    @Override
    public BigDecimal selectAllOrderAmount(SysOrder sysOrder) {
        return sysOrderMapper.selectAllOrderAmount(sysOrder);
    }

    /**
     * 根据订单ID更新该订单的服务到期时间
     * @param orderId
     * @return
     */
    @Override
    public int updateOrderServerEndDate(Long orderId){
        //更新订单服务到期时间
        SysOrder sysOrder = selectSysOrderById(orderId);
        if(sysOrder != null){
            //设置服务到期时间
            setOrderServerEndDate(sysOrder);
            sysOrder.setUpdateTime(new Date());
            return updateSysOrder(sysOrder);
        }
        return 0;
    }

    /**
     * 给对象SysOrder对象设置服务到期时间
     * @param sysOrder
     */
    private void setOrderServerEndDate(SysOrder sysOrder){
        sysOrder.setServerEndTime(DateUtils.localDateToDate(sysCommissionDayService.getServerEndDate(sysOrder)));
    }
}