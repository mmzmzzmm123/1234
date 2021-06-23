package com.stdiet.custom.service.impl;

import com.stdiet.common.annotation.Excel;
import com.stdiet.common.utils.DateUtils;
import com.stdiet.common.utils.SecurityUtils;
import com.stdiet.common.utils.StringUtils;
import com.stdiet.custom.domain.SysCommision;
import com.stdiet.custom.domain.SysCustomer;
import com.stdiet.custom.domain.SysOrder;
import com.stdiet.custom.dto.response.EveryMonthTotalAmount;
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
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjusters;
import java.util.Date;
import java.util.List;
import java.util.Map;

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
        //新增订单时，食谱开始时间和提成计算开始时间保持一致
        sysOrder.setCommissStartTime(sysOrder.getStartTime());
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
                    row = autoCreateSecondAfterSaleOrder(sysOrder);
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
                row = autoCreateSecondAfterSaleOrder(sysOrder);
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
        //售中单
        else if("3".equals(sysOrder.getOrderType())){
            sysOrder.setPreSaleId(null);
            //一开、二开
           if("0".equals(sysOrder.getOrderCountType()) || "1".equals(sysOrder.getOrderCountType())){
                sysOrder.setNutritionistId((sysOrder.getNutritionistIdList() != null && sysOrder.getNutritionistIdList().length > 0) ? sysOrder.getNutritionistIdList()[0] : null);
                sysOrder.setMainOrderId(0L);
                sysOrder.setAfterSaleCommissOrder(0);
                if("0".equals(sysOrder.getOrderCountType())){
                    row = sysOrderMapper.insertSysOrder(sysOrder);
                }else{
                    //二开单不存在售前推荐人
                    sysOrder.setPushPreSaleId(null);
                    row = sysOrderMapper.insertSysOrder(sysOrder);
                    //需要自动创建售后二开提成单
                    if(row > 0 && sysOrder.getSecondAfterSaleFlag() != null && sysOrder.getSecondAfterSaleFlag().intValue() == 1){
                        row = autoCreateSecondAfterSaleOrder(sysOrder);
                    }
                }
           }else{
               sysOrder.setAfterSaleCommissOrder(0);
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
               //拆分二开单
               if("3".equals(sysOrder.getOrderCountType())){
                   //二开单不存在售前推荐人
                   sysOrder.setPushPreSaleId(null);
               }
               row = sysOrderMapper.insertSysOrder(sysOrder);
               //添加副单
               sysOrder.setMainOrderId(sysOrder.getOrderId());
               sysOrder.setOrderId(sysOrder.getOrderId()+1);
               sysOrder.setNutritionistId(sysOrder.getNutritionistIdList()[1-mainIndex]);
               sysOrder.setAmount(BigDecimal.valueOf(amount.doubleValue()*Integer.parseInt(rateArray[1-mainIndex])/10));
               row = sysOrderMapper.insertSysOrder(sysOrder);
               //拆分二开单
               if(row > 0 && "3".equals(sysOrder.getOrderCountType()) && sysOrder.getSecondAfterSaleFlag() != null && sysOrder.getSecondAfterSaleFlag().intValue() == 1){
                   sysOrder.setAmount(amount);
                   row = autoCreateSecondAfterSaleOrder(sysOrder);
               }
           }
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
            sysOrder.setOnSaleId(null);
            sysOrder.setPushPreSaleId(null);
            sysOrder.setAfterSaleId(null);
            sysOrder.setNutritionistId(null);
            sysOrder.setNutriAssisId(null);
            sysOrder.setPlannerId(null);
            sysOrder.setPlannerAssisId(null);
            sysOrder.setOperatorId(null);
            sysOrder.setOperatorAssisId(null);
            sysOrder.setMainOrderId(0L);
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
        if("3".equals(sysOrder.getOrderType())){
            sysOrder.setPreSaleId(null);
        }
        if(oldSysOrder.getStartTime() == null){//确保提成计算时间不为空
            sysOrder.setCommissStartTime(sysOrder.getOrderTime());
        }
        //如果更新了开始服务时间，需要判断是否需要同步提成计算时间
        if (oldSysOrder.getStartTime() != null && sysOrder.getStartTime() != null
                && ChronoUnit.DAYS.between(DateUtils.dateToLocalDate(oldSysOrder.getStartTime()), DateUtils.dateToLocalDate(sysOrder.getStartTime())) != 0) {
            //本月第一天
            LocalDate monthStart = LocalDate.now().with(TemporalAdjusters.firstDayOfMonth());
            //旧的开始时间和新的开始时间都要需要大于本月第一天
            if(oldSysOrder.getCommissStartTime() != null && ChronoUnit.DAYS.between(monthStart, DateUtils.dateToLocalDate(oldSysOrder.getCommissStartTime())) >= 0 && ChronoUnit.DAYS.between(monthStart, DateUtils.dateToLocalDate(sysOrder.getStartTime())) >= 0){
                sysOrder.setCommissStartTime(sysOrder.getStartTime());
            }
        }
        //更新订单
        int row = sysOrderMapper.updateSysOrder(sysOrder);
        // 审核后的订单才生成食谱
        if (row > 0 && isNeedRegenerateRecipesPlan(oldSysOrder, sysOrder)) {
            //异步更新客户所有订单的服务时间范围以及生成食谱计划
            updateOrderServerStartEndDate(sysOrder.getCusId());
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
        if(oldSysOrder.getReviewStatus().equals("no") && newSysOrder.getReviewStatus().equals("yes")){
            return true;
        }
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
     * 客户增加、修改、删除暂停记录、新增订单、修改订单订单的相关时间，需要更新该用户对应所有订单的开始时间、结束时间
     * @param cusId
     * @return
     */
    @Override
    public int updateOrderServerStartEndDate(Long cusId){
        int row = 0;
        //查询该用户的所有订单
        List<SysOrder> orderList = getAllOrderByCusId(cusId);
        if(orderList != null && orderList.size() > 0){
            LocalDate lastServerEndTime = null;
            for (SysOrder sysOrder : orderList) {
                LocalDate newStartTime = null;
                //判断是否提成单，拆分单中的副单，体验单,定金单
                if(sysOrder.getStartTime() == null || sysOrder.getAfterSaleCommissOrder().intValue() == 1 || sysOrder.getMainOrderId().intValue() != 0 ||
                        "2".equals(sysOrder.getOrderType()) || !isNeedByOrderMoneyType(sysOrder)){
                    //设置服务到期时间
                    setOrderServerEndDate(sysOrder);
                    sysOrder.setUpdateTime(new Date());
                    row = updateSysOrder(sysOrder);
                }else {
                    //判断前一个订单的结束时间是否大于第二个订单的
                    if (lastServerEndTime != null && ChronoUnit.DAYS.between(lastServerEndTime, DateUtils.dateToLocalDate(sysOrder.getStartTime())) <= 0) {
                        newStartTime = lastServerEndTime.plusDays(1);
                        //本月第一天
                        LocalDate monthStart = LocalDate.now().with(TemporalAdjusters.firstDayOfMonth());
                        //旧的开始时间和新的开始时间都要需要大于本月第一天
                        if (sysOrder.getCommissStartTime() != null && ChronoUnit.DAYS.between(monthStart, DateUtils.dateToLocalDate(sysOrder.getCommissStartTime())) >= 0 && ChronoUnit.DAYS.between(monthStart, newStartTime) >= 0) {
                            sysOrder.setCommissStartTime(DateUtils.localDateToDate(newStartTime));
                        }
                        sysOrder.setStartTime(DateUtils.localDateToDate(newStartTime));
                    }
                    //设置服务到期时间
                    setOrderServerEndDate(sysOrder);
                    sysOrder.setUpdateTime(new Date());
                    row = updateSysOrder(sysOrder);
                    lastServerEndTime = DateUtils.dateToLocalDate(sysOrder.getServerEndTime());
                }
            }
            //异步更新食谱计划
            sysRecipesPlanService.regenerateRecipesPlan(cusId);
        }
        return row;
    }

    //定金单或尾款单是否需要生成食谱计划，2021-01-12修改为6月1日之后成交的订单只生成定金单计划
    private boolean isNeedByOrderMoneyType(SysOrder sysOrder){
        if("0".equals(sysOrder.getOrderMoneyType())){
            return true;
        }else{
            //成交时间
            LocalDate orderDate = DateUtils.dateToLocalDate(sysOrder.getOrderTime());
            if(ChronoUnit.DAYS.between(SysRecipesPlanServiceImpl.newVersionPlanStartDate, orderDate) >= 0){
                return "1".equals(sysOrder.getOrderMoneyType());
            }else{
                return "2".equals(sysOrder.getOrderMoneyType());
            }
        }
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

    /**
     * 查询客户订单数量
     * @param sysCustomer
     * @return
     */
    @Override
    public int getOrderCountByCustomer(SysCustomer sysCustomer){
        return sysOrderMapper.getOrderCountByCustomer(sysCustomer);
    }

    /**
     * 查询每年每月的总金额
     * @return
     */
    @Override
    public List<EveryMonthTotalAmount> getTotalAmountByUserId(SysCommision sysCommision){
        return sysOrderMapper.getTotalAmountByUserId(sysCommision);
    }

    /**
     * 获取订单数量（按天提成计算）
     * @param sysCommision
     * @return
     */
    public int selectSimpleOrderMessageCount(SysCommision sysCommision){
        return sysOrderMapper.selectSimpleOrderMessageCount(sysCommision);
    }

    /**
     *
     * @return
     */
    @Override
    public void updateOrderStartTime(SysOrder sysOrder, LocalDate nowStartDate){
        LocalDate oldStartDate = sysOrder.getStartTime() != null ? DateUtils.dateToLocalDate(sysOrder.getStartTime()) : null;
        if (oldStartDate != null && nowStartDate != null
                && ChronoUnit.DAYS.between(oldStartDate, nowStartDate) != 0) {
            SysOrder newOrder = new SysOrder();
            newOrder.setStartTime(DateUtils.localDateToDate(nowStartDate));
            newOrder.setOrderId(sysOrder.getOrderId());
            newOrder.setUpdateTime(new Date());
            //本月第一天
            LocalDate monthStart = LocalDate.now().with(TemporalAdjusters.firstDayOfMonth());
            //旧的开始时间和新的开始时间都要需要大于本月第一天
            if(ChronoUnit.DAYS.between(monthStart, oldStartDate) >= 0 && ChronoUnit.DAYS.between(monthStart, nowStartDate) >= 0){
                sysOrder.setCommissStartTime(sysOrder.getStartTime());
            }
        }
    }

    /**
     * 根据客户ID查询对应所有订单
     * @return
     */
    public List<SysOrder> getAllOrderByCusId(Long cusId){
        return sysOrderMapper.getAllOrderByCusId(cusId);
    }

    /**
     * 根据openid查询订单数量
     * @param openid
     * @return
     */
    public int getOrderCountByOpenId(String openid){
        return sysOrderMapper.getOrderCountByOpenId(openid);
    }
}