package com.stdiet.custom.service.impl;

import com.stdiet.common.utils.DateUtils;
import com.stdiet.common.utils.SynchrolockUtil;
import com.stdiet.custom.domain.SysOrder;
import com.stdiet.custom.domain.SysOrderPause;
import com.stdiet.custom.domain.SysRecipesPlan;
import com.stdiet.custom.mapper.SysRecipesPlanMapper;
import com.stdiet.custom.service.ISysOrderPauseService;
import com.stdiet.custom.service.ISysOrderService;
import com.stdiet.custom.service.ISysRecipesPlanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 食谱计划Service业务层处理
 *
 * @author xzj
 * @date 2021-01-15
 */
@Service("sysRecipesPlanService")
@Transactional
public class SysRecipesPlanServiceImpl implements ISysRecipesPlanService {
    public static final String generateRecipesPlanLockKey = "generateRecipesPlanLock::%s";
    @Autowired
    private SysRecipesPlanMapper sysRecipesPlanMapper;
    @Autowired
    private ISysOrderService sysOrderService;
    @Autowired
    private ISysOrderPauseService sysOrderPauseService;
    @Autowired
    private SynchrolockUtil synchrolockUtil;

    /**
     * 查询食谱计划
     *
     * @param id 食谱计划ID
     * @return 食谱计划
     */
    @Override
    public SysRecipesPlan selectSysRecipesPlanById(Long id) {
        return sysRecipesPlanMapper.selectSysRecipesPlanById(id);
    }

    /**
     * 查询食谱计划列表
     *
     * @param sysRecipesPlan 食谱计划
     * @return 食谱计划
     */
    @Override
    public List<SysRecipesPlan> selectSysRecipesPlanList(SysRecipesPlan sysRecipesPlan) {
        return sysRecipesPlanMapper.selectSysRecipesPlanList(sysRecipesPlan);
    }

    /**
     * 新增食谱计划
     *
     * @param sysRecipesPlan 食谱计划
     * @return 结果
     */
    @Override
    public int insertSysRecipesPlan(SysRecipesPlan sysRecipesPlan) {
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
    public int updateSysRecipesPlan(SysRecipesPlan sysRecipesPlan) {
        sysRecipesPlan.setUpdateTime(DateUtils.getNowDate());
        //目前只能修改发送状态，所以修改时加上发送时间
        sysRecipesPlan.setSendTime(DateUtils.getNowDate());
        return sysRecipesPlanMapper.updateSysRecipesPlan(sysRecipesPlan);
    }

    /**
     * 批量删除食谱计划
     *
     * @param ids 需要删除的食谱计划ID
     * @return 结果
     */
    @Override
    public int deleteSysRecipesPlanByIds(Long[] ids) {
        return sysRecipesPlanMapper.deleteSysRecipesPlanByIds(ids);
    }

    /**
     * 删除食谱计划信息
     *
     * @param id 食谱计划ID
     * @return 结果
     */
    @Override
    public int deleteSysRecipesPlanById(Long id) {
        return sysRecipesPlanMapper.deleteSysRecipesPlanById(id);
    }



    /**
     * 异步方法，根据订单ID生成对应的食谱计划，退款订单不生成食谱计划
     *
     * @param orderId 订单ID
     * @return 结果
     */
    @Override
    @Async
    public void regenerateRecipesPlan(Long orderId) {
        if (orderId == null || orderId <= 0) {
            return;
        }
        SysOrder sysOrder = sysOrderService.selectSysOrderById(orderId);
        //订单为空、金额小于0不进行食谱生成、更新，只对2021年开始的订单进行食谱计划生成，判断订单金额、开始时间、结束时间，为空则直接返回，不重新生成食谱计划
        if (sysOrder == null && DateUtils.dateToLocalDate(sysOrder.getOrderTime()).getYear() < 2021
                || sysOrder.getAmount().floatValue() <= 0 || sysOrder.getStartTime() == null || sysOrder.getServerEndTime() == null) {
            return;
        }
//        System.out.println(DateUtils.dateToLocalDate(sysOrder.getOrderTime()).getYear());
        try {
            //获取redis中该订单对应的锁
            if (synchrolockUtil.lock(String.format(generateRecipesPlanLockKey, orderId))) {
                SysRecipesPlan queryParam = new SysRecipesPlan();
                queryParam.setOrderId(orderId);
                List<SysRecipesPlan> oldRecipesPlanList = sysRecipesPlanMapper.selectSysRecipesPlanList(queryParam);
                //判断是否已存在食谱
                if (oldRecipesPlanList != null && oldRecipesPlanList.size() > 0) {
                    Long[] orderIdArray = new Long[1];
                    orderIdArray[0] = orderId;
                    //删除该订单对于食谱
                    delRecipesPlanByOrderId(orderIdArray);
                }
//                //判断订单金额、开始时间、结束时间，为空则直接返回，不重新生成食谱计划
//                if (sysOrder.getAmount().floatValue() <= 0 || sysOrder.getStartTime() == null || sysOrder.getServerEndTime() == null) {
//                    return;
//                }
                SysOrderPause pauseParam = new SysOrderPause();
                pauseParam.setOrderId(sysOrder.getOrderId());
                //暂停记录列表
                List<SysOrderPause> pauseList = sysOrderPauseService.selectSysOrderPauseList(pauseParam);
                List<SysRecipesPlan> planList = generatePlan(sysOrder, oldRecipesPlanList,
                        DateUtils.dateToLocalDate(sysOrder.getStartTime()), DateUtils.dateToLocalDate(sysOrder.getServerEndTime()), pauseList);
                if (planList != null && planList.size() > 0) {
                    sysRecipesPlanMapper.insertBatch(planList);
                }
                /*for (SysRecipesPlan sysRecipesPlan : planList) {
                     getTestDate(sysRecipesPlan.getStartDate(), sysRecipesPlan.getEndDate());
                 }*/
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // 一定要释放锁
            synchrolockUtil.unlock(String.format(generateRecipesPlanLockKey, orderId));
        }
    }

    public void getTestDate(Date date, Date date2) {
        LocalDate d = DateUtils.dateToLocalDate(date);
        LocalDate d2 = DateUtils.dateToLocalDate(date2);
        String s1 = d.getYear() + "-" + d.getMonthValue() + "-" + d.getDayOfMonth();
        String s2 = d2.getYear() + "-" + d2.getMonthValue() + "-" + d2.getDayOfMonth();
        System.out.println(s1 + "   " + s2);
    }

    /**
     * 根据订单ID、订单开始服务时间、结束时间、暂停列表生成食谱计划列表
     *
     * @param sysOrder           订单对象
     * @param oldRecipesPlanList 旧的食谱计划
     * @param serverStartDate    服务开始时间
     * @param serverEndDate      服务结束时间
     * @param pauseList          暂停列表
     * @return
     */
    public List<SysRecipesPlan> generatePlan(SysOrder sysOrder, List<SysRecipesPlan> oldRecipesPlanList,
                                             LocalDate serverStartDate, LocalDate serverEndDate, List<SysOrderPause> pauseList) {
        List<SysRecipesPlan> planList = new ArrayList<>();
        boolean breakFlag = false;
        LocalDate planStartDate = serverStartDate;
        LocalDate planEndDate = planStartDate.plusDays(6);
        while (true) {
            SysRecipesPlan sysRecipesPlan = new SysRecipesPlan();
            //判断是否大于服务到期时间
            if (ChronoUnit.DAYS.between(planEndDate, serverEndDate) <= 0) {
                planEndDate = serverEndDate;
                breakFlag = true;
            }
            long pauseDay = getPauseDayeCount(planStartDate, planEndDate, pauseList);
            //加上暂停时间
            planEndDate = planEndDate.plusDays(pauseDay);
            sysRecipesPlan.setStartDate(DateUtils.localDateToDate(planStartDate));
            sysRecipesPlan.setEndDate(DateUtils.localDateToDate(planEndDate));
            sysRecipesPlan.setOrderId(sysOrder.getOrderId());
            sysRecipesPlan.setCusId(sysOrder.getCusId());
            /*//当开始时间小于等于当前时间，默认为已发送，发送时间为前一天
            if(ChronoUnit.DAYS.between(planStartDate, LocalDate.now()) >= 0){
                sysRecipesPlan.setSendFlag(1);
                sysRecipesPlan.setSendTime(DateUtils.localDateToDate(LocalDate.now().minusDays(1)));
            }*/
            //将旧食谱计划中的发送状态、发送时间、食谱复制到新食谱计划中
            boolean existFlag = oldRecipesPlanList.size() >= planList.size() + 1;
            sysRecipesPlan.setSendFlag(existFlag ? oldRecipesPlanList.get(planList.size()).getSendFlag() : 0);
            sysRecipesPlan.setSendTime(existFlag ? oldRecipesPlanList.get(planList.size()).getSendTime() : null);
            sysRecipesPlan.setRecipesId(existFlag ? oldRecipesPlanList.get(planList.size()).getRecipesId() : null);

            planList.add(sysRecipesPlan);

            planStartDate = planEndDate.plusDays(1);
            planEndDate = planStartDate.plusDays(6);

            if (breakFlag) {
                break;
            }
        }
        return planList;
    }

    /**
     * 根据食谱开始时间、结束时间、暂停列表获取在食谱计划范围内的暂停天数
     *
     * @param planStartDate 计划开始时间
     * @param planEndDate   计划结束时间
     * @param pauseList     暂停列表
     * @return
     */
    public long getPauseDayeCount(LocalDate planStartDate, LocalDate planEndDate, List<SysOrderPause> pauseList) {
        long pauseDay = 0;
        //判断这个时间内是否存在暂停
        if (pauseList != null && pauseList.size() > 0) {
            for (SysOrderPause sysOrderPause : pauseList) {
                LocalDate pauseStartDate = DateUtils.dateToLocalDate(sysOrderPause.getPauseStartDate());
                LocalDate pauseEndDate = DateUtils.dateToLocalDate(sysOrderPause.getPauseEndDate());
                if (ChronoUnit.DAYS.between(pauseEndDate, planStartDate) > 0 || ChronoUnit.DAYS.between(planEndDate, pauseStartDate) > 0) {
                    continue;
                }
                if (ChronoUnit.DAYS.between(pauseStartDate, planStartDate) > 0) {
                    pauseStartDate = planStartDate;
                }
                if (ChronoUnit.DAYS.between(planEndDate, pauseEndDate) > 0) {
                    pauseEndDate = planEndDate;
                }
                pauseDay += ChronoUnit.DAYS.between(pauseStartDate, pauseEndDate);
            }
        }
        return pauseDay;
    }

    /**
     * 根据订单ID删除所有食谱安排计划
     *
     * @param orderIds 订单ID
     * @return
     */
    @Override
    public int delRecipesPlanByOrderId(Long[] orderIds) {
        return sysRecipesPlanMapper.delRecipesPlanByOrderId(orderIds);
    }

    /**
     * 多条件查询食谱计划列表
     *
     * @param sysRecipesPlan
     * @return
     */
    @Override
    public List<SysRecipesPlan> selectPlanListByCondition(SysRecipesPlan sysRecipesPlan) {
        return sysRecipesPlanMapper.selectPlanListByCondition(sysRecipesPlan);
    }

    /**
     * 根据订单ID查询食谱计划
     *
     * @param sysRecipesPlan
     * @return
     */
    @Override
    public List<SysRecipesPlan> selectPlanListByOrderId(SysRecipesPlan sysRecipesPlan) {
        return sysRecipesPlanMapper.selectPlanListByOrderId(sysRecipesPlan);
    }

    @Override
    public void myGenerateRecipesPlan(SysOrder sysOrder) {

    }
}