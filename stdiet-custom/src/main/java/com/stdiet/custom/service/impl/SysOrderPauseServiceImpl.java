package com.stdiet.custom.service.impl;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import com.stdiet.common.utils.DateUtils;
import com.stdiet.custom.domain.SysOrder;
import com.stdiet.custom.service.ISysCommissionDayService;
import com.stdiet.custom.service.ISysOrderService;
import com.stdiet.custom.service.ISysRecipesPlanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.stdiet.custom.mapper.SysOrderPauseMapper;
import com.stdiet.custom.domain.SysOrderPause;
import com.stdiet.custom.service.ISysOrderPauseService;
import org.springframework.transaction.annotation.Transactional;

/**
 * 订单服务暂停Service业务层处理
 *
 * @author wonder
 * @date 2021-01-07
 */
@Service
@Transactional
public class SysOrderPauseServiceImpl implements ISysOrderPauseService
{
    @Autowired
    private SysOrderPauseMapper sysOrderPauseMapper;

    @Autowired
    private ISysOrderService sysOrderService;

    /**
     * 查询订单服务暂停
     *
     * @param id 订单服务暂停ID
     * @return 订单服务暂停
     */
    @Override
    public SysOrderPause selectSysOrderPauseById(Long id)
    {
        return sysOrderPauseMapper.selectSysOrderPauseById(id);
    }

    /**
     * 查询订单服务暂停列表
     *
     * @param sysOrderPause 订单服务暂停
     * @return 订单服务暂停
     */
    @Override
    public List<SysOrderPause> selectSysOrderPauseList(SysOrderPause sysOrderPause)
    {
        return sysOrderPauseMapper.selectSysOrderPauseList(sysOrderPause);
    }

    /**
     * 新增订单服务暂停
     *
     * @param sysOrderPause 订单服务暂停
     * @return 结果
     */
    @Override
    public int insertSysOrderPause(SysOrderPause sysOrderPause)
    {
        sysOrderPause.setCreateTime(DateUtils.getNowDate());
        if(sysOrderPauseMapper.insertSysOrderPause(sysOrderPause) > 0){
            //修改订单服务到期时间
            return sysOrderService.updateOrderServerStartEndDate(sysOrderPause.getCusId());
        }
        return 0;
    }

    /**
     * 修改订单服务暂停
     *
     * @param sysOrderPause 订单服务暂停
     * @return 结果
     */
    @Override
    public int updateSysOrderPause(SysOrderPause sysOrderPause)
    {
        sysOrderPause.setUpdateTime(DateUtils.getNowDate());
        if(sysOrderPauseMapper.updateSysOrderPause(sysOrderPause) > 0){
            return sysOrderService.updateOrderServerStartEndDate(sysOrderPause.getCusId());
        }
        return 0;
    }

    /**
     * 批量删除订单服务暂停
     *
     * @param ids 需要删除的订单服务暂停ID
     * @return 结果
     */
    @Override
    public int deleteSysOrderPauseByIds(Long[] ids)
    {
        SysOrderPause sysOrderPause = selectSysOrderPauseById(ids[0]);
        int row = sysOrderPauseMapper.deleteSysOrderPauseByIds(ids);
        if(row > 0){
            if(sysOrderPause != null && sysOrderPause.getCusId() != null){
                sysOrderService.updateOrderServerStartEndDate(sysOrderPause.getCusId());
            }
        }
        return row;
    }

    /**
     * 删除订单服务暂停信息
     *
     * @param id 订单服务暂停ID
     * @return 结果
     */
    @Override
    public int deleteSysOrderPauseById(Long id)
    {
        SysOrderPause sysOrderPause = selectSysOrderPauseById(id);
        if(sysOrderPause != null && sysOrderPauseMapper.deleteSysOrderPauseById(id) > 0){
            return sysOrderService.updateOrderServerStartEndDate(sysOrderPause.getCusId());
        }
        return 0;
    }

    /**
     * 根据订单ID、时间范围查询数量
     * @param sysOrderPause
     * @return
     */
    @Override
    public int getCountByCusIdAndPauseDate(SysOrderPause sysOrderPause){
        return sysOrderPauseMapper.getCountByCusIdAndPauseDate(sysOrderPause);
    }

    /**
     * 根据订单ID删除暂停记录
     * @param orderIds
     * @return
     */
    @Override
    public int deletePauseByOrderId(Long[] orderIds){
        return sysOrderPauseMapper.deletePauseByOrderId(orderIds);
    }

    /**
     * 根据订单ID获取暂停计划
     * @param orderId
     * @return
     */
    @Override
    public List<SysOrderPause> getPauseListByOrderId(Long orderId){
        return sysOrderPauseMapper.getPauseListByOrderId(orderId);
    }

    @Override
    public long selectNearMainOrderIdByCusId(Long cusId) {
        return sysOrderPauseMapper.selectNearMainOrderIdByCusId(cusId);
    }

    @Override
    public List<SysOrderPause> getPauseListByCusIdAndOrderId(SysOrderPause sysOrderPauses){
        return sysOrderPauseMapper.getPauseListByCusIdAndOrderId(sysOrderPauses);
    }
}