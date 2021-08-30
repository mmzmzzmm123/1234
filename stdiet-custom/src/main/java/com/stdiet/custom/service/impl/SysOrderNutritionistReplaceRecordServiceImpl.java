package com.stdiet.custom.service.impl;

import java.util.ArrayList;
import java.util.List;

import com.stdiet.common.core.domain.AjaxResult;
import com.stdiet.common.utils.DateUtils;
import com.stdiet.common.utils.SecurityUtils;
import com.stdiet.custom.domain.SysOrder;
import com.stdiet.custom.dto.request.AddMuchReplaceRecordRequest;
import com.stdiet.custom.service.ISysOrderService;
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

    @Autowired
    private ISysOrderService sysOrderService;

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

    /**
     * 根据用户ID查询营养师、售后更换记录
     * @param userId
     * @return
     */
    public List<SysOrderNutritionistReplaceRecord> getSysOrderReplaceRecordByUserId(Long userId){
        return sysOrderNutritionistReplaceRecordMapper.getSysOrderReplaceRecordByUserId(userId);
    }

    /**
     * 批量添加更换记录
     * @param addMuchReplaceRecordRequestList
     */
    @Override
    public AjaxResult addMuchReplaceRecord(List<AddMuchReplaceRecordRequest> addMuchReplaceRecordRequestList){
        List<SysOrderNutritionistReplaceRecord> addRecordList = new ArrayList<>();
        int repeatNum = 0;
        for (AddMuchReplaceRecordRequest record : addMuchReplaceRecordRequestList) {
            //根据客户ID查询对应订单
            List<SysOrder> orderList = sysOrderService.getAllOrderByCusId(record.getCustomerId());
            for (SysOrder order : orderList) {
                if(order.getAfterSaleCommissOrder().intValue() == 1 || "2".equals(order.getOrderType())){
                    continue;
                }
                //判断更换开始时间是否大于等于订单结束时间
                if(order.getStartTime() == null || order.getServerEndTime() == null ||
                        order.getServerEndTime().getTime() < record.getStartTime().getTime()){
                    continue;
                }
                SysOrderNutritionistReplaceRecord addRecord = new SysOrderNutritionistReplaceRecord();
                addRecord.setOrderId(order.getOrderId());
                addRecord.setStartTime(record.getStartTime());
                Long addRecordNutritionistId = null;
                Long addRecordNutriAssisId = null;
                Long addRecordAfterSaleId = null;
                //已存在的更换记录 type 1 营养师  2助理  3售后
                int existNutritionistRecordCount = sysOrderNutritionistReplaceRecordMapper.getReplaceRecordCount(order.getOrderId(),1);
                int existNutriAssisRecordCount = sysOrderNutritionistReplaceRecordMapper.getReplaceRecordCount(order.getOrderId(),2);
                int existAfterSaleRecordCount = sysOrderNutritionistReplaceRecordMapper.getReplaceRecordCount(order.getOrderId(),3);
                if(record.getNutritionistId() == null || order.getNutritionistId() == null || order.getNutritionistId().longValue() != record.getNutritionistId().longValue()
                    || existNutritionistRecordCount != 0){
                    //判断是否已存在相同时间的更换记录
                    addRecord.setNutritionistId(record.getNutritionistId());
                    if(sysOrderNutritionistReplaceRecordMapper.getReplaceRecordByReplaceRecord(addRecord) == 0){
                        addRecordNutritionistId = record.getNutritionistId();
                    }
                }
                if(record.getNutriAssisId() == null || order.getNutriAssisId() == null || order.getNutriAssisId().longValue() != record.getNutriAssisId().longValue()
                        || existNutriAssisRecordCount != 0){
                    addRecord.setNutritionistId(null);
                    addRecord.setNutriAssisId(record.getNutriAssisId());
                    if(sysOrderNutritionistReplaceRecordMapper.getReplaceRecordByReplaceRecord(addRecord) == 0){
                        addRecordNutriAssisId = record.getNutriAssisId();
                    }
                }
                if(record.getAfterSaleId() == null || order.getAfterSaleId() == null || order.getAfterSaleId().longValue() != record.getAfterSaleId().longValue()
                        || existAfterSaleRecordCount != 0){
                    addRecord.setNutritionistId(null);
                    addRecord.setNutriAssisId(null);
                    addRecord.setAfterSaleId(record.getAfterSaleId());
                    if(sysOrderNutritionistReplaceRecordMapper.getReplaceRecordByReplaceRecord(addRecord) == 0){
                        addRecordAfterSaleId = record.getAfterSaleId();
                    }
                }
                if(addRecordNutritionistId != null || addRecordNutriAssisId != null || addRecordAfterSaleId != null){
                    addRecord.setNutritionistId(addRecordNutritionistId);
                    addRecord.setNutriAssisId(addRecordNutriAssisId);
                    addRecord.setAfterSaleId(addRecordAfterSaleId);
                    addRecordList.add(addRecord);
                }
            }
        }
        if(addRecordList.size() > 0){
            //批量添加
            if(sysOrderNutritionistReplaceRecordMapper.batchInsertReplaceRecord(addRecordList) > 0){
                return AjaxResult.success();
            }else{
                return AjaxResult.error("添加失败");
            }
        }
        return AjaxResult.success("不存在或已存在更换记录");
    }
}