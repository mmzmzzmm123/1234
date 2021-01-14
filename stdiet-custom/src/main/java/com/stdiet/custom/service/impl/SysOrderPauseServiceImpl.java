package com.stdiet.custom.service.impl;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import com.stdiet.common.utils.DateUtils;
import com.stdiet.custom.service.ISysCommissionDayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.stdiet.custom.mapper.SysOrderPauseMapper;
import com.stdiet.custom.domain.SysOrderPause;
import com.stdiet.custom.service.ISysOrderPauseService;

/**
 * 订单服务暂停Service业务层处理
 *
 * @author wonder
 * @date 2021-01-07
 */
@Service
public class SysOrderPauseServiceImpl implements ISysOrderPauseService
{
    @Autowired
    private SysOrderPauseMapper sysOrderPauseMapper;

    @Autowired
    private ISysCommissionDayService sysCommissionDayService;

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
        return sysOrderPauseMapper.insertSysOrderPause(sysOrderPause);
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
        return sysOrderPauseMapper.updateSysOrderPause(sysOrderPause);
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
        return sysOrderPauseMapper.deleteSysOrderPauseByIds(ids);
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
        return sysOrderPauseMapper.deleteSysOrderPauseById(id);
    }

    /**
     * 根据订单ID、时间范围查询数量
     * @param sysOrderPause
     * @return
     */
    public int getCountByOrderIdAndPauseDate(SysOrderPause sysOrderPause){
        return sysOrderPauseMapper.getCountByOrderIdAndPauseDate(sysOrderPause);
    }
}