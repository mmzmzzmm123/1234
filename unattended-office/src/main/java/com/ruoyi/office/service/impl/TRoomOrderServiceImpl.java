package com.ruoyi.office.service.impl;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.Date;
import java.util.List;

import com.alibaba.fastjson2.JSON;
import com.ruoyi.common.config.WxPayConfig;
import com.ruoyi.common.core.domain.entity.WxUser;
import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.bean.BeanUtils;
import com.ruoyi.common.utils.http.HttpUtils;
import com.ruoyi.office.domain.*;
import com.ruoyi.office.domain.enums.OfficeEnum;
import com.ruoyi.office.domain.vo.*;
import com.ruoyi.office.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruoyi.office.mapper.TRoomOrderMapper;
import org.springframework.transaction.annotation.Transactional;

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

    @Autowired
    ITWxUserCouponService userCouponService;

    /**
     * 新增房间占用（点支付时再次校验可用性并改变状态，支付失败回滚）
     *
     * @param tRoomOrder 房间占用（点支付时再次校验可用性并改变状态，支付失败回滚）
     * @return 结果
     */
    @Override
    public int insertTRoomOrder(TRoomOrder tRoomOrder) {
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

    @Autowired
    WxPayConfig wxPayConfig;

    @Autowired
    ITWxUserCouponService wxUserCouponService;

    @Autowired
    ITStoreCouponService storeCouponService;

    @Override
    public String finish(PrepayResp vo, Long id) {

        //查询支付状态；


        // 更新订单状态
        return "";
    }

    @Autowired
    ITWxUserService wxUserService;

    @Override
    @Transactional
    public synchronized PrepayResp orderRoom(PrepayReq prepayReq, Long userId) {
        PrepayResp resp = new PrepayResp();

        TRoomOrder tRoomOrder = new TRoomOrder();
        BeanUtils.copyProperties(prepayReq, tRoomOrder);
        List<TRoomOrder> roomOrders = tRoomOrderMapper.selectConflictRoomPeriod(prepayReq);
        if (roomOrders.size() > 0) {
            throw new ServiceException("预定时间段冲突，请刷新预定情况后重试");
        }
        // 计算总金额
        BigDecimal totalPrice = calcPrice(prepayReq.getRoomId(), prepayReq.getStartTime(), prepayReq.getEndTime());
        // 计算订单号
        long orderNo = 0l;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        final String prefix = prepayReq.getRoomId() + sdf.format(new Date());
        Long maxId = tRoomOrderMapper.getHourMaxOrder(prefix);
        if (maxId == null)
            orderNo = Long.parseLong(prefix + "00");
        else {
            orderNo = maxId + 1;
        }

        if (tRoomOrder.getPayType() == OfficeEnum.PayType.WX_PAY.getCode()) { // 直接支付 发起微信支付 预支付交易单，返回微信支付返回的标识.
            WxPayReq payReq = new WxPayReq();
            payReq.setAppid(wxPayConfig.getAppid());
            payReq.setMchid(wxPayConfig.getMchid());
            payReq.setDescription("roomId: " + prepayReq.getRoomId());
            payReq.setOut_trade_no(String.valueOf(orderNo));
            payReq.setNotify_url(wxPayConfig.getNotify_url());

            WxPayAmount wxCallbackAmount = new WxPayAmount();
            wxCallbackAmount.setTotal(totalPrice.intValue() * 100);
            payReq.setAmount(wxCallbackAmount);

            WxPayPayer wxCallbackPayer = new WxPayPayer();
            TWxUser wxUser = wxUserService.selectTWxUserById(userId);
            wxCallbackPayer.setOpenid(wxUser.getOpenId());
            payReq.setPayer(wxCallbackPayer);
            String prepayId = HttpUtils.sendPost(wxPayConfig.getPostUrl(), JSON.toJSONString(payReq));

            tRoomOrder.setPrepayId(prepayId);
            tRoomOrder.setTotalAmount(totalPrice);
            tRoomOrder.setPayAmount(totalPrice);
            tRoomOrder.setStatus(OfficeEnum.RoomOrderStatus.TO_PAY.getCode());// 待支付
            tRoomOrder.setCreateTime(DateUtils.getNowDate());
            tRoomOrderMapper.insertTRoomOrder(tRoomOrder);

            resp.setPrepayId(prepayId);
            resp.setOrderId(tRoomOrder.getId());

        } else if (tRoomOrder.getPayType() == OfficeEnum.PayType.CARD_BALANCE_PAY.getCode()) {  // 储值卡余额支付
            TWxUserAmount wxUserAmount = new TWxUserAmount();
            // 判断用户在商户下的余额是否足够；
            long roomId = roomOrders.get(0).getRoomId();
            TRoom room = roomService.selectTRoomById(roomId);

            wxUserAmount.setUserId(Long.parseLong(room.getCreateBy()));
            wxUserAmount.setWxUserId(tRoomOrder.getUserId());
            final List<TWxUserAmount> wxUserAmounts = wxUserAmountService.selectTWxUserAmountList(wxUserAmount);
            if (wxUserAmounts.size() == 0 || wxUserAmounts.get(0).getAmount().compareTo(totalPrice) == -1)
                throw new ServiceException("储值卡余额不够，请充值后使用");

            wxUserAmount.setAmount(totalPrice);
            // 扣除余额
            wxUserAmountService.minusCardValue(wxUserAmount);

            tRoomOrder.setTotalAmount(totalPrice);
            tRoomOrder.setPayAmount(totalPrice);
            tRoomOrder.setStatus(OfficeEnum.RoomOrderStatus.ORDERED.getCode());// 已预约
            tRoomOrder.setCreateTime(DateUtils.getNowDate());
            tRoomOrderMapper.insertTRoomOrder(tRoomOrder);

        } else if (tRoomOrder.getPayType() == OfficeEnum.PayType.COUPON_PAY.getCode()) { // 优惠券支付
            validCoupon(prepayReq, userId);// 校验可用性

            BigDecimal payAmt = new BigDecimal(0);
            tRoomOrder.setTotalAmount(totalPrice);
            tRoomOrder.setCouponAmount(totalPrice);
            tRoomOrder.setPayAmount(payAmt);
            tRoomOrder.setStatus(OfficeEnum.RoomOrderStatus.ORDERED.getCode());// 已预约
            tRoomOrder.setCreateTime(DateUtils.getNowDate());
            tRoomOrderMapper.insertTRoomOrder(tRoomOrder);

        }
        return resp;
    }

    @Override
    public boolean checkCoupon(PrepayReq vo, Long userId) {
        try {
            validCoupon(vo, userId);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    private boolean validCoupon(PrepayReq vo, Long userId) {
        if (vo.getCouponId() == 0) {
            throw new ServiceException("请选择卡券");
        }
        TWxUserCoupon userCoupon = wxUserCouponService.selectTWxUserCouponById(vo.getCouponId());
        if (userCoupon == null)
            throw new ServiceException("卡券不存在");
        // 1. 是否在有效期
        Date today = DateUtils.parseDate(DateUtils.getDate());
        if (userCoupon.getStartDate().after(today) || userCoupon.getEndDate().before(today))
            throw new ServiceException("请在有效期范围内使用");
        // 是否适用当前店铺
        if (userCoupon.getUserId() != userId) {
            throw new ServiceException("优惠券使用异常");
        }
        TRoom room = roomService.selectTRoomById(vo.getRoomId());
        if (userCoupon.getStoreId() != 0) {
            if (userCoupon.getStoreId() != room.getStoreId())
                throw new ServiceException("非本门店优惠券");
        } else {
            TStoreCoupon storeCoupon = storeCouponService.selectTStoreCouponById(userCoupon.getCouponId());
            String couponSysuser = storeCoupon.getCreateBy();
            if (room.getCreateBy() != couponSysuser)
                throw new ServiceException("非本门店优惠券");
        }
        // 2. 是否在可用日期 周一 周二 周三...
        int weekDay = today.getDay();
        if (weekDay == 0) weekDay = 7;
        if (!userCoupon.getWeekDays().contains(weekDay + ""))
            throw new ServiceException("当前日期不可用");
        // 3. 预约时间是否符合优惠券时间
        String hourCouponType = OfficeEnum.CouponType.HOUR_CARD.getCode() + "";
        if (hourCouponType.equalsIgnoreCase(userCoupon.getCardType())) {
            int hours = Math.abs((int) (userCoupon.getEndDate().getTime() - userCoupon.getStartDate().getTime()) / (1000 * 60 * 60));
            int orderHours = Math.abs((int) (vo.getEndTime().getTime() - vo.getStartTime().getTime()) / (1000 * 60 * 60));
            if (hours < orderHours)
                throw new ServiceException("预约时间段超出优惠券可用小时数");
        }
        return true;
    }

    // 处理微信支付回调
    @Override
    public String orderRoomWxCallback(WxPayCallback callback, WxUser wxUser) {
        // https://blog.csdn.net/wnn654321/article/details/123298162?spm=1001.2101.3001.6650.1&utm_medium=distribute.pc_relevant.none-task-blog-2%7Edefault%7ECTRLIST%7ERate-1-123298162-blog-127161225.235%5Ev38%5Epc_relevant_default_base&depth_1-utm_source=distribute.pc_relevant.none-task-blog-2%7Edefault%7ECTRLIST%7ERate-1-123298162-blog-127161225.235%5Ev38%5Epc_relevant_default_base&utm_relevant_index=2
        // 解密返回数据
        if (!callback.getEvent_type().equalsIgnoreCase("TRANSACTION.SUCCESS"))
            return "SUCCESS";
        WxPayCallbackEncryptResource encryptResource = callback.getResource();

        String totalAmt = "";
        String openId = "";
        long orderNo = 0l;
        // 处理订单状态
        TRoomOrder roomOrder = new TRoomOrder();
        roomOrder.setOrderNo(orderNo);
        final List<TRoomOrder> roomOrders = tRoomOrderMapper.selectTRoomOrderList(roomOrder);
        if (roomOrders.size() > 0)
            roomOrder = roomOrders.get(0);
        // 验证金额和 openid
        if (!openId.equalsIgnoreCase(wxUser.getOpenId()) || roomOrder.getPayAmount().compareTo(new BigDecimal(totalAmt)) != 0) {
            return "FAIL:金额或用户不匹配";
        }

        roomOrder.setStatus(OfficeEnum.RoomOrderStatus.ORDERED.getCode());// 已预约
        tRoomOrderMapper.updateTRoomOrder(roomOrder);

        return "SUCCESS";
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
