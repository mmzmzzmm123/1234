package com.stdiet.custom.service.impl;

import com.stdiet.common.utils.DateUtils;
import com.stdiet.common.utils.StringUtils;
import com.stdiet.common.utils.SynchrolockUtil;
import com.stdiet.common.utils.sign.Md5Utils;
import com.stdiet.custom.domain.SysOrder;
import com.stdiet.custom.domain.SysOrderPause;
import com.stdiet.custom.domain.SysRecipesPlan;
import com.stdiet.custom.domain.SysRecipesPlanListInfo;
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
import java.util.*;

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
        try{
            Thread.sleep(5000);
        }catch (Exception e){
            e.printStackTrace();
        }
        if (orderId == null || orderId <= 0) {
            return;
        }
        SysOrder sysOrder = sysOrderService.selectSysOrderById(orderId);
        //订单为空、金额小于0、订单未审核不进行食谱生成、更新，只对2021年开始的订单进行食谱计划生成，判断订单金额、开始时间、结束时间，为空则直接返回，不重新生成食谱计划
        if (sysOrder == null || !sysOrder.getReviewStatus().equals("yes") /*|| DateUtils.dateToLocalDate(sysOrder.getOrderTime()).getYear() < 2021*/
                || sysOrder.getAmount().floatValue() <= 0 || sysOrder.getStartTime() == null || sysOrder.getServerEndTime() == null) {
            return;
        }
        //判断是否提成单，拆分单中的副单，体验单,定金单
        if(sysOrder.getAfterSaleCommissOrder().intValue() == 1 || ("1".equals(sysOrder.getOrderType()) && sysOrder.getMainOrderId().intValue() != 0) ||
            "2".equals(sysOrder.getOrderType()) || "1".equals(sysOrder.getOrderMoneyType())){
            System.out.println("---------------------不生成食谱------------------------");
            return;
        }
        try {
            //获取redis中该订单对应的锁
            if (synchrolockUtil.lock(String.format(generateRecipesPlanLockKey, orderId))) {
                //判断是否已存在食谱计划
                SysRecipesPlan queryParam = new SysRecipesPlan();
                queryParam.setOrderId(orderId);
                List<SysRecipesPlan> oldRecipesPlanList = sysRecipesPlanMapper.selectSysRecipesPlanList(queryParam);
                //暂停记录列表，按暂停开始时间顺序
                List<SysOrderPause> pauseList = sysOrderPauseService.getPauseListByOrderId(sysOrder.getOrderId());
                List<SysRecipesPlan> planList = generatePlan(sysOrder, oldRecipesPlanList, DateUtils.dateToLocalDate(sysOrder.getStartTime()), DateUtils.dateToLocalDate(sysOrder.getServerEndTime()), pauseList);
                if(oldRecipesPlanList != null && oldRecipesPlanList.size() > 0){
                    updateOrAddRecipesPlan(oldRecipesPlanList, planList);
                }else{
                    if (planList != null && planList.size() > 0) {
                        sysRecipesPlanMapper.insertBatch(planList);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // 一定要释放锁
            synchrolockUtil.unlock(String.format(generateRecipesPlanLockKey, orderId));
        }
    }

    /**
     * 更新食谱计划，删除旧食谱中多余的，添加新食谱中多的
     * @param oldRecipesPlanList
     * @param newRecipesPlanList
     */
    private void updateOrAddRecipesPlan(List<SysRecipesPlan> oldRecipesPlanList, List<SysRecipesPlan> newRecipesPlanList){
        int newSize = newRecipesPlanList.size();
        int index = 0;
        List<SysRecipesPlan> addList = new ArrayList<>();
        List<SysRecipesPlan> updateList = new ArrayList<>();
        List<Long> delList = new ArrayList<>();
        for (SysRecipesPlan plan : oldRecipesPlanList) {
            if(index < newSize){
                plan.setStartDate(newRecipesPlanList.get(index).getStartDate());
                plan.setEndDate(newRecipesPlanList.get(index).getEndDate());
                plan.setPauseDate(newRecipesPlanList.get(index).getPauseDate());
                plan.setStartNumDay(plan.getStartNumDay());
                plan.setEndNumDay(plan.getEndNumDay());
                updateList.add(plan);
            }else{
                delList.add(plan.getId());
            }
            index++;
        }
        if(newSize > oldRecipesPlanList.size()){
            addList = newRecipesPlanList.subList(oldRecipesPlanList.size(),newSize);
        }
        //更新
        if(updateList.size() > 0){
            for (SysRecipesPlan plan : updateList) {
                sysRecipesPlanMapper.updateSysRecipesPlan(plan);
            }
        }
        //删除多余的食谱计划
        if(delList.size() > 0){
            sysRecipesPlanMapper.deleteSysRecipesPlanByIds(delList.toArray(new Long[delList.size()]));
        }
        //添加新的
        if(addList.size() > 0){
            sysRecipesPlanMapper.insertBatch(addList);
        }
    }

    /**
     * 根据订单ID、订单开始服务时间、结束时间、暂停列表生成食谱计划列表
     *
     * @param sysOrder           订单对象
     * @param serverStartDate    服务开始时间
     * @param serverEndDate      服务结束时间
     * @param pauseList          暂停列表
     * @return
     */
    private List<SysRecipesPlan> generatePlan(SysOrder sysOrder, List<SysRecipesPlan> oldRecipesPlanList, LocalDate serverStartDate, LocalDate serverEndDate, List<SysOrderPause> pauseList) {
        //查询该客户最后一次食谱计划对应天数
        int oldStartNumDay = 0;
        if(oldRecipesPlanList.size() == 0){
            SysRecipesPlan lastSysRecipesPlan = getLastDayRecipesPlan(sysOrder.getCusId());
            oldStartNumDay = lastSysRecipesPlan == null || lastSysRecipesPlan.getEndNumDay() == null ? 0 : lastSysRecipesPlan.getEndNumDay().intValue();
        }else{
            oldStartNumDay =  oldRecipesPlanList.get(0).getStartNumDay() - 1;
        }
        List<SysRecipesPlan> planList = new ArrayList<>();
        LocalDate planStartDate = null;
        LocalDate planEndDate = serverStartDate.plusDays(-1);
        boolean breakFlag = true;
        do {
            SysRecipesPlan sysRecipesPlan = new SysRecipesPlan();
            planStartDate = planEndDate.plusDays(1);
            planEndDate = planStartDate.plusDays(6);
            //判断是否大于服务到期时间
            if (ChronoUnit.DAYS.between(planEndDate, serverEndDate) <= 0) {
                planEndDate = serverEndDate;
            }
            String[] pauseResult = dealPlanPause(planStartDate, planEndDate, pauseList);
            //根据暂停结果返回的数据更新计划开始、结束时间
            planStartDate = DateUtils.stringToLocalDate(pauseResult[0],"yyyyMMdd");
            planEndDate = DateUtils.stringToLocalDate(pauseResult[1],"yyyyMMdd");
            //判断是否大于服务到期时间
            if (ChronoUnit.DAYS.between(planEndDate, serverEndDate) <= 0) {
                planEndDate = serverEndDate;
                breakFlag = false;
            }
            //暂停日期
            sysRecipesPlan.setPauseDate(pauseResult[3]);
            sysRecipesPlan.setStartDate(DateUtils.localDateToDate(planStartDate));
            sysRecipesPlan.setEndDate(DateUtils.localDateToDate(planEndDate));
            sysRecipesPlan.setOrderId(sysOrder.getOrderId());
            sysRecipesPlan.setCusId(sysOrder.getCusId());
            sysRecipesPlan.setOutId(Md5Utils.hash(String.valueOf(sysOrder.getCusId())));
            oldStartNumDay += 1;
            sysRecipesPlan.setStartNumDay(oldStartNumDay);
            long dayNumber = ChronoUnit.DAYS.between(planStartDate, planEndDate);
            oldStartNumDay +=  dayNumber > 6 ? 6 : dayNumber;
            sysRecipesPlan.setEndNumDay(oldStartNumDay);
            //添加暂停范围内的日期
            planList.add(sysRecipesPlan);
        }while (breakFlag);
        return planList;
    }

    /**
     * 根据食谱开始时间、结束时间、暂停列表获取在食谱计划范围内的暂停天数，以及返回调整之后的计划开始、结束时间，以及暂停日期
     * @param planStartDate 原先的计划开始时间
     * @param planEndDate 原先计划结束时间
     * @param pauseList 暂停列表集合
     * @return
     */
    private String[] dealPlanPause(LocalDate planStartDate, LocalDate planEndDate, List<SysOrderPause> pauseList) {
        //分别为计划开始时间、计划结束时间，范围内暂停天数，具体暂停日期，使用|隔开
        String[] result = {"","","0",""};
        long pauseDay = 0;
        Set<String> pauseDateString = new TreeSet<>(new MyComparator());
        //判断这个时间内是否存在暂停
        if (pauseList != null && pauseList.size() > 0) {
            //每条暂停时间的范围不会重叠，在添加暂停时做了限制
            for (SysOrderPause sysOrderPause : pauseList) {
                while(true){
                    LocalDate pauseStartDate = DateUtils.dateToLocalDate(sysOrderPause.getPauseStartDate());
                    LocalDate pauseEndDate = DateUtils.dateToLocalDate(sysOrderPause.getPauseEndDate());
                    if (ChronoUnit.DAYS.between(pauseEndDate, planStartDate) > 0 || ChronoUnit.DAYS.between(planEndDate, pauseStartDate) > 0) {
                        break;
                    }
                    if (ChronoUnit.DAYS.between(pauseStartDate, planStartDate) > 0) {
                        pauseStartDate = planStartDate;
                    }
                    if (ChronoUnit.DAYS.between(planEndDate, pauseEndDate) > 0) {
                        pauseEndDate = planEndDate;
                    }
                    //判断暂停记录是否从食谱计划开始时间开始的
                    if(ChronoUnit.DAYS.between(pauseStartDate, planStartDate) == 0){
                        planStartDate = pauseEndDate.plusDays(1);
                        planEndDate = planStartDate.plusDays(6);
                    }else{
                        planEndDate = planEndDate.plusDays(ChronoUnit.DAYS.between(pauseStartDate, pauseEndDate) + 1);
                        pauseDateString.addAll(getPauseDateString(pauseStartDate, pauseEndDate));
                        break;
                    }
                }
                /*System.out.println("---------------------------------------");
                System.out.println(DateUtils.localDateToString(planStartDate, "yyyy-MM-dd"));
                System.out.println(DateUtils.localDateToString(planEndDate, "yyyy-MM-dd"));
                System.out.println("---------------------------------------");*/
            }
        }
        result[0] = DateUtils.localDateToString(planStartDate,"yyyyMMdd");
        result[1] = DateUtils.localDateToString(planEndDate,"yyyyMMdd");
        result[2] = pauseDay + "";
        result[3] = pauseDateString.size() > 0 ? StringUtils.join(pauseDateString,"|") : "";
        return result;
    }

    /**
     * 根据暂停时间范围获取范围内的日期
     * @return
     */
    private Set<String> getPauseDateString(LocalDate pauseStartDate, LocalDate pauseEndDate){
        Set<String> pauseDateList = new HashSet<>();
        long daysBetween = ChronoUnit.DAYS.between(pauseStartDate, pauseEndDate);
        for(int i = 0; i <= daysBetween; i++){
            pauseDateList.add(DateUtils.localDateToString(pauseStartDate.plusDays(i),"yyyyMMdd"));
        }
        return pauseDateList;
    }

    /**
     * 集合排序key值比较器
     * */
    class MyComparator implements Comparator<String>{

        @Override
        public int compare(String o1, String o2) {
            return Integer.parseInt(o1) - Integer.parseInt(o2);
        }
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

    /**
     * 根据客户ID查询最后一天食谱计划
     * @param customerId
     * @return
     */
    public SysRecipesPlan getLastDayRecipesPlan(Long customerId){
        return sysRecipesPlanMapper.getLastDayRecipesPlan(customerId);
    }

    @Override
    public Long getCusIdByOutId(String outId) {
        return sysRecipesPlanMapper.getCusIdByOutId(outId);
    }

    @Override
    public List<SysRecipesPlanListInfo> selectRecipesPlanListInfo(String outId) {
        return sysRecipesPlanMapper.selectRecipesPlanListInfo(outId);
    }

    @Override
    public List<SysRecipesPlan> selectPlanListByCusId(Long cusId) {
        return sysRecipesPlanMapper.selectPlanListByCusId(cusId);
    }

    public List<SysRecipesPlan> selectRecipesModelList(SysRecipesPlan sysRecipesPlan) {
        return sysRecipesPlanMapper.selectRecipesModelList(sysRecipesPlan);
    }

}