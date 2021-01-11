package com.stdiet.custom.service.impl;

import com.stdiet.common.utils.DateUtils;
import com.stdiet.common.utils.StringUtils;
import com.stdiet.custom.domain.*;
import com.stdiet.custom.mapper.SysCommisionMapper;
import com.stdiet.custom.mapper.SysOrderMapper;
import com.stdiet.custom.mapper.SysOrderPauseMapper;
import com.stdiet.custom.service.ISysCommisionService;
import com.stdiet.custom.service.ISysCommissionDayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
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
        List<SysCommision> list = sysCommisionMapper.selectSysCommisionDayDetail(sysCommision);
        if(list != null && list.size() > 0){
            Map<Long, List<SysOrderCommisionDayDetail>> orderDetailMap = getOrderByList(null);
            SysCommissionDayDetail sysCommissionDayDetail = null;
            for(SysCommision commision : list){
                sysCommissionDayDetail = new SysCommissionDayDetail();
                sysCommissionDayDetail.setUserId(commision.getUserId());
                sysCommissionDayDetail.setNickName(commision.getUserName());
                sysCommissionDayDetail.setPostId(commision.getPostId());
                sysCommissionDayDetail.setPostName(commision.getPostName());
                sysCommissionDayDetail.setOrderTotalAmount(commision.getAmount());
                //获取查询时间
                LocalDate localDate = StringUtils.isEmpty(sysCommision.getBeginTime()) ? LocalDate.now() : LocalDate.parse(sysCommision.getBeginTime(), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                //dealServerOrderCommissionDetail2(orderDetailMap.get(sysCommissionDayDetail.getUserId()), sysCommissionDayDetail, localDate.getYear()+""+localDate.getMonth().getValue());
                dealServerOrderCommissionDetail2(orderDetailMap.get(sysCommissionDayDetail.getUserId()), sysCommissionDayDetail);
                result.add(sysCommissionDayDetail);
            }
        }
        return result;
    }


    /**
     * 根据用户ID统计出该用户在该月所有订单的服务数量、服务总天数、服务订单总额、暂停总天数
     * **/
    public void dealServerOrderCommissionDetail2(List<SysOrderCommisionDayDetail> orderDetailList, SysCommissionDayDetail sysCommissionDayDetail){
        //总提成
        double totalCommissionAmount = 0.0;
        //已发放提成
        double totalHasSentCommissionAmount = 0.0;
        //未发放提成
        double totalNotSentCommissionAmount = 0.0;
        //未发放提成记录
        List<Map<String, Object>> sendDetailList = new ArrayList<>();
        if(orderDetailList != null){
            //获取每个月的成交总额度
            Map<String, Double> orderAmount = new TreeMap<>(new MyComparator());
            Set<String> commissionMonthSet = new TreeSet<>(new MyComparator());
            for (SysOrderCommisionDayDetail sysOrderCommisionDayDetail : orderDetailList) {
                String yearMonth = sysOrderCommisionDayDetail.getOrderTime().getYear()+""+sysOrderCommisionDayDetail.getOrderTime().getMonth().getValue();
                if(orderAmount.containsKey(yearMonth)){
                    orderAmount.put(yearMonth, orderAmount.get(yearMonth) + sysOrderCommisionDayDetail.getOrderAmount().doubleValue());
                }else{
                    orderAmount.put(yearMonth, sysOrderCommisionDayDetail.getOrderAmount().doubleValue());
                }
                commissionMonthSet.addAll(sysOrderCommisionDayDetail.getEveryYearMonthServerMoney().keySet());
            }
            //获取提成比例以及计算提成
            Map<String, Float> rateMap = getRateByAmount2(sysCommissionDayDetail.getUserId(), sysCommissionDayDetail.getPostId(), orderAmount);

            int i = 1;
            double commissionA = 0D;
            for (String ym : commissionMonthSet) {
                double ym_mession = 0;
                if(orderAmount.containsKey(ym)){
                    totalCommissionAmount += orderAmount.get(ym) * rateMap.get(ym) / 100D;
                }
                for (SysOrderCommisionDayDetail sysOrderCommisionDayDetail : orderDetailList) {
                    Map<String, BigDecimal> everyYearMonthServerMoney = sysOrderCommisionDayDetail.getEveryYearMonthServerMoney();
                    if(everyYearMonthServerMoney.containsKey(ym)){
                        String orderYearMonth = sysOrderCommisionDayDetail.getOrderTime().getYear()+""+sysOrderCommisionDayDetail.getOrderTime().getMonth().getValue();
                        double m = (everyYearMonthServerMoney.get(ym) == null) ? 0 : everyYearMonthServerMoney.get(ym).doubleValue();
                        ym_mession += m * rateMap.get(orderYearMonth) / 100D;
                    }
                }
                if(isSendCommissionByYearMonth(ym)){
                    totalHasSentCommissionAmount += ym_mession;
                }else{
                    Map<String, Object> map = new HashMap<>();
                    map.put("yearMonth", ym);
                    if(i++ != commissionMonthSet.size()){
                        map.put("yearMonthCommission", getMoney(ym_mession, 1));
                    }else{
                        System.out.println("最后："+commissionA);
                        map.put("yearMonthCommission", getMoney(totalCommissionAmount - commissionA, 1));
                    }
                    sendDetailList.add(map);
                }
                commissionA += getMoney(ym_mession, 1).doubleValue();

            }
        }
        sysCommissionDayDetail.setTotalCommissionAmount(getMoney(totalCommissionAmount,1));
        sysCommissionDayDetail.setTotalHasSentCommissionAmount(getMoney(totalHasSentCommissionAmount,1));
        sysCommissionDayDetail.setTotalNotSentCommissionAmount(getMoney(totalCommissionAmount - totalHasSentCommissionAmount,1));
        sysCommissionDayDetail.setSendDetailList(sendDetailList);
    }

    public boolean isSendCommissionByYearMonth(String yearMonth){
        LocalDate localDate = LocalDate.of(Integer.parseInt(yearMonth.substring(0,4)), Integer.parseInt(yearMonth.substring(4)), 1);
        LocalDate nowDate = LocalDate.now();
        if(localDate.getMonth().getValue() >= 0){
            return false;
        }
        return true;
    }

    /**
     * 根据用户成交总额获取对应比例，再计算提成
     * */
    public Map<String, Float> getRateByAmount2(Long userId, Long postId, Map<String, Double> amountMap){
        SysCommision tmpQueryCom = new SysCommision();
        tmpQueryCom.setUserId(userId);
        tmpQueryCom.setPostId(postId);
        List<SysCommision> tmpComList = sysCommisionMapper.selectSysCommisionList(tmpQueryCom);
        Map<String, Float> rateMap = new TreeMap<>(new MyComparator());
        for(String yearMonth : amountMap.keySet()){
            double orderAmount = amountMap.get(yearMonth);
            rateMap.put(yearMonth, 0F);
            if(tmpComList != null && tmpComList.size() > 0){
                for (int i = 0; i < tmpComList.size(); i++) {
                    SysCommision com = tmpComList.get(i);
                    double cAmount = com.getAmount().floatValue();
                    if (orderAmount <= cAmount && i == 0) {
                        // 第一条规则
                        rateMap.put(yearMonth,com.getRate());
                        break;
                    } else if (i == tmpComList.size() - 1 && orderAmount > cAmount) {
                        // 最后一条规则
                        rateMap.put(yearMonth,com.getRate());
                        break;
                    } else if (cAmount < orderAmount && orderAmount <= tmpComList.get(i + 1).getAmount().floatValue()) {
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
     * 根据用户ID统计出该用户在该月所有订单的服务数量、服务总天数、服务订单总额、暂停总天数
     * **/
    public void dealServerOrderCommissionDetail(List<SysOrderCommisionDayDetail> orderDetailList, SysCommissionDayDetail sysCommissionDayDetail, String yearMonth){
        //服务订单数量
        int serverOrderCount = 0;
        //服务总天数
        int serverTotalDay = 0;
        //服务订单总金额
        double serverTotalAmount = 0.0;
        //暂停总天数
        int pauseTotalDay = 0;
        if(orderDetailList != null){
            for (SysOrderCommisionDayDetail sysOrderCommisionDayDetail : orderDetailList) {
                //Map<String, Integer> dayMap = ((Map<String, Integer>)orderMap.get("everyYearMonthServerDay"));
                if(sysOrderCommisionDayDetail.getEveryYearMonthServerDay() != null && sysOrderCommisionDayDetail.getEveryYearMonthServerDay().containsKey(yearMonth)){
                    int day = sysOrderCommisionDayDetail.getEveryYearMonthServerDay().get(yearMonth).intValue();
                    serverTotalDay += day;
                    serverOrderCount += day > 0 ? 1 : 0;
                }
                if(sysOrderCommisionDayDetail.getEveryYearMonthServerMoney() != null && sysOrderCommisionDayDetail.getEveryYearMonthServerMoney().containsKey(yearMonth)){
                    serverTotalAmount += sysOrderCommisionDayDetail.getEveryYearMonthServerMoney().get(yearMonth).doubleValue();
                }
                //dayMap = ((Map<String, Integer>)orderMap.get("everyYearMonthPauseDay"));
                if(sysOrderCommisionDayDetail.getEveryYearMonthPauseDay() != null && sysOrderCommisionDayDetail.getEveryYearMonthPauseDay().containsKey(yearMonth)){
                    pauseTotalDay += sysOrderCommisionDayDetail.getEveryYearMonthPauseDay().get(yearMonth).intValue();
                }
            }
        }
        sysCommissionDayDetail.setServerOrderTotalCount(serverOrderCount);
        sysCommissionDayDetail.setServerTotalDay(serverTotalDay);
        sysCommissionDayDetail.setServerTotalAmount(getMoney(serverTotalAmount, 1));
        sysCommissionDayDetail.setServerOrderPauseTotalDay(pauseTotalDay);
        //获取提成比例以及计算提成
        getRateByAmount(sysCommissionDayDetail);

    }

    /**
     * 根据用户成交总额获取对应比例，再计算提成
     * */
    public void getRateByAmount(SysCommissionDayDetail sysCommissionDayDetail){
        SysCommision tmpQueryCom = new SysCommision();
        tmpQueryCom.setUserId(sysCommissionDayDetail.getUserId());
        tmpQueryCom.setPostId(sysCommissionDayDetail.getPostId());
        List<SysCommision> tmpComList = sysCommisionMapper.selectSysCommisionList(tmpQueryCom);
        double orderAmount = sysCommissionDayDetail.getOrderTotalAmount().doubleValue();
        sysCommissionDayDetail.setRate(0F);
        sysCommissionDayDetail.setCommissionTotalAmount(getMoney(0.0D, 1));
        if(tmpComList != null && tmpComList.size() > 0){
            for (int i = 0; i < tmpComList.size(); i++) {
                SysCommision com = tmpComList.get(i);
                double cAmount = com.getAmount().floatValue();
                if (orderAmount <= cAmount && i == 0) {
                    // 第一条规则
                    sysCommissionDayDetail.setRate(com.getRate());
                    break;
                } else if (i == tmpComList.size() - 1 && orderAmount > cAmount) {
                    // 最后一条规则
                    sysCommissionDayDetail.setRate(com.getRate());
                    break;
                } else if (cAmount < orderAmount && orderAmount <= tmpComList.get(i + 1).getAmount().floatValue()) {
                    // 中间规则
                    sysCommissionDayDetail.setRate(tmpComList.get(i + 1).getRate());
                    break;
                }
            }
        }
        double serverAmount = sysCommissionDayDetail.getServerTotalAmount().doubleValue();
        serverAmount = serverAmount * sysCommissionDayDetail.getRate() / 100D;
        sysCommissionDayDetail.setCommissionTotalAmount(getMoney(serverAmount, 1));
    }

    /**
     * 查询2021年1月份之后所有订单，对订单进行处理，得出每笔订单的相关信息
     * */
    public Map<Long, List<SysOrderCommisionDayDetail>> getOrderByList(Long userId){
        //查询2021年1月份之后所有订单
        List<SysOrder> orderList = sysOrderMapper.selectSimpleOrderMessage(userId);
        //整理出每个用户对应的订单List
        Map<Long, List<SysOrderCommisionDayDetail>> userOrderResultMap = new HashMap<>();
        for (SysOrder sysOrder : orderList) {
            //建档时间为空、售后人员ID为空、营养师ID为空、订单金额为空或小于0，都视为异常订单
            if(sysOrder.getStartTime() == null || sysOrder.getAfterSaleId() == null || sysOrder.getNutritionistId() == null
                    || sysOrder.getAmount() == null || sysOrder.getAmount().doubleValue() < 0){
                System.out.println("客户："+ sysOrder.getCustomer() +",营养师："+sysOrder.getNutritionist() + ",售后" + sysOrder.getAfterSale());
                continue;
            }
            //对每笔订单进行处理，统计出该比订单在每年每月对应的服务天数、金额、暂停天数等
            SysOrderCommisionDayDetail sysOrderCommisionDayDetail = statisticsOrderMessage(sysOrder);
            addUserOrderResultMap(sysOrder.getAfterSaleId(), sysOrderCommisionDayDetail, userOrderResultMap);
            addUserOrderResultMap(sysOrder.getNutritionistId(), sysOrderCommisionDayDetail, userOrderResultMap);
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
        //服务开始时间(客户建档时间)
        LocalDate serverStartDate = DateUtils.dateToLocalDate(sysOrder.getStartTime());
        //订单总服务月数
        int serverMonth = sysOrder.getServeTimeId().intValue()/30;
        //赠送时长
        int giveDay = sysOrder.getGiveServeDay().intValue();
        //订单金额
        BigDecimal orderAmount = sysOrder.getAmount();
        //每年每月暂停天数，key为年份加月份，如:2021年1月=20211
        Map<String, Integer> everyYearMonthPauseDay = getEveryYearMonthPauseDay(sysOrder.getOrderPauseList());
        //该笔订单暂停总天数
        int pauseTotalDay = getTotalByMap(everyYearMonthPauseDay);
        //根据开始时间、服务月数、赠送天数、暂停天数算出该笔订单服务到期时间
        LocalDate serverEndDate = getServerEndDate(serverStartDate, serverMonth, giveDay, pauseTotalDay);
        //计算每年每月服务天数
        Map<String, Integer> everyYearMonthServerDay = getEveryYearMonthDayCount(serverStartDate, serverEndDate, everyYearMonthPauseDay);
        //服务总天数
        int serverDay = getTotalByMap(everyYearMonthServerDay);
        //每天对应金额
        BigDecimal dayMoney = getMoney(orderAmount.doubleValue()/serverDay, 1);
        //每年每月对于金额
        Map<String, BigDecimal> everyYearMonthServerMoney = getEveryMonthServerMoney(everyYearMonthServerDay, orderAmount, dayMoney);

        SysOrderCommisionDayDetail sysOrderCommisionDayDetail = new SysOrderCommisionDayDetail();
        sysOrderCommisionDayDetail.setOrderTime(DateUtils.dateToLocalDateTime(sysOrder.getOrderTime()));
        sysOrderCommisionDayDetail.setName(sysOrder.getCustomer());
        sysOrderCommisionDayDetail.setServerStartDate(serverStartDate);
        sysOrderCommisionDayDetail.setServerEndDate(serverEndDate);
        sysOrderCommisionDayDetail.setServerMonth(serverMonth);
        sysOrderCommisionDayDetail.setGiveDay(giveDay);
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
    public Map<String, Integer> getEveryYearMonthPauseDay(List<SysOrderPause> list){
        Map<String, Integer> pauseMap = new TreeMap<>(new MyComparator());
        for (SysOrderPause sysOrderPause : list) {
            if(sysOrderPause.getPauseStartDate() == null || sysOrderPause.getPauseEndDate() == null){
                continue;
            }
            LocalDate pauseStartDate = DateUtils.dateToLocalDate(sysOrderPause.getPauseStartDate());
            LocalDate pauseEndDate = DateUtils.dateToLocalDate(sysOrderPause.getPauseEndDate());
            //根据暂停记录获取该条记录在每年每月的暂停天数
            Map<String, Integer> orderYearMonthPauseDay = getEveryYearMonthDayCount(pauseStartDate, pauseEndDate, null);
            //每条暂停记录的暂停天数进行汇总
            for (String key : orderYearMonthPauseDay.keySet()) {
                if(pauseMap.containsKey(key)){
                    pauseMap.put(key, pauseMap.get(key) + orderYearMonthPauseDay.get(key));
                }else{
                    pauseMap.put(key, orderYearMonthPauseDay.get(key));
                }
            }
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
                everyMonthServerMoney.put(key, getMoney(everyMonthServerDay.get(key) * dayMoney.doubleValue(), 1));
                totalMoney += everyMonthServerMoney.get(key).doubleValue();
            }else{
                //由于小数点只保留一位，最后一个月的金额等于总额减去前几个月金额，避免总数不一致
                everyMonthServerMoney.put(key, getMoney(orderMoney.doubleValue() - totalMoney, 1));
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
            if(ChronoUnit.DAYS.between(everyMonthLastDate, endDate) >= 0){
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
     * double转为BigDecimal，保留一位小数，向下舍去
     * */
    public BigDecimal getMoney(double money, int n){
        return new BigDecimal(money).setScale(n, RoundingMode.DOWN);
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
        DecimalFormat df = new DecimalFormat("#.#");
        BigDecimal money = new BigDecimal(3300.00/91);
        System.out.println(Period.between(LocalDate.of(2020,2, 28), LocalDate.of(2020,1, 27)).getDays());
        System.out.println(ChronoUnit.DAYS.between(LocalDate.of(2021,2, 28), LocalDate.of(2021,3, 1)));
        System.out.println(LocalDate.of(2020,1, 20).getYear());
        System.out.println(LocalDate.of(2021,1, 30).plusMonths(1));

        System.out.println("s" + LocalDate.of(2021,1, 30).getMonth().getValue());

        System.out.println("----------------------------");
        LocalDate server_end_date = LocalDate.now().plusMonths(3);
        server_end_date = server_end_date.plusDays(7);
        server_end_date = server_end_date.minusDays(7);
        System.out.println(server_end_date);

        System.out.println(LocalDate.now().getMonth().getValue());

        Float f = 520.4F;
        System.out.println(f * 1 / 100F);

        System.out.println(new BigDecimal(f * 1 / 100F).setScale(1, RoundingMode.DOWN));
    }



}
