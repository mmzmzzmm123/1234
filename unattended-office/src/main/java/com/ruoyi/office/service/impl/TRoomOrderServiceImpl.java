package com.ruoyi.office.service.impl;

import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.bean.WxMaSubscribeMessage;
import cn.hutool.core.date.DateTime;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.binarywang.wxpay.bean.notify.WxPayOrderNotifyV3Result;
import com.github.binarywang.wxpay.bean.request.WxPayUnifiedOrderV3Request;
import com.github.binarywang.wxpay.bean.result.WxPayOrderQueryV3Result;
import com.github.binarywang.wxpay.bean.result.WxPayUnifiedOrderV3Result;
import com.github.binarywang.wxpay.bean.result.enums.TradeTypeEnum;
import com.github.binarywang.wxpay.config.WxPayConfig;
import com.github.binarywang.wxpay.constant.WxPayConstants;
import com.github.binarywang.wxpay.exception.WxPayException;
import com.github.binarywang.wxpay.service.WxPayService;
import com.ruoyi.common.core.domain.entity.SysDictData;
import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.bean.BeanUtils;
import com.ruoyi.common.utils.uuid.IdUtils;
import com.ruoyi.office.domain.*;
import com.ruoyi.office.domain.enums.OfficeEnum;
import com.ruoyi.office.domain.vo.*;
import com.ruoyi.office.horn.HornConfig;
import com.ruoyi.office.mapper.TRoomOrderMapper;
import com.ruoyi.office.service.*;
import com.ruoyi.office.util.WxMsgSender;
import com.ruoyi.system.service.ISysDictDataService;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.template.WxMpTemplateData;
import me.chanjar.weixin.mp.bean.template.WxMpTemplateMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

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
      /*  for (TRoomPrice roomPrice : tRoomPrices) {
            if (roomPrice.getStopTime() < stHour || endHour < roomPrice.getStartTime())
                continue;

            int tempSt = 0, tempEnd = 0;
            if (roomPrice.getStartTime() >= stHour)
                tempSt = roomPrice.getStartTime();
            else
                tempSt = stHour;

            if (roomPrice.getStopTime() <= endHour)
                tempEnd = roomPrice.getStopTime() ;
            else tempEnd = endHour;

            int minuts = tempEnd - tempSt;
            totalPrice = totalPrice.add(roomPrice.getPrice().multiply(new BigDecimal(minuts)));
        }*/

        for (TRoomPrice roomPrice : tRoomPrices) {
            // endHour 不计数订单时间； roomPrice.getStopTime()计入价格时间
            while (stHour < endHour && stHour <= roomPrice.getStopTime()) {
                if (stHour >= roomPrice.getStartTime())
                    totalPrice = totalPrice.add(roomPrice.getPrice());
                stHour++;
            }
        }
        return totalPrice;
    }

    @Autowired
    ITWxUserCouponService wxUserCouponService;

    @Autowired
    ITStoreCouponService storeCouponService;

    @Transactional
    @Override
    public WxPayOrderQueryV3Result finish(PrepayResp vo, Long wxuserid) {
        TRoomOrder order = tRoomOrderMapper.selectTRoomOrderById(vo.getOrderId());
        if (order.getStatus().equals(OfficeEnum.RoomOrderStatus.ORDERED.getCode()))
            return null;
        //查询支付状态；
        WxPayOrderQueryV3Result v3Result = null;
        try {
            WxPayService wxPayService = payService.getConfigByRoom(order.getRoomId());
            v3Result = wxPayService.queryOrderV3("", String.valueOf(order.getOrderNo()));
            log.debug("查询订单返回:" + v3Result.toString());
        } catch (WxPayException e) {
            log.error("查询微信后台订单失败： " + e.getMessage());
            throw new ServiceException("查询微信后台订单失败");
        }
        // 根据业务需要，更新商户平台订单状态
        String tradState = v3Result.getTradeState();
        if (tradState.equalsIgnoreCase(WxPayConstants.WxpayTradeStatus.SUCCESS)) {
            // 业务需求
            TRoomOrder updateOrder = new TRoomOrder();
            updateOrder.setId(order.getId());
            updateOrder.setStatus(OfficeEnum.RoomOrderStatus.ORDERED.getCode());
            if (v3Result.getAmount() != null) {
                final WxPayOrderQueryV3Result.Amount v3ResultAmount = v3Result.getAmount();
                final Integer payerTotal = v3ResultAmount.getPayerTotal();
                if (payerTotal != 0) {
                    if (!v3ResultAmount.getTotal().equals(order.getTotalAmount().multiply(new BigDecimal(100)).intValue())) {
                        throw new ServiceException("订单金额不一致");
                    }
                    BigDecimal payAmt = new BigDecimal(v3ResultAmount.getPayerTotal()).divide(new BigDecimal(100), 3, RoundingMode.HALF_UP);
                    updateOrder.setPayAmount(payAmt);
                    updateOrder.setCouponAmount(order.getTotalAmount().subtract(payAmt));
                }

            }
            tRoomOrderMapper.updateTRoomOrder(updateOrder);


        } else if (tradState.equalsIgnoreCase(WxPayConstants.WxpayTradeStatus.REFUND)) {
            throw new ServiceException("订单转入退款");
        } else if (tradState.equalsIgnoreCase(WxPayConstants.WxpayTradeStatus.NOTPAY)) {
            throw new ServiceException("订单未支付或已支付未收到微信通知");
        } else if (tradState.equalsIgnoreCase(WxPayConstants.WxpayTradeStatus.CLOSED)) {
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
        } else if (tradState.equalsIgnoreCase(WxPayConstants.WxpayTradeStatus.REVOKED)) {
            throw new ServiceException("已撤销（仅付款码支付会返回）");
        } else if (tradState.equalsIgnoreCase(WxPayConstants.WxpayTradeStatus.USER_PAYING)) {
            throw new ServiceException("用户支付中（仅付款码支付会返回）");
        } else if (tradState.equalsIgnoreCase(WxPayConstants.WxpayTradeStatus.PAY_ERROR)) {
            throw new ServiceException("支付失败（仅付款码支付会返回）");
        }/*else{
            throw new ServiceException(v3Result.getTradeStateDesc());
        }*/

        try {
            sendVxMessage(wxuserid, order);
            TRoom tRoom = roomService.selectTRoomById(order.getRoomId());
            TStore tStore = storeService.selectTStoreById(tRoom.getStoreId());

            TStoreUser tStoreUser = new TStoreUser();
            tStoreUser.setStoreId(tStore.getId());
            List<TStoreUser> tStoreUserList = itStoreUserService.selectTStoreUserList(tStoreUser);
            for (TStoreUser item : tStoreUserList) {
                TWxUser tWxUser = itWxUserService.selectTWxUserById(item.getUserId());
                if (tWxUser != null && tWxUser.getMpOpenId() != null && !tWxUser.getMpOpenId().equals("")) {
                    sendVxOrderMpMessage(tWxUser.getMpOpenId(), tStore, tRoom, order, "");
                }
            }
//            sendVxOrderMpMessage("oNosp6pg1nwPpNK0ojVRG3nXMUqM", tStore, tRoom, order, "已预约");
//            sendVxOrderMpMessage("oNosp6nU4uj40-rGGCG83wkQwdzE", tStore, tRoom, order, "已预约");
//            sendVxOrderMpMessage("oNosp6o1yVW4UQ2Jh6zS9B-B2SM4", tStore, tRoom, order, "已预约");
        } catch (Exception e) {
//            throw new ServiceException("消息推送失败");
            log.error("消息推送失败");
        }


        return v3Result;
    }

    @Autowired
    ITWxUserService wxUserService;

  /*  @Autowired
    private WxPayService wxPayService;*/

    @Autowired
    ITWxUserPromotionService userPromotionService;

    @Override
    @Transactional
    public synchronized PrepayResp prepay(PrepayReq prepayReq, Long userId) {
        PrepayResp resp = new PrepayResp();

        TRoomOrder tRoomOrder = new TRoomOrder();
        BeanUtils.copyProperties(prepayReq, tRoomOrder);

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

        if (prepayReq.getPayType().equals(OfficeEnum.PayType.WX_PAY.getCode())) { // 直接支付 发起微信支付 预支付交易单，返回微信支付返回的标识.

            // 满减券
            BigDecimal payAMT = totalPrice;
            if (prepayReq.getCouponId() != null && prepayReq.getCouponId() != 0) {
                TWxUserPromotion qry = new TWxUserPromotion();
                qry.setCouponId(prepayReq.getCouponId());
                qry.setWxId(userId);
                final List<TWxUserPromotion> userPromotions = userPromotionService.selectTWxUserPromotionList(qry);
                if (userPromotions.size() == 0) {
                    throw new ServiceException("满减券不存在");
                }
                TWxUserPromotion userPromotion = userPromotions.get(0);
                if (validPromotion(prepayReq.getRoomId(), userPromotion, userId)) {
                    if (totalPrice.compareTo(userPromotion.getStandardPrice()) < 0) {
                        throw new ServiceException("未达到满减金额");
                    } else {
                        payAMT = totalPrice.subtract(userPromotion.getDiscountPrice());
                    }
                }
            }

            WxPayService wxPayService = payService.getConfigByRoom(prepayReq.getRoomId());
            WxPayUnifiedOrderV3Request v3Request = new WxPayUnifiedOrderV3Request();
            final WxPayConfig config = wxPayService.getConfig();

            WxPayUnifiedOrderV3Request.Amount v3Amount = new WxPayUnifiedOrderV3Request.Amount();
            v3Amount.setTotal(payAMT.multiply(new BigDecimal(100)).intValue());
            WxPayUnifiedOrderV3Request.Payer v3payer = new WxPayUnifiedOrderV3Request.Payer();
            v3payer.setOpenid(wxUser.getOpenId());

            v3Request.setAppid(config.getAppId()).setMchid(config.getMchId()).setNotifyUrl(config.getPayScoreNotifyUrl())
                    .setDescription("roomId: " + prepayReq.getRoomId()).setOutTradeNo(String.valueOf(orderNo))
                    .setAmount(v3Amount)
                    .setPayer(v3payer)
                    .setAttach(OfficeEnum.WxTradeType.ROOM_ORDER.getCode());

            WxPayUnifiedOrderV3Result.JsapiResult jsapiResult = null;
            try {
                jsapiResult = wxPayService.createOrderV3(TradeTypeEnum.valueOf("JSAPI"), v3Request);

                tRoomOrder.setOrderNo(orderNo);
                tRoomOrder.setTotalAmount(totalPrice);
                tRoomOrder.setPayAmount(totalPrice);
                tRoomOrder.setStatus(OfficeEnum.RoomOrderStatus.TO_PAY.getCode());// 待支付
                tRoomOrder.setCreateTime(DateUtils.getNowDate());
                tRoomOrder.setCreateBy(prepayReq.getUserId() + "");
                tRoomOrderMapper.insertTRoomOrder(tRoomOrder);
            } catch (WxPayException e) {
                e.printStackTrace();
                log.error("JSAPI 下单：" + e.getLocalizedMessage());
                throw new ServiceException(e.getLocalizedMessage());
            }
            resp.setJsapiResult(jsapiResult);
            resp.setOrderId(tRoomOrder.getId());
            log.debug("/order: return:" + resp.getOrderId() + jsapiResult.toString());
        } else if (prepayReq.getPayType().equals(OfficeEnum.PayType.CARD_BALANCE_PAY.getCode())) {  // 储值卡余额支付
            TWxUserAmount wxUserAmount = new TWxUserAmount();
            // 判断用户在商户下的余额是否足够；
            long roomId = prepayReq.getRoomId();
            TRoom room = roomService.selectTRoomById(roomId);

            wxUserAmount.setUserId(Long.parseLong(room.getCreateBy()));
            wxUserAmount.setWxUserId(tRoomOrder.getUserId());
            final List<TWxUserAmount> wxUserAmounts = wxUserAmountService.selectTWxUserAmountList(wxUserAmount);
            if (wxUserAmounts.size() == 0 || wxUserAmounts.get(0).getAmount().compareTo(totalPrice) < 0)
                throw new ServiceException("储值卡余额不够，请充值后使用");
            BigDecimal cashAmount = wxUserAmounts.get(0).getCashAmount();
            BigDecimal welfareAmount = wxUserAmounts.get(0).getWelfareAmount();
            wxUserAmount.setAmount(totalPrice);

            //本金足够
            if (totalPrice.compareTo(cashAmount) < 0) {
                wxUserAmount.setCashAmount(totalPrice);
                wxUserAmount.setWelfareAmount(new BigDecimal(0));
            } else {
                //优先扣除本金
                wxUserAmount.setCashAmount(cashAmount);
                wxUserAmount.setWelfareAmount(totalPrice.subtract(cashAmount));
            }
            // 扣除余额
            wxUserAmountService.minusCardValue(wxUserAmount);

            tRoomOrder.setPayType(OfficeEnum.PayType.CARD_BALANCE_PAY.getCode());
            tRoomOrder.setOrderNo(orderNo);
            tRoomOrder.setTotalAmount(totalPrice);
            //本金足够
            if (totalPrice.compareTo(cashAmount) < 0) {
                tRoomOrder.setPayAmount(totalPrice);
            } else {
                tRoomOrder.setPayAmount(cashAmount);
                tRoomOrder.setWelfareAmount(totalPrice.subtract(cashAmount));
            }
            tRoomOrder.setStatus(OfficeEnum.RoomOrderStatus.ORDERED.getCode());// 已预约
            tRoomOrder.setCreateTime(DateUtils.getNowDate());
            tRoomOrder.setCreateBy(prepayReq.getUserId() + "");
            tRoomOrder.setOrderType("小时收费");
            tRoomOrderMapper.insertTRoomOrder(tRoomOrder);

        } else if (prepayReq.getPayType().equals(OfficeEnum.PayType.COUPON_PAY.getCode())) { // 美团券券支付
            validCoupon(prepayReq, userId);// 校验可用性

            //  优惠券置为已使用
            TWxUserCoupon wxUserCoupon = new TWxUserCoupon();
            wxUserCoupon.setCouponId(prepayReq.getCouponId());
            wxUserCoupon.setStatus(1l);
            wxUserCouponService.updateTWxUserCoupon(wxUserCoupon);

            BigDecimal payAmt = new BigDecimal(0);
            tRoomOrder.setPayType(OfficeEnum.PayType.CARD_BALANCE_PAY.getCode());
            tRoomOrder.setOrderNo(orderNo);
            tRoomOrder.setCouponId(prepayReq.getCouponId());
            tRoomOrder.setTotalAmount(totalPrice);
            tRoomOrder.setCouponAmount(totalPrice);
            tRoomOrder.setPayAmount(payAmt);
            tRoomOrder.setStatus(OfficeEnum.RoomOrderStatus.ORDERED.getCode());// 已预约
            tRoomOrder.setCreateTime(DateUtils.getNowDate());
            tRoomOrder.setCreateBy(prepayReq.getUserId() + "");
            tRoomOrderMapper.insertTRoomOrder(tRoomOrder);

        }
        return resp;
    }

    @Autowired
    ITRoomPackageService roomPackageService;


    @Override
    @Transactional
    public synchronized PrepayResp packPrepay(PackPrepayReq prepayReq, long userId) {
        PrepayResp resp = new PrepayResp();


        // 计算总金额
        BigDecimal totalPrice = new BigDecimal(0);
        TRoomPackage roomPackage = roomPackageService.selectTRoomPackageById(prepayReq.getPackId());
        if (roomPackage == null) {
            throw new ServiceException("套餐不可用");
        }
        totalPrice = roomPackage.getPrice();

        TRoom room = roomService.selectTRoomById(roomPackage.getRoomId());
        if (OfficeEnum.RoomStatus.NO_USE.getCode().equalsIgnoreCase(room.getStatus())) {
            throw new ServiceException("所选房间不可用");
        }

        TRoomOrder tRoomOrder = new TRoomOrder();
        BeanUtils.copyProperties(prepayReq, tRoomOrder);

        Date endTime = DateUtils.addMinutes(prepayReq.getStartTime(), roomPackage.getMinutes().intValue());
        // 改成分钟后订单时间冲突校验；
        PrepayReq confQry = new PrepayReq();
        confQry.setRoomId(roomPackage.getRoomId());
        confQry.setStartTime(prepayReq.getStartTime());
        confQry.setEndTime(endTime);
        List<TRoomOrder> exOrders = tRoomOrderMapper.selectConflictRoomPeriod(confQry);
        if (exOrders.size() > 0) {
            throw new ServiceException("预约时间冲突");
        }

        tRoomOrder.setRoomId(roomPackage.getRoomId());
        tRoomOrder.setStartTime(prepayReq.getStartTime());
//        tRoomOrder.setEndTime(DateUtils.addHours(prepayReq.getStartTime(), (int) (roomPackage.getMinutes() / 60)));
        tRoomOrder.setEndTime(endTime);
        tRoomOrder.setRoomPackId(prepayReq.getPackId());
        tRoomOrder.setOrderType("房间小时套餐");


        // 计算订单号
        long orderNo = 0l;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        final String prefix = roomPackage.getRoomId() + sdf.format(new Date());
        Long maxId = tRoomOrderMapper.getHourMaxOrder(prefix);
        if (maxId == null)
            orderNo = Long.parseLong(prefix + "00");
        else {
            orderNo = maxId + 1;
        }

        TWxUser wxUser = wxUserService.selectTWxUserById(userId);

        if (prepayReq.getPayType().equals(OfficeEnum.PayType.WX_PAY.getCode())) { // 直接支付 发起微信支付 预支付交易单，返回微信支付返回的标识.

            // 满减券
            BigDecimal payAMT = totalPrice;
            if (prepayReq.getCouponId() != null && prepayReq.getCouponId() != 0) {
                TWxUserPromotion qry = new TWxUserPromotion();
                qry.setCouponId(prepayReq.getCouponId());
                qry.setWxId(userId);
                final List<TWxUserPromotion> userPromotions = userPromotionService.selectTWxUserPromotionList(qry);
                if (userPromotions.size() == 0) {
                    throw new ServiceException("满减券不存在");
                }
                TWxUserPromotion userPromotion = userPromotions.get(0);
                if (validPromotion(roomPackage.getRoomId(), userPromotion, userId)) {
                    if (totalPrice.compareTo(userPromotion.getStandardPrice()) < 0) {
                        throw new ServiceException("未达到满减金额");
                    } else {
                        payAMT = totalPrice.subtract(userPromotion.getDiscountPrice());
                    }
                }
            }
            WxPayService wxPayService = payService.getConfigByRoom(roomPackage.getRoomId());
            WxPayUnifiedOrderV3Request v3Request = new WxPayUnifiedOrderV3Request();
            final WxPayConfig config = wxPayService.getConfig();

            WxPayUnifiedOrderV3Request.Amount v3Amount = new WxPayUnifiedOrderV3Request.Amount();
            v3Amount.setTotal(payAMT.multiply(new BigDecimal(100)).intValue());
            WxPayUnifiedOrderV3Request.Payer v3payer = new WxPayUnifiedOrderV3Request.Payer();
            v3payer.setOpenid(wxUser.getOpenId());

            WxPayUnifiedOrderV3Request.Discount detail = new WxPayUnifiedOrderV3Request.Discount();
            WxPayUnifiedOrderV3Request.GoodsDetail goodsDetail = new WxPayUnifiedOrderV3Request.GoodsDetail();
            goodsDetail.setMerchantGoodsId(roomPackage.getId().toString());
            goodsDetail.setQuantity(1);
            goodsDetail.setUnitPrice(v3Amount.getTotal());
            List<WxPayUnifiedOrderV3Request.GoodsDetail> goodsDetailList = new ArrayList<>();
            goodsDetailList.add(goodsDetail);
            detail.setGoodsDetails(goodsDetailList);

            v3Request.setAppid(config.getAppId()).setMchid(config.getMchId()).setNotifyUrl(config.getPayScoreNotifyUrl())
                    .setDescription("roomId: " + roomPackage.getRoomId()).setOutTradeNo(String.valueOf(orderNo))
                    .setAmount(v3Amount)
                    .setPayer(v3payer)
                    .setAttach(OfficeEnum.WxTradeType.ROOM_ORDER.getCode())
                    .setDetail(detail)
                    .setGoodsTag(roomPackage.getRemark());

            WxPayUnifiedOrderV3Result.JsapiResult jsapiResult = null;
            try {
                jsapiResult = wxPayService.createOrderV3(TradeTypeEnum.valueOf("JSAPI"), v3Request);

                tRoomOrder.setOrderNo(orderNo);
                tRoomOrder.setTotalAmount(totalPrice);
                tRoomOrder.setPayAmount(totalPrice);
                tRoomOrder.setStatus(OfficeEnum.RoomOrderStatus.TO_PAY.getCode());// 待支付
                tRoomOrder.setCreateTime(DateUtils.getNowDate());
                tRoomOrder.setCreateBy(prepayReq.getUserId() + "");
                tRoomOrderMapper.insertTRoomOrder(tRoomOrder);
            } catch (WxPayException e) {
                e.printStackTrace();
                log.error("JSAPI 下单：" + e.getLocalizedMessage());
                throw new ServiceException(e.getLocalizedMessage());
            }
            resp.setJsapiResult(jsapiResult);
            resp.setOrderId(tRoomOrder.getId());
            log.debug("/order: return:" + resp.getOrderId() + jsapiResult.toString());
        } else if (prepayReq.getPayType().equals(OfficeEnum.PayType.CARD_BALANCE_PAY.getCode())) {  // 储值卡余额支付
            TWxUserAmount wxUserAmount = new TWxUserAmount();
            // 判断用户在商户下的余额是否足够；
            wxUserAmount.setUserId(Long.parseLong(room.getCreateBy()));
            wxUserAmount.setWxUserId(tRoomOrder.getUserId());
            final List<TWxUserAmount> wxUserAmounts = wxUserAmountService.selectTWxUserAmountList(wxUserAmount);
            if (wxUserAmounts.size() == 0 || wxUserAmounts.get(0).getAmount().compareTo(totalPrice) < 0)
                throw new ServiceException("储值卡余额不够，请充值后使用");

            BigDecimal cashAmount = wxUserAmounts.get(0).getCashAmount();
            BigDecimal welfareAmount = wxUserAmounts.get(0).getWelfareAmount();
            wxUserAmount.setAmount(totalPrice);

            //本金足够
            if (totalPrice.compareTo(cashAmount) < 0) {
                wxUserAmount.setCashAmount(totalPrice);
                wxUserAmount.setWelfareAmount(new BigDecimal(0));
            } else {
                //优先扣除本金
                wxUserAmount.setCashAmount(cashAmount);
                wxUserAmount.setWelfareAmount(totalPrice.subtract(cashAmount));
            }
            // 扣除余额
            wxUserAmountService.minusCardValue(wxUserAmount);

            tRoomOrder.setPayType(OfficeEnum.PayType.CARD_BALANCE_PAY.getCode());
            tRoomOrder.setOrderNo(orderNo);
            tRoomOrder.setTotalAmount(totalPrice);
            tRoomOrder.setPayAmount(totalPrice);
            tRoomOrder.setStatus(OfficeEnum.RoomOrderStatus.ORDERED.getCode());// 已预约
            tRoomOrder.setCreateTime(DateUtils.getNowDate());
            tRoomOrder.setCreateBy(prepayReq.getUserId() + "");
            tRoomOrderMapper.insertTRoomOrder(tRoomOrder);

        } else if (prepayReq.getPayType().equals(OfficeEnum.PayType.COUPON_PAY.getCode())) { // 美团券券支付
//            validCoupon(prepayReq, userId);// 校验可用性

            //  优惠券置为已使用
            TWxUserCoupon wxUserCoupon = new TWxUserCoupon();
            wxUserCoupon.setCouponId(prepayReq.getCouponId());
            wxUserCoupon.setStatus(1l);
            wxUserCouponService.updateTWxUserCoupon(wxUserCoupon);

            BigDecimal payAmt = new BigDecimal(0);
            tRoomOrder.setPayType(OfficeEnum.PayType.CARD_BALANCE_PAY.getCode());
            tRoomOrder.setOrderNo(orderNo);
            tRoomOrder.setCouponId(prepayReq.getCouponId());
            tRoomOrder.setTotalAmount(totalPrice);
            tRoomOrder.setCouponAmount(totalPrice);
            tRoomOrder.setPayAmount(payAmt);
            tRoomOrder.setStatus(OfficeEnum.RoomOrderStatus.ORDERED.getCode());// 已预约
            tRoomOrder.setCreateTime(DateUtils.getNowDate());
            tRoomOrder.setCreateBy(prepayReq.getUserId() + "");
            tRoomOrderMapper.insertTRoomOrder(tRoomOrder);

        }
        return resp;
    }

    @Override
    @Transactional
    public PrepayResp reOrder(ReorderPayReq reorederReq, long userId) {
        PrepayResp resp = new PrepayResp();

        TRoomOrder roomOrder = tRoomOrderMapper.selectTRoomOrderById(reorederReq.getOrderId());
        if (roomOrder == null) {
            throw new ServiceException("订单不存在");
        }
        if (!roomOrder.getCreateBy().equals(userId + "")) {
            throw new ServiceException("非本人订单");
        }

        // 计算总金额
        BigDecimal totalPrice = roomOrder.getTotalAmount();

        // 计算订单号
//        long orderNo = roomOrder.getOrderNo();

        // 计算订单号
        long orderNo = 0l;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        final String prefix = roomOrder.getRoomId() + sdf.format(new Date());
        Long maxId = tRoomOrderMapper.getHourMaxOrder(prefix);
        if (maxId == null) {
            orderNo = Long.parseLong(prefix + "00");
        } else {
            orderNo = maxId + 1;
        }


        TWxUser wxUser = wxUserService.selectTWxUserById(userId);

        if (reorederReq.getPayType().equals(OfficeEnum.PayType.WX_PAY.getCode())) { // 直接支付 发起微信支付 预支付交易单，返回微信支付返回的标识.

            // 满减券
            BigDecimal payAMT = totalPrice;
            if (reorederReq.getCouponId() != null && reorederReq.getCouponId() != 0) {
                TWxUserPromotion qry = new TWxUserPromotion();
                qry.setCouponId(reorederReq.getCouponId());
                qry.setWxId(userId);
                final List<TWxUserPromotion> userPromotions = userPromotionService.selectTWxUserPromotionList(qry);
                if (userPromotions.size() == 0) {
                    throw new ServiceException("满减券不存在");
                }
                TWxUserPromotion userPromotion = userPromotions.get(0);
                if (validPromotion(roomOrder.getRoomId(), userPromotion, userId)) {
                    if (totalPrice.compareTo(userPromotion.getStandardPrice()) < 0) {
                        throw new ServiceException("未达到满减金额");
                    } else {
                        payAMT = totalPrice.subtract(userPromotion.getDiscountPrice());
                    }
                }
            }

            WxPayService wxPayService = payService.getConfigByRoom(roomOrder.getRoomId());
            WxPayUnifiedOrderV3Request v3Request = new WxPayUnifiedOrderV3Request();
            final WxPayConfig config = wxPayService.getConfig();

            WxPayUnifiedOrderV3Request.Amount v3Amount = new WxPayUnifiedOrderV3Request.Amount();
            v3Amount.setTotal(payAMT.intValue() * 100);
            WxPayUnifiedOrderV3Request.Payer v3payer = new WxPayUnifiedOrderV3Request.Payer();
            v3payer.setOpenid(wxUser.getOpenId());

            v3Request.setAppid(config.getAppId()).setMchid(config.getMchId()).setNotifyUrl(config.getPayScoreNotifyUrl())
                    .setDescription("roomId: " + roomOrder.getRoomId()).setOutTradeNo(String.valueOf(orderNo))
                    .setAmount(v3Amount)
                    .setPayer(v3payer)
                    .setAttach(OfficeEnum.WxTradeType.ROOM_ORDER.getCode());

            WxPayUnifiedOrderV3Result.JsapiResult jsapiResult = null;
            try {
                jsapiResult = wxPayService.createOrderV3(TradeTypeEnum.valueOf("JSAPI"), v3Request);

                // 微信支付是不是要换个订单号?
                roomOrder.setOrderNo(orderNo);
                roomOrder.setPayType(OfficeEnum.PayType.WX_PAY.getCode());
                roomOrder.setStatus(OfficeEnum.RoomOrderStatus.TO_PAY.getCode());// 待支付
                roomOrder.setUpdateTime(DateUtils.getNowDate());
                roomOrder.setUpdateBy(userId + "");
                tRoomOrderMapper.updateTRoomOrder(roomOrder);
            } catch (WxPayException e) {
                e.printStackTrace();
                log.error("JSAPI 下单：" + e.getLocalizedMessage());
                if (e.getLocalizedMessage().contains("该订单已支付")) {
                    // 微信支付是不是要换个订单号?
                    roomOrder.setPayType(OfficeEnum.PayType.WX_PAY.getCode());
                    roomOrder.setStatus(OfficeEnum.RoomOrderStatus.ORDERED.getCode());// 已支付
                    roomOrder.setUpdateTime(DateUtils.getNowDate());
                    roomOrder.setUpdateBy(userId + "");
                    tRoomOrderMapper.updateTRoomOrder(roomOrder);
                }
                throw new ServiceException(e.getLocalizedMessage());
            }
            resp.setJsapiResult(jsapiResult);
            resp.setOrderId(roomOrder.getId());

        } else if (reorederReq.getPayType().equals(OfficeEnum.PayType.CARD_BALANCE_PAY.getCode())) {  // 储值卡余额支付
            TWxUserAmount wxUserAmount = new TWxUserAmount();
            // 判断用户在商户下的余额是否足够；
            TRoom room = roomService.selectTRoomById(roomOrder.getRoomId());

            wxUserAmount.setUserId(Long.parseLong(room.getCreateBy()));
            wxUserAmount.setWxUserId(roomOrder.getUserId());
            final List<TWxUserAmount> wxUserAmounts = wxUserAmountService.selectTWxUserAmountList(wxUserAmount);
            if (wxUserAmounts.size() == 0 || wxUserAmounts.get(0).getAmount().compareTo(totalPrice) < 0)
                throw new ServiceException("储值卡余额不够，请充值后使用");

            BigDecimal cashAmount = wxUserAmounts.get(0).getCashAmount();
            BigDecimal welfareAmount = wxUserAmounts.get(0).getWelfareAmount();
            wxUserAmount.setAmount(totalPrice);
            //本金足够
            if (totalPrice.compareTo(cashAmount) < 0) {
                wxUserAmount.setCashAmount(totalPrice);
                wxUserAmount.setWelfareAmount(new BigDecimal(0));
            } else {
                //优先扣除本金
                wxUserAmount.setCashAmount(cashAmount);
                wxUserAmount.setWelfareAmount(totalPrice.subtract(cashAmount));
            }
            // 扣除余额
            wxUserAmountService.minusCardValue(wxUserAmount);

            roomOrder.setPayType(OfficeEnum.PayType.CARD_BALANCE_PAY.getCode());
            roomOrder.setOrderNo(orderNo);
            roomOrder.setTotalAmount(totalPrice);
            roomOrder.setPayAmount(totalPrice);
            roomOrder.setStatus(OfficeEnum.RoomOrderStatus.ORDERED.getCode());// 已预约
            roomOrder.setUpdateTime(DateUtils.getNowDate());
            roomOrder.setUpdateBy(userId + "");
            tRoomOrderMapper.updateTRoomOrder(roomOrder);

        } else if (reorederReq.getPayType().equals(OfficeEnum.PayType.COUPON_PAY.getCode())) { // 美团券券支付

            PrepayReq checkVo = new PrepayReq();
            checkVo.setRoomId(roomOrder.getRoomId());
            checkVo.setCouponId(reorederReq.getCouponId());
            checkVo.setStartTime(roomOrder.getStartTime());
            checkVo.setEndTime(roomOrder.getEndTime());
            validCoupon(checkVo, userId);// 校验可用性

            //  优惠券置为已使用
            TWxUserCoupon wxUserCoupon = new TWxUserCoupon();
            wxUserCoupon.setCouponId(reorederReq.getCouponId());
            wxUserCoupon.setStatus(1l);
            wxUserCouponService.updateTWxUserCoupon(wxUserCoupon);

            BigDecimal payAmt = new BigDecimal(0);
            roomOrder.setPayType(OfficeEnum.PayType.CARD_BALANCE_PAY.getCode());
            roomOrder.setOrderNo(orderNo);
            roomOrder.setCouponId(reorederReq.getCouponId());
            roomOrder.setTotalAmount(totalPrice);
            roomOrder.setCouponAmount(totalPrice);
            roomOrder.setPayAmount(payAmt);
            roomOrder.setStatus(OfficeEnum.RoomOrderStatus.ORDERED.getCode());// 已预约
            roomOrder.setUpdateTime(DateUtils.getNowDate());
            roomOrder.setUpdateBy(userId + "");
            tRoomOrderMapper.updateTRoomOrder(roomOrder);

        }
        return resp;
    }

    @Autowired
    ITStoreService storeService;

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

    private boolean validPromotion(Long roomId, TWxUserPromotion userPromotion, Long userId) {
        if (userPromotion.getCouponType() != 1) {
            throw new ServiceException("优惠券类型不适用");
        }
        // 1. 适用优惠券的时间（当前日期）是否在有效期
        if (DateUtils.getNowDate().before(userPromotion.getStartDate()) || DateUtils.getNowDate().after(userPromotion.getEndDate())) {
            throw new ServiceException("当前时间段不可用");
        }

        if (userPromotion.getWxId() != userId) {
            throw new ServiceException("非本人优惠券");
        }

        TRoom room = roomService.selectTRoomById(roomId);
        if (userPromotion.getStoreId() != 0) {
            if (room.getStoreId() != userPromotion.getStoreId()) {
                throw new ServiceException("当前门店不可用");
            }
        } else {
            if (userPromotion.getMerchantId() != 0) {
                TStore store = storeService.selectTStoreById(room.getStoreId());
                if (!store.getCreateBy().equalsIgnoreCase(String.valueOf(userPromotion.getMerchantId()))) {
                    throw new ServiceException("当前商户不可适用");
                }
            }
        }

        // 2. 是否在可用日期 周一 周二 周三...
        Date today = DateUtils.parseDate(DateUtils.getDate());
        int weekDay = today.getDay();
        if (weekDay == 0) weekDay = 7;
        if (!userPromotion.getWeekDays().contains(weekDay + ""))
            throw new ServiceException("当前日期不可用");

        return true;
    }

    // 处理非续费套餐微信支付回调
    @Override
    public void wxnotify(String orderNo, String openId, WxPayOrderNotifyV3Result.Amount amt, String wxCallback) {

        // 处理订单状态
        TRoomOrder roomOrder = new TRoomOrder();
        roomOrder.setOrderNo(Long.parseLong(orderNo));
        final List<TRoomOrder> roomOrders = tRoomOrderMapper.selectTRoomOrderList(roomOrder);
        if (roomOrders.size() > 0) {
            roomOrder = roomOrders.get(0);
        }
        // 注意：微信会通知多次，因此需判断此订单
        if (roomOrder.getStatus().equals(OfficeEnum.RoomOrderStatus.ORDERED.getCode())) {
            return;
        }

        TWxUser wxUser = wxUserService.selectTWxUserById(roomOrder.getUserId());
        // 验证金额和 openid
        if (!openId.equalsIgnoreCase(wxUser.getOpenId()) || roomOrder.getPayAmount().multiply(new BigDecimal(100)).compareTo(new BigDecimal(amt.getTotal())) != 0) {
            throw new ServiceException("FAIL:金额或用户不匹配");
        }

        TRoomOrder update = new TRoomOrder();
        update.setId(roomOrder.getId());
        update.setStatus(OfficeEnum.RoomOrderStatus.ORDERED.getCode());// 已预约
        update.setRemark(wxCallback);

        if (amt.getPayerTotal() != null && amt.getPayerTotal() != 0) {
            BigDecimal payAmt = new BigDecimal(amt.getPayerTotal()).divide(new BigDecimal(100), 3, RoundingMode.HALF_UP);
            update.setPayAmount(payAmt);
            update.setCouponAmount(roomOrder.getTotalAmount().subtract(payAmt));
        }

        tRoomOrderMapper.updateTRoomOrder(update);
    }

    private void sendVxMessage(Long wxuserid, TRoomOrder tRoomOrder) {
        try {
            //todo定时任务检测商家订单到期
            TWxUser wxUser = wxUserService.selectTWxUserById(wxuserid);

            TRoom tRoom = roomService.selectTRoomById(tRoomOrder.getRoomId());
            TStore tStore = storeService.selectTStoreById(tRoom.getStoreId());
            WxMaSubscribeMessage wxMaSubscribeMessage = new WxMaSubscribeMessage();
            wxMaSubscribeMessage.setToUser(wxUser.getOpenId());
            wxMaSubscribeMessage.setTemplateId("58BvI5jDnq61I9slOIIjf89J9ionC95R14eUJ9rQLWA");

            List<WxMaSubscribeMessage.MsgData> msgDataList = new ArrayList<>();
            WxMaSubscribeMessage.MsgData msgData = new WxMaSubscribeMessage.MsgData();
            msgData.setName("thing1");
            msgData.setValue("订单号：" + tRoomOrder.getOrderNo());
            msgDataList.add(msgData);
            msgData = new WxMaSubscribeMessage.MsgData();
            msgData.setName("time2");
            msgData.setValue(DateUtils.parseDateToStr("yyyy-MM-dd HH:mm:ss", tRoomOrder.getStartTime()));
            msgDataList.add(msgData);
            msgData = new WxMaSubscribeMessage.MsgData();
            msgData.setName("thing6");
            msgData.setValue(tStore.getName() + "-" + tRoom.getName());
            msgDataList.add(msgData);
            wxMaSubscribeMessage.setData(msgDataList);
            customerWxMaService.getMsgService().sendSubscribeMsg(wxMaSubscribeMessage);

            System.out.println("Message sent successfully!");
        } catch (Exception e) {
            System.out.println("Failed to send message: " + e.getMessage());
        }
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

   /* @Override
    public RoomAvailablePeriod getAvailablePeriod(RoomAvailablePeriod vo) {
        // 计算可用时段
        Date orderDate = vo.getDate();
        Date orderDateAfter = DateUtils.addDays(orderDate, 1);
        TRoomOrder qry = new TRoomOrder();
        qry.setRoomId(vo.getRoomId());
        qry.setStartTime(DateUtils.addDays(orderDate, -1)); // 订单有可能是昨天延续到今天的；
        List<TRoomOrder> roomOrderList = tRoomOrderMapper.selectTRoomOrderList(qry);

        List<TRoomOrder> resList = new ArrayList<>();


        vo.setOrderList(roomOrderList);
        return vo;
    }*/

    @Override
    public RoomAvailablePeriod getAvailablePeriod(RoomAvailablePeriod vo) {
        // 校验房间可用性？ 不可用前端就不要进入；
        // 计算可用时段
        Date orderDate = vo.getDate();
        Date orderDateAfter = DateUtils.addDays(orderDate, 1);
        Date orderDateAfter2 = DateUtils.addDays(orderDate, 2);
        TRoomOrder qry = new TRoomOrder();
        qry.setRoomId(vo.getRoomId());
        qry.setStartTime(DateUtils.addDays(orderDate, -1)); // 订单有可能是昨天延续到今天的；
        List<TRoomOrder> roomOrderList = tRoomOrderMapper.selectTRoomOrderList(qry);
        List<TRoomOrder> resList = new ArrayList<>();
        // 计算已占用时间
        List<Integer> orderDateHourList = new ArrayList<>();
        List<Integer> orderDateAfterHourList = new ArrayList<>();

        for (TRoomOrder roomOrder : roomOrderList) {
            if (roomOrder.getStatus() == OfficeEnum.RoomOrderStatus.CANCEL.getCode()) {
                continue;
            }
            if (!roomOrder.getStartTime().before(orderDate) && roomOrder.getStartTime().before(orderDateAfter2)) {
                TRoomOrder temp = new TRoomOrder();
                temp.setStartTime(roomOrder.getStartTime());
                temp.setEndTime(roomOrder.getEndTime());
                resList.add(temp);
            }
            Date orderSt = roomOrder.getStartTime();
            Date orderEnd = roomOrder.getEndTime();
            if (roomOrder.getStatus() == OfficeEnum.RoomOrderStatus.IDEAL.getCode() || roomOrder.getStatus() == OfficeEnum.RoomOrderStatus.CANCEL.getCode() || orderSt.after(orderDateAfter) || orderEnd.before(orderDate)) // 筛选包含orderDate和orderDate+1 日期的有效订单
                continue;

            do {
                if (orderSt.before(orderDate)) {
//                    continue;
                } else {
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
        vo.setOrderList(resList);
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

    @Override
    public List<RoomOrderWxVo> getWxRoomOrder(RoomOrderWxReqVo tRoomOrder) {
        final List<RoomOrderWxVo> roomOrders = tRoomOrderMapper.getWxRoomOrder(tRoomOrder);
        for (RoomOrderWxVo vo : roomOrders) {
            vo.setOrderStatus(OfficeEnum.RoomOrderStatus.GetValueByCode(vo.getStatus()).getInfo());
        }
        return roomOrders;
    }

    @Autowired
    ITEquipmentService equipmentService;

    @Resource(name = "customerWxMaService")
    WxMaService customerWxMaService;


    /**
     * 订单开始提醒
     */
    @Override
    public void msgOrder() {
        TRoomOrder roomOrder = new TRoomOrder();
        roomOrder.setStatus(OfficeEnum.RoomOrderStatus.ORDERED.getCode());
        List<TRoomOrder> roomOrderList = tRoomOrderMapper.selectTRoomOrderList(roomOrder);

        for (TRoomOrder tRoomOrder : roomOrderList) {
            try {
                TRoom tRoom = roomService.selectTRoomById(tRoomOrder.getRoomId());
                if (tRoom == null) {
                    continue;
                }
                TStore tStore = storeService.selectTStoreById(tRoom.getStoreId());
                if (tStore == null) {
                    continue;
                }
                if (tRoomOrder.getStartTime().before(new Date())) {
                    continue;
                }
                if ((tRoomOrder.getStartTime().getTime() - DateTime.now().getTime()) / 1000 > 15 * 60) {
                    continue;
                }

//                sendVxBeginMpMessage("oNosp6pg1nwPpNK0ojVRG3nXMUqM", tStore, tRoom, roomOrder, "订单已开始");
//                sendVxBeginMpMessage("oNosp6nU4uj40-rGGCG83wkQwdzE", tStore, tRoom, roomOrder, "订单已开始");
//                sendVxBeginMpMessage("oNosp6o1yVW4UQ2Jh6zS9B-B2SM4", tStore, tRoom, roomOrder, "订单已开始");


                TWxUser tWxUser = wxUserService.selectTWxUserById(tRoomOrder.getUserId());
                WxMaSubscribeMessage wxMaSubscribeMessage = new WxMaSubscribeMessage();
                wxMaSubscribeMessage.setToUser(tWxUser.getOpenId());
                //todo模版配置
                wxMaSubscribeMessage.setTemplateId("58BvI5jDnq61I9slOIIjf89J9ionC95R14eUJ9rQLWA");
                List<WxMaSubscribeMessage.MsgData> msgDataList = new ArrayList<>();
                WxMaSubscribeMessage.MsgData msgData = new WxMaSubscribeMessage.MsgData();
                msgData.setName("thing1");
                msgData.setValue("您的订单即将开始！");
                msgDataList.add(msgData);
                msgData = new WxMaSubscribeMessage.MsgData();
                msgData.setName("time2");
                msgData.setValue(DateUtils.parseDateToStr("yyyy-MM-dd HH:mm:ss", tRoomOrder.getStartTime()));
                msgDataList.add(msgData);
                msgData = new WxMaSubscribeMessage.MsgData();
                msgData.setName("thing6");
                msgData.setValue(tStore.getName() + ":" + tRoom.getName());
                msgDataList.add(msgData);
                wxMaSubscribeMessage.setData(msgDataList);
                customerWxMaService.getMsgService().sendSubscribeMsg(wxMaSubscribeMessage);
            } catch (Exception e) {
                continue;
            }
        }
    }

    @Autowired
    ITRoomCleanRecordService cleanRecordService;

    @Autowired
    ITStoreUserService storeUserService;

    @Autowired
    ITStoreUserService itStoreUserService;

    @Autowired
    ITWxUserService itWxUserService;

    /**
     * 订单结束提醒
     */
    @Transactional
    @Override
    public void scanOrder() {

        Map<Long, TEquipment> equipments = equipmentService.selectTEquipmentList(new TEquipment()).stream().collect(Collectors.toMap(TEquipment::getId, Function.identity()));

        String[] minuteStrs = HornConfig.minutes.split(",");

        List<Integer> alertList = new ArrayList<>();
        for (String m : minuteStrs) {
            alertList.add(Integer.parseInt(m));
        }
        Collections.sort(alertList);
//        Collections.reverse(alertList);

        TRoomOrder roomOrder = new TRoomOrder();
        roomOrder.setStatus(OfficeEnum.RoomOrderStatus.USING.getCode());
//        roomOrder.setStatus(12);
//        roomOrder.setOrderNo(292023101501l);
        List<TRoomOrder> roomOrderList = tRoomOrderMapper.selectTRoomOrderList(roomOrder);
        for (TRoomOrder order : roomOrderList) {
            if (order.getEndTime().before(new Date())) {
                // 超期发送关闭
                roomService.closeRoom(order.getRoomId());

                // 更新订单为关闭;
                TRoomOrder up = new TRoomOrder();
                up.setId(order.getId());
                up.setStatus(OfficeEnum.RoomOrderStatus.USED.getCode());// 4); //  待支付	1 已预约	2 使用中	3 超时未使用	4 已完成	5 取消	9
                tRoomOrderMapper.updateTRoomOrder(up);

                // 更改房间状态清洁中
                TRoom room = new TRoom();
                room.setId(order.getRoomId());
                room.setStatus(OfficeEnum.RoomStatus.IN_CLEAN.getCode());//"2"); //0 可用 1 不可用 2 清洁中 3 使用中
                roomService.updateTRoom(room);

               /* TWxUser wxUser = wxUserService.selectRoomCleaner(order.getRoomId());
                // 写待打扫记录
                TRoomCleanRecord cleanRecord = new TRoomCleanRecord();
                cleanRecord.setStatus(OfficeEnum.CleanRecordStatus.TOBE_CLEAN.getCode());
                cleanRecord.setRoomId(order.getRoomId());
                cleanRecord.setWxUserId(wxUser.getId());
                cleanRecord.setOrderNo(order.getRoomId() + new Date().getTime());
                cleanRecord.setCreateTime(new Date());
                cleanRecordService.insertTRoomCleanRecord(cleanRecord);*/
                // 订单结束发送通知
                TRoom sendRoom = roomService.selectTRoomById(order.getRoomId());
                TStore tStore = storeService.selectTStoreById(sendRoom.getStoreId());
                TStoreUser tStoreUser = new TStoreUser();
                tStoreUser.setStoreId(tStore.getId());
                List<TStoreUser> tStoreUserList = itStoreUserService.selectTStoreUserList(tStoreUser);
                for (TStoreUser item : tStoreUserList) {
                    TWxUser tWxUser = itWxUserService.selectTWxUserById(item.getUserId());
                    if (tWxUser != null) {
                        if (!tWxUser.getMpOpenId().equals("") && tWxUser.getMpOpenId() != null) {
                            sendVxFinishMpMessage(tWxUser.getMpOpenId(), tStore, sendRoom, order, "");
                        }
                    }
                }

//                sendVxFinishMpMessage("oNosp6pg1nwPpNK0ojVRG3nXMUqM", tStore, sendRoom, order, "已完成");
//                sendVxFinishMpMessage("oNosp6nU4uj40-rGGCG83wkQwdzE", tStore, sendRoom, order, "已完成");
//                sendVxFinishMpMessage("oNosp6o1yVW4UQ2Jh6zS9B-B2SM4", tStore, sendRoom, order, "已完成");
//                if (wxUser.getMpOpenId() != null) {
////                    sendVxMpMessage(wxUser.getMpOpenId(), tStore, room, roomOrder);
//
//                }

                continue;
            }

            // 未超期订单 判断是否发送提醒;
            int diff = Math.abs((int) ((new Date().getTime() - order.getEndTime().getTime()) / (1000 * 60)));
            for (int min : alertList) {
                if (diff > min)
                    continue;

                if (min < order.getLeftMinute()) {
                    TRoom room = roomService.selectTRoomById(order.getRoomId());
                    for (String s : room.getTableCode().split(",")) {
                        TEquipment equipment = equipments.get(Long.parseLong(s));
                        if (OfficeEnum.EquipType.HORN.getCode().equalsIgnoreCase(equipment.getEquipType())) {
//                                CloudHornRegResponse resp = HornConfig.hornSend(equipment.getEquipControl(), "您的订单还有" + diff + "分钟结束，请及时续费，以免断电影响使用，谢谢");

                            order.setLeftMinute(min);// 标识已经通知
                            tRoomOrderMapper.updateTRoomOrder(order);
                        }
                    }
                }
            }
        }
//        System.out.println("订单结束提醒完成");
    }

    @Resource
    WxMpService wxMpService;

    private void sendVxOrderMpMessage(String mpOpenId, TStore tStore, TRoom tRoom, TRoomOrder tRoomOrder, String sendMsg) {
        try {

            List<WxMpTemplateData> data = Arrays.asList(
                    new WxMpTemplateData("time4", DateUtils.parseDateToStr("yyyy-MM-dd HH:mm:ss", tRoomOrder.getStartTime())),
                    new WxMpTemplateData("thing2", tStore.getName() + "-" + tRoom.getName()),
                    new WxMpTemplateData("time13", DateUtils.parseDateToStr("yyyy-MM-dd HH:mm:ss", DateTime.now())),
                    new WxMpTemplateData("time8", DateUtils.parseDateToStr("yyyy-MM-dd HH:mm:ss", tRoomOrder.getEndTime())),
                    new WxMpTemplateData("number12", tRoomOrder.getOrderNo().toString())
            );

            wxMpService.getAccessToken();
            WxMpTemplateMessage templateMessage = WxMpTemplateMessage.builder()
                    .toUser(mpOpenId)//要推送的用户openid
                    .data(data) //数据
                    .templateId("AhjAbw7d75UulaLv5BXyVxX4RN92ScjEKsqzq2Y7gJU")//模版id
//                    .url("http://www.baidu.com") // 点击详情跳转地址
                    .build();

            wxMpService.getTemplateMsgService().sendTemplateMsg(templateMessage);

        } catch (Exception e) {
            log.error("Failed to send message: " + e.getMessage());
        }
    }

    private void sendVxFinishMpMessage(String mpOpenId, TStore tStore, TRoom tRoom, TRoomOrder tRoomOrder, String sendMsg) {
        try {

            List<WxMpTemplateData> data = Arrays.asList(
                    new WxMpTemplateData("thing2", tStore.getName()),
                    new WxMpTemplateData("thing17", tRoom.getName()),
                    new WxMpTemplateData("character_string8", tRoomOrder.getOrderNo().toString()),
                    new WxMpTemplateData("time18", DateUtils.parseDateToStr("yyyy-MM-dd HH:mm:ss", tRoomOrder.getEndTime())),
                    new WxMpTemplateData("time23", DateUtils.parseDateToStr("yyyy-MM-dd HH:mm:ss", tRoomOrder.getStartTime()))
            );

            wxMpService.getAccessToken();
            WxMpTemplateMessage templateMessage = WxMpTemplateMessage.builder()
                    .toUser(mpOpenId)//要推送的用户openid
                    .data(data) //数据
                    .templateId("t-ptuJjgGEc_tqo7qLM861eT9Dp25Bcw2JVumbmX7u8")//模版id
//                    .url("http://www.baidu.com") // 点击详情跳转地址
                    .build();

            wxMpService.getTemplateMsgService().sendTemplateMsg(templateMessage);

        } catch (Exception e) {
            log.error("Failed to send message: " + e.getMessage());
        }
    }

    private void sendVxBeginMpMessage(String mpOpenId, TStore tStore, TRoom tRoom, TRoomOrder tRoomOrder, String sendMsg) {
        try {

            List<WxMpTemplateData> data = Arrays.asList(
                    new WxMpTemplateData("thing2", tStore.getName()),
                    new WxMpTemplateData("thing3", tRoom.getName()),
                    new WxMpTemplateData("character_string6", tRoomOrder.getOrderNo().toString()),
                    new WxMpTemplateData("time8", DateUtils.parseDateToStr("yyyy-MM-dd HH:mm:ss", tRoomOrder.getEndTime())),
                    new WxMpTemplateData("time4", DateUtils.parseDateToStr("yyyy-MM-dd HH:mm:ss", tRoomOrder.getStartTime()))
            );

            wxMpService.getAccessToken();
            WxMpTemplateMessage templateMessage = WxMpTemplateMessage.builder()
                    .toUser(mpOpenId)//要推送的用户openid
                    .data(data) //数据
                    .templateId("ZGF6vCmr_v9DobuDcB_NRsC3Encd1gQNJ5R8JIFApPk")//模版id
//                    .url("http://www.baidu.com") // 点击详情跳转地址
                    .build();

            wxMpService.getTemplateMsgService().sendTemplateMsg(templateMessage);

        } catch (Exception e) {
            log.error("Failed to send message: " + e.getMessage());
        }
    }

    @Override
    public List<RoomOrderWxVo> getOrderCanOpen(OrderCanOpenReq wxUserId) {
        List<RoomOrderWxVo> resList = new ArrayList<>();
        final List<RoomOrderWxVo> orderCanOpen = tRoomOrderMapper.getOrderCanOpen(wxUserId);
        for (RoomOrderWxVo vo : orderCanOpen) {
            if (vo.getStatus() == 4 && vo.getEndTime().before(new Date())) {
                continue;
            }
            resList.add(vo);

        }
        return resList;
    }

    @Autowired
    private ISysDictDataService dictDataService;

    @Override
    public void scanToPayOrder() {
        TRoomOrder qry = new TRoomOrder();
        qry.setStatus(1);//  待支付	1 已预约	2 使用中	3 超时未使用	4 已完成	5 取消	9
        final List<TRoomOrder> roomOrders = tRoomOrderMapper.selectTRoomOrderList(qry);

        int closeOrderMinute = 30;
        SysDictData dictData = new SysDictData();
        dictData.setDictLabel("close_order");
        final List<SysDictData> dictDataList = dictDataService.selectDictDataList(dictData);
        if (dictDataList.size() > 0)
            closeOrderMinute = Integer.parseInt(dictDataList.get(0).getDictValue());


        for (TRoomOrder roomOrder : roomOrders) {
            Date cancelTime = DateUtils.addMinutes(roomOrder.getCreateTime(), closeOrderMinute);
            if (cancelTime.after(new Date())) {
                TRoomOrder up = new TRoomOrder();
                up.setId(roomOrder.getId());
                up.setStatus(9);
                tRoomOrderMapper.updateTRoomOrder(up);
            }
        }
    }

    @Autowired
    ITStorePromotionService storePromotionService;

    @Override
    @Transactional
    public PrepayResp promotionPrepay(PrepayReq prepayReq, long userId) {
        PrepayResp resp = new PrepayResp();

        if (prepayReq.getCouponId() == null || prepayReq.getCouponId() == 0) {
            throw new ServiceException("优惠券异常");
        }
        TStorePromotion storePromotion = storePromotionService.selectTStorePromotionById(prepayReq.getCouponId());
        if (storePromotion == null) {
            throw new ServiceException("优惠券异常");
        }

        TRoom room = roomService.selectTRoomById(prepayReq.getRoomId());
        if (OfficeEnum.RoomStatus.NO_USE.getCode().equalsIgnoreCase(room.getStatus())) {
            throw new ServiceException("所选房间不可用");
        }
        if (room.getStoreId() != storePromotion.getStoreId()) {
            throw new ServiceException("优惠券不适用于该房间");
        }

        if (OfficeEnum.ValidType.START_END_DATE.getCode().equals(storePromotion.getValidType())) { // 有效期区间
            if (new Date().before(storePromotion.getStartDate()) || new Date().after(DateUtils.addDays(storePromotion.getEndDate(), 1))) {
                throw new ServiceException("不在有效期范围内");
            }
        }

        Date endTime = DateUtils.addMinutes(prepayReq.getStartTime(), storePromotion.getMaxMinute().intValue());
        // 改成分钟后订单时间冲突校验；
        PrepayReq confQry = new PrepayReq();
        confQry.setRoomId(prepayReq.getRoomId());
        confQry.setStartTime(prepayReq.getStartTime());
        confQry.setEndTime(endTime);
        List<TRoomOrder> exOrders = tRoomOrderMapper.selectConflictRoomPeriod(confQry);
        if (exOrders.size() > 0) {
            throw new ServiceException("预约时间冲突");
        }

        int weekDay = prepayReq.getStartTime().getDay();
        if (weekDay == 0) weekDay = 7;
        if (!storePromotion.getWeekDays().contains(weekDay + "")) {
            throw new ServiceException("该优惠券预约日期不可使用");
        }

        TWxUserPromotion userPromotion = new TWxUserPromotion();
        BeanUtils.copyProperties(storePromotion, userPromotion);
        userPromotion.setWxId(userId);
        userPromotion.setStatus("已使用");
        userPromotion.setCouponId(storePromotion.getId());


        TRoomOrder tRoomOrder = new TRoomOrder();
        BeanUtils.copyProperties(prepayReq, tRoomOrder);
//        tRoomOrder.setEndTime(DateUtils.addHours(prepayReq.getStartTime(), storePromotion.getMaxMinute().intValue()));
        tRoomOrder.setEndTime(endTime);// 改为分钟计时订单
        tRoomOrder.setUserId(userId);
        tRoomOrder.setPayType(OfficeEnum.PayType.WX_PAY.getCode());

        // 计算总金额
        BigDecimal totalPrice = storePromotion.getStandardPrice();

        // 计算订单号
        long orderNo = getOrderNo(prepayReq.getRoomId());

        TWxUser wxUser = wxUserService.selectTWxUserById(userId);

        WxPayService wxPayService = payService.getConfigByStore(storePromotion.getStoreId());
        WxPayUnifiedOrderV3Request v3Request = new WxPayUnifiedOrderV3Request();
        final WxPayConfig config = wxPayService.getConfig();

        WxPayUnifiedOrderV3Request.Amount v3Amount = new WxPayUnifiedOrderV3Request.Amount();
        v3Amount.setTotal(totalPrice.multiply(new BigDecimal(100)).intValue());
        WxPayUnifiedOrderV3Request.Payer v3payer = new WxPayUnifiedOrderV3Request.Payer();
        v3payer.setOpenid(wxUser.getOpenId());

        WxPayUnifiedOrderV3Request.Discount detail = new WxPayUnifiedOrderV3Request.Discount();
        WxPayUnifiedOrderV3Request.GoodsDetail goodsDetail = new WxPayUnifiedOrderV3Request.GoodsDetail();
        goodsDetail.setMerchantGoodsId(storePromotion.getId().toString());
        goodsDetail.setQuantity(1);
        goodsDetail.setUnitPrice(v3Amount.getTotal());
        List<WxPayUnifiedOrderV3Request.GoodsDetail> goodsDetailList = new ArrayList<>();
        goodsDetailList.add(goodsDetail);
        detail.setGoodsDetails(goodsDetailList);

        v3Request.setAppid(config.getAppId()).setMchid(config.getMchId()).setNotifyUrl(config.getPayScoreNotifyUrl())
                .setDescription("roomId: " + prepayReq.getRoomId()).setOutTradeNo(String.valueOf(orderNo))
                .setAmount(v3Amount)
                .setPayer(v3payer)
                .setAttach(OfficeEnum.WxTradeType.ROOM_ORDER.getCode())
                .setDetail(detail)
                .setGoodsTag(storePromotion.getRemark());

        WxPayUnifiedOrderV3Result.JsapiResult jsapiResult = null;
        try {
            jsapiResult = wxPayService.createOrderV3(TradeTypeEnum.valueOf("JSAPI"), v3Request);

            tRoomOrder.setOrderNo(orderNo);
            tRoomOrder.setTotalAmount(totalPrice);
            tRoomOrder.setPayAmount(totalPrice);
            tRoomOrder.setStatus(OfficeEnum.RoomOrderStatus.TO_PAY.getCode());// 待支付
            tRoomOrder.setCreateTime(DateUtils.getNowDate());
            tRoomOrder.setCreateBy(userId + "");

            userPromotionService.insertTWxUserPromotion(userPromotion);

            tRoomOrder.setPromotionId(userPromotion.getId());
            tRoomOrder.setOrderType("团购套餐");
            tRoomOrderMapper.insertTRoomOrder(tRoomOrder);


        } catch (WxPayException e) {
            e.printStackTrace();
            log.error("JSAPI 下单：" + e.getLocalizedMessage());
            throw new ServiceException(e.getLocalizedMessage());
        }
        resp.setJsapiResult(jsapiResult);
        resp.setOrderId(tRoomOrder.getId());
        log.debug("/order with promtion: return:" + resp.getOrderId() + jsapiResult.toString());

        return resp;
    }

    @Override
    public List<RoomOrderH5Vo> selectTRoomOrderH5List(RoomOrderH5QryVo tRoomOrder) {
        return tRoomOrderMapper.selectTRoomOrderH5List(tRoomOrder);
    }

    @Override
    public TRoomOrder order4Guest(TRoomOrder tRoomOrder) {
        /*TWxUser qry = new TWxUser();
        qry.setPhone(tRoomOrder.getUserId() + "");
        final List<TWxUser> tWxUsers = wxUserService.selectTWxUserList(qry);
        if (tWxUsers.size() == 0) {
            throw new ServiceException("该手机号还未登录过小程序，未能识别");
        }
        tRoomOrder.setUserId(tWxUsers.get(0).getId());*/
        PrepayReq confQry = new PrepayReq();
        confQry.setRoomId(tRoomOrder.getRoomId());
        confQry.setStartTime(tRoomOrder.getStartTime());
        confQry.setEndTime(tRoomOrder.getEndTime());
        final List<TRoomOrder> confList = tRoomOrderMapper.selectConflictRoomPeriod(confQry);
        if (confList.size() > 0) {
            throw new ServiceException("房间预约时间段冲突");
        }

        tRoomOrder.setUserId(0l);
        tRoomOrder.setPayType(OfficeEnum.PayType.ORDER4GUEST_PAY.getCode());//
        tRoomOrder.setStatus(OfficeEnum.RoomOrderStatus.ORDERED.getCode());//
        tRoomOrder.setOrderNo(getOrderNo(tRoomOrder.getRoomId()));
        tRoomOrder.setTotalAmount(new BigDecimal(0));
        tRoomOrder.setPayAmount(new BigDecimal(0));
        tRoomOrder.setWelfareAmount(new BigDecimal(0));
        tRoomOrder.setRandomCode(IdUtils.randomUUID());
        tRoomOrderMapper.insertTRoomOrder(tRoomOrder);
        return tRoomOrder;
    }

    private Long getOrderNo(long roomId) {
        // 计算订单号
        long orderNo = 0l;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        final String prefix = roomId + sdf.format(new Date());
        Long maxId = tRoomOrderMapper.getHourMaxOrder(prefix);
        if (maxId == null) {
            orderNo = Long.parseLong(prefix + "00");
        } else {
            orderNo = maxId + 1;
        }
        return orderNo;
    }

    @Override
    public List<TRoomOrder> getInUseOrder(TRoomOrder roomOrder) {
        return tRoomOrderMapper.getInUseOrder(roomOrder);
    }

    @Override
    public int order4GuestOpenRoom(TRoomOrder tRoomOrder) {
        // 判断是否到达开门允许的事件范围
        SysDictData dictData = new SysDictData();
        dictData.setDictLabel("open_room");
        final List<SysDictData> sysDictData = dictDataService.selectDictDataList(dictData);
        int minutes = 15;
        if (sysDictData.size() > 0) {
            minutes = Integer.parseInt(sysDictData.get(0).getDictValue());
        }
        TRoomOrder qry = new TRoomOrder();
        qry.setOrderNo(tRoomOrder.getOrderNo());
        qry.setRandomCode(tRoomOrder.getRandomCode());
        List<TRoomOrder> roomOrders = tRoomOrderMapper.selectTRoomOrderList(qry);
        if (roomOrders.size() == 0) {
            throw new ServiceException("订单校验失败");
        }

        TRoomOrder roomOrder = roomOrders.get(0);

        long diff = (roomOrder.getStartTime().getTime() - new Date().getTime()) / 60000;
        if (diff > minutes)
            throw new ServiceException("订单开始前" + minutes + "分钟才可以开门");

        String errMsg = roomService.openRoom(roomOrder.getRoomId());
        TRoomOrder upOrder = new TRoomOrder();
        upOrder.setId(roomOrder.getId());
        upOrder.setStatus(OfficeEnum.RoomOrderStatus.USING.getCode());// 3);// 使用中
        tRoomOrderMapper.updateTRoomOrder(upOrder);
        TRoom room = new TRoom();
        room.setId(roomOrder.getRoomId());
        room.setStatus(OfficeEnum.RoomStatus.IN_USE.getCode());// 使用中
        roomService.updateTRoom(room);
        if (StringUtils.isNotEmpty(errMsg)) {
            throw new ServiceException(errMsg);
        }

        TRoom exRoom = roomService.selectTRoomById(roomOrder.getRoomId());
        TStore store = storeService.selectTStoreById(exRoom.getStoreId());

        //代客下单提醒商家
        TStoreUser tStoreUser = new TStoreUser();
        tStoreUser.setStoreId(store.getId());
        List<TStoreUser> tStoreUserList = itStoreUserService.selectTStoreUserList(tStoreUser);
        for (TStoreUser item : tStoreUserList) {
            TWxUser tWxUser = itWxUserService.selectTWxUserById(item.getUserId());
            if (tWxUser != null && tWxUser.getMpOpenId() != null && !tWxUser.getMpOpenId().equals("")) {
                new WxMsgSender().sendOrderStartMsg(tWxUser.getMpOpenId(), exRoom.getName(), store.getName(), roomOrder);
            }
        }
//        new WxMsgSender().sendOrderStartMsg("oNosp6pg1nwPpNK0ojVRG3nXMUqM", exRoom.getName(), store.getName(), roomOrder);
//        new WxMsgSender().sendOrderStartMsg("oNosp6nU4uj40-rGGCG83wkQwdzE", exRoom.getName(), store.getName(), roomOrder);
//        new WxMsgSender().sendOrderStartMsg("oNosp6o1yVW4UQ2Jh6zS9B-B2SM4", exRoom.getName(), store.getName(), roomOrder);

        return 0;
    }

    @Override
    public void merchantCharge4Guest(MerchantOrderChargeReq orderChargeReq) {
        final TRoomOrder roomOrder = tRoomOrderMapper.selectTRoomOrderById(orderChargeReq.getId());
        if (roomOrder == null) {
            throw new ServiceException("订单不存在");
        }
        Date endTime = DateUtils.addMinutes(roomOrder.getEndTime(), orderChargeReq.getMinutes());
        PrepayReq qry = new PrepayReq();
        qry.setRoomId(roomOrder.getRoomId());
        qry.setStartTime(roomOrder.getStartTime());
        qry.setEndTime(endTime);
        final List<TRoomOrder> orderList = tRoomOrderMapper.selectConflictRoomPeriod(qry);
        if (orderList.size() > 1) { // 跟原订单肯定冲突 所以》1
            throw new ServiceException("订单时间冲突");
        }
        TRoomOrder up = new TRoomOrder();
        up.setId(orderChargeReq.getId());
        up.setEndTime(endTime);
        up.setRemark("商户续单" + orderChargeReq.getMinutes() + "分钟;" + roomOrder.getRemark());
        tRoomOrderMapper.updateTRoomOrder(up);
    }

    @Override
    public void merchantChangeTime4Guest(MerchantOrderChangeTimeReq req) {
        final TRoomOrder roomOrder = tRoomOrderMapper.selectTRoomOrderById(req.getOrderId());
        if (roomOrder == null) {
            throw new ServiceException("订单不存在");
        }
        final long diffMinutes = (req.getStartTime().getTime() - roomOrder.getStartTime().getTime()) / (1000 * 60);
        Date endTime = DateUtils.addMinutes(roomOrder.getEndTime(), (int) diffMinutes);

        PrepayReq qry = new PrepayReq();
        qry.setRoomId(roomOrder.getRoomId());
        qry.setStartTime(req.getStartTime());
        qry.setEndTime(endTime);
        final List<TRoomOrder> orderList = tRoomOrderMapper.selectConflictRoomPeriod(qry);
        if (orderList.size() > 0) { // 跟原订单有可能也冲突
            for (TRoomOrder temp : orderList) {
                if (!temp.getId().equals(roomOrder.getId())) {
                    throw new ServiceException("订单时间冲突");
                }
            }
        }

        TRoomOrder up = new TRoomOrder();
        up.setId(roomOrder.getId());
        up.setStartTime(req.getStartTime());
        up.setEndTime(endTime);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        up.setRemark("商家提前订单开始时间从" + sdf.format(roomOrder.getStartTime()) + "到" + sdf.format(req.getStartTime()) + ";" + roomOrder.getRemark());
        tRoomOrderMapper.updateTRoomOrder(up);
    }

    @Transactional
    @Override
    public void merchantChangeRoom4Guest(OrderChangeRoomReq req) {
        final TRoomOrder roomOrder = tRoomOrderMapper.selectTRoomOrderById(req.getOrderId());
        if (roomOrder == null) {
            throw new ServiceException("订单不存在");
        }
        PrepayReq qry = new PrepayReq();
        qry.setRoomId(req.getRoomId());
        qry.setStartTime(roomOrder.getStartTime());
        qry.setEndTime(roomOrder.getEndTime());
        final List<TRoomOrder> orderList = tRoomOrderMapper.selectConflictRoomPeriod(qry);
        if (orderList.size() > 0) { // 跟原订单肯定冲突 所以》1
            throw new ServiceException("订单时间冲突");
        }
        if (roomOrder.getStatus() == OfficeEnum.RoomOrderStatus.USING.getCode()) {
            TRoom room = new TRoom();
            room.setId(roomOrder.getRoomId());
            room.setStatus(OfficeEnum.RoomStatus.CAN_USE.getCode());
        }
        TRoomOrder upOrder = new TRoomOrder();
        upOrder.setId(roomOrder.getId());
        upOrder.setRoomId(req.getRoomId());
        tRoomOrderMapper.updateTRoomOrder(upOrder);
    }

    @Autowired
    private ITRoomChargePriceService roomChargePriceService;

    @Autowired
    private ITRoomOrderChargeService orderChargeService;

    @Autowired
    private ITWxPayService payService;

    @Transactional
    @Override
    public PrepayResp orderCharge(MiniOrderChargeReq req, long wxUserId) {
        // 1 原订单进行中 2 套餐复合型
        TRoomOrder inusedOrder = new TRoomOrder();
        inusedOrder.setStatus(OfficeEnum.RoomOrderStatus.USING.getCode());
        inusedOrder.setRoomId(req.getRoomId());
        List<TRoomOrder> roomOrders = tRoomOrderMapper.selectTRoomOrderList(inusedOrder);
        if (roomOrders.size() == 0) {
            throw new ServiceException("该房间没有进行中的订单");
        }

        final TRoomChargePrice tRoomChargePrice = roomChargePriceService.selectTRoomChargePriceById(req.getChargePackId());
        if (tRoomChargePrice == null) {
            throw new ServiceException("续费套餐不存在");
        } else if (!roomOrders.get(0).getRoomId().equals(tRoomChargePrice.getRoomId())) {
            throw new ServiceException("续费套餐与当前订单套餐房间不一致");
        }
        TRoomOrder roomOrder = roomOrders.get(0);
        Date endTime = DateUtils.addMinutes(roomOrder.getEndTime(), tRoomChargePrice.getMinutes().intValue());
        BigDecimal totalPrice = tRoomChargePrice.getPrice();

        PrepayResp resp = new PrepayResp();
        // 订单时间冲突校验；
        PrepayReq confQry = new PrepayReq();
        confQry.setRoomId(roomOrder.getRoomId());
        confQry.setStartTime(roomOrder.getEndTime());
        confQry.setEndTime(endTime);
        List<TRoomOrder> exOrders = tRoomOrderMapper.selectConflictRoomPeriod(confQry);
        if (exOrders.size() > 0) {
            throw new ServiceException("预约时间冲突");
        }

        TRoomOrderCharge chargeOrder = new TRoomOrderCharge();
        chargeOrder.setChargePackId(tRoomChargePrice.getId());
        chargeOrder.setOrgOrderId(roomOrder.getId());
        chargeOrder.setRoomId(roomOrder.getRoomId());
        chargeOrder.setWxUserId(wxUserId);
        chargeOrder.setChargeMinute(tRoomChargePrice.getMinutes());
        chargeOrder.setEndTime(endTime);

        // 计算订单号
        long orderNo = 0l;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        final String prefix = roomOrder.getRoomId() + sdf.format(new Date());
        Long maxId = orderChargeService.getHourMaxOrder(prefix);
        if (maxId == null)
            orderNo = Long.parseLong(prefix + "00");
        else {
            orderNo = maxId + 1;
        }

        TWxUser wxUser = wxUserService.selectTWxUserById(wxUserId);

        if (req.getPayType().equals(OfficeEnum.PayType.WX_PAY.getCode())) { // 直接支付 发起微信支付 预支付交易单，返回微信支付返回的标
            WxPayService iwxPayService = payService.getConfigByRoom(roomOrder.getRoomId());
            WxPayUnifiedOrderV3Request v3Request = new WxPayUnifiedOrderV3Request();
            final WxPayConfig config = iwxPayService.getConfig();

            WxPayUnifiedOrderV3Request.Amount v3Amount = new WxPayUnifiedOrderV3Request.Amount();
            v3Amount.setTotal(totalPrice.multiply(new BigDecimal(100)).intValue());
            WxPayUnifiedOrderV3Request.Payer v3payer = new WxPayUnifiedOrderV3Request.Payer();
            v3payer.setOpenid(wxUser.getOpenId());

            v3Request.setAppid(config.getAppId()).setMchid(config.getMchId()).setNotifyUrl(config.getPayScoreNotifyUrl())
                    .setDescription("roomId: " + roomOrder.getRoomId()).setOutTradeNo(String.valueOf(orderNo))
                    .setAmount(v3Amount)
                    .setPayer(v3payer)
                    .setAttach(OfficeEnum.WxTradeType.ROOM_ORDER_CHARGE.getCode())
//                    .setDetail(detail)
                    .setGoodsTag(tRoomChargePrice.getName());

            WxPayUnifiedOrderV3Result.JsapiResult jsapiResult = null;
            try {
                jsapiResult = iwxPayService.createOrderV3(TradeTypeEnum.valueOf("JSAPI"), v3Request);

                chargeOrder.setPayType(OfficeEnum.PayType.WX_PAY.getCode());
                chargeOrder.setOrderNo(orderNo);
                chargeOrder.setTotalAmount(totalPrice);
                chargeOrder.setPayAmount(totalPrice);
                chargeOrder.setStatus(OfficeEnum.ChargeOrderStatus.TO_PAY.getCode());// 待支付
                chargeOrder.setCreateTime(DateUtils.getNowDate());
                chargeOrder.setCreateBy(wxUserId + "");

                orderChargeService.insertTRoomOrderCharge(chargeOrder);
            } catch (WxPayException e) {
                e.printStackTrace();
                log.error("JSAPI 下单：" + e.getLocalizedMessage());
                throw new ServiceException(e.getLocalizedMessage());
            }
            resp.setJsapiResult(jsapiResult);
            resp.setOrderId(chargeOrder.getId());
            log.debug("续费订单支付请求返回:" + resp.getOrderId() + jsapiResult.toString());
        } else if (req.getPayType().equals(OfficeEnum.PayType.CARD_BALANCE_PAY.getCode())) {  // 储值卡余额支付
            TWxUserAmount wxUserAmount = new TWxUserAmount();
            // 判断用户在商户下的余额是否足够；
            TRoom room = roomService.selectTRoomById(roomOrder.getRoomId());
            wxUserAmount.setUserId(Long.parseLong(room.getCreateBy()));
            wxUserAmount.setWxUserId(roomOrder.getUserId());
            final List<TWxUserAmount> wxUserAmounts = wxUserAmountService.selectTWxUserAmountList(wxUserAmount);
            if (wxUserAmounts.size() == 0 || wxUserAmounts.get(0).getAmount().compareTo(totalPrice) < 0) {
                throw new ServiceException("储值卡余额不够，请充值后使用");
            }
            BigDecimal cashAmount = wxUserAmounts.get(0).getCashAmount();
            BigDecimal welfareAmount = wxUserAmounts.get(0).getWelfareAmount();
            wxUserAmount.setAmount(totalPrice);

            //本金足够
            if (totalPrice.compareTo(cashAmount) < 0) {
                wxUserAmount.setCashAmount(totalPrice);
                wxUserAmount.setWelfareAmount(new BigDecimal(0));
            } else {
                //优先扣除本金
                wxUserAmount.setCashAmount(cashAmount);
                wxUserAmount.setWelfareAmount(totalPrice.subtract(cashAmount));
            }
            // 扣除余额
            wxUserAmountService.minusCardValue(wxUserAmount);

            chargeOrder.setPayType(OfficeEnum.PayType.CARD_BALANCE_PAY.getCode());
            chargeOrder.setOrderNo(orderNo);
            chargeOrder.setTotalAmount(totalPrice);
            chargeOrder.setPayAmount(totalPrice);
            chargeOrder.setStatus(OfficeEnum.ChargeOrderStatus.PAYED.getCode());/// 待支付
            chargeOrder.setCreateTime(DateUtils.getNowDate());
            chargeOrder.setCreateBy(wxUserId + "");
            orderChargeService.insertTRoomOrderCharge(chargeOrder);

            // 续费订单支付成功，原订单修改结束时间，添加订单类型备注；
            TRoomOrder updateOrder = new TRoomOrder();
            updateOrder.setId(roomOrder.getId());
            updateOrder.setEndTime(endTime);
            updateOrder.setRemark("续费订单;" + roomOrder.getRemark());
            updateOrder.setOrderType(roomOrder.getOrderType() + ";续费订单");
            tRoomOrderMapper.updateTRoomOrder(updateOrder);

        } else if (req.getPayType().equals(OfficeEnum.PayType.COUPON_PAY.getCode())) { // 美团券券支付
//            validCoupon(prepayReq, userId);// 校验可用性

            //  优惠券置为已使用
            TWxUserCoupon wxUserCoupon = new TWxUserCoupon();
            wxUserCoupon.setCouponId(req.getCouponId());
            wxUserCoupon.setStatus(1l);
            wxUserCouponService.updateTWxUserCoupon(wxUserCoupon);

            BigDecimal payAmt = new BigDecimal(0);
            chargeOrder.setPayType(OfficeEnum.PayType.COUPON_PAY.getCode());
            chargeOrder.setOrderNo(orderNo);
            chargeOrder.setCouponId(req.getCouponId());
            chargeOrder.setTotalAmount(totalPrice);
            chargeOrder.setCouponAmount(totalPrice);
            chargeOrder.setPayAmount(payAmt);
            chargeOrder.setStatus(OfficeEnum.ChargeOrderStatus.PAYED.getCode());// 已预约
            chargeOrder.setCreateTime(DateUtils.getNowDate());
            chargeOrder.setCreateBy(wxUserId + "");
            orderChargeService.insertTRoomOrderCharge(chargeOrder);

            // 续费订单支付成功，原订单修改结束时间，添加订单类型备注；
            TRoomOrder updateOrder = new TRoomOrder();
            updateOrder.setId(roomOrder.getId());
            updateOrder.setEndTime(endTime);
            updateOrder.setRemark("续费订单;" + roomOrder.getRemark());
            updateOrder.setOrderType(roomOrder.getOrderType() + ";续费订单");
            tRoomOrderMapper.updateTRoomOrder(updateOrder);

        }
        return resp;
    }

    @Transactional
    @Override
    public WxPayOrderQueryV3Result finishCharge(Long orderId, Long wxuserid) {
        final TRoomOrderCharge tRoomOrderCharge = orderChargeService.selectTRoomOrderChargeById(orderId);
        if (tRoomOrderCharge.getStatus().equalsIgnoreCase(OfficeEnum.ChargeOrderStatus.PAYED.getCode())) {
            return null;
        }
        /*if (tRoomOrderCharge.getWxUserId().equals(wxuserid)) {
            throw new ServiceException("非本人订单");
        }*/
        TRoomOrder orgRoomOrder = tRoomOrderMapper.selectTRoomOrderById(tRoomOrderCharge.getOrgOrderId());
        //查询支付状态；
        WxPayOrderQueryV3Result v3Result = null;
        try {
            WxPayService wxPayService = payService.getConfigByRoom(tRoomOrderCharge.getRoomId());
            v3Result = wxPayService.queryOrderV3("", String.valueOf(tRoomOrderCharge.getOrderNo()));
            log.debug("续费订单查询订单返回:" + v3Result.toString());
        } catch (WxPayException e) {
            log.error("续费订单查询微信后台订单失败： " + e.getMessage());
            throw new ServiceException("查询微信后台订单失败");
        }
        // 根据业务需要，更新商户平台订单状态
        String tradState = v3Result.getTradeState();
        if (tradState.equalsIgnoreCase(WxPayConstants.WxpayTradeStatus.SUCCESS)) {
            // 原订单结束时间修改，插入续费订单记录
            Date newEndTime = tRoomOrderCharge.getEndTime();

            TRoomOrderCharge upChargeOrder = new TRoomOrderCharge();
            upChargeOrder.setId(tRoomOrderCharge.getId());
            upChargeOrder.setStatus(OfficeEnum.ChargeOrderStatus.PAYED.getCode());
            if (v3Result.getAmount() != null) {
                final WxPayOrderQueryV3Result.Amount v3ResultAmount = v3Result.getAmount();
                final Integer payerTotal = v3ResultAmount.getPayerTotal();
                if (payerTotal != 0) {
                    if (!v3ResultAmount.getTotal().equals(tRoomOrderCharge.getTotalAmount().multiply(new BigDecimal(100)).intValue())) {
                        throw new ServiceException("订单金额不一致");
                    }
                    BigDecimal payAmt = new BigDecimal(v3ResultAmount.getPayerTotal()).divide(new BigDecimal(100), 3, RoundingMode.HALF_UP);
                    upChargeOrder.setPayAmount(payAmt);
                    upChargeOrder.setCouponAmount(orgRoomOrder.getTotalAmount().subtract(payAmt));
                }

            }
            orderChargeService.updateTRoomOrderCharge(upChargeOrder);

            // 续费订单支付成功，原订单修改结束时间，添加订单类型备注；
            TRoomOrder updateOrder = new TRoomOrder();
            updateOrder.setId(orgRoomOrder.getId());
            updateOrder.setEndTime(newEndTime);
            updateOrder.setRemark("续费" + tRoomOrderCharge.getChargeMinute() + "分钟订单;" + orgRoomOrder.getRemark());
            updateOrder.setOrderType(orgRoomOrder.getOrderType() + ";续费" + tRoomOrderCharge.getChargeMinute() + "分钟订单");
            tRoomOrderMapper.updateTRoomOrder(updateOrder);

        } else if (tradState.equalsIgnoreCase(WxPayConstants.WxpayTradeStatus.REFUND)) {
            throw new ServiceException("订单转入退款");
        } else if (tradState.equalsIgnoreCase(WxPayConstants.WxpayTradeStatus.NOTPAY)) {
            throw new ServiceException("订单未支付或已支付未收到微信通知");
        } else if (tradState.equalsIgnoreCase(WxPayConstants.WxpayTradeStatus.CLOSED)) {
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
        } else if (tradState.equalsIgnoreCase(WxPayConstants.WxpayTradeStatus.REVOKED)) {
            throw new ServiceException("已撤销（仅付款码支付会返回）");
        } else if (tradState.equalsIgnoreCase(WxPayConstants.WxpayTradeStatus.USER_PAYING)) {
            throw new ServiceException("用户支付中（仅付款码支付会返回）");
        } else if (tradState.equalsIgnoreCase(WxPayConstants.WxpayTradeStatus.PAY_ERROR)) {
            throw new ServiceException("支付失败（仅付款码支付会返回）");
        }/*else{
            throw new ServiceException(v3Result.getTradeStateDesc());
        }*/

        try {
            sendVxMessage(wxuserid, orgRoomOrder);
            TRoom tRoom = roomService.selectTRoomById(orgRoomOrder.getRoomId());
            TStore tStore = storeService.selectTStoreById(tRoom.getStoreId());

            TStoreUser tStoreUser = new TStoreUser();
            tStoreUser.setStoreId(tStore.getId());
            List<TStoreUser> tStoreUserList = itStoreUserService.selectTStoreUserList(tStoreUser);
            for (TStoreUser item : tStoreUserList) {
                TWxUser tWxUser = itWxUserService.selectTWxUserById(item.getUserId());
                if (tWxUser != null && tWxUser.getMpOpenId() != null && !tWxUser.getMpOpenId().equals("")) {
                    sendVxOrderMpMessage(tWxUser.getMpOpenId(), tStore, tRoom, orgRoomOrder, "");
                }
            }
//            sendVxOrderMpMessage("oNosp6pg1nwPpNK0ojVRG3nXMUqM", tStore, tRoom, orgRoomOrder, "已续费");
//            sendVxOrderMpMessage("oNosp6nU4uj40-rGGCG83wkQwdzE", tStore, tRoom, orgRoomOrder, "已续费");
//            sendVxOrderMpMessage("oNosp6o1yVW4UQ2Jh6zS9B-B2SM4", tStore, tRoom, orgRoomOrder, "已续费");
        } catch (Exception e) {
            log.error("消息推送失败");
        }

        return v3Result;
    }

    // 处理续费套餐微信支付回调
    @Override
    public void wxChargeNotify(String orderNo, String openId, WxPayOrderNotifyV3Result.Amount amt, String wxCallback) {

        // 处理订单状态
        TRoomOrderCharge chargeOrder = new TRoomOrderCharge();
        chargeOrder.setOrderNo(Long.parseLong(orderNo));
        final List<TRoomOrderCharge> roomOrders = orderChargeService.selectTRoomOrderChargeList(chargeOrder);
        if (roomOrders.size() > 0) {
            chargeOrder = roomOrders.get(0);
        }
        // 注意：微信会通知多次，因此需判断此订单
        if (OfficeEnum.ChargeOrderStatus.PAYED.getCode().equalsIgnoreCase(chargeOrder.getStatus())) {
            return;
        }

        TWxUser wxUser = wxUserService.selectTWxUserById(chargeOrder.getWxUserId());
        // 验证金额和 openid
        if (!openId.equalsIgnoreCase(wxUser.getOpenId()) || chargeOrder.getTotalAmount().multiply(new BigDecimal(100)).compareTo(new BigDecimal(amt.getTotal())) != 0) {
            throw new ServiceException("FAIL:金额或用户不匹配");
        }

        Date newEndTime = chargeOrder.getEndTime();

        // 续费订单支付成功，原订单修改结束时间，添加订单类型备注；
        TRoomOrder oldRoomOrder = tRoomOrderMapper.selectTRoomOrderById(chargeOrder.getOrgOrderId());
        TRoomOrder updateOrder = new TRoomOrder();
        updateOrder.setId(chargeOrder.getOrgOrderId());
        updateOrder.setEndTime(newEndTime);
        updateOrder.setOrderType(oldRoomOrder.getOrderType() + ";续费" + chargeOrder.getChargeMinute() + "分钟订单");
        tRoomOrderMapper.updateTRoomOrder(updateOrder);

        TRoomOrderCharge upChargeOrder = new TRoomOrderCharge();
        upChargeOrder.setId(chargeOrder.getId());
        upChargeOrder.setStatus(OfficeEnum.ChargeOrderStatus.PAYED.getCode());
        if (amt.getPayerTotal() != null && amt.getPayerTotal().intValue() != 0) {
            BigDecimal payAmt = new BigDecimal(amt.getPayerTotal()).divide(new BigDecimal(100), 3, RoundingMode.HALF_UP);
            upChargeOrder.setPayAmount(payAmt);
            upChargeOrder.setCouponAmount(chargeOrder.getTotalAmount().subtract(payAmt));
        }
        orderChargeService.updateTRoomOrderCharge(upChargeOrder);
    }

    public static void main(String[] args) {
        BigDecimal totalAmt = new BigDecimal("39.9");
        BigDecimal payAmt = new BigDecimal(3990).divide(new BigDecimal(100), 3, RoundingMode.HALF_UP);
        System.out.println(payAmt);
        System.out.println(totalAmt.multiply(new BigDecimal(1000)).subtract(payAmt.multiply(new BigDecimal(1000))));
    }
}
