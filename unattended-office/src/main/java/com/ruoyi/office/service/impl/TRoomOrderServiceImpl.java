package com.ruoyi.office.service.impl;

import java.math.BigDecimal;
import java.time.LocalTime;
import java.util.Date;
import java.util.List;

import ch.qos.logback.core.util.TimeUtil;
import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.office.domain.TRoom;
import com.ruoyi.office.domain.TRoomPrice;
import com.ruoyi.office.domain.TWxUserAmount;
import com.ruoyi.office.domain.vo.GetRoomPriceVo;
import com.ruoyi.office.service.ITRoomPriceService;
import com.ruoyi.office.service.ITRoomService;
import com.ruoyi.office.service.ITWxUserAmountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruoyi.office.mapper.TRoomOrderMapper;
import com.ruoyi.office.domain.TRoomOrder;
import com.ruoyi.office.service.ITRoomOrderService;

/**
 * 房间占用（点支付时再次校验可用性并改变状态，支付失败回滚）Service业务层处理
 *
 * @author ruoyi
 * @date 2023-05-29
 */
@Service
public class TRoomOrderServiceImpl extends ServiceImpl<TRoomOrderMapper, TRoomOrder> implements ITRoomOrderService {
    @Autowired
    private TRoomOrderMapper tRoomOrderMapper;

    /**
     * 查询房间占用（点支付时再次校验可用性并改变状态，支付失败回滚）
     *
     * @param id 房间占用（点支付时再次校验可用性并改变状态，支付失败回滚）主键
     * @return 房间占用（点支付时再次校验可用性并改变状态，支付失败回滚）
     */
    @Override
    public TRoomOrder selectTRoomOrderById(Long id) {
        return tRoomOrderMapper.selectTRoomOrderById(id);
    }

    /**
     * 查询房间占用（点支付时再次校验可用性并改变状态，支付失败回滚）列表
     *
     * @param tRoomOrder 房间占用（点支付时再次校验可用性并改变状态，支付失败回滚）
     * @return 房间占用（点支付时再次校验可用性并改变状态，支付失败回滚）
     */
    @Override
    public List<TRoomOrder> selectTRoomOrderList(TRoomOrder tRoomOrder) {
        return tRoomOrderMapper.selectTRoomOrderList(tRoomOrder);
    }

    @Autowired
    ITRoomPriceService priceService;

    @Autowired
    ITWxUserAmountService wxUserAmountService;

    @Autowired
    ITRoomService roomService;

    /**
     * 新增房间占用（点支付时再次校验可用性并改变状态，支付失败回滚）
     *
     * @param tRoomOrder 房间占用（点支付时再次校验可用性并改变状态，支付失败回滚）
     * @return 结果
     */
    @Override
    public synchronized int insertTRoomOrder(TRoomOrder tRoomOrder) {
        List<TRoomOrder> roomOrders = tRoomOrderMapper.selectConflictRoomPeriod(tRoomOrder);
        if (roomOrders.size() > 0) {
            throw new ServiceException("预定时间段冲突，请刷新预定情况后重试");
        }


        BigDecimal totalPrice = calcPrice(tRoomOrder.getRoomId(), tRoomOrder.getStartTime(), tRoomOrder.getEndTime());

        BigDecimal couponAmt = new BigDecimal(0);
        if (tRoomOrder.getCouponId() != 0) {
            // 优惠券处理优惠金额
        }

        BigDecimal payAmt = totalPrice.subtract(couponAmt);

        if (tRoomOrder.getPayType() == 1) { // 直接支付

        } else if (tRoomOrder.getPayType() == 2) { // 储值卡余额支付
            TWxUserAmount wxUserAmount = new TWxUserAmount();
            long roomId = roomOrders.get(0).getRoomId();
            TRoom room = roomService.selectTRoomById(roomId);

            wxUserAmount.setUserId(Long.parseLong(room.getCreateBy()));
            wxUserAmount.setAmount(payAmt);
            wxUserAmount.setWxUserId(tRoomOrder.getUserId());
            wxUserAmountService.minusCardValue(wxUserAmount);
        } else if (tRoomOrder.getPayType() == 4) { // 优惠券支付
            // 1. 是否在有效期  是否适用当前店铺

            // 2. 是否在可用日期 周一 周二 周三...

            // 3. 预约时间是否符合优惠券时间

        }

        tRoomOrder.setTotalAmount(totalPrice);
        tRoomOrder.setCouponAmount(couponAmt);
        tRoomOrder.setPayAmount(payAmt);
        tRoomOrder.setStatus(1l);// 已预约
        tRoomOrder.setCreateTime(DateUtils.getNowDate());
        return tRoomOrderMapper.insertTRoomOrder(tRoomOrder);
    }

    @Override
    public BigDecimal getPeriodPrice(GetRoomPriceVo vo) {
        return calcPrice(vo.getRoomId(), vo.getStartTime(), vo.getEndTime());
    }

    private BigDecimal calcPrice(long roomId, Date st, Date et) {
        LocalTime startTime = LocalTime.of(st.getHours(), st.getMinutes());
        LocalTime endTime = LocalTime.of(et.getHours(), et.getMinutes());

        TRoomPrice priceQry = new TRoomPrice();
        priceQry.setRoomId(roomId);
        priceQry.setStatus(0l);
        final List<TRoomPrice> tRoomPrices = priceService.selectTRoomPriceList(priceQry);
        BigDecimal totalPrice = new BigDecimal(0);
        for (TRoomPrice roomPrice : tRoomPrices) {
            if (roomPrice.getStopTime().isBefore(startTime) || endTime.isBefore(roomPrice.getStartTime()))
                continue;

            if (roomPrice.getStartTime().isAfter(startTime))
                startTime = roomPrice.getStartTime();
            if (!roomPrice.getStopTime().isBefore(endTime)) {
                endTime = roomPrice.getStopTime();
            }
            int minuts = (endTime.getHour() - startTime.getHour()) * 60
                    + (endTime.getMinute() - startTime.getMinute());
            totalPrice = totalPrice.add(roomPrice.getPrice().multiply(new BigDecimal(minuts).divide(new BigDecimal(60))));
        }

        return totalPrice;
    }

    /**
     * 修改房间占用（点支付时再次校验可用性并改变状态，支付失败回滚）
     *
     * @param tRoomOrder 房间占用（点支付时再次校验可用性并改变状态，支付失败回滚）
     * @return 结果
     */
    @Override
    public int updateTRoomOrder(TRoomOrder tRoomOrder) {
        tRoomOrder.setUpdateTime(DateUtils.getNowDate());
        return tRoomOrderMapper.updateTRoomOrder(tRoomOrder);
    }

    /**
     * 批量删除房间占用（点支付时再次校验可用性并改变状态，支付失败回滚）
     *
     * @param ids 需要删除的房间占用（点支付时再次校验可用性并改变状态，支付失败回滚）主键
     * @return 结果
     */
    @Override
    public int deleteTRoomOrderByIds(Long[] ids) {
        return tRoomOrderMapper.deleteTRoomOrderByIds(ids);
    }

    /**
     * 删除房间占用（点支付时再次校验可用性并改变状态，支付失败回滚）信息
     *
     * @param id 房间占用（点支付时再次校验可用性并改变状态，支付失败回滚）主键
     * @return 结果
     */
    @Override
    public int deleteTRoomOrderById(Long id) {
        return tRoomOrderMapper.deleteTRoomOrderById(id);
    }
}
