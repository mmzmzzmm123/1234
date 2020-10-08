package com.ruoyi.custom.service.impl;

import java.util.List;

import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.custom.mapper.SysOrderMapper;
import com.ruoyi.custom.domain.SysOrder;
import com.ruoyi.custom.service.ISysOrderService;

/**
 * 销售订单Service业务层处理
 *
 * @author wonder
 * @date 2020-09-24
 */
@Service
public class SysOrderServiceImpl implements ISysOrderService {
    @Autowired
    private SysOrderMapper sysOrderMapper;

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
        sysOrder.setCreateTime(DateUtils.getNowDate());
        sysOrder.setOrderTime(DateUtils.getNowDate());
        sysOrder.setOrderId(new Long(DateUtils.dateTimeNow()));
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
}