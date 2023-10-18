package com.ruoyi.office.service.impl;

import cn.hutool.core.date.DateTime;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.office.domain.TJoinUs;
import com.ruoyi.office.domain.TRoom;
import com.ruoyi.office.domain.TRoomOrder;
import com.ruoyi.office.domain.TStore;
import com.ruoyi.office.domain.vo.BusinessAnalysisH5Vo;
import com.ruoyi.office.mapper.TJoinUsMapper;
import com.ruoyi.office.service.*;
import org.apache.poi.hpsf.Decimal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.sql.Time;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

/**
 * 我要加盟Service业务层处理
 *
 * @author ruoyi
 * @date 2023-09-17
 */
@Service
public class BusinessAnalysisServiceImpl implements IBusinessAnalysisService {
    @Autowired
    private ITStoreService itStoreService;

    @Autowired
    private ITRoomService itRoomService;

    @Autowired
    private ITRoomOrderService itRoomOrderService;

    public BusinessAnalysisH5Vo getBusinessAnalysis(Long userId) {
        BusinessAnalysisH5Vo businessAnalysisH5Vo = new BusinessAnalysisH5Vo();
        BigDecimal totalAmount = new BigDecimal(0);
        BigDecimal payAmount = new BigDecimal(0);
        BigDecimal welfareAmount = new BigDecimal(0);
        BigDecimal couponAmount = new BigDecimal(0);

        BigDecimal totalAmountMonth = new BigDecimal(0);
        BigDecimal payAmountMonth = new BigDecimal(0);
        BigDecimal welfareAmountMonth = new BigDecimal(0);
        BigDecimal couponAmountMonth = new BigDecimal(0);

        BigDecimal totalAmountDay = new BigDecimal(0);
        BigDecimal payAmountDay = new BigDecimal(0);
        BigDecimal welfareAmountDay = new BigDecimal(0);
        BigDecimal couponAmountDay = new BigDecimal(0);

        Long todayOrderNum = 0L;
        Long thisMonthOrderNum = 0L;
        Long totalOrderNum = 0L;

        TStore tStore = new TStore();
        tStore.setUserId(userId);
        List<TStore> storeList = itStoreService.selectTStoreList(tStore);
        for (TStore store : storeList) {
            TRoom tRoom = new TRoom();
            tRoom.setStoreId(store.getId());
            BigDecimal totalStoreAmount = new BigDecimal(0);
            BigDecimal payStoreAmount = new BigDecimal(0);
            BigDecimal welfareStoreAmount = new BigDecimal(0);
            BigDecimal couponStoreAmount = new BigDecimal(0);

            BigDecimal totalStoreAmountToday = new BigDecimal(0);
            BigDecimal payStoreAmountToday = new BigDecimal(0);
            BigDecimal welfareStoreAmountToday = new BigDecimal(0);
            BigDecimal couponStoreAmountToday = new BigDecimal(0);

            BigDecimal totalStoreAmountMonth = new BigDecimal(0);
            BigDecimal payStoreAmountMonth = new BigDecimal(0);
            BigDecimal welfareStoreAmountMonth = new BigDecimal(0);
            BigDecimal couponStoreAmountMonth = new BigDecimal(0);
            List<TRoom> roomList = itRoomService.selectTRoomList(tRoom);
            for (TRoom room : roomList) {
                TRoomOrder tRoomOrder = new TRoomOrder();
                tRoomOrder.setRoomId(room.getId());
                List<TRoomOrder> roomOrderList = itRoomOrderService.selectTRoomOrderList(tRoomOrder);
                BigDecimal totalRoomAmount = roomOrderList.stream().map(TRoomOrder::getTotalAmount).reduce(BigDecimal.ZERO, BigDecimal::add);
                BigDecimal payRoomAmount = roomOrderList.stream().map(TRoomOrder::getPayAmount).reduce(BigDecimal.ZERO, BigDecimal::add);
                BigDecimal welfareRoomAmount = roomOrderList.stream().map(TRoomOrder::getWelfareAmount).reduce(BigDecimal.ZERO, BigDecimal::add);
                BigDecimal couponRoomAmount = roomOrderList.stream().map(TRoomOrder::getCouponAmount).reduce(BigDecimal.ZERO, BigDecimal::add);

                BigDecimal todayTotalRoomAmount = roomOrderList.stream().filter(x -> x.getStartTime().after(DateUtils.getTodayDate())).
                        filter(x -> x.getEndTime().after(DateUtils.getNextDayDate())).
                        map(TRoomOrder::getTotalAmount).reduce(BigDecimal.ZERO, BigDecimal::add);
                BigDecimal todayPayRoomAmount = roomOrderList.stream().filter(x -> x.getStartTime().after(DateUtils.getTodayDate())).
                        filter(x -> x.getEndTime().after(DateUtils.getNextDayDate())).
                        map(TRoomOrder::getPayAmount).reduce(BigDecimal.ZERO, BigDecimal::add);
                BigDecimal todayWelfareRoomAmount = roomOrderList.stream().filter(x -> x.getStartTime().after(DateUtils.getTodayDate())).
                        filter(x -> x.getEndTime().after(DateUtils.getNextDayDate())).
                        map(TRoomOrder::getWelfareAmount).reduce(BigDecimal.ZERO, BigDecimal::add);
                BigDecimal todayCouponRoomAmount = roomOrderList.stream().filter(x -> x.getStartTime().after(DateUtils.getTodayDate())).
                        filter(x -> x.getEndTime().after(DateUtils.getNextDayDate())).
                        map(TRoomOrder::getCouponAmount).reduce(BigDecimal.ZERO, BigDecimal::add);

                Date firstDayOfMonth = DateUtils.getFirstDayOfMonth(DateTime.now().month());
                Date lastDayOfMonth = DateUtils.getLastDayOfMonth(DateTime.now().month());
                BigDecimal monthTotalRoomAmount = roomOrderList.stream().filter(x -> x.getStartTime().after(firstDayOfMonth)).
                        filter(x -> x.getEndTime().after(lastDayOfMonth)).
                        map(TRoomOrder::getTotalAmount).reduce(BigDecimal.ZERO, BigDecimal::add);
                BigDecimal monthPayRoomAmount = roomOrderList.stream().filter(x -> x.getStartTime().after(firstDayOfMonth)).
                        filter(x -> x.getEndTime().after(lastDayOfMonth)).
                        map(TRoomOrder::getPayAmount).reduce(BigDecimal.ZERO, BigDecimal::add);
                BigDecimal monthWelfareRoomAmount = roomOrderList.stream().filter(x -> x.getStartTime().after(firstDayOfMonth)).
                        filter(x -> x.getEndTime().after(lastDayOfMonth)).
                        map(TRoomOrder::getWelfareAmount).reduce(BigDecimal.ZERO, BigDecimal::add);
                BigDecimal monthCouponRoomAmount = roomOrderList.stream().filter(x -> x.getStartTime().after(firstDayOfMonth)).
                        filter(x -> x.getEndTime().after(lastDayOfMonth)).
                        map(TRoomOrder::getCouponAmount).reduce(BigDecimal.ZERO, BigDecimal::add);

                totalStoreAmount = totalStoreAmount.subtract(totalRoomAmount);
                payStoreAmount = payStoreAmount.subtract(payRoomAmount);
                welfareStoreAmount = welfareStoreAmount.subtract(welfareRoomAmount);
                couponStoreAmount = couponStoreAmount.subtract(couponRoomAmount);

                totalStoreAmountToday = totalStoreAmountToday.subtract(todayTotalRoomAmount);
                payStoreAmountToday = payStoreAmountToday.subtract(todayPayRoomAmount);
                welfareStoreAmountToday = welfareStoreAmountToday.subtract(todayWelfareRoomAmount);
                couponStoreAmountToday = couponStoreAmountToday.subtract(todayCouponRoomAmount);

                totalStoreAmountMonth = totalStoreAmountMonth.subtract(monthTotalRoomAmount);
                payStoreAmountMonth = payStoreAmountMonth.subtract(monthPayRoomAmount);
                welfareStoreAmountMonth = welfareStoreAmountMonth.subtract(monthWelfareRoomAmount);
                couponStoreAmountMonth = couponStoreAmountMonth.subtract(monthCouponRoomAmount);

                if (room.getStartTime().before(DateUtils.getNextDayDate()) && room.getStartTime().after(DateUtils.getTodayDate()))
                    todayOrderNum++;
                if (room.getStartTime().before(lastDayOfMonth) && room.getStartTime().after(firstDayOfMonth))
                    thisMonthOrderNum++;
                totalOrderNum++;
            }

            totalAmount = totalAmount.subtract(totalStoreAmount);
            payAmount = payAmount.subtract(payStoreAmount);
            welfareAmount = welfareAmount.subtract(welfareStoreAmount);
            couponAmount = couponAmount.subtract(couponStoreAmount);

            totalAmountMonth = totalAmountMonth.subtract(totalStoreAmountMonth);
            payAmountMonth = payAmountMonth.subtract(payStoreAmountMonth);
            welfareAmountMonth = welfareAmountMonth.subtract(welfareStoreAmountMonth);
            couponAmountMonth = couponAmountMonth.subtract(couponStoreAmountMonth);

            totalAmountDay = totalAmountDay.subtract(totalStoreAmountToday);
            payAmountDay = payAmountDay.subtract(payStoreAmountToday);
            welfareAmountDay = welfareAmountDay.subtract(welfareStoreAmountToday);
            couponAmountDay = couponAmountDay.subtract(couponStoreAmountToday);
        }
        businessAnalysisH5Vo.setThisMonthOrderNum(thisMonthOrderNum);
        businessAnalysisH5Vo.setTotalOrderNum(totalOrderNum);
        businessAnalysisH5Vo.setTodayOrderNum(todayOrderNum);

        businessAnalysisH5Vo.setTodayIncome(payAmountDay);
        businessAnalysisH5Vo.setThisMonthIncome(payAmountMonth);
        businessAnalysisH5Vo.setTotalIncome(payAmount);

        businessAnalysisH5Vo.setTodayTurnover(totalAmountDay);
        businessAnalysisH5Vo.setThisMonthTurnover(totalAmountMonth);
        businessAnalysisH5Vo.setTotalTurnover(totalAmount);
        return businessAnalysisH5Vo;
    }
}
