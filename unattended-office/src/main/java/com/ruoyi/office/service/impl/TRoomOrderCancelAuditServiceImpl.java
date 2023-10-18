package com.ruoyi.office.service.impl;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.github.binarywang.wxpay.bean.notify.WxPayRefundNotifyV3Result;
import com.github.binarywang.wxpay.bean.request.WxPayRefundV3Request;
import com.github.binarywang.wxpay.bean.request.WxPayUnifiedOrderV3Request;
import com.github.binarywang.wxpay.bean.result.WxPayRefundV3Result;
import com.github.binarywang.wxpay.bean.result.WxPayUnifiedOrderV3Result;
import com.github.binarywang.wxpay.bean.result.enums.TradeTypeEnum;
import com.github.binarywang.wxpay.config.WxPayConfig;
import com.github.binarywang.wxpay.exception.WxPayException;
import com.github.binarywang.wxpay.service.WxPayService;
import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.office.domain.*;
import com.ruoyi.office.domain.enums.OfficeEnum;
import com.ruoyi.office.domain.vo.RefundAuditVo;
import com.ruoyi.office.service.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruoyi.office.mapper.TRoomOrderCancelAuditMapper;
import org.springframework.transaction.annotation.Transactional;

/**
 * 预约取消审核Service业务层处理
 *
 * @author ruoyi
 * @date 2023-06-15
 */
@Service
public class TRoomOrderCancelAuditServiceImpl extends ServiceImpl<TRoomOrderCancelAuditMapper, TRoomOrderCancelAudit> implements ITRoomOrderCancelAuditService {
    @Autowired
    private TRoomOrderCancelAuditMapper tRoomOrderCancelAuditMapper;

    /**
     * 查询预约取消审核
     *
     * @param id 预约取消审核主键
     * @return 预约取消审核
     */
    @Override
    public TRoomOrderCancelAudit selectTRoomOrderCancelAuditById(Long id) {
        return tRoomOrderCancelAuditMapper.selectTRoomOrderCancelAuditById(id);
    }

    /**
     * 查询预约取消审核列表
     *
     * @param tRoomOrderCancelAudit 预约取消审核
     * @return 预约取消审核
     */
    @Override
    public List<TRoomOrderCancelAudit> selectTRoomOrderCancelAuditList(TRoomOrderCancelAudit tRoomOrderCancelAudit) {
        return tRoomOrderCancelAuditMapper.selectTRoomOrderCancelAuditList(tRoomOrderCancelAudit);
    }

    /**
     * 新增预约取消审核
     *
     * @param tRoomOrderCancelAudit 预约取消审核
     * @return 结果
     */
    @Override
    public int insertTRoomOrderCancelAudit(TRoomOrderCancelAudit tRoomOrderCancelAudit) {
        tRoomOrderCancelAudit.setCreateTime(DateUtils.getNowDate());
        return tRoomOrderCancelAuditMapper.insertTRoomOrderCancelAudit(tRoomOrderCancelAudit);
    }

    /**
     * 修改预约取消审核
     *
     * @param tRoomOrderCancelAudit 预约取消审核
     * @return 结果
     */
    @Override
    public int updateTRoomOrderCancelAudit(TRoomOrderCancelAudit tRoomOrderCancelAudit) {
        tRoomOrderCancelAudit.setUpdateTime(DateUtils.getNowDate());
        return tRoomOrderCancelAuditMapper.updateTRoomOrderCancelAudit(tRoomOrderCancelAudit);
    }

    /**
     * 批量删除预约取消审核
     *
     * @param ids 需要删除的预约取消审核主键
     * @return 结果
     */
    @Override
    public int deleteTRoomOrderCancelAuditByIds(Long[] ids) {
        return tRoomOrderCancelAuditMapper.deleteTRoomOrderCancelAuditByIds(ids);
    }

    /**
     * 删除预约取消审核信息
     *
     * @param id 预约取消审核主键
     * @return 结果
     */
    @Override
    public int deleteTRoomOrderCancelAuditById(Long id) {
        return tRoomOrderCancelAuditMapper.deleteTRoomOrderCancelAuditById(id);
    }

    @Override
    public List<RefundAuditVo> selectCancelAuditInfoList(RefundAuditVo tRoomOrderCancelAudit) {
        return tRoomOrderCancelAuditMapper.selectCancelAuditInfoList(tRoomOrderCancelAudit);
    }

    @Autowired
    private WxPayService wxPayService;

    @Autowired
    ITRoomOrderService roomOrderService;

    @Autowired
    ITWxUserCouponService wxUserCouponService;

    @Autowired
    ITWxUserAmountService wxUserAmountService;

    @Autowired
    ITRoomService roomService;

    @Override
    @Transactional
    public String approve(TRoomOrderCancelAudit cancelAudit) {

        TRoomOrderCancelAudit tRoomOrderCancelAudit = tRoomOrderCancelAuditMapper.selectTRoomOrderCancelAuditById(cancelAudit.getId());

        long roomOrderNo = tRoomOrderCancelAudit.getOrderNo();
        TRoomOrder qry = new TRoomOrder();
        qry.setOrderNo(roomOrderNo);
        final List<TRoomOrder> roomOrders = roomOrderService.selectTRoomOrderList(qry);
        if (roomOrders.size() == 0)
            throw new ServiceException("原支付订单不存在");
        int payType = roomOrders.get(0).getPayType();
        if (payType == OfficeEnum.PayType.CARD_BALANCE_PAY.getCode()) {

            TRoomOrder updateOrder = new TRoomOrder();
            updateOrder.setId(roomOrders.get(0).getId());
            updateOrder.setStatus(OfficeEnum.RoomOrderStatus.CANCEL.getCode());//9); // 已取消
            roomOrderService.updateTRoomOrder(updateOrder);

            //  退还余额账户
            TWxUserAmount wxUserAmount = new TWxUserAmount();
            // 判断用户在商户下的余额是否足够；
            long roomId = roomOrders.get(0).getRoomId();
            TRoom room = roomService.selectTRoomById(roomId);

            wxUserAmount.setUserId(Long.parseLong(room.getCreateBy()));
            wxUserAmount.setWxUserId(roomOrders.get(0).getUserId());
            wxUserAmount.setAmount(roomOrders.get(0).getPayAmount().negate());
//            wxUserAmount.setCashAmount(new BigDecimal(0)); 此功能暂时没用，需要时记得区分充值支付余额与赠送余额分开处理
//            wxUserAmount.setWelfareAmount(new BigDecimal(0));
            // 扣除余额
            wxUserAmountService.minusCardValue(wxUserAmount);

        } else if (payType == OfficeEnum.PayType.COUPON_PAY.getCode()) {
            TRoomOrder updateOrder = new TRoomOrder();
            updateOrder.setId(roomOrders.get(0).getId());
            updateOrder.setStatus(OfficeEnum.RoomOrderStatus.CANCEL.getCode()); // 已取消
            roomOrderService.updateTRoomOrder(updateOrder);

            //  退还优惠券 状态改为未使用
            TWxUserCoupon wxUserCoupon = new TWxUserCoupon();
            wxUserCoupon.setCouponId(roomOrders.get(0).getCouponId());
            wxUserCoupon.setStatus(0l);
            wxUserCouponService.updateTWxUserCoupon(wxUserCoupon);


        } else if (payType == OfficeEnum.PayType.WX_PAY.getCode()) {
            int amt = tRoomOrderCancelAudit.getRefundAmount().multiply(new BigDecimal(1000)).intValue();
            // 计算订单号
            long orderNo = 0l;
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
            final String prefix = sdf.format(new Date());
            Long maxId = tRoomOrderCancelAuditMapper.getDayMaxOrder(prefix);
            if (maxId == null)
                orderNo = Long.parseLong(prefix + "0000");
            else {
                orderNo = maxId + 1;
            }

            WxPayRefundV3Request v3Request = new WxPayRefundV3Request();
            final WxPayConfig config = wxPayService.getConfig();

            v3Request.setOutTradeNo(String.valueOf(tRoomOrderCancelAudit.getOrderNo())).setOutRefundNo(String.valueOf(orderNo))
                    .setNotifyUrl(config.getNotifyUrl() + "/refund")
                    .setReason("用户申请")
                    .setAmount(
                            new WxPayRefundV3Request.Amount() {
                            }.setRefund(amt).setTotal(amt).setCurrency("CNY"));

            WxPayRefundV3Result apiResult = null;
            try {
                apiResult = this.wxPayService.refundV3(v3Request);
                BeanUtils.copyProperties(apiResult, tRoomOrderCancelAudit);
                tRoomOrderCancelAudit.setRefundCreateTime(apiResult.getCreateTime());
                tRoomOrderCancelAudit.setRefundStatus(apiResult.getStatus());
                if (apiResult.getStatus().equalsIgnoreCase("success"))
                    tRoomOrderCancelAudit.setStatus(3l);
                else
                    tRoomOrderCancelAudit.setStatus(1l); // 1 通过 2 退回 3 退款成功
                tRoomOrderCancelAuditMapper.updateTRoomOrderCancelAudit(tRoomOrderCancelAudit);

                TRoomOrder roomOrder = new TRoomOrder();
                roomOrder.setOrderNo(tRoomOrderCancelAudit.getOrderNo());
                roomOrder = roomOrderService.selectTRoomOrderList(roomOrder).get(0);
                TRoomOrder updateOrder = new TRoomOrder();
                updateOrder.setId(roomOrder.getId());
                updateOrder.setStatus(OfficeEnum.RoomOrderStatus.CANCEL.getCode());//9); // 已取消
                roomOrderService.updateTRoomOrder(updateOrder);

            } catch (WxPayException e) {
                log.error("JSAPI 申请退款：" + e.getLocalizedMessage());
                throw new ServiceException("微信退款申请失败");
            }

            if (apiResult != null) {
                switch (apiResult.getStatus()) {
                    case "SUCCESS":
                        return "退款成功";
                    case "CLOSED":
                        return "退款关闭";
                    case "PROCESSING":
                        return "退款处理中";
                    case "ABNORMAL":
                        return "退款异常";
                }
            }
        }


        return "操作失败";
    }

    @Override
    public void refundApproveNotify(WxPayRefundNotifyV3Result.DecryptNotifyResult result) {
        // 修改退款订单 退款状态
        TRoomOrderCancelAudit cancelAudit = new TRoomOrderCancelAudit();
        cancelAudit.setRefundOrderNo(Long.parseLong(result.getOutRefundNo()));
        List<TRoomOrderCancelAudit> tRoomOrderCancelAuditList = tRoomOrderCancelAuditMapper.selectTRoomOrderCancelAuditList(cancelAudit);
        if (tRoomOrderCancelAuditList.size() == 0) {
            throw new ServiceException("未找到该退款单号订单");
        }
        if (tRoomOrderCancelAuditList.get(0).getStatus() == 3)
            return;

        TRoomOrderCancelAudit audit = new TRoomOrderCancelAudit();
        audit.setId(tRoomOrderCancelAuditList.get(0).getId());
        audit.setStatus(3l);
        tRoomOrderCancelAuditMapper.updateTRoomOrderCancelAudit(audit);
    }
}
