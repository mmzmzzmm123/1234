package com.stdiet.custom.service.impl;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import com.stdiet.common.utils.DateUtils;
import com.stdiet.common.utils.SynchrolockUtil;
import com.stdiet.custom.domain.SysOrder;
import com.stdiet.custom.domain.SysOrderPause;
import com.stdiet.custom.service.ISysOrderPauseService;
import com.stdiet.custom.service.ISysOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.QueryAnnotation;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import com.stdiet.custom.mapper.SysRecipesPlanMapper;
import com.stdiet.custom.domain.SysRecipesPlan;
import com.stdiet.custom.service.ISysRecipesPlanService;
import org.springframework.transaction.annotation.Transactional;

/**
 * 食谱计划Service业务层处理
 *
 * @author xzj
 * @date 2021-01-15
 */
@Service
@Transactional
public class SysRecipesPlanServiceImpl implements ISysRecipesPlanService
{
    @Autowired
    private SysRecipesPlanMapper sysRecipesPlanMapper;

    @Autowired
    private ISysOrderService sysOrderService;

    @Autowired
    private ISysOrderPauseService sysOrderPauseService;

    @Autowired
    private SynchrolockUtil synchrolockUtil;

    public static final String generateRecipesPlanLockKey = "generateRecipesPlanLock::%s";

    /**
     * 查询食谱计划
     *
     * @param id 食谱计划ID
     * @return 食谱计划
     */
    @Override
    public SysRecipesPlan selectSysRecipesPlanById(Long id)
    {
        return sysRecipesPlanMapper.selectSysRecipesPlanById(id);
    }

    /**
     * 查询食谱计划列表
     *
     * @param sysRecipesPlan 食谱计划
     * @return 食谱计划
     */
    @Override
    public List<SysRecipesPlan> selectSysRecipesPlanList(SysRecipesPlan sysRecipesPlan)
    {
        return sysRecipesPlanMapper.selectSysRecipesPlanList(sysRecipesPlan);
    }

    /**
     * 新增食谱计划
     *
     * @param sysRecipesPlan 食谱计划
     * @return 结果
     */
    @Override
    public int insertSysRecipesPlan(SysRecipesPlan sysRecipesPlan)
    {
        sysRecipesPlan.setCreateTime(DateUtils.getNowDate());
        return sysRecipesPlanMapper.insertSysRecipesPlan(sysRecipesPlan);
    }

    /**
     * 修改食谱计划
     *
     * @param sysRecipesPlan 食谱计划
     * @return 结果
     */
    @Override
    public int updateSysRecipesPlan(SysRecipesPlan sysRecipesPlan)
    {
        sysRecipesPlan.setUpdateTime(DateUtils.getNowDate());
        return sysRecipesPlanMapper.updateSysRecipesPlan(sysRecipesPlan);
    }

    /**
     * 批量删除食谱计划
     *
     * @param ids 需要删除的食谱计划ID
     * @return 结果
     */
    @Override
    public int deleteSysRecipesPlanByIds(Long[] ids)
    {
        return sysRecipesPlanMapper.deleteSysRecipesPlanByIds(ids);
    }

    /**
     * 删除食谱计划信息
     *
     * @param id 食谱计划ID
     * @return 结果
     */
    @Override
    public int deleteSysRecipesPlanById(Long id)
    {
        return sysRecipesPlanMapper.deleteSysRecipesPlanById(id);
    }

    /**
     * 根据订单ID生成对应的食谱计划
     *
     * @param orderId 订单ID
     * @return 结果
     */
    @Override
    @Async("generateRecipesPlan")
    public void autoGenerateRecipesPlanByOrderId(Long orderId){
        if(orderId == null || orderId <= 0){
            return;
        }
        SysOrder sysOrder = sysOrderService.selectSysOrderById(orderId);
        if(sysOrder == null){
            return;
        }
        try{
            //获取redis中该订单对应的锁
            if(synchrolockUtil.lock(String.format(generateRecipesPlanLockKey, orderId))){
                //删除该订单对于食谱
                sysRecipesPlanMapper.delRecipesPlanByOrderId(orderId);
                //订单服务开始时间、结束时间不能为空
                if(sysOrder.getStartTime() != null && sysOrder.getServerEndTime() != null){
                    SysOrderPause pauseParam = new SysOrderPause();
                    pauseParam.setOrderId(sysOrder.getOrderId());
                    //暂停记录列表
                    List<SysOrderPause> pauseList = sysOrderPauseService.selectSysOrderPauseList(pauseParam);
                    List<SysRecipesPlan> planList = generatePlan(orderId, DateUtils.dateToLocalDate(sysOrder.getStartTime()), DateUtils.dateToLocalDate(sysOrder.getServerEndTime()), pauseList);
                    if(planList != null && planList.size() > 0){
                        sysRecipesPlanMapper.insertBatch(planList);
                    }
                    for (SysRecipesPlan sysRecipesPlan : planList) {
                        getTestDate(sysRecipesPlan.getStartDate(), sysRecipesPlan.getEndDate());
                    }
                }
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        } finally {
            // 一定要释放锁
            synchrolockUtil.unlock(String.format(generateRecipesPlanLockKey, orderId));
        }
    }

    public void getTestDate(Date date, Date date2){
        LocalDate d = DateUtils.dateToLocalDate(date);
        LocalDate d2 =  DateUtils.dateToLocalDate(date2);
        String s1 = d.getYear() +"-" + d.getMonthValue() + "-" + d.getDayOfMonth();
        String s2 = d2.getYear() +"-" + d2.getMonthValue() + "-" + d2.getDayOfMonth();
        System.out.println(s1 + "   " + s2);
    }

    /**
     * 根据订单ID、订单开始服务时间、结束时间、暂停列表生成食谱计划列表
     * @param orderId 订单ID
     * @param serverStartDate 服务开始时间
     * @param serverEndDate 服务结束时间
     * @param pauseList 暂停列表
     * @return
     */
    public List<SysRecipesPlan> generatePlan(Long orderId, LocalDate serverStartDate, LocalDate serverEndDate, List<SysOrderPause> pauseList){
        List<SysRecipesPlan> planList = new ArrayList<>();
        boolean breakFlag = false;
        LocalDate planStartDate = serverStartDate;
        LocalDate planEndDate = planStartDate.plusDays(6);
        while (true){

            SysRecipesPlan sysRecipesPlan = new SysRecipesPlan();
            //判断是否大于服务到期时间
            if(ChronoUnit.DAYS.between(planEndDate, serverEndDate) > 0){

            }else{
                planEndDate = serverEndDate;
                breakFlag = true;
            }
            long pauseDay = getPauseDayeCount(planStartDate, planEndDate, pauseList);
            //加上暂停时间
            planEndDate = planEndDate.plusDays(pauseDay);
            sysRecipesPlan.setStartDate(DateUtils.localDateToDate(planStartDate));
            sysRecipesPlan.setEndDate(DateUtils.localDateToDate(planEndDate));
            sysRecipesPlan.setOrderId(orderId);
            planList.add(sysRecipesPlan);

            planStartDate = planEndDate.plusDays(1);
            planEndDate = planStartDate.plusDays(6);

            if(breakFlag){
                break;
            }
        }
        return planList;
    }

    /**
     * 根据食谱开始时间、结束时间、暂停列表获取在食谱计划范围内的暂停天数
     * @param planStartDate 计划开始时间
     * @param planEndDate 计划结束时间
     * @param pauseList  暂停列表
     * @return
     */
    public long getPauseDayeCount(LocalDate planStartDate, LocalDate planEndDate, List<SysOrderPause> pauseList){
        long pauseDay = 0;
        //判断这个时间内是否存在暂停
        if(pauseList != null && pauseList.size() > 0){
            for(SysOrderPause sysOrderPause : pauseList){
                LocalDate pauseStartDate = DateUtils.dateToLocalDate(sysOrderPause.getPauseStartDate());
                LocalDate pauseEndDate = DateUtils.dateToLocalDate(sysOrderPause.getPauseEndDate());
                if(ChronoUnit.DAYS.between(pauseEndDate, planStartDate) > 0 || ChronoUnit.DAYS.between(planEndDate, pauseStartDate) > 0){
                    continue;
                }
                if(ChronoUnit.DAYS.between(pauseStartDate, planStartDate) > 0){
                    pauseStartDate = planStartDate;
                }
                if(ChronoUnit.DAYS.between(planEndDate, pauseEndDate) > 0){
                    pauseEndDate = planEndDate;
                }
                pauseDay += ChronoUnit.DAYS.between(pauseStartDate, pauseEndDate);
            }
        }
        return pauseDay;
    }
}