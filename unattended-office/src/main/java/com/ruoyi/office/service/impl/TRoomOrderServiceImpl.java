package com.ruoyi.office.service.impl;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.bean.WxMaSubscribeMessage;
import cn.hutool.core.date.DateTime;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
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
import com.ruoyi.office.domain.*;
import com.ruoyi.office.domain.enums.OfficeEnum;
import com.ruoyi.office.domain.vo.*;
import com.ruoyi.office.horn.HornConfig;
import com.ruoyi.office.service.*;
import com.ruoyi.system.service.ISysDictDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruoyi.office.mapper.TRoomOrderMapper;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

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

    @Override
    public WxPayOrderQueryV3Result finish(PrepayResp vo, Long wxuserid) {
        TRoomOrder order = tRoomOrderMapper.selectTRoomOrderById(vo.getOrderId());
        if (order.getStatus().equals(OfficeEnum.RoomOrderStatus.ORDERED.getCode()))
            return null;
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
        if (tradState.equalsIgnoreCase(WxPayConstants.WxpayTradeStatus.SUCCESS)) {
            // 业务需求
            TRoomOrder updateOrder = new TRoomOrder();
            updateOrder.setId(order.getId());
            updateOrder.setStatus(OfficeEnum.RoomOrderStatus.ORDERED.getCode());
          /*  if (v3Result.getAmount() != null) {
                final WxPayOrderQueryV3Result.Amount v3ResultAmount = v3Result.getAmount();
                final Integer payerTotal = v3ResultAmount.getPayerTotal();
                if (payerTotal != 0) {
                    if (order.getTotalAmount().intValue() != v3ResultAmount.getTotal().intValue()) {
                        throw new ServiceException("订单金额不一致");
                    }
                    updateOrder.setPayAmount(new BigDecimal(v3ResultAmount.getPayerTotal()));
                    if (v3Result.getPromotionDetails() != null) {
                        final List<WxPayOrderQueryV3Result.PromotionDetail> promotionDetails = v3Result.getPromotionDetails();
                        int totalCouponAmt = 0;
                        for (WxPayOrderQueryV3Result.PromotionDetail promotionDetail : promotionDetails) {
                            totalCouponAmt += promotionDetail.getAmount();
                        }
                        updateOrder.setCouponAmount(new BigDecimal(totalCouponAmt));
                    } else {
                        updateOrder.setCouponAmount(order.getTotalAmount().multiply(new BigDecimal(100)).subtract(new BigDecimal(v3ResultAmount.getPayerTotal())));
                    }
                }

            }*/
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

        return v3Result;
    }

    @Autowired
    ITWxUserService wxUserService;

    @Autowired
    private WxPayService wxPayService;

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

        if (prepayReq.getPayType() == OfficeEnum.PayType.WX_PAY.getCode()) { // 直接支付 发起微信支付 预支付交易单，返回微信支付返回的标识.

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
                    if (totalPrice.compareTo(userPromotion.getStandardPrice()) == -1) {
                        throw new ServiceException("未达到满减金额");
                    } else {
                        payAMT = totalPrice.subtract(userPromotion.getDiscountPrice());
                    }
                }
            }

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
                jsapiResult = this.wxPayService.createOrderV3(TradeTypeEnum.valueOf("JSAPI"), v3Request);

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
            BigDecimal cashAmount = wxUserAmounts.get(0).getCashAmount();
            BigDecimal welfareAmount = wxUserAmounts.get(0).getWelfareAmount();
            wxUserAmount.setAmount(totalPrice);

            //本金足够
            if (totalPrice.compareTo(cashAmount) == -1) {
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
            if (totalPrice.compareTo(cashAmount) == -1) {
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

        } else if (prepayReq.getPayType() == OfficeEnum.PayType.COUPON_PAY.getCode()) { // 美团券券支付
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


        TRoomOrder tRoomOrder = new TRoomOrder();
        BeanUtils.copyProperties(prepayReq, tRoomOrder);
        tRoomOrder.setRoomId(roomPackage.getRoomId());
        tRoomOrder.setStartTime(prepayReq.getStartTime());
        tRoomOrder.setEndTime(DateUtils.addHours(prepayReq.getStartTime(), (int) (roomPackage.getMinutes() / 60)));
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

        if (prepayReq.getPayType() == OfficeEnum.PayType.WX_PAY.getCode()) { // 直接支付 发起微信支付 预支付交易单，返回微信支付返回的标识.

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
                    if (totalPrice.compareTo(userPromotion.getStandardPrice()) == -1) {
                        throw new ServiceException("未达到满减金额");
                    } else {
                        payAMT = totalPrice.subtract(userPromotion.getDiscountPrice());
                    }
                }
            }

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
            List<WxPayUnifiedOrderV3Request.GoodsDetail> goodsDetailList=new ArrayList<>();
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
                jsapiResult = this.wxPayService.createOrderV3(TradeTypeEnum.valueOf("JSAPI"), v3Request);

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
        } else if (prepayReq.getPayType() == OfficeEnum.PayType.CARD_BALANCE_PAY.getCode()) {  // 储值卡余额支付
            TWxUserAmount wxUserAmount = new TWxUserAmount();
            // 判断用户在商户下的余额是否足够；
            long roomId = roomPackage.getRoomId();
            TRoom room = roomService.selectTRoomById(roomId);

            wxUserAmount.setUserId(Long.parseLong(room.getCreateBy()));
            wxUserAmount.setWxUserId(tRoomOrder.getUserId());
            final List<TWxUserAmount> wxUserAmounts = wxUserAmountService.selectTWxUserAmountList(wxUserAmount);
            if (wxUserAmounts.size() == 0 || wxUserAmounts.get(0).getAmount().compareTo(totalPrice) == -1)
                throw new ServiceException("储值卡余额不够，请充值后使用");

            wxUserAmount.setAmount(totalPrice);
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

        } else if (prepayReq.getPayType() == OfficeEnum.PayType.COUPON_PAY.getCode()) { // 美团券券支付
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

        if (reorederReq.getPayType() == OfficeEnum.PayType.WX_PAY.getCode()) { // 直接支付 发起微信支付 预支付交易单，返回微信支付返回的标识.

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
                    if (totalPrice.compareTo(userPromotion.getStandardPrice()) == -1) {
                        throw new ServiceException("未达到满减金额");
                    } else {
                        payAMT = totalPrice.subtract(userPromotion.getDiscountPrice());
                    }
                }
            }

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
                jsapiResult = this.wxPayService.createOrderV3(TradeTypeEnum.valueOf("JSAPI"), v3Request);

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

        } else if (reorederReq.getPayType() == OfficeEnum.PayType.CARD_BALANCE_PAY.getCode()) {  // 储值卡余额支付
            TWxUserAmount wxUserAmount = new TWxUserAmount();
            // 判断用户在商户下的余额是否足够；
            TRoom room = roomService.selectTRoomById(roomOrder.getRoomId());

            wxUserAmount.setUserId(Long.parseLong(room.getCreateBy()));
            wxUserAmount.setWxUserId(roomOrder.getUserId());
            final List<TWxUserAmount> wxUserAmounts = wxUserAmountService.selectTWxUserAmountList(wxUserAmount);
            if (wxUserAmounts.size() == 0 || wxUserAmounts.get(0).getAmount().compareTo(totalPrice) == -1)
                throw new ServiceException("储值卡余额不够，请充值后使用");


            wxUserAmount.setAmount(totalPrice);
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

        } else if (reorederReq.getPayType() == OfficeEnum.PayType.COUPON_PAY.getCode()) { // 美团券券支付

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

    // 处理微信支付回调
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
            update.setPayAmount(new BigDecimal(amt.getPayerTotal()));
            update.setCouponAmount(new BigDecimal(amt.getTotal() - amt.getPayerTotal()));
        }

        tRoomOrderMapper.updateTRoomOrder(update);
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

        return tRoomOrderMapper.getWxRoomOrder(tRoomOrder);
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

    /**
     * 订单结束提醒
     */
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
        List<TRoomOrder> roomOrderList = tRoomOrderMapper.selectTRoomOrderList(roomOrder);
        for (TRoomOrder order : roomOrderList) {
            if (order.getEndTime().before(new Date())) {
                // 超期发送关闭
                roomService.closeRoom(order.getRoomId());

                // 更新订单为关闭;
                TRoomOrder up = new TRoomOrder();
                up.setId(order.getId());
                up.setStatus(4); //  待支付	1 已预约	2 使用中	3 超时未使用	4 已完成	5 取消	9
                tRoomOrderMapper.updateTRoomOrder(up);

                TRoom room = new TRoom();
                room.setId(order.getRoomId());
                room.setStatus("0"); //0 可用 1 不可用 2 清洁中 3 使用中
                roomService.updateTRoom(room);


                continue;
            }

            // 未超期订单 判断是否发送提醒;
            int diff = Math.abs((int) ((new Date().getTime() - order.getEndTime().getTime()) / (1000 * 60)));
            for (int min : alertList) {
                if (diff > min)
                    continue;
                if (StringUtils.isEmpty(order.getRemark())) {
                    TRoom room = roomService.selectTRoomById(order.getRoomId());
                    for (String s : room.getTableCode().split(",")) {
                        TEquipment equipment = equipments.get(Long.parseLong(s));
                        if (OfficeEnum.EquipType.HORN.getCode().equalsIgnoreCase(equipment.getEquipType())) {
                            CloudHornRegResponse resp = HornConfig.hornSend(equipment.getEquipControl(), "您的订单还有" + diff + "分钟结束，请及时续费，以免断电影响使用，谢谢");

                            order.setRemark(min + "");// 标识已经通知
                            tRoomOrderMapper.updateTRoomOrder(order);
                        }
                    }
                } else {
                    if (min != Integer.parseInt(order.getRemark())) {
                        TRoom room = roomService.selectTRoomById(order.getRoomId());
                        for (String s : room.getTableCode().split(",")) {
                            TEquipment equipment = equipments.get(Long.parseLong(s));
                            if (OfficeEnum.EquipType.HORN.getCode().equalsIgnoreCase(equipment.getEquipType())) {
                                CloudHornRegResponse resp = HornConfig.hornSend(equipment.getEquipControl(), "您的订单还有" + diff + "分钟结束，请及时续费，以免断电影响使用，谢谢");

                                order.setRemark(min + "");// 标识已经通知
                                tRoomOrderMapper.updateTRoomOrder(order);
                            }
                        }
                    }
                }
            }
        }
//        System.out.println("订单结束提醒完成");
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
        if (room.getStoreId() != storePromotion.getStoreId()) {
            throw new ServiceException("优惠券不适用于该房间");
        }

        if (OfficeEnum.ValidType.START_END_DATE.getCode().equals(storePromotion.getValidType())) { // 有效期区间
            if (new Date().before(storePromotion.getStartDate()) || new Date().after(DateUtils.addDays(storePromotion.getEndDate(), 1))) {
                throw new ServiceException("不在有效期范围内");
            }
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
        tRoomOrder.setEndTime(DateUtils.addHours(prepayReq.getStartTime(), storePromotion.getMaxMinute().intValue()));
        tRoomOrder.setUserId(userId);
        tRoomOrder.setPayType(OfficeEnum.PayType.WX_PAY.getCode());

        // 计算总金额
        BigDecimal totalPrice = storePromotion.getStandardPrice();

        // 计算订单号
        long orderNo = getOrderNo(prepayReq.getRoomId());

        TWxUser wxUser = wxUserService.selectTWxUserById(userId);

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
        List<WxPayUnifiedOrderV3Request.GoodsDetail> goodsDetailList=new ArrayList<>();
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
            jsapiResult = this.wxPayService.createOrderV3(TradeTypeEnum.valueOf("JSAPI"), v3Request);

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
    public int order4Guest(TRoomOrder tRoomOrder) {
        TWxUser qry = new TWxUser();
        qry.setPhone(tRoomOrder.getUserId() + "");
        final List<TWxUser> tWxUsers = wxUserService.selectTWxUserList(qry);
        if (tWxUsers.size() == 0) {
            throw new ServiceException("该手机号还未登录过小程序，未能识别");
        }
        tRoomOrder.setUserId(tWxUsers.get(0).getId());
        tRoomOrder.setPayType(0);//
        tRoomOrder.setStatus(OfficeEnum.RoomOrderStatus.ORDERED.getCode());//
        tRoomOrder.setOrderNo(getOrderNo(tRoomOrder.getRoomId()));
        tRoomOrder.setTotalAmount(new BigDecimal(0));
        tRoomOrder.setPayAmount(new BigDecimal(0));
        tRoomOrder.setWelfareAmount(new BigDecimal(0));
        return tRoomOrderMapper.insertTRoomOrder(tRoomOrder);
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
}
