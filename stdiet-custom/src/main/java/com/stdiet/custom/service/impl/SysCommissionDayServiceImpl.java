package com.stdiet.custom.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.stdiet.common.core.domain.AjaxResult;
import com.stdiet.common.utils.DateUtils;
import com.stdiet.custom.domain.*;
import com.stdiet.custom.dto.request.SysOrderCommision;
import com.stdiet.custom.dto.response.EveryMonthTotalAmount;
import com.stdiet.custom.mapper.SysCommisionMapper;
import com.stdiet.custom.mapper.SysOrderMapper;
import com.stdiet.custom.mapper.SysOrderPauseMapper;
import com.stdiet.custom.service.ISysCommissionDayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.*;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjusters;
import java.util.*;

@Service
public class SysCommissionDayServiceImpl implements ISysCommissionDayService {

    @Autowired
    private SysCommisionMapper sysCommisionMapper;

    @Autowired
    private SysOrderMapper sysOrderMapper;

    @Autowired
    private SysOrderPauseMapper sysOrderPauseMapper;

    @Override
    public List<SysCommissionDayDetail> calculateCommissionByDay(SysCommision sysCommision){
        List<SysCommissionDayDetail> result = new ArrayList<>();
        //查询用户
        List<SysCommision> list = sysCommisionMapper.getAfterSaleAndNutri(sysCommision);
        //合计
        SysCommissionDayDetail total = new SysCommissionDayDetail();
        total.setTotalCommissionAmount(new BigDecimal(0));
        total.setTotalHasSentCommissionAmount(new BigDecimal(0));
        total.setTotalNotSentCommissionAmount(new BigDecimal(0));
        total.setNextMonthCommission(new BigDecimal(0));
        if(list != null && list.size() > 0){
            Map<Long, List<SysOrderCommisionDayDetail>> orderDetailMap = getOrderByList(sysCommision);
            SysCommissionDayDetail sysCommissionDayDetail = null;
            for(SysCommision commision : list){
                sysCommissionDayDetail = new SysCommissionDayDetail();
                sysCommissionDayDetail.setUserId(commision.getUserId());
                sysCommissionDayDetail.setNickName(commision.getUserName());
                sysCommissionDayDetail.setPostId(commision.getPostId());
                sysCommissionDayDetail.setPostName(commision.getPostName());
                dealServerOrderCommissionDetail(orderDetailMap.get(sysCommissionDayDetail.getUserId()), sysCommissionDayDetail);
                result.add(sysCommissionDayDetail);
                //统计所以用户总提成、已发放提成、未发放提成
                total.setTotalCommissionAmount(total.getTotalCommissionAmount().add(sysCommissionDayDetail.getTotalCommissionAmount()));
                total.setTotalHasSentCommissionAmount(total.getTotalHasSentCommissionAmount().add(sysCommissionDayDetail.getTotalHasSentCommissionAmount()));
                total.setTotalNotSentCommissionAmount(total.getTotalNotSentCommissionAmount().add(sysCommissionDayDetail.getTotalNotSentCommissionAmount()));
                total.setNextMonthCommission(total.getNextMonthCommission().add(sysCommissionDayDetail.getNextMonthCommission()));
            }
        }
        total.setPostName("胜唐体控");
        total.setNickName("合计");
        result.add(total);
        return result;
    }

    /**
     * 根据订单计算该笔订单的服务到期时间
     * @param sysOrder 订单对象
     * @return
     */
    @Override
    public LocalDate getServerEndDate(SysOrder sysOrder){
        LocalDate serverEndDate = null;
        if(sysOrder != null && sysOrder.getStartTime() != null){
            //服务开始时间(客户建档时间)
            LocalDate serverStartDate = DateUtils.dateToLocalDate(sysOrder.getStartTime());
            //订单总服务月数
            int serverMonth = sysOrder.getServeTimeId() != null ? sysOrder.getServeTimeId().intValue()/30 : 0;
            //服务天数(不满一个月的零头)
            int serverSmallDay = sysOrder.getServeTimeId().intValue()%30 - (serverMonth > 0 ? 0 : 1);
            //赠送时长
            int giveDay = sysOrder.getGiveServeDay() != null ? sysOrder.getGiveServeDay().intValue() : 0;
            //服务到期时间（加赠送时间，不加暂停时间）
            serverEndDate = serverStartDate.plusMonths(serverMonth).plusDays(giveDay+serverSmallDay);
            List<SysOrderPause> pausesList = sysOrder.getOrderPauseList();
            if(pausesList == null){
                if(sysOrder.getOrderId() != null){
                    SysOrderPause sysOrderPause = new SysOrderPause();
                    sysOrderPause.setOrderId(sysOrder.getOrderId());
                    pausesList = sysOrderPauseMapper.selectSysOrderPauseList(sysOrderPause);
                }else{
                    pausesList = new ArrayList<>();
                }
            }
            //每年每月暂停天数，key为年份加月份，如:2021年1月=20211
            Map<String, Integer> everyYearMonthPauseDay = getEveryYearMonthPauseDay(pausesList, serverStartDate, serverEndDate);
            //该笔订单暂停总天数
            int pauseTotalDay = getTotalByMap(everyYearMonthPauseDay);
            //服务到期时间加上暂停时间
            serverEndDate = serverEndDate.plusDays(pauseTotalDay);
        }
        return serverEndDate;
    }

    /**
     * 计算订单提成详情
     * @param sysCommision
     */
    @Override
    public AjaxResult calculateOrderCommissionDetail(SysCommision sysCommision){
        AjaxResult result = AjaxResult.error("参数错误");
        if(sysCommision.getUserId() == null){
            return result;
        }
        //分页查询2021年1月份之后所有订单
        Map<Long, List<SysOrderCommisionDayDetail>> orderUserMap = getOrderByList(sysCommision);
        if(orderUserMap == null || !orderUserMap.containsKey(sysCommision.getUserId())){
            return result;
        }
        //查询用户
        List<SysCommision> list = sysCommisionMapper.getAfterSaleAndNutri(sysCommision);
        if(list == null || list.size() == 0){
            return result;
        }
        Long userId = list.get(0).getUserId();
        Long postId = list.get(0).getPostId();
        //获取每个月的成交总额度
        List<EveryMonthTotalAmount> everyMonthTotalAmountList = sysOrderMapper.getTotalAmountByUserId(sysCommision);
        if(everyMonthTotalAmountList == null || everyMonthTotalAmountList.size() == 0){
            return result;
        }
        Map<String, BigDecimal> everyMonthTotalAmountMap = new TreeMap<>(new MyComparator());
        for (EveryMonthTotalAmount everyMonthTotalAmount : everyMonthTotalAmountList) {
            everyMonthTotalAmountMap.put(everyMonthTotalAmount.getYearMonth(), everyMonthTotalAmount.getTotalAmount());
        }
        //获取每个月的提成比例以及计算提成
        Map<String, Float> rateMap = getRateByAmount(userId, postId, everyMonthTotalAmountMap);
        BigDecimal totalCommission = BigDecimal.valueOf(0);
        //根据用户ID获取对应订单列表
        List<SysOrderCommisionDayDetail> orderDetailList = orderUserMap.get(userId);
        for(SysOrderCommisionDayDetail sysOrderCommisionDayDetail : orderDetailList){
            String yearMonth = sysOrderCommisionDayDetail.getOrderTime().getYear() + "" + sysOrderCommisionDayDetail.getOrderTime().getMonth().getValue();
            sysOrderCommisionDayDetail.setMonthOrderTotalAmount(everyMonthTotalAmountMap.get(yearMonth));
            sysOrderCommisionDayDetail.setCommissionRate(rateMap.get(yearMonth));
            //计算该笔订单总提成
            sysOrderCommisionDayDetail.setOrderCommission(getMoney(sysOrderCommisionDayDetail.getOrderAmount().doubleValue() * sysOrderCommisionDayDetail.getCommissionRate() / 100D));
            Map<String, BigDecimal> everyYearMonthServerCommission = new TreeMap<>(new MyComparator());
            for (String everyMonth : sysOrderCommisionDayDetail.getEveryYearMonthServerMoney().keySet()) {
                everyYearMonthServerCommission.put(everyMonth, getMoney(sysOrderCommisionDayDetail.getEveryYearMonthServerMoney().get(everyMonth).doubleValue() * sysOrderCommisionDayDetail.getCommissionRate() / 100D));
            }
            sysOrderCommisionDayDetail.setEveryYearMonthServerCommission(everyYearMonthServerCommission);
            totalCommission = totalCommission.add(sysOrderCommisionDayDetail.getOrderCommission());
        }
        result = AjaxResult.success();
        int total = sysOrderMapper.selectSimpleOrderMessageCount(sysCommision);
        result.put("total", total);
        result.put("list", orderDetailList);
        result.put("totalCommission", totalCommission);
        return result;
    }


    /**
     * 根据用户ID统计出该用户在该月所有订单的服务数量、服务总天数、服务订单总额、暂停总天数
     * **/
    public void dealServerOrderCommissionDetail(List<SysOrderCommisionDayDetail> orderDetailList, SysCommissionDayDetail sysCommissionDayDetail){
        //总提成
        BigDecimal totalCommissionAmount = new BigDecimal(0);
        //已发放提成
        BigDecimal totalHasSentCommissionAmount = new BigDecimal(0);
        //未发放提成记录
        List<Map<String, Object>> sendDetailList = new ArrayList<>();
        if(orderDetailList != null){
            //获取每个月的成交总额度
            Map<String, BigDecimal> orderAmount = new TreeMap<>(new MyComparator());
            Set<String> commissionMonthSet = new TreeSet<>(new MyComparator());
            for (SysOrderCommisionDayDetail sysOrderCommisionDayDetail : orderDetailList) {
                String yearMonth = sysOrderCommisionDayDetail.getOrderTime().getYear()+""+sysOrderCommisionDayDetail.getOrderTime().getMonth().getValue();
                if(orderAmount.containsKey(yearMonth)){
                    orderAmount.put(yearMonth, orderAmount.get(yearMonth).add(sysOrderCommisionDayDetail.getOrderAmount()));
                }else{
                    orderAmount.put(yearMonth, sysOrderCommisionDayDetail.getOrderAmount());
                }
                commissionMonthSet.addAll(sysOrderCommisionDayDetail.getEveryYearMonthServerMoney().keySet());
            }
            //获取提成比例以及计算提成
            Map<String, Float> rateMap = getRateByAmount(sysCommissionDayDetail.getUserId(), sysCommissionDayDetail.getPostId(), orderAmount);

            int i = 1;
            BigDecimal commissionA = new BigDecimal(0);
            for (String ym : commissionMonthSet) {
                BigDecimal ym_mession = new BigDecimal(0);
                if(orderAmount.containsKey(ym)){
                    totalCommissionAmount = totalCommissionAmount.add(getMoney(orderAmount.get(ym).doubleValue() * rateMap.get(ym) / 100D));
                }
                for (SysOrderCommisionDayDetail sysOrderCommisionDayDetail : orderDetailList) {
                    Map<String, BigDecimal> everyYearMonthServerMoney = sysOrderCommisionDayDetail.getEveryYearMonthServerMoney();
                    if(everyYearMonthServerMoney.containsKey(ym)){
                        String orderYearMonth = sysOrderCommisionDayDetail.getOrderTime().getYear()+""+sysOrderCommisionDayDetail.getOrderTime().getMonth().getValue();
                        BigDecimal m = (everyYearMonthServerMoney.get(ym) == null) ? new BigDecimal(0) : everyYearMonthServerMoney.get(ym);
                        ym_mession = ym_mession.add(getMoney(m.doubleValue() * rateMap.get(orderYearMonth) / 100D));
                    }
                }
                if(isSendCommissionByYearMonth(ym)){
                    totalHasSentCommissionAmount = totalHasSentCommissionAmount.add(ym_mession);
                }else{
                    Map<String, Object> map = new HashMap<>();
                    map.put("yearMonth", ym);
                    if(i != commissionMonthSet.size()){
                        //计算未发放的提成（除最后一个月）
                        commissionA = commissionA.add(ym_mession);
                        map.put("yearMonthCommission", ym_mession);
                    }else{
                        //最后一个月除去已发放的月份的提成，再减去前面未发放提成，防止出现误差
                        map.put("yearMonthCommission", totalCommissionAmount.subtract(totalHasSentCommissionAmount).subtract(commissionA));
                    }
                    sendDetailList.add(map);
                }
                i++;
            }
        }
        sysCommissionDayDetail.setTotalCommissionAmount(totalCommissionAmount);
        sysCommissionDayDetail.setTotalHasSentCommissionAmount(totalHasSentCommissionAmount);
        //未发放提成 = 总提成 - 已发放提成
        sysCommissionDayDetail.setTotalNotSentCommissionAmount(totalCommissionAmount.subtract(totalHasSentCommissionAmount));
        sysCommissionDayDetail.setSendDetailList(sendDetailList);
        sysCommissionDayDetail.setNextMonthCommission(sendDetailList.size() > 0 ? (BigDecimal)sendDetailList.get(0).get("yearMonthCommission") : new BigDecimal(0));
    }

    /**判断该月提成是否已发放*/
    public boolean isSendCommissionByYearMonth(String yearMonth){
        LocalDate localDate = LocalDate.of(Integer.parseInt(yearMonth.substring(0,4)), Integer.parseInt(yearMonth.substring(4)), 1);
        LocalDate nowDate = LocalDate.now();
        long betweenMonth = ChronoUnit.MONTHS.between(localDate, nowDate);
        if(betweenMonth > 1){
            return true;
        }else if(betweenMonth == 1){
            //判断目前是否大于15号
            return nowDate.getDayOfMonth() > 15 ? true : false;
        }
        return false;
    }

    /**
     * 根据用户成交总额获取对应比例，再计算提成
     * */
    public Map<String, Float> getRateByAmount(Long userId, Long postId, Map<String, BigDecimal> amountMap){
        SysCommision tmpQueryCom = new SysCommision();
        tmpQueryCom.setUserId(userId);
        tmpQueryCom.setPostId(postId);
        List<SysCommision> tmpComList = sysCommisionMapper.selectSysCommisionList(tmpQueryCom);
        Map<String, Float> rateMap = new TreeMap<>(new MyComparator());
        for(String yearMonth : amountMap.keySet()){
            BigDecimal orderAmount = amountMap.get(yearMonth);
            rateMap.put(yearMonth, 0F);
            if(tmpComList != null && tmpComList.size() > 0){
                for (int i = 0; i < tmpComList.size(); i++) {
                    SysCommision com = tmpComList.get(i);
                    double cAmount = com.getAmount().floatValue();
                    if (orderAmount.floatValue() <= cAmount && i == 0) {
                        // 第一条规则
                        rateMap.put(yearMonth,com.getRate());
                        break;
                    } else if (i == tmpComList.size() - 1 && orderAmount.floatValue() > cAmount) {
                        // 最后一条规则
                        rateMap.put(yearMonth,com.getRate());
                        break;
                    } else if (cAmount < orderAmount.floatValue() && orderAmount.floatValue() <= tmpComList.get(i + 1).getAmount().floatValue()) {
                        // 中间规则
                        rateMap.put(yearMonth,tmpComList.get(i + 1).getRate());
                        break;
                    }
                }
            }
        }
        return rateMap;
    }

    /**
     * 查询2021年1月份之后所有订单，对订单进行处理，得出每笔订单的相关信息
     * */
    public Map<Long, List<SysOrderCommisionDayDetail>> getOrderByList(SysCommision sysCommision){
        //查询2021年1月份之后所有订单
        List<SysOrder> orderList = sysOrderMapper.selectSimpleOrderMessage(sysCommision);
        //整理出每个用户对应的订单List
        Map<Long, List<SysOrderCommisionDayDetail>> userOrderResultMap = new HashMap<>();
        for (SysOrder sysOrder : orderList) {
            //提成开始时间为空、售后人员ID为空、营养师ID都为空、订单金额为空，都视为异常订单
            if(sysOrder.getOrderTime() == null || sysOrder.getCommissStartTime() == null || sysOrder.getServeTimeId() == null
                    || (sysOrder.getAfterSaleId() == null && sysOrder.getNutritionistId() == null)
                    || sysOrder.getAmount() == null){
                //System.out.println("客户："+ sysOrder.getCustomer() +",营养师："+sysOrder.getNutritionist() + ",售后" + sysOrder.getAfterSale());
                continue;
            }
            //对每笔订单进行处理，统计出该比订单在每年每月对应的服务天数、金额、暂停天数等
            SysOrderCommisionDayDetail sysOrderCommisionDayDetail = statisticsOrderMessage(sysOrder);
            if(sysOrder.getAfterSaleId() != null && sysOrder.getAfterSaleId() > 0L){
                addUserOrderResultMap(sysOrder.getAfterSaleId(), sysOrderCommisionDayDetail, userOrderResultMap);
            }
            if(sysOrder.getNutritionistId() != null && sysOrder.getNutritionistId() > 0L){
                addUserOrderResultMap(sysOrder.getNutritionistId(), sysOrderCommisionDayDetail, userOrderResultMap);
            }
        }
        return userOrderResultMap;
    }

    /**
     * 根据用户ID将订单添加到对应用户的订单List
     * */
    public void addUserOrderResultMap(Long id, SysOrderCommisionDayDetail sysOrderCommisionDayDetail, Map<Long, List<SysOrderCommisionDayDetail>> map){
        if(map.containsKey(id)){
            map.get(id).add(sysOrderCommisionDayDetail);
        }else{
            List<SysOrderCommisionDayDetail> orderMessageMapList = new ArrayList<>();
            orderMessageMapList.add(sysOrderCommisionDayDetail);
            map.put(id, orderMessageMapList);
        }
    }

    /**
     * 统计每笔订单的服务开始时间、结束时间、每年每月服务天数、服务金额、服务暂停天数等信息
     * */
    public SysOrderCommisionDayDetail statisticsOrderMessage(SysOrder sysOrder){
        //提成计算开始时间（与食谱计划开始时间可能不同）
        LocalDate serverStartDate = DateUtils.dateToLocalDate(sysOrder.getCommissStartTime());
        //订单总服务月数
        int serverMonth = sysOrder.getServeTimeId().intValue()/30;
        //服务天数(不满一个月的零头)
        int serverSmallDay = sysOrder.getServeTimeId().intValue()%30 - (serverMonth > 0 ? 0 : 1);
        //赠送时长
        int giveDay = sysOrder.getGiveServeDay().intValue();
        //服务到期时间（加赠送时间，不加暂停时间）
        LocalDate serverEndDate = serverStartDate.plusMonths(serverMonth).plusDays(giveDay+serverSmallDay);
        //订单金额
        BigDecimal orderAmount = sysOrder.getAmount();
        //每年每月暂停天数，key为年份加月份，如:2021年1月=20211
        Map<String, Integer> everyYearMonthPauseDay = getEveryYearMonthPauseDay(sysOrder.getOrderPauseList(), serverStartDate, serverEndDate);
        //该笔订单暂停总天数
        int pauseTotalDay = getTotalByMap(everyYearMonthPauseDay);
        //服务到期时间加上暂停时间
        serverEndDate = serverEndDate.plusDays(pauseTotalDay);
        //计算每年每月服务天数
        Map<String, Integer> everyYearMonthServerDay = getEveryYearMonthDayCount(serverStartDate, serverEndDate, everyYearMonthPauseDay);
        //服务总天数
        int serverDay = getTotalByMap(everyYearMonthServerDay);
        //每天对应金额
        BigDecimal dayMoney = getMoney(orderAmount.doubleValue()/serverDay);
        //每年每月对于金额
        Map<String, BigDecimal> everyYearMonthServerMoney = getEveryMonthServerMoney(everyYearMonthServerDay, orderAmount, dayMoney);

        SysOrderCommisionDayDetail sysOrderCommisionDayDetail = new SysOrderCommisionDayDetail();
        sysOrderCommisionDayDetail.setOrderTime(DateUtils.dateToLocalDateTime(sysOrder.getOrderTime()));
        sysOrderCommisionDayDetail.setName(sysOrder.getCustomer());
        sysOrderCommisionDayDetail.setServerStartDate(serverStartDate);
        sysOrderCommisionDayDetail.setServerEndDate(serverEndDate);
        sysOrderCommisionDayDetail.setServerMonth(serverMonth);
        sysOrderCommisionDayDetail.setGiveDay(giveDay);
        sysOrderCommisionDayDetail.setPauseTotalDay(pauseTotalDay);
        sysOrderCommisionDayDetail.setOrderAmount(orderAmount);
        sysOrderCommisionDayDetail.setServerDay(serverDay);
        sysOrderCommisionDayDetail.setDayMoney(dayMoney);
        sysOrderCommisionDayDetail.setEveryYearMonthPauseDay(everyYearMonthPauseDay);
        sysOrderCommisionDayDetail.setEveryYearMonthServerDay(everyYearMonthServerDay);
        sysOrderCommisionDayDetail.setEveryYearMonthServerMoney(everyYearMonthServerMoney);

        return sysOrderCommisionDayDetail;
    }

    /**
     * 获取每年每月暂停天数
     * @Param list 暂停记录集合
     * */
    public Map<String, Integer> getEveryYearMonthPauseDay(List<SysOrderPause> list, LocalDate serverStartDate, LocalDate serverEndDate){
        Map<String, Integer> pauseMap = new TreeMap<>(new MyComparator());
        if(list == null){
            return pauseMap;
        }
        for (SysOrderPause sysOrderPause : list) {
            if(sysOrderPause.getPauseStartDate() == null || sysOrderPause.getPauseEndDate() == null){
                continue;
            }
            LocalDate pauseStartDate = DateUtils.dateToLocalDate(sysOrderPause.getPauseStartDate());
            LocalDate pauseEndDate = DateUtils.dateToLocalDate(sysOrderPause.getPauseEndDate());
            //判断暂停时间段是否在服务周期范围内
            if(ChronoUnit.DAYS.between(pauseEndDate, serverStartDate) > 0 || ChronoUnit.DAYS.between(serverEndDate, pauseStartDate) > 0){
                continue;
            }
            if(ChronoUnit.DAYS.between(pauseStartDate, serverStartDate) > 0){
                pauseStartDate = serverStartDate;
            }
            if(ChronoUnit.DAYS.between(serverEndDate, pauseEndDate) > 0){
                pauseEndDate = serverEndDate;
            }
            //根据暂停记录获取该条记录在每年每月的暂停天数
            Map<String, Integer> orderYearMonthPauseDay = getEveryYearMonthDayCount(pauseStartDate, pauseEndDate, null);
            int totalDay = 0;
            //每条暂停记录的暂停天数进行汇总
            for (String key : orderYearMonthPauseDay.keySet()) {
                totalDay += orderYearMonthPauseDay.get(key).intValue();
                if(pauseMap.containsKey(key)){
                    pauseMap.put(key, pauseMap.get(key) + orderYearMonthPauseDay.get(key));
                }else{
                    pauseMap.put(key, orderYearMonthPauseDay.get(key));
                }
            }
            //服务到期时间刷新
            serverEndDate = serverEndDate.plusDays(totalDay);
        }
        return pauseMap;
    }

    /**
     * 获取Map集合中Value的总和
     * */
    public int getTotalByMap(Map<String, Integer> map){
        int total = 0;
        for(String key : map.keySet()){
            total += map.get(key).intValue();
        }
        return total;
    }

    /**
     * 获取订单服务时间范围中每年每月服务天数，减去当月暂停天数
     * @Param server_start_date 服务开始时间
     * @Param server_end_date   服务到期时间
     * @Param pauseDayMap       每年每月暂停天数Map
     * */
    public Map<String, Integer> getEveryMonthServerDay(LocalDate server_start_date, LocalDate server_end_date, Map<String, Integer> pauseDayMap){
        return getEveryYearMonthDayCount(server_start_date, server_end_date, pauseDayMap);
    }

    /**
     * 获取订单服务时间范围中每年每月服务金额
     * @Param everyMonthServerDay 每年每月服务天数
     * @Param orderMoney 订单总额
     * @Param dayMoney 每天对于金额
     * */
    public Map<String, BigDecimal> getEveryMonthServerMoney(Map<String, Integer> everyMonthServerDay, BigDecimal orderMoney, BigDecimal dayMoney){
        Map<String, BigDecimal > everyMonthServerMoney = new TreeMap<>(new MyComparator());
        Set<String> keySet = everyMonthServerDay.keySet();
        int i = 1;
        double totalMoney = 0.0;
        for(String key : keySet){
            if(i++ != keySet.size()){
                everyMonthServerMoney.put(key, getMoney(everyMonthServerDay.get(key) * dayMoney.doubleValue()));
                totalMoney += everyMonthServerMoney.get(key).doubleValue();
            }else{
                //由于小数点只保留一位，最后一个月的金额等于总额减去前几个月金额，避免总数不一致
                everyMonthServerMoney.put(key, getMoney(orderMoney.doubleValue() - totalMoney));
            }
        }
        return everyMonthServerMoney;
    }


    /**
     * 根据开始日期、结束日期统计出时间范围内每年每月对应的天数
     * */
    public Map<String, Integer> getEveryYearMonthDayCount(LocalDate startDate, LocalDate endDate, Map<String, Integer> lessDayMap){
        Map<String, Integer> everyYearMonthServerDay = new TreeMap<>(new MyComparator());
        //每月开始第一天
        LocalDate everyMonthFirstDate = startDate;
        //每月最后一天
        LocalDate everyMonthLastDate = everyMonthFirstDate.with(TemporalAdjusters.lastDayOfMonth());
        int day = 0;
        boolean breakFlag = false;
        //写100防止死循环
        for(int i = 0; i < 100; i++){
            if(ChronoUnit.DAYS.between(everyMonthLastDate, endDate) > 0){
                day = Period.between(everyMonthFirstDate, everyMonthLastDate).getDays() + 1;
            }else{
                day = Period.between(everyMonthFirstDate, endDate).getDays() + 1;
                breakFlag = true;
            }
            String key = everyMonthFirstDate.getYear()+""+everyMonthFirstDate.getMonth().getValue();
            day -= (lessDayMap == null || lessDayMap.get(key) == null) ? 0 : lessDayMap.get(key);
            everyYearMonthServerDay.put(key, day >= 0 ? day : 0);
            everyMonthFirstDate = (everyMonthFirstDate.plusMonths(1));
            everyMonthFirstDate = everyMonthFirstDate.of(everyMonthFirstDate.getYear(), everyMonthFirstDate.getMonthValue(), 1);
            everyMonthLastDate = everyMonthFirstDate.with(TemporalAdjusters.lastDayOfMonth());
            if(breakFlag){
                break;
            }
        }
        return everyYearMonthServerDay;
    }

    /**
     * 根据服务月数、赠送时长、暂停天数计算出服务截止日期
     * **/
    public LocalDate getServerEndDate(LocalDate server_start_date, int server_month, int give_daye, int pauseDayCount){
        return server_start_date.plusMonths(server_month).plusDays(give_daye + pauseDayCount);
    }

    /**
     * double转为BigDecimal，保留2位小数，四舍五入
     * */
    public BigDecimal getMoney(Double money){
        return new BigDecimal(money.toString()).setScale(2, BigDecimal.ROUND_HALF_UP);
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

    public static void main(String[] args){
        Double s = 31190.1;
       /* NumberFormat nf = NumberFormat.getNumberInstance();
        // 保留小数位数
        nf.setMaximumFractionDigits(1);
        // 如果不需要四舍五入，可以使用
        nf.setRoundingMode(RoundingMode.DOWN);
        System.out.println(Double.parseDouble(nf.format(s)));
        System.out.println( new BigDecimal(nf.format(s)).doubleValue());*/
        /*System.out.println(new BigDecimal(s).setScale(1, BigDecimal.ROUND_HALF_UP).doubleValue());

        LocalDate localDate = LocalDate.of(2020, 1, 15);
        System.out.println(ChronoUnit.MONTHS.between(localDate, LocalDate.now()));
        System.out.println(localDate.getDayOfMonth());
        System.out.println(localDate.getDayOfWeek());
        System.out.println(localDate.getDayOfYear());

        System.out.println(ChronoUnit.DAYS.between(LocalDate.of(2021, 1,14), LocalDate.now()));*/

        /*System.out.println(ts(109792.8 * 6 / 100D, 1));
        System.out.println(ts(6587.8,2).doubleValue());*/

    }
}
