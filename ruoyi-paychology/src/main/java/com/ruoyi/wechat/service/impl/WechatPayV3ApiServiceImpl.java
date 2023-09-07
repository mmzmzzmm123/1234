package com.ruoyi.wechat.service.impl;

import cn.hutool.core.util.StrUtil;
import com.ruoyi.common.constant.IntegralRecordConstants;
import com.ruoyi.common.constant.PsyConstants;
import com.ruoyi.common.enums.GaugeStatus;
import com.ruoyi.common.enums.OrderPayStatus;
import com.ruoyi.common.enums.OrderStatus;
import com.ruoyi.common.event.publish.IntegralPublisher;
import com.ruoyi.common.utils.IDhelper;
import com.ruoyi.course.constant.CourConstant;
import com.ruoyi.course.domain.CourOrder;
import com.ruoyi.course.service.ICourOrderService;
import com.ruoyi.course.service.ICourUserCourseSectionService;
import com.ruoyi.gauge.constant.GaugeConstant;
import com.ruoyi.gauge.domain.PsyOrder;
import com.ruoyi.gauge.domain.PsyOrderPay;
import com.ruoyi.gauge.service.IPsyOrderPayService;
import com.ruoyi.gauge.service.IPsyOrderService;
import com.ruoyi.psychology.constant.ConsultConstant;
import com.ruoyi.psychology.domain.PsyConsultPay;
import com.ruoyi.psychology.service.IPsyConsultOrderService;
import com.ruoyi.psychology.service.IPsyConsultPayService;
import com.ruoyi.psychology.service.IPsyUserService;
import com.ruoyi.psychology.vo.PsyConsultOrderVO;
import com.ruoyi.user.domain.PsyUserIntegralRecord;
import com.ruoyi.user.service.IPsyUserIntegralRecordService;
import com.ruoyi.wechat.service.WechatPayV3ApiService;
import com.ruoyi.wechat.vo.WechatPayVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.UUID;

//import com.ruoyi.course.task.OrderCancelTask;

@Service
@Slf4j
public class WechatPayV3ApiServiceImpl implements WechatPayV3ApiService {

    private static final Integer ORDER_CANCEL_TIME = 30 * 60 * 1000;

    @Resource
    private IPsyUserService psyUserService;

    @Resource
    private IntegralPublisher integralPublisher;

    @Resource
    private ICourOrderService courOrderService;

    @Resource
    private IPsyOrderPayService orderPayService;

    @Resource
    private IPsyOrderService psyOrderService;

    @Resource
    private IPsyConsultPayService psyConsultPayService;

    @Resource
    private IPsyUserIntegralRecordService psyUserIntegralRecordService;

    @Resource
    private IPsyConsultOrderService psyConsultOrderService;

    @Resource
    private IPsyOrderPayService psyOrderPayService;

    @Resource
    private ICourUserCourseSectionService userCourseSectionService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void wechatPay(WechatPayVO wechatPay) {
//        OrderCancelTask orderCancelTask = new OrderCancelTask();
        String nickName = psyUserService.selectPsyUserById(wechatPay.getUserId()).getName();

        if (CourConstant.MODULE_COURSE.equals(wechatPay.getModule())) {

            // TODO: 内部生成订单
            CourOrder courOrder = new CourOrder();
            courOrder.setOrderId(wechatPay.getOutTradeNo());
            courOrder.setStatus(CourConstant.COUR_ORDER_STATUE_CREATED);
            courOrder.setAmount(wechatPay.getAmount());
            courOrder.setUserId(wechatPay.getUserId());
            courOrder.setCourseId(wechatPay.getCourseId());
            CourOrder newCourOrder = courOrderService.generateCourOrder(courOrder);

            // TODO: 定时将未支付的订单取消任务
//            orderCancelTask.setOrderId(newCourOrder.getId());
//            orderCancelTask.setModule(wechatPay.getModule());

            // TODO: 内部生成支付对象
            PsyOrderPay orderPay = new PsyOrderPay();
            orderPay.setOrderId(newCourOrder.getId());
            orderPay.setPayType(CourConstant.PAY_WAY_WEIXIN); // 微信
            orderPay.setPayStatus(CourConstant.PAY_STATUE_PENDING);
            orderPay.setAmount(wechatPay.getAmount());
            orderPay.setPayId(UUID.randomUUID().toString()); // 当前使用随机生成的支付ID，后续使用第三方支付平台返回的
            orderPayService.insertPsyOrderPay(orderPay);

//            Timer timer = new Timer();
//            timer.schedule(orderCancelTask, ORDER_CANCEL_TIME);
        } else if (GaugeConstant.MODULE_GAUGE.equals(wechatPay.getModule())) {

            // TODO: 内部生成订单
            PsyOrder psyOrder = PsyOrder.builder()
                    .orderId(wechatPay.getOutTradeNo())
                    .amount(wechatPay.getAmount())
                    .orderStatus(OrderStatus.CREATE.getValue())
                    .gaugeStatus(GaugeStatus.UNFINISHED.getValue())
                    .gaugeId(wechatPay.getGaugeId())
                    .userId(wechatPay.getUserId())
                    .build();
            psyOrder.setCreateBy(nickName);

            PsyOrder newPsyOrder = psyOrderService.generatePsyOrder(psyOrder);

            // TODO: 定时将未支付的订单取消任务
//            orderCancelTask.setOrderId(newPsyOrder.getId());
//            orderCancelTask.setModule(wechatPay.getModule());

            // TODO: 内部生成支付对象
            PsyOrderPay psyOrderPay = PsyOrderPay.builder()
                    .orderId(newPsyOrder.getId())
                    .amount(wechatPay.getAmount())
                    .payStatus(OrderPayStatus.NEED_PAY.getValue())
                    .payId(UUID.randomUUID().toString())
                    .build();
            psyOrderPay.setCreateBy(psyUserService.selectPsyUserById(wechatPay.getUserId()).getName());
            psyOrderPayService.insertPsyOrderPay(psyOrderPay);

//            Timer timer = new Timer();
//            timer.schedule(orderCancelTask, ORDER_CANCEL_TIME);
        } else if (ConsultConstant.MODULE_CONSULT.equals(wechatPay.getModule())) {
            // 心理咨询服务
            Long id = wechatPay.getOrderId() != null ? wechatPay.getOrderId() : IDhelper.getNextId();
            PsyConsultOrderVO psyConsultOrderVO = new PsyConsultOrderVO();
            psyConsultOrderVO.setId(id);
            psyConsultOrderVO.setConsultId(wechatPay.getConsultId());
            psyConsultOrderVO.setOrderNo(wechatPay.getOutTradeNo());
            // 创建时才需要更新总价
            // psyConsultOrderVO.setAmount(wechatPay.getAmount());
            psyConsultOrderVO.setPay(wechatPay.getAmount());
            psyConsultOrderVO.setServeId(wechatPay.getServeId());
            psyConsultOrderVO.setUserId(wechatPay.getUserId());
            psyConsultOrderVO.setNickName(nickName);
            psyConsultOrderVO.setWorkId(wechatPay.getWorkId());
            psyConsultOrderVO.setTime(wechatPay.getTime());

            // 更新原订单
            if (wechatPay.getOrderId() != null) {
                psyConsultOrderService.updatePayOrder(psyConsultOrderVO);
            } else {
                psyConsultOrderVO.setAmount(wechatPay.getAmount());
                psyConsultOrderService.add(psyConsultOrderVO);
            }

            // TODO: 定时将未支付的订单取消任务
//            orderCancelTask.setId(id);
//            orderCancelTask.setModule(wechatPay.getModule());

            // TODO: 内部生成支付对象
            PsyConsultPay pay = new PsyConsultPay();
            pay.setOrderId(id);
            pay.setAmount(wechatPay.getAmount());
            pay.setOutTradeNo(wechatPay.getOutTradeNo());
            pay.setPayType(CourConstant.PAY_WAY_WEIXIN); // 微信
            pay.setDelFlag("0");
            pay.setStatus(ConsultConstant.PAY_STATUE_PENDING);
            psyConsultPayService.add(pay);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void wechatPayNotify(String outTradeNo, String payId) {
        PsyUserIntegralRecord record = new PsyUserIntegralRecord();
        record.setIntegral(0);

        if (outTradeNo.startsWith(PsyConstants.ORDER_COURSE)) {
            // TODO: 修改订单状态为已完成
            CourOrder courOrder = courOrderService.selectCourOrderByOrderId(outTradeNo);
            // 判断状态，防止重复更新
            if (CourConstant.COUR_ORDER_STATUE_CREATED == courOrder.getStatus()) {
                courOrder.setStatus(CourConstant.COUR_ORDER_STATUE_FINISHED);
                courOrderService.updateCourOrder(courOrder);

                // TODO: 将用户-课程-章节关系初始化

                userCourseSectionService.initCourUserCourseSection(courOrder.getUserId(), courOrder.getCourseId());

                // TODO: 修改支付对象状态为已支付
                PsyOrderPay orderPay = new PsyOrderPay();
                orderPay.setOrderId(courOrder.getId()); // 订单ID
                orderPay.setPayStatus(CourConstant.PAY_STATUE_PAID);
                orderPay.setPayId(payId);
                orderPayService.updatePsyOrderPayByOrderId(orderPay);

                if (courOrder.getAmount().compareTo(BigDecimal.ZERO) > 0) {
                    record.setLinkId(String.valueOf(courOrder.getId()));
                    record.setLinkType(IntegralRecordConstants.INTEGRAL_RECORD_LINK_TYPE_COURSE);
                    record.setUid(courOrder.getUserId());
                    record.setIntegral(psyUserIntegralRecordService.getIntegral(courOrder.getAmount(), IntegralRecordConstants.INTEGRAL_RECORD_LINK_TYPE_COURSE));
                }
            }
        } else if (outTradeNo.startsWith(PsyConstants.ORDER_GAUGE)) {
            // TODO: 修改订单状态为已完成
            PsyOrder psyOrder = psyOrderService.selectPsyOrderByOrderId(outTradeNo);
            if (OrderStatus.CREATE.getValue() == psyOrder.getOrderStatus()) {
                psyOrder.setOrderStatus(OrderStatus.FINISHED.getValue());
                psyOrderService.updatePsyOrder(psyOrder);

                // TODO: 修改支付对象状态为已支付
                PsyOrderPay orderPay = new PsyOrderPay();
                orderPay.setOrderId(psyOrder.getId()); // 订单ID
                orderPay.setPayStatus(CourConstant.PAY_STATUE_PAID);
                orderPay.setPayId(payId);
                orderPayService.updatePsyOrderPayByOrderId(orderPay);

                if (psyOrder.getAmount().compareTo(BigDecimal.ZERO) > 0) {
                    record.setLinkId(String.valueOf(psyOrder.getId()));
                    record.setLinkType(IntegralRecordConstants.INTEGRAL_RECORD_LINK_TYPE_COURSE);
                    record.setUid(psyOrder.getUserId());
                    record.setIntegral(psyUserIntegralRecordService.getIntegral(psyOrder.getAmount(), IntegralRecordConstants.INTEGRAL_RECORD_LINK_TYPE_COURSE));
                }
            }
        } else if (outTradeNo.startsWith(PsyConstants.ORDER_CONSULT)) {
            // TODO: 修改订单状态为已完成
            PsyConsultPay pay = psyConsultPayService.getOneByOrder(outTradeNo);
            PsyConsultOrderVO psyOrder = psyConsultOrderService.getOne(pay.getOrderId());

            if (ConsultConstant.CONSULT_ORDER_STATUE_CREATED.equals(psyOrder.getStatus())) {
                pay.setPayId(payId);
                pay.setStatus(ConsultConstant.PAY_STATUE_PAID);
                psyOrder.setStatus(ConsultConstant.CONSULT_ORDER_STATUE_PENDING);
                psyOrder.setPayStatus(ConsultConstant.PAY_STATUE_PAID);

                psyConsultPayService.update(pay);
                psyConsultOrderService.wechatPayNotify(psyOrder);

                if (psyOrder.getAmount().compareTo(BigDecimal.ZERO) > 0) {
                    record.setLinkId(String.valueOf(psyOrder.getId()));
                    record.setLinkType(IntegralRecordConstants.INTEGRAL_RECORD_LINK_TYPE_ORDER);
                    record.setUid(psyOrder.getUserId());
                    record.setIntegral(psyUserIntegralRecordService.getIntegral(psyOrder.getAmount(), IntegralRecordConstants.INTEGRAL_RECORD_LINK_TYPE_ORDER));
                }
            }
        }
        // 有积分时候,才赠送积分
        if (record.getIntegral() > 0 && StringUtils.isNotBlank(record.getLinkId())) {
            record.setDelFlag(0);
            record.setFrozenTime(0);
            record.setMark(StrUtil.format("用户付款成功,订单增加{}积分", record.getIntegral()));
            record.setType(IntegralRecordConstants.INTEGRAL_RECORD_TYPE_ADD);
            record.setTitle(IntegralRecordConstants.BROKERAGE_RECORD_TITLE_ORDER);
            record.setStatus(IntegralRecordConstants.INTEGRAL_RECORD_STATUS_COMPLETE);
            integralPublisher.publish(record);
        }
    }
}
