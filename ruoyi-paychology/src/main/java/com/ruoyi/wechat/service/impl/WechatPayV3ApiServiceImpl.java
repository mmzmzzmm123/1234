package com.ruoyi.wechat.service.impl;

import com.ruoyi.common.enums.GaugeStatus;
import com.ruoyi.common.enums.OrderPayStatus;
import com.ruoyi.common.enums.OrderStatus;
import com.ruoyi.common.utils.IDhelper;
import com.ruoyi.course.constant.CourConstant;
import com.ruoyi.course.domain.CourOrder;
import com.ruoyi.course.service.ICourOrderService;
import com.ruoyi.course.service.ICourUserCourseSectionService;
import com.ruoyi.course.task.OrderCancelTask;
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
import com.ruoyi.wechat.service.WechatPayV3ApiService;
import com.ruoyi.wechat.vo.WechatPayVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;

@Service
@Slf4j
public class WechatPayV3ApiServiceImpl implements WechatPayV3ApiService {

    private static final Integer ORDER_CANCEL_TIME = 30 * 60 * 1000;

    @Resource
    private IPsyUserService psyUserService;

    @Resource
    private ICourOrderService courOrderService;

    @Resource
    private IPsyOrderPayService orderPayService;

    @Resource
    private IPsyOrderService psyOrderService;

    @Resource
    private IPsyConsultPayService psyConsultPayService;

    @Resource
    private IPsyConsultOrderService psyConsultOrderService;

    @Resource
    private IPsyOrderPayService psyOrderPayService;

    @Resource
    private ICourUserCourseSectionService userCourseSectionService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void wechatPay(WechatPayVO wechatPay) {
        OrderCancelTask orderCancelTask = new OrderCancelTask();

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
            orderCancelTask.setOrderId(newCourOrder.getId());
            orderCancelTask.setModule(wechatPay.getModule());

            // TODO: 内部生成支付对象
            PsyOrderPay orderPay = new PsyOrderPay();
            orderPay.setOrderId(newCourOrder.getId());
            orderPay.setPayType(CourConstant.PAY_WAY_WEIXIN); // 微信
            orderPay.setPayStatus(CourConstant.PAY_STATUE_PENDING);
            orderPay.setAmount(wechatPay.getAmount());
            orderPay.setPayId(UUID.randomUUID().toString()); // 当前使用随机生成的支付ID，后续使用第三方支付平台返回的
            orderPayService.insertPsyOrderPay(orderPay);

            Timer timer = new Timer();
            timer.schedule(orderCancelTask, ORDER_CANCEL_TIME);
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
            psyOrder.setCreateBy(psyUserService.selectPsyUserById(wechatPay.getUserId()).getName());

            PsyOrder newPsyOrder = psyOrderService.generatePsyOrder(psyOrder);

            // TODO: 定时将未支付的订单取消任务
            orderCancelTask.setOrderId(newPsyOrder.getId());
            orderCancelTask.setModule(wechatPay.getModule());

            // TODO: 内部生成支付对象
            PsyOrderPay psyOrderPay = PsyOrderPay.builder()
                    .orderId(newPsyOrder.getId())
                    .amount(wechatPay.getAmount())
                    .payStatus(OrderPayStatus.NEED_PAY.getValue())
                    .payId(UUID.randomUUID().toString())
                    .build();
            psyOrderPay.setCreateBy(psyUserService.selectPsyUserById(wechatPay.getUserId()).getName());
            psyOrderPayService.insertPsyOrderPay(psyOrderPay);

            Timer timer = new Timer();
            timer.schedule(orderCancelTask, ORDER_CANCEL_TIME);
        } else if (ConsultConstant.MODULE_CONSULT.equals(wechatPay.getModule())) {
            // 心理咨询服务
            Long id = wechatPay.getOrderId() != null && wechatPay.getOrderId() > 0 ? wechatPay.getOrderId() : IDhelper.getNextId();
            PsyConsultOrderVO psyConsultOrderVO = new PsyConsultOrderVO();
            psyConsultOrderVO.setId(id);
            psyConsultOrderVO.setOrderNo(wechatPay.getOutTradeNo());
            psyConsultOrderVO.setAmount(wechatPay.getAmount());
            psyConsultOrderVO.setWorkId(wechatPay.getWorkId());
            psyConsultOrderVO.setServeId(wechatPay.getServeId());
            psyConsultOrderVO.setUserId(Long.valueOf(wechatPay.getUserId()));

            // 更新原订单
            if (wechatPay.getOrderId() != null && wechatPay.getOrderId() > 0) {
                psyConsultOrderService.updatePayOrder(psyConsultOrderVO);
            } else {
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
        if (outTradeNo.startsWith("COU")) {
            // TODO: 修改订单状态为已完成
            CourOrder courOrder = courOrderService.selectCourOrderByOrderId(outTradeNo);
            courOrder.setStatus(CourConstant.COUR_ORDER_STATUE_FINISHED);
            courOrderService.updateCourOrder(courOrder);

            // TODO: 将用户-课程-章节关系初始化

            userCourseSectionService.initCourUserCourseSection(courOrder.getUserId(), courOrder.getCourseId());

            // TODO: 修改支付对象状态为已支付
//            String payId = res.getString("transaction_id"); // 微信支付系统生成的订单号
            PsyOrderPay orderPay = new PsyOrderPay();
            orderPay.setOrderId(courOrder.getId()); // 订单ID
            orderPay.setPayStatus(CourConstant.PAY_STATUE_PAID);
            orderPay.setPayId(payId);
            orderPayService.updatePsyOrderPayByOrderId(orderPay);
        } else if (outTradeNo.startsWith("GAU")) {
            // TODO: 修改订单状态为已完成
            PsyOrder psyOrder = psyOrderService.selectPsyOrderByOrderId(outTradeNo);
            psyOrder.setOrderStatus(OrderStatus.FINISHED.getValue());
            psyOrderService.updatePsyOrder(psyOrder);

            // TODO: 修改支付对象状态为已支付
//            String payId = res.getString("transaction_id"); // 微信支付系统生成的订单号
            PsyOrderPay orderPay = new PsyOrderPay();
            orderPay.setOrderId(psyOrder.getId()); // 订单ID
            orderPay.setPayStatus(CourConstant.PAY_STATUE_PAID);
            orderPay.setPayId(payId);
            orderPayService.updatePsyOrderPayByOrderId(orderPay);
        } else if (outTradeNo.startsWith("CON")) {
            // TODO: 修改订单状态为已完成
            PsyConsultPay pay = psyConsultPayService.getOneByOrder(outTradeNo);
            pay.setStatus(ConsultConstant.PAY_STATUE_PAID);

            PsyConsultOrderVO psyOrder = psyConsultOrderService.getOne(pay.getOrderId());
            psyOrder.setStatus(ConsultConstant.CONSULT_ORDER_STATUE_PENDING);

            if (psyOrder.getWorkId() > 0) {
                psyOrder.setNum(0);
                psyOrder.setBuyNum(1);
                psyOrder.setStatus(ConsultConstant.CONSULT_ORDER_STATUE_UNCONSULT);
            }

            psyConsultPayService.update(pay);
            psyConsultOrderService.update(psyOrder);
        }
    }
}
