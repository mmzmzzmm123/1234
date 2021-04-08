package com.stdiet.custom.service.impl;

import java.util.List;
import com.stdiet.common.utils.DateUtils;
import com.stdiet.common.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.stdiet.custom.mapper.SysOrderNutritionistReplaceRecordMapper;
import com.stdiet.custom.domain.SysOrderNutritionistReplaceRecord;
import com.stdiet.custom.service.ISysOrderNutritionistReplaceRecordService;

/**
 * 订单营养师、售后更换记录Service业务层处理
 *
 * @author xzj
 * @date 2021-04-02
 */
@Service
public class SysOrderNutritionistReplaceRecordServiceImpl implements ISysOrderNutritionistReplaceRecordService
{
    @Autowired
    private SysOrderNutritionistReplaceRecordMapper sysOrderNutritionistReplaceRecordMapper;

    /**
     * 查询订单营养师、售后更换记录
     *
     * @param id 订单营养师、售后更换记录ID
     * @return 订单营养师、售后更换记录
     */
    @Override
    public SysOrderNutritionistReplaceRecord selectSysOrderNutritionistReplaceRecordById(Long id)
    {
        return sysOrderNutritionistReplaceRecordMapper.selectSysOrderNutritionistReplaceRecordById(id);
    }

    /**
     * 查询订单营养师、售后更换记录列表
     *
     * @param sysOrderNutritionistReplaceRecord 订单营养师、售后更换记录
     * @return 订单营养师、售后更换记录
     */
    @Override
    public List<SysOrderNutritionistReplaceRecord> selectSysOrderNutritionistReplaceRecordList(SysOrderNutritionistReplaceRecord sysOrderNutritionistReplaceRecord)
    {
        return sysOrderNutritionistReplaceRecordMapper.selectSysOrderNutritionistReplaceRecordList(sysOrderNutritionistReplaceRecord);
    }

    /**
     * 新增订单营养师、售后更换记录
     *
     * @param sysOrderNutritionistReplaceRecord 订单营养师、售后更换记录
     * @return 结果
     */
    @Override
    public int insertSysOrderNutritionistReplaceRecord(SysOrderNutritionistReplaceRecord sysOrderNutritionistReplaceRecord)
    {
        sysOrderNutritionistReplaceRecord.setCreateTime(DateUtils.getNowDate());
        sysOrderNutritionistReplaceRecord.setCreateBy(SecurityUtils.getLoginUser().getUser().getUserId()+"");
        return sysOrderNutritionistReplaceRecordMapper.insertSysOrderNutritionistReplaceRecord(sysOrderNutritionistReplaceRecord);
    }

    /**
     * 修改订单营养师、售后更换记录
     *
     * @param sysOrderNutritionistReplaceRecord 订单营养师、售后更换记录
     * @return 结果
     */
    @Override
    public int updateSysOrderNutritionistReplaceRecord(SysOrderNutritionistReplaceRecord sysOrderNutritionistReplaceRecord)
    {
        sysOrderNutritionistReplaceRecord.setUpdateTime(DateUtils.getNowDate());
        sysOrderNutritionistReplaceRecord.setUpdateBy(SecurityUtils.getLoginUser().getUser().getUserId()+"");
        return sysOrderNutritionistReplaceRecordMapper.updateSysOrderNutritionistReplaceRecord(sysOrderNutritionistReplaceRecord);
    }

    /**
     * 批量删除订单营养师、售后更换记录
     *
     * @param ids 需要删除的订单营养师、售后更换记录ID
     * @return 结果
     */
    @Override
    public int deleteSysOrderNutritionistReplaceRecordByIds(Long[] ids)
    {
        return sysOrderNutritionistReplaceRecordMapper.deleteSysOrderNutritionistReplaceRecordByIds(ids);
    }

    /**
     * 删除订单营养师、售后更换记录信息
     *
     * @param id 订单营养师、售后更换记录ID
     * @return 结果
     */
    @Override
    public int deleteSysOrderNutritionistReplaceRecordById(Long id)
    {
        return sysOrderNutritionistReplaceRecordMapper.deleteSysOrderNutritionistReplaceRecordById(id);
    }

    /**
     * 根据订单ID查询订单对于营养师、售后更换记录
     * @param orderId
     * @return
     */
    public List<SysOrderNutritionistReplaceRecord> getSysOrderReplaceRecordByOrderId(Long orderId){
        return sysOrderNutritionistReplaceRecordMapper.getSysOrderReplaceRecordByOrderId(orderId);
    }
}