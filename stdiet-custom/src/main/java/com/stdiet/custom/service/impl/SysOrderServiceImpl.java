package com.stdiet.custom.service.impl;

import com.stdiet.common.annotation.Excel;
import com.stdiet.common.utils.DateUtils;
import com.stdiet.common.utils.SecurityUtils;
import com.stdiet.common.utils.StringUtils;
import com.stdiet.custom.domain.SysOrder;
import com.stdiet.custom.mapper.SysOrderMapper;
import com.stdiet.custom.service.ISysCommissionDayService;
import com.stdiet.custom.service.ISysOrderPauseService;
import com.stdiet.custom.service.ISysOrderService;
import com.stdiet.custom.service.ISysRecipesPlanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;

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

    @Autowired
    private ISysRecipesPlanService sysRecipesPlanService;

    @Autowired
    private ISysOrderPauseService sysOrderPauseService;

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
        sysOrder.setCreateBy(SecurityUtils.getUsername());
        //sysOrder.setOrderTime(orderTime);
        //计算服务到期时间
        setOrderServerEndDate(sysOrder);
        sysOrder.setOrderId(Long.parseLong(DateUtils.parseDateToStr(DateUtils.YYYYMMDDHHMMSS, orderTime)));
        //获取订单类型
        Integer[] orderType = sysOrder.getOrderTypeList();
        if(orderType == null || orderType.length != 3){
            return 0;
        }
        sysOrder.setOrderType(String.valueOf(orderType[0]));
        sysOrder.setOrderCountType(String.valueOf(orderType[1]));
        sysOrder.setOrderMoneyType(String.valueOf(orderType[2]));
        int row = 0;
        //普通单
        if("0".equals(sysOrder.getOrderType())){
            sysOrder.setNutritionistId((sysOrder.getNutritionistIdList() != null && sysOrder.getNutritionistIdList().length > 0) ? sysOrder.getNutritionistIdList()[0] : null);
            sysOrder.setMainOrderId(0L);
            sysOrder.setAfterSaleCommissOrder(0);
            sysOrder.setOnSaleId(null);
            //二开单
            if("1".equals(sysOrder.getOrderCountType())){
                row = sysOrderMapper.insertSysOrder(sysOrder);
                //需要自动创建售后二开提成单
                if(row > 0 && sysOrder.getSecondAfterSaleFlag() != null && sysOrder.getSecondAfterSaleFlag().intValue() == 1){
                    autoCreateSecondAfterSaleOrder(sysOrder);
                }
            }else{
                row = sysOrderMapper.insertSysOrder(sysOrder);
            }
        }
        //比例拆分单
        else if("1".equals(sysOrder.getOrderType())){
            sysOrder.setAfterSaleCommissOrder(0);
            sysOrder.setOnSaleId(null);
            String rate = sysOrder.getNutritionistRate();
            if(StringUtils.isEmpty(rate) || rate.indexOf(",") == -1 || "0,10".equals(rate) || sysOrder.getNutritionistIdList().length != 2){
                return 0;
            }
            String[] rateArray = rate.split(",");
            if(Integer.parseInt(rateArray[0]) + Integer.parseInt(rateArray[1]) != 10){
                return 0;
            }
            BigDecimal amount = sysOrder.getAmount();
            //获取主单的数组下标
            int mainIndex = 0;
            if(Integer.parseInt(rateArray[1]) > Integer.parseInt(rateArray[0])){
                mainIndex = 1;
            }
            //添加主单
            sysOrder.setNutritionistId(sysOrder.getNutritionistIdList()[mainIndex]);
            sysOrder.setAmount(BigDecimal.valueOf(amount.doubleValue()*Integer.parseInt(rateArray[mainIndex])/10));
            sysOrder.setMainOrderId(0L);
            row = sysOrderMapper.insertSysOrder(sysOrder);
            //添加副单
            sysOrder.setMainOrderId(sysOrder.getOrderId());
            sysOrder.setOrderId(sysOrder.getOrderId()+1);
            sysOrder.setNutritionistId(sysOrder.getNutritionistIdList()[1-mainIndex]);
            sysOrder.setAmount(BigDecimal.valueOf(amount.doubleValue()*Integer.parseInt(rateArray[1-mainIndex])/10));
            row = sysOrderMapper.insertSysOrder(sysOrder);
            //二开，是否需要自动创建售后二开提成单
            if(row > 0 && "1".equals(sysOrder.getOrderCountType()) && sysOrder.getSecondAfterSaleFlag() != null && sysOrder.getSecondAfterSaleFlag().intValue() == 1){
                sysOrder.setAmount(amount);
                autoCreateSecondAfterSaleOrder(sysOrder);
            }
        }
        //体验单
        else if("2".equals(sysOrder.getOrderType())){
            sysOrder.setMainOrderId(0L);
            sysOrder.setAfterSaleCommissOrder(0);
            sysOrder.setAfterSaleId(null);
            sysOrder.setNutritionistId(null);
            sysOrder.setNutriAssisId(null);
            row = sysOrderMapper.insertSysOrder(sysOrder);
        }
        return row;
    }

    /**
     * 创建售后二开提成订单
     * @param sysOrder
     * @return
     */
    private int autoCreateSecondAfterSaleOrder(SysOrder sysOrder){
        if(sysOrder != null){
            sysOrder.setPreSaleId(sysOrder.getAfterSaleId());
            sysOrder.setAfterSaleId(null);
            sysOrder.setNutritionistId(null);
            sysOrder.setNutriAssisId(null);
            sysOrder.setPlannerId(null);
            sysOrder.setPlannerAssisId(null);
            sysOrder.setOperatorId(null);
            sysOrder.setOperatorAssisId(null);
            sysOrder.setAfterSaleCommissOrder(1);
            sysOrder.setOrderId(sysOrder.getOrderId()+1);
            return sysOrderMapper.insertSysOrder(sysOrder);
        }
        return 0;
    }

    /**
     * 修改销售订单
     *
     * @param sysOrder 销售订单
     * @return 结果
     */
    @Override
    public int updateSysOrder(SysOrder sysOrder) {
        //获取旧订单对象
        SysOrder oldSysOrder = sysOrderMapper.selectSysOrderById(sysOrder.getOrderId());
        //计算服务到期时间
        setOrderServerEndDate(sysOrder);
        sysOrder.setUpdateBy(SecurityUtils.getUsername());
        sysOrder.setUpdateTime(DateUtils.getNowDate());
        //体验单
        if("2".equals(sysOrder.getOrderType())){
            sysOrder.setAfterSaleId(null);
            sysOrder.setNutritionistId(null);
            sysOrder.setNutriAssisId(null);
        }
        //提成单
        if(sysOrder.getAfterSaleCommissOrder().intValue() == 1){
            sysOrder.setAfterSaleId(null);
            sysOrder.setNutritionistId(null);
            sysOrder.setNutriAssisId(null);
            sysOrder.setPlannerId(null);
            sysOrder.setPlannerAssisId(null);
            sysOrder.setOperatorId(null);
            sysOrder.setOperatorAssisId(null);
        }
        //更新订单
        int row = sysOrderMapper.updateSysOrder(sysOrder);
        // 审核后的订单才生成食谱
        if (row > 0 && oldSysOrder.getReviewStatus().equals("no") && sysOrder.getReviewStatus().equals("yes")) {
            //异步更新食谱计划
            if (isNeedRegenerateRecipesPlan(oldSysOrder, sysOrder)) {
                sysRecipesPlanService.regenerateRecipesPlan(sysOrder.getOrderId());
            }
        }
        return row;
    }

    /**
     * 判断是否需要重新生成食谱计划
     *
     * @param oldSysOrder
     * @param newSysOrder
     * @return
     */
    private boolean isNeedRegenerateRecipesPlan(SysOrder oldSysOrder, SysOrder newSysOrder) {
        if (oldSysOrder.getServeTimeId() != null && newSysOrder.getServeTimeId() != null && oldSysOrder.getServeTimeId().intValue() != newSysOrder.getServeTimeId().intValue()) {
            return true;
        }
        if (newSysOrder.getGiveServeDay() != null && oldSysOrder.getGiveServeDay() != null && oldSysOrder.getGiveServeDay().intValue() != newSysOrder.getGiveServeDay().intValue()) {
            return true;
        }
        if (oldSysOrder.getStartTime() != null && newSysOrder.getStartTime() != null
                && ChronoUnit.DAYS.between(DateUtils.dateToLocalDate(oldSysOrder.getStartTime()), DateUtils.dateToLocalDate(newSysOrder.getStartTime())) != 0) {
            return true;
        }
        return false;
    }

    /**
     * 批量删除销售订单
     *
     * @param orderIds 需要删除的销售订单ID
     * @return 结果
     */
    @Override
    public int deleteSysOrderByIds(Long[] orderIds) {
        int row = sysOrderMapper.deleteSysOrderByIds(orderIds);
        if (row > 0) {
            deletePauseAndPlan(orderIds);
        }
        return row;
    }

    /**
     * 删除销售订单信息
     *
     * @param orderId 销售订单ID
     * @return 结果
     */
    @Override
    public int deleteSysOrderById(Long orderId) {
        int row = sysOrderMapper.deleteSysOrderById(orderId);
        if (row > 0) {
            Long[] orderIdArray = new Long[1];
            orderIdArray[0] = orderId;
            deletePauseAndPlan(orderIdArray);
        }
        return row;
    }

    /**
     * 计算所有订单总额
     *
     * @param sysOrder
     * @return
     */
    @Override
    public BigDecimal selectAllOrderAmount(SysOrder sysOrder) {
        return sysOrderMapper.selectAllOrderAmount(sysOrder);
    }

    /**
     * 根据订单ID更新该订单的服务到期时间，异步更新食谱计划
     *
     * @param orderId    订单ID
     * @param updatePlan 是否更新食谱
     * @return
     */
    @Override
    public int updateOrderServerEndDate(Long orderId, boolean updatePlan) {
        int row = 0;
        //更新订单服务到期时间
        SysOrder sysOrder = selectSysOrderById(orderId);
        if (sysOrder != null) {
            //设置服务到期时间
            setOrderServerEndDate(sysOrder);
            sysOrder.setUpdateTime(new Date());
            row = updateSysOrder(sysOrder);
            if (row > 0) {
                //异步更新食谱计划
                sysRecipesPlanService.regenerateRecipesPlan(sysOrder.getOrderId());
            }
        }
        return row;
    }

    /**
     * 给对象SysOrder对象设置服务到期时间
     *
     * @param sysOrder
     */
    private void setOrderServerEndDate(SysOrder sysOrder) {
        LocalDate serverEndDate = sysCommissionDayService.getServerEndDate(sysOrder);
        sysOrder.setServerEndTime(serverEndDate != null ? DateUtils.localDateToDate(serverEndDate) : null);
    }

    /**
     * 删除订单的同时删除暂停记录、食谱计划安排
     *
     * @param orderIds 订单ID集合
     */
    private void deletePauseAndPlan(Long[] orderIds) {
        //删除暂停记录
        sysOrderPauseService.deletePauseByOrderId(orderIds);
        //删除食谱计划
        sysRecipesPlanService.delRecipesPlanByOrderId(orderIds);
    }
}