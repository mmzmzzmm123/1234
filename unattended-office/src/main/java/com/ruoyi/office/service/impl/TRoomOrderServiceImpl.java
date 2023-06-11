package com.ruoyi.office.service.impl;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.binarywang.wxpay.bean.request.WxPayUnifiedOrderV3Request;
import com.github.binarywang.wxpay.bean.result.WxPayOrderQueryV3Result;
import com.github.binarywang.wxpay.bean.result.WxPayUnifiedOrderV3Result;
import com.github.binarywang.wxpay.bean.result.enums.TradeTypeEnum;
import com.github.binarywang.wxpay.config.WxPayConfig;
import com.github.binarywang.wxpay.exception.WxPayException;
import com.github.binarywang.wxpay.service.WxPayService;
import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.bean.BeanUtils;
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

        PrepayReq prepayReq = new PrepayReq();
        prepayReq.setRoomId(roomId);
        prepayReq.setStartTime(st);
        prepayReq.setEndTime(et);
        List<TRoomOrder> roomOrders = tRoomOrderMapper.selectConflictRoomPeriod(prepayReq);
        if (roomOrders.size() > 0) {
            throw new ServiceException("预定时间段冲突，请刷新预定情况后重试");
        }

        BigDecimal totalPrice = new BigDecimal(0);

        TRoomPrice priceQry = new TRoomPrice();
        priceQry.setRoomId(roomId);
        priceQry.setStatus(0l);
        final List<TRoomPrice> tRoomPrices = priceService.selectTRoomPriceList(priceQry);

        if (st.getDate() != et.getDate()) {
            totalPrice = totalPrice.add(getPeriodPrice(st.getHours(), 24, tRoomPrices)).add(getPeriodPrice(0, et.getHours(), tRoomPrices));
        } else {
            totalPrice = totalPrice.add((getPeriodPrice(st.getHours(), et.getHours(), tRoomPrices)));
        }

        return totalPrice;
    }

    private BigDecimal getPeriodPrice(int stHour, int endHour, List<TRoomPrice> tRoomPrices) {
        BigDecimal totalPrice = new BigDecimal(0);
        for (TRoomPrice roomPrice : tRoomPrices) {
            if (roomPrice.getStopTime() < stHour || endHour < roomPrice.getStartTime())
                continue;

            if (roomPrice.getStartTime() > stHour)
                stHour = roomPrice.getStartTime();
            if (roomPrice.getStopTime() <= endHour)
                endHour = roomPrice.getStopTime();

            int minuts = endHour - stHour;
            totalPrice = totalPrice.add(roomPrice.getPrice().multiply(new BigDecimal(minuts)));
        }
        return totalPrice;
    }

    @Autowired
    ITWxUserCouponService wxUserCouponService;

    @Autowired
    ITStoreCouponService storeCouponService;

    @Override
    public void finish(PrepayResp vo, Long wxuserid) {
        TRoomOrder order = tRoomOrderMapper.selectTRoomOrderById(vo.getOrderId());
        if (order.getStatus().equals(OfficeEnum.RoomOrderStatus.ORDERED.getCode()))
            return;
        //查询支付状态；
        WxPayOrderQueryV3Result v3Result = null;
        try {
            v3Result = wxPayService.queryOrderV3("", String.valueOf(order.getOrderNo()));
        } catch (WxPayException e) {
            log.error("查询微信后台订单失败： " + e.getMessage());
            throw new ServiceException("查询微信后台订单失败");
        }
        // 根据业务需要，更新商户平台订单状态
        String tradState = v3Result.getTradeState();
        if (tradState.equalsIgnoreCase("SUCCES")) {
            // 业务需求
            TRoomOrder updateOrder = new TRoomOrder();
            updateOrder.setId(order.getId());
            updateOrder.setStatus(OfficeEnum.RoomOrderStatus.ORDERED.getCode());
            tRoomOrderMapper.updateTRoomOrder(updateOrder);
        } else if (tradState.equalsIgnoreCase(OfficeEnum.WxPayState.REFUND.getCode())) {
            throw new ServiceException("订单转入退款");
        } else if (tradState.equalsIgnoreCase(OfficeEnum.WxPayState.NOTPAY.getCode())) {
            throw new ServiceException("订单未支付或已支付未收到微信通知");
        } else if (tradState.equalsIgnoreCase(OfficeEnum.WxPayState.CLOSED.getCode())) {
            // 关闭订单API
            //最新更新时间：2020.05.26 版本说明
            //
            //以下情况需要调用关单接口：
            //1、商户订单支付失败需要生成新单号重新发起支付，要对原订单号调用关单，避免重复支付；
            //2、系统下单后，用户支付超时，系统退出不再受理，避免用户继续，请调用关单接口。
            //
            //注意：
            //• 关单没有时间限制，建议在订单生成后间隔几分钟（最短5分钟）再调用关单接口，避免出现订单状态同步不及时导致关单失败。
            throw new ServiceException("支付订单已关闭");
        } else if (tradState.equalsIgnoreCase(OfficeEnum.WxPayState.REVOKED.getCode())) {
            throw new ServiceException("已撤销（仅付款码支付会返回）");
        } else if (tradState.equalsIgnoreCase(OfficeEnum.WxPayState.USERPAYING.getCode())) {
            throw new ServiceException("用户支付中（仅付款码支付会返回）");
        } else if (tradState.equalsIgnoreCase(OfficeEnum.WxPayState.PAYERROR.getCode())) {
            throw new ServiceException("支付失败（仅付款码支付会返回）");
        }
    }

    @Autowired
    ITWxUserService wxUserService;

    @Autowired
    private WxPayService wxPayService;

    @Override
    @Transactional
    public synchronized PrepayResp prepay(PrepayReq prepayReq, Long userId) {
        PrepayResp resp = new PrepayResp();

        TRoomOrder tRoomOrder = new TRoomOrder();
        BeanUtils.copyProperties(prepayReq, tRoomOrder);
         /*List<TRoomOrder> roomOrders = tRoomOrderMapper.selectConflictRoomPeriod(prepayReq);
        if (roomOrders.size() > 0) {
            throw new ServiceException("预定时间段冲突，请刷新预定情况后重试");
        }*/
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

        TWxUser wxUser = wxUserService.selectTWxUserById(userId);

        if (prepayReq.getPayType() == OfficeEnum.PayType.WX_PAY.getCode()) { // 直接支付 发起微信支付 预支付交易单，返回微信支付返回的标识.

            WxPayUnifiedOrderV3Request v3Request = new WxPayUnifiedOrderV3Request();
            final WxPayConfig config = wxPayService.getConfig();

            ArrayList<WxPayUnifiedOrderV3Request.GoodsDetail> goodsDetails = new ArrayList<>();
            goodsDetails.add(new WxPayUnifiedOrderV3Request.GoodsDetail() {
            }.setMerchantGoodsId("").setUnitPrice(0).setQuantity(0));

            v3Request.setAppid(config.getAppId()).setMchid(config.getMchId()).setNotifyUrl(config.getNotifyUrl())
                    .setDescription("roomId: " + prepayReq.getRoomId()).setOutTradeNo(String.valueOf(orderNo))
                    .setAmount(new WxPayUnifiedOrderV3Request.Amount() {
                    }.setTotal(totalPrice.intValue() * 100))
                    .setPayer(new WxPayUnifiedOrderV3Request.Payer() {
                    }.setOpenid(wxUser.getOpenId()));

            WxPayUnifiedOrderV3Result.JsapiResult jsapiResult = null;
            try {
                jsapiResult = this.wxPayService.createOrderV3(TradeTypeEnum.valueOf("JSAPI"), v3Request);

                tRoomOrder.setOrderNo(orderNo);
                tRoomOrder.setTotalAmount(totalPrice);
                tRoomOrder.setPayAmount(totalPrice);
                tRoomOrder.setStatus(OfficeEnum.RoomOrderStatus.TO_PAY.getCode());// 待支付
                tRoomOrder.setCreateTime(DateUtils.getNowDate());
                tRoomOrderMapper.insertTRoomOrder(tRoomOrder);
            } catch (WxPayException e) {
                e.printStackTrace();
                log.error("JSAPI 下单：" + e.getLocalizedMessage());
            }
            resp.setJsapiResult(jsapiResult);
            resp.setOrderId(tRoomOrder.getId());

        } else if (prepayReq.getPayType() == OfficeEnum.PayType.CARD_BALANCE_PAY.getCode()) {  // 储值卡余额支付
            TWxUserAmount wxUserAmount = new TWxUserAmount();
            // 判断用户在商户下的余额是否足够；
            long roomId = prepayReq.getRoomId();
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

        } else if (prepayReq.getPayType() == OfficeEnum.PayType.COUPON_PAY.getCode()) { // 优惠券支付
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
    public void wxnotify(String orderNo, String openId, int centTotal) {

        // 处理订单状态
        TRoomOrder roomOrder = new TRoomOrder();
        roomOrder.setOrderNo(Long.parseLong(orderNo));
        final List<TRoomOrder> roomOrders = tRoomOrderMapper.selectTRoomOrderList(roomOrder);
        if (roomOrders.size() > 0)
            roomOrder = roomOrders.get(0);

        TWxUser wxUser = wxUserService.selectTWxUserById(roomOrder.getUserId());
        // 验证金额和 openid
        if (!openId.equalsIgnoreCase(wxUser.getOpenId()) || roomOrder.getPayAmount().multiply(new BigDecimal(100)).compareTo(new BigDecimal(centTotal)) != 0) {
            throw new ServiceException("FAIL:金额或用户不匹配");
        }
        // 注意：微信会通知多次，因此需判断此订单
        if (roomOrder.getStatus().equals(OfficeEnum.RoomOrderStatus.ORDERED.getCode()))
            return;

        TRoomOrder update = new TRoomOrder();
        update.setId(roomOrder.getId());
        update.setStatus(OfficeEnum.RoomOrderStatus.ORDERED.getCode());// 已预约
        tRoomOrderMapper.updateTRoomOrder(roomOrder);

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

    @Override
    public RoomAvailablePeriod getAvailablePeriod(RoomAvailablePeriod vo) {
        // 校验房间可用性？ 不可用前端就不要进入；
        // 计算可用时段
        Date orderDate = vo.getDate();
        Date orderDateAfter = DateUtils.addDays(orderDate, 1);
        TRoomOrder qry = new TRoomOrder();
        qry.setRoomId(vo.getRoomId());
        qry.setStartTime(DateUtils.addDays(orderDate, -1)); // 订单有可能是昨天延续到今天的；
        List<TRoomOrder> roomOrderList = tRoomOrderMapper.selectTRoomOrderList(qry);

        // 计算已占用时间
        List<Integer> orderDateHourList = new ArrayList<>();
        List<Integer> orderDateAfterHourList = new ArrayList<>();

        for (TRoomOrder roomOrder : roomOrderList) {
            Date orderSt = roomOrder.getStartTime();
            Date orderEnd = roomOrder.getEndTime();
            if (roomOrder.getStatus() == OfficeEnum.RoomOrderStatus.IDEAL.getCode() || roomOrder.getStatus() == OfficeEnum.RoomOrderStatus.CANCEL.getCode() || orderSt.after(orderDateAfter) || orderEnd.before(orderDate)) // 筛选包含orderDate和orderDate+1 日期的有效订单
                continue;

            do {
                if (orderSt.before(orderDate))
                    continue;
                else {
                    if (orderSt.before(orderDateAfter))
                        orderDateHourList.add(orderSt.getHours());
                    else
                        orderDateAfterHourList.add(orderSt.getHours());
                }

                orderSt = DateUtils.addHours(orderSt, 1);
            } while (orderSt.before(orderEnd));

        }

        vo.setCanNotUseList(orderDateHourList);
        vo.setCanNotUseList2(orderDateAfterHourList);

        return vo;
    }

    @Override
    public TRoomOrder continueOrder(Long userId) {
        QueryWrapper<TRoomOrder> qw = new QueryWrapper<>();
        Date now = new Date();
        qw.lambda().lt(TRoomOrder::getStartTime, now).gt(TRoomOrder::getEndTime, now).eq(TRoomOrder::getStatus, OfficeEnum.RoomOrderStatus.USING.getCode());
        List<TRoomOrder> orderList = tRoomOrderMapper.selectList(qw);
        if (orderList == null || orderList.size() == 0)
            throw new ServiceException("没有找到进行中的订单，请直接预约");
        return orderList.get(0);
    }

}
