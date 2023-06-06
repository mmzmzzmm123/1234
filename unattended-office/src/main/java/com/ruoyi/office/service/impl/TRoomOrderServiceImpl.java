package com.ruoyi.office.service.impl;

import java.math.BigDecimal;
import java.time.LocalTime;
import java.util.Date;
import java.util.List;

import com.alibaba.fastjson2.JSON;
import com.ruoyi.common.config.WxPayConfig;
import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.http.HttpUtils;
import com.ruoyi.office.domain.TRoom;
import com.ruoyi.office.domain.TRoomPrice;
import com.ruoyi.office.domain.TWxUserAmount;
import com.ruoyi.office.domain.vo.*;
import com.ruoyi.office.service.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruoyi.office.mapper.TRoomOrderMapper;
import com.ruoyi.office.domain.TRoomOrder;
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

    @Override
    @Transactional
    public synchronized String orderRoom(GetRoomPriceVo vo, Long userId) {
        TRoomOrder tRoomOrder = new TRoomOrder();
        BeanUtils.copyProperties(vo, tRoomOrder);
        List<TRoomOrder> roomOrders = tRoomOrderMapper.selectConflictRoomPeriod(tRoomOrder);
        if (roomOrders.size() > 0) {
            throw new ServiceException("预定时间段冲突，请刷新预定情况后重试");
        }
        // 计算总金额
        BigDecimal totalPrice = calcPrice(tRoomOrder.getRoomId(), tRoomOrder.getStartTime(), tRoomOrder.getEndTime());

        if (tRoomOrder.getPayType() == 1) { // 直接支付
            // 发起微信支付 预支付交易单，返回微信支付返回的标识.
            WxPayReq payReq = new WxPayReq();
            payReq.setAppid(wxPayConfig.getAppid());
            payReq.setMchid(wxPayConfig.getMchid());
            payReq.setDescription("商品描述");
            payReq.setOut_trade_no("out_trad_no");
            payReq.setNotify_url(wxPayConfig.getNotify_url());
            WxPayAmount wxCallbackAmount = new WxPayAmount();
            wxCallbackAmount.setTotal(totalPrice.intValue() * 100);
            payReq.setAmount(wxCallbackAmount);
            WxPayPayer wxCallbackPayer = new WxPayPayer();
            wxCallbackPayer.setOpenid("xxxxx");
            payReq.setPayer(wxCallbackPayer);
            String prepayId = HttpUtils.sendPost(wxPayConfig.getPostUrl(), JSON.toJSONString(payReq));

            tRoomOrder.setPrepayId(prepayId);
            tRoomOrder.setTotalAmount(totalPrice);
            tRoomOrder.setPayAmount(totalPrice);
            tRoomOrder.setStatus(1l);// 待支付
            tRoomOrder.setCreateTime(DateUtils.getNowDate());
            tRoomOrderMapper.insertTRoomOrder(tRoomOrder);

            return prepayId;

        } else if (tRoomOrder.getPayType() == 2) { // 储值卡余额支付
            // 判断用户在商户下的余额是否足够；
            TWxUserAmount wxUserAmount = new TWxUserAmount();
            long roomId = roomOrders.get(0).getRoomId();
            TRoom room = roomService.selectTRoomById(roomId);

            wxUserAmount.setUserId(Long.parseLong(room.getCreateBy()));
            wxUserAmount.setWxUserId(tRoomOrder.getUserId());
            final List<TWxUserAmount> wxUserAmounts = wxUserAmountService.selectTWxUserAmountList(wxUserAmount);
            if (wxUserAmounts.size() == 0 || wxUserAmounts.get(0).getAmount().compareTo(totalPrice) == -1)
                throw new ServiceException("储值卡余额不够，请充值后使用");

            wxUserAmount.setAmount(totalPrice);
            wxUserAmountService.minusCardValue(wxUserAmount);

            tRoomOrder.setTotalAmount(totalPrice);
            tRoomOrder.setPayAmount(totalPrice);
            tRoomOrder.setStatus(2l);// 已预约
            tRoomOrder.setCreateTime(DateUtils.getNowDate());
            tRoomOrderMapper.insertTRoomOrder(tRoomOrder);

        } else if (tRoomOrder.getPayType() == 4) { // 优惠券支付
            // 1. 是否在有效期  是否适用当前店铺

            // 2. 是否在可用日期 周一 周二 周三...

            // 3. 预约时间是否符合优惠券时间


            BigDecimal couponAmt = new BigDecimal(0);
            if (tRoomOrder.getCouponId() != 0) {
                // 优惠券处理优惠金额
            }

            BigDecimal payAmt = totalPrice.subtract(couponAmt);

            tRoomOrder.setTotalAmount(totalPrice);
            tRoomOrder.setCouponAmount(couponAmt);
            tRoomOrder.setPayAmount(payAmt);
            tRoomOrder.setStatus(2l);// 已预约
            tRoomOrder.setCreateTime(DateUtils.getNowDate());
            tRoomOrderMapper.insertTRoomOrder(tRoomOrder);

        }
        return "";
    }

    // 处理微信支付回调
    @Override
    public String orderRoomWxCallback(WxPayCallback callback) {
        // 解密返回数据

        // 处理订单状态
//        tRoomOrder.setStatus(2l);// 已预约

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
