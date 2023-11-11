package com.ruoyi.api.order.service;

import cn.hutool.core.collection.ListUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.ObjectUtil;
import com.github.binarywang.wxpay.bean.result.WxPayRefundResult;
import com.github.binarywang.wxpay.bean.result.WxPayUnifiedOrderResult;
import com.ruoyi.api.order.model.dto.*;
import com.ruoyi.api.order.model.vo.ApiOrderInfoVo;
import com.ruoyi.api.order.model.vo.ApiOrderOfStaffThisWeekVo;
import com.ruoyi.api.payment.model.vo.ApiOrderPayInfoVo;
import com.ruoyi.common.constant.Constants;
import com.ruoyi.common.constant.HttpStatus;
import com.ruoyi.common.constant.RedisKeyConstants;
import com.ruoyi.common.constant.SysTipsConstants;
import com.ruoyi.common.core.redis.RedisCache;
import com.ruoyi.common.enums.*;
import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.common.rocketmq.RocketMqService;
import com.ruoyi.common.rocketmq.constants.MqConstants;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.ServletUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.TokenUtils;
import com.ruoyi.common.utils.bean.BeanUtils;
import com.ruoyi.common.utils.spring.SpringUtils;
import com.ruoyi.common.utils.uuid.IdUtils;
import com.ruoyi.common.weixin.WxService;
import com.ruoyi.common.weixin.model.dto.UnifiedOrderRequestDto;
import com.ruoyi.common.weixin.model.dto.WxPayRefundDto;
import com.ruoyi.common.weixin.model.vo.WxPayResultVo;
import com.ruoyi.distribution.domain.DistributionRelation;
import com.ruoyi.distribution.mapper.DistributionRelationMapper;
import com.ruoyi.order.domain.OrderComment;
import com.ruoyi.order.domain.OrderDetails;
import com.ruoyi.order.domain.OrderInfo;
import com.ruoyi.order.mapper.OrderCommentMapper;
import com.ruoyi.order.mapper.OrderDetailsMapper;
import com.ruoyi.order.mapper.OrderInfoMapper;
import com.ruoyi.payment.domain.PaymentOrder;
import com.ruoyi.payment.domain.PaymentRefund;
import com.ruoyi.payment.mapper.PaymentOrderMapper;
import com.ruoyi.payment.mapper.PaymentRefundMapper;
import com.ruoyi.platform.domain.PlatformGift;
import com.ruoyi.platform.mapper.PlatformGiftMapper;
import com.ruoyi.service.domain.ServiceInfo;
import com.ruoyi.service.domain.ServiceItem;
import com.ruoyi.service.domain.ServiceItemPrice;
import com.ruoyi.service.mapper.ServiceInfoMapper;
import com.ruoyi.service.mapper.ServiceItemMapper;
import com.ruoyi.staff.domain.StaffInfo;
import com.ruoyi.staff.domain.StaffLevelConfig;
import com.ruoyi.staff.mapper.StaffInfoMapper;
import com.ruoyi.staff.mapper.StaffLevelConfigMapper;
import com.ruoyi.user.domain.UserInfo;
import com.ruoyi.user.domain.UserWallet;
import com.ruoyi.user.domain.UserWalletRecord;
import com.ruoyi.user.mapper.UserInfoMapper;
import com.ruoyi.user.mapper.UserWalletMapper;
import com.ruoyi.user.mapper.UserWalletRecordMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Collectors;

/**
 * @author LAM
 * @date 2023/10/10 1:52
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class ApiOrderService {

    private final ReentrantLock lock = new ReentrantLock();
    private final UserWalletMapper userWalletMapper;
    private final UserWalletRecordMapper userWalletRecordMapper;
    private final StaffInfoMapper staffInfoMapper;
    private final StaffLevelConfigMapper staffLevelConfigMapper;
    private final OrderInfoMapper orderInfoMapper;
    private final OrderDetailsMapper orderDetailsMapper;
    private final PlatformGiftMapper giftMapper;
    private final PaymentOrderMapper paymentOrderMapper;
    private final ServiceInfoMapper serviceInfoMapper;
    private final ServiceItemMapper serviceItemMapper;
    private final PaymentRefundMapper paymentRefundMapper;
    private final OrderCommentMapper orderCommentMapper;
    private final UserInfoMapper userInfoMapper;
    private final DistributionRelationMapper distributionRelationMapper;
    private final RedisCache redisCache;
    private final WxService wxService;
    private final RocketMqService rocketMqService;


    /**
     * 礼物赠送订单提交
     *
     * @param dto 表单数据
     * @return 支付数据结果
     */
    @Transactional(rollbackFor = Exception.class)
    public ApiOrderPayInfoVo giftOrderSubmit(ApiGiftOrderFormDto dto) {
        log.info("礼物赠送订单提交：开始，参数：{}", dto);
        ApiOrderPayInfoVo vo = new ApiOrderPayInfoVo();
        // 获取本地的bean实例
        ApiOrderService apiOrderService = SpringUtils.getBean(ApiOrderService.class);

        // 查询礼物信息
        PlatformGift platformGift = giftMapper.selectPlatformGiftById(dto.getGiftId());
        // 查询店员信息
        StaffInfo staffInfo = staffInfoMapper.selectStaffInfoByUserId(dto.getStaffId());
        if (ObjectUtil.isNull(staffInfo)) {
            log.warn("礼物赠送订单提交：失败，无法找到对应的店员信息");
            throw new ServiceException("亲爱的，系统繁忙，请稍后重试", HttpStatus.WARN_WX);
        }
        // 查询店员等级配置信息
        StaffLevelConfig staffLevelConfig = selectStaffLevelInfo(staffInfo.getStaffLevel());
        // 判断基本信息
        if (ObjectUtil.isNull(platformGift) || ObjectUtil.isNull(staffLevelConfig)) {
            log.warn("礼物赠送订单提交：失败，礼物信息/店员等级配置信息为空");
            throw new ServiceException("亲爱的，系统繁忙，请稍后重试", HttpStatus.WARN_WX);
        }
        // 分销关系绑定
        apiOrderService.bindDistributionRelation(dto.getShareUserId());
        // 续单首单结果
        Boolean ifContinuous = ifContinuous(TokenUtils.getUserId(), dto.getStaffId());
        // 金额计算
        BigDecimal payAmount = platformGift.getPrice().multiply(BigDecimal.valueOf(dto.getNum()));
        // 自动取消时间
        String autoCancelTime = DateUtils.rollMinute(5, DateUtils.YYYY_MM_DD_HH_MM_SS);
        // 商品描述
        String description = staffInfo.getNickName() + "收到了打赏礼物[" + platformGift.getName() + "] x" + dto.getNum();
        // 支付状态
        String payState = PayStateEnums.WAIT_PAY.getCode();
        // 封装订单信息
        OrderInfo orderInfo = buildBaseOrderInfo();
        BeanUtils.copyBeanProp(orderInfo, dto);
        orderInfo.setOrderType(OrderTypeEnums.GIFT.getCode())
                .setStaffUserId(dto.getStaffId())
                .setStaffLevel(staffInfo.getStaffLevel())
                .setCommissionRatio(ifContinuous ? staffLevelConfig.getOrderRatio() : staffLevelConfig.getFirstOrderRatio())
                .setIfContinuous(ifContinuous ? SysYesNoEnums.YES.getCode() : SysYesNoEnums.NO.getCode())
                .setAmount(payAmount)
                .setPayAmount(payAmount)
                .setAutoExpireTime(DateUtils.parseDate(autoCancelTime))
                .setAutoFinshTime(DateUtils.parseDate(autoCancelTime));
        // 微信支付
        if (PayWayEnums.WEI_XIN_PAY.getCode().equals(dto.getPayWay())) {
            log.info("礼物赠送订单提交：描述，微信支付，开始微信业务");
            // 开始构建支付数据
            WxPayResultVo wxPayResultVo = buildWxPayResultVo(orderInfo, description);
            buildPayInfo(vo, wxPayResultVo);
            // 开始插入order信息和order详情信息和支付单信息
            orderInfoMapper.insertOrderInfo(orderInfo);
        }
        // 余额支付
        if (PayWayEnums.AMOUNT_PAY.getCode().equals(dto.getPayWay())) {
            log.info("礼物赠送订单提交：描述，余额支付，开始余额业务");
            buildAmountPayOrder(orderInfo);
            // 开始插入order信息和order详情信息和支付单信息
            orderInfoMapper.insertOrderInfo(orderInfo);
            // 余额支付业务
            apiOrderService.balancePay(orderInfo);
            // 修改支付状态为已完成
            payState = PayStateEnums.SUCCESS.getCode();
            log.info("礼物赠送订单提交：余额支付完成，进行下一步业务");
        }
        // 构建并插入orderDetails
        OrderDetails orderDetails = new OrderDetails();
        orderDetails.setOrderId(orderInfo.getId())
                .setState(orderInfo.getOrderState())
                .setGiftId(dto.getGiftId())
                .setDetailsTitle("赠送礼物-" + platformGift.getName())
                .setNum(dto.getNum())
                .setTotalPrice(orderInfo.getPayAmount());
        orderDetailsMapper.insertOrderDetails(orderDetails);
        // 构建并插入支付订单
        Long paymentOrderId = apiOrderService.buildPaymentOrder(orderInfo, description, payState, autoCancelTime);
        // 根据支付类型判断发送什么类型的消息
        if (PayWayEnums.WEI_XIN_PAY.getCode().equals(orderInfo.getPayWay())) {
            log.info("礼物赠送订单提交：描述，支付类型：微信支付，发送订单超时自动取消消息");
            String key = RedisKeyConstants.ORDER_PAY_INFO + orderInfo.getOrderNo();
            redisCache.setCacheObject(key, vo);
            redisCache.expire(key, 40, TimeUnit.MINUTES);
            rocketMqService.delayedSend(MqConstants.TOPIC_ORDER_EXPIRE, paymentOrderId, MqDelayLevelEnums.level_9);
        }
        if (PayWayEnums.AMOUNT_PAY.getCode().equals(orderInfo.getPayWay())) {
            log.info("礼物赠送订单提交：描述，支付类型：余额支付，发送赠送礼物成功消息");
            // 发送mq消息处理赠送礼物后的业务
            rocketMqService.delayedSend(MqConstants.TOPIC_GIVE_GIFT_SUCCESS, orderInfo.getId(), MqDelayLevelEnums.level_2);
        }
        log.info("礼物赠送订单提交：完成，返回数据：{}", vo);
        return vo;
    }

    /**
     * 打赏订单提交
     *
     * @param dto 参数
     * @return 结果
     */
    @Transactional(rollbackFor = Exception.class)
    public ApiOrderPayInfoVo rewardOrderSubmit(ApiRewardOrderFormDto dto) {
        log.info("打赏订单提交：开始，参数：{}", dto);

        ApiOrderPayInfoVo vo = new ApiOrderPayInfoVo();

        // 获取本地的bean实例
        ApiOrderService apiOrderService = SpringUtils.getBean(ApiOrderService.class);
        // 查询店员信息
        StaffInfo staffInfo = staffInfoMapper.selectStaffInfoByUserId(dto.getStaffId());
        if (ObjectUtil.isNull(staffInfo)) {
            log.warn("打赏订单提交：失败，无法找到对应的店员信息");
            throw new ServiceException("亲爱的，系统繁忙，请稍后重试", HttpStatus.WARN_WX);
        }
        // 查询店员等级配置信息
        StaffLevelConfig staffLevelConfig = selectStaffLevelInfo(staffInfo.getStaffLevel());
        if (ObjectUtil.isNull(staffLevelConfig)) {
            log.warn("打赏订单提交：失败，无法找到对应店员等级配置信息");
            throw new ServiceException("亲爱的，系统繁忙，请稍后重试", HttpStatus.WARN_WX);
        }
        // 分销关系绑定
        apiOrderService.bindDistributionRelation(dto.getShareUserId());
        // 当前用户标识
        Long userId = TokenUtils.getUserId();
        // 自动取消时间
        String autoCancelTime = DateUtils.rollMinute(5, DateUtils.YYYY_MM_DD_HH_MM_SS);
        // 续单首单结果
        Boolean ifContinuous = ifContinuous(userId, dto.getStaffId());
        // 商品描述
        String description = staffInfo.getNickName() + "收到打赏枫叶币 " + dto.getAmount() + " 个";
        // 支付状态
        String payState = PayStateEnums.WAIT_PAY.getCode();
        // 构建订单数据
        OrderInfo orderInfo = buildBaseOrderInfo();
        BeanUtils.copyBeanProp(orderInfo, dto);
        orderInfo.setOrderType(OrderTypeEnums.REWARD.getCode())
                .setIfContinuous(ifContinuous ? SysYesNoEnums.YES.getCode() : SysYesNoEnums.NO.getCode())
                .setStaffUserId(dto.getStaffId())
                .setStaffLevel(staffInfo.getStaffLevel())
                .setCommissionRatio(ifContinuous ? staffLevelConfig.getOrderRatio() : staffLevelConfig.getFirstOrderRatio())
                .setPayAmount(dto.getAmount())
                .setAutoExpireTime(DateUtils.parseDate(autoCancelTime))
                .setAutoFinshTime(DateUtils.parseDate(autoCancelTime));
        // 根据支付方式进行对应业务
        // 微信支付
        if (PayWayEnums.WEI_XIN_PAY.getCode().equals(orderInfo.getPayWay())) {
            log.info("打赏订单提交：描述，微信支付，开始微信业务");
            // 开始构建支付数据
            WxPayResultVo wxPayResultVo = buildWxPayResultVo(orderInfo, description);
            buildPayInfo(vo, wxPayResultVo);
            // 开始插入order信息和order详情信息和支付单信息
            orderInfoMapper.insertOrderInfo(orderInfo);
        }
        // 余额支付
        if (PayWayEnums.AMOUNT_PAY.getCode().equals(orderInfo.getPayWay())) {
            log.info("打赏订单提交：描述，余额支付，开始余额业务");
            buildAmountPayOrder(orderInfo);
            // 开始插入order信息和order详情信息和支付单信息
            orderInfoMapper.insertOrderInfo(orderInfo);
            // 余额支付业务
            apiOrderService.balancePay(orderInfo);
            // 修改支付订单的状态为已完成
            payState = PayStateEnums.SUCCESS.getCode();
            log.info("打赏订单提交：余额支付完成，进行下一步业务");
        }
        // 构建并插入orderDetails
        OrderDetails orderDetails = new OrderDetails();
        orderDetails.setOrderId(orderInfo.getId())
                .setState(orderInfo.getOrderState())
                .setDetailsTitle("打赏-" + orderInfo.getPayAmount() + "个枫叶币")
                .setNum(Long.parseLong("1"))
                .setTotalPrice(orderInfo.getPayAmount());
        orderDetailsMapper.insertOrderDetails(orderDetails);
        // 构建并插入支付订单
        Long paymentOrderId = apiOrderService.buildPaymentOrder(orderInfo, description, payState, autoCancelTime);
        // 根据支付类型判断发送什么类型的消息
        if (PayWayEnums.AMOUNT_PAY.getCode().equals(orderInfo.getPayWay())) {
            log.info("打赏订单提交：描述，支付类型：余额支付，发送赠送礼物成功消息");
            // 发送mq消息处理赠送礼物后的业务
            rocketMqService.delayedSend(MqConstants.TOPIC_REWARD_SUCCESS, orderInfo.getId(), MqDelayLevelEnums.level_2);
        }
        if (PayWayEnums.WEI_XIN_PAY.getCode().equals(orderInfo.getPayWay())) {
            log.info("打赏订单提交：描述，支付类型：微信支付，发送订单超时自动取消消息");
            // 发送延时消息支付订单过期（五分钟）
            String key = RedisKeyConstants.ORDER_PAY_INFO + orderInfo.getOrderNo();
            redisCache.setCacheObject(key, vo);
            redisCache.expire(key, 40, TimeUnit.MINUTES);
            rocketMqService.delayedSend(MqConstants.TOPIC_ORDER_EXPIRE, paymentOrderId, MqDelayLevelEnums.level_9);
        }
        log.info("打赏订单提交：完成，返回数据：{}", vo);
        return vo;
    }

    /**
     * 随机单提交
     *
     * @param dto 表单数据
     * @return 结果
     */
    public ApiOrderPayInfoVo randomOrderSubmit(ApiRandomOrderFormDto dto) {
        log.info("随机单提交：开始，参数：{}", dto);
        ApiOrderPayInfoVo vo = new ApiOrderPayInfoVo();
        // 获取本地的bean实例
        ApiOrderService apiOrderService = SpringUtils.getBean(ApiOrderService.class);
        // 查询店员等级配置信息
        StaffLevelConfig staffLevelConfig = selectStaffLevelInfo(dto.getStaffLevel());
        if (ObjectUtil.isNull(staffLevelConfig)) {
            log.warn("随机单提交：失败，无法找到对应店员等级配置信息");
            throw new ServiceException("亲爱的，系统繁忙，请稍后重试", HttpStatus.WARN_WX);
        }
        // 查询服务信息
        ServiceInfo serviceInfo = serviceInfoMapper.selectServiceInfoById(dto.getServiceId());
        if (ObjectUtil.isNull(serviceInfo)) {
            log.warn("随机单提交：失败，服务信息为空");
            throw new ServiceException("亲爱的，系统繁忙，请稍后重试", HttpStatus.WARN_WX);
        }
        ServiceItem serviceItem = serviceItemMapper.selectServiceItemById(dto.getServiceItemId());
        if (ObjectUtil.isNull(serviceItem)) {
            log.warn("随机单提交：失败，服务子项目为空");
            throw new ServiceException("亲爱的，系统繁忙，请稍后重试", HttpStatus.WARN_WX);
        }
        ServiceItemPrice serviceItemPrice = serviceItem.getServiceItemPriceList().stream().filter(item -> item.getStaffLevelConfigId().equals(staffLevelConfig.getId())).findFirst().orElse(null);
        if (ObjectUtil.isNull(serviceItemPrice)) {
            log.warn("随机单提交：失败，服务子项目对应等级价格信息为空");
            throw new ServiceException("亲爱的，系统繁忙，请稍后重试", HttpStatus.WARN_WX);
        }
        // 分销关系绑定
        apiOrderService.bindDistributionRelation(dto.getShareUserId());
        // 自动取消时间
        String autoCancelTime = DateUtils.rollMinute(5, DateUtils.YYYY_MM_DD_HH_MM_SS);
        // 订单金额
        BigDecimal orderAmount = serviceItemPrice.getPrice().multiply(BigDecimal.valueOf(dto.getNum()));
        // 构建订单信息
        OrderInfo orderInfo = buildBaseOrderInfo();
        BeanUtils.copyBeanProp(orderInfo, dto);
        orderInfo.setOrderType(OrderTypeEnums.RANDOM.getCode())
                .setAmount(orderAmount)
                .setPayAmount(orderAmount)
                .setAutoExpireTime(DateUtils.parseDate(autoCancelTime));
        // 订单描述
        String description = "随机订单：" + serviceInfo.getName() + "-" + serviceItem.getName();
        // 支付和订单状态（主要处理余额支付的）
        String payState = PayStateEnums.WAIT_PAY.getCode();
        String orderState = OrderStateEnums.WAIT_PAY.getCode();
        // 微信支付
        if (PayWayEnums.WEI_XIN_PAY.getCode().equals(orderInfo.getPayWay())) {
            log.info("随机单提交：描述，微信支付，开始微信业务");
            // 开始构建支付数据
            WxPayResultVo wxPayResultVo = buildWxPayResultVo(orderInfo, description);
            buildPayInfo(vo, wxPayResultVo);
            // 开始插入order信息和order详情信息和支付单信息
            orderInfoMapper.insertOrderInfo(orderInfo);
        }
        // 余额支付
        if (PayWayEnums.AMOUNT_PAY.getCode().equals(orderInfo.getPayWay())) {
            log.info("随机单提交：描述，余额支付，开始余额业务");
            orderInfo.setOrderState(OrderStateEnums.PAID.getCode());
            // 开始插入order信息和order详情信息和支付单信息
            orderInfoMapper.insertOrderInfo(orderInfo);
            // 余额支付业务
            apiOrderService.balancePay(orderInfo);
            // 修改支付订单的状态为已完成
            payState = PayStateEnums.SUCCESS.getCode();
            orderState = OrderStateEnums.FINISH.getCode();
            log.info("随机单提交：余额支付完成，进行下一步业务");
        }
        // 构建并插入orderDetails
        OrderDetails orderDetails = new OrderDetails();
        orderDetails.setOrderId(orderInfo.getId())
                .setServiceItemId(dto.getServiceItemId())
                .setServiceId(dto.getServiceId())
                .setStaffLevelConfigId(staffLevelConfig.getId())
                .setState(orderState)
                .setDetailsTitle(serviceInfo.getName() + "-" + serviceItem.getName())
                .setNum(dto.getNum())
                .setTotalPrice(orderInfo.getPayAmount());
        orderDetailsMapper.insertOrderDetails(orderDetails);
        // 构建并插入支付订单
        Long paymentOrderId = apiOrderService.buildPaymentOrder(orderInfo, description, payState, autoCancelTime);
        // 根据支付类型判断发送什么类型的消息
        if (PayWayEnums.AMOUNT_PAY.getCode().equals(orderInfo.getPayWay())) {
            log.info("随机单提交：描述，支付类型：余额支付，发送指定下单成功消息");
            // 随机单支付成功后通知客户与店员
            rocketMqService.delayedSend(MqConstants.TOPIC_RANDOM_ORDER_SUCCESS_NOTICE, orderInfo.getId(), MqDelayLevelEnums.level_2);
            // 发送延时消息自动退单（5分钟）
            rocketMqService.delayedSend(MqConstants.TOPIC_ORDER_AUTO_CANCEL, orderInfo.getId(), MqDelayLevelEnums.level_9);
        }
        if (PayWayEnums.WEI_XIN_PAY.getCode().equals(orderInfo.getPayWay())) {
            log.info("随机单提交：描述，支付类型：微信支付，发送订单超时自动取消消息");
            String key = RedisKeyConstants.ORDER_PAY_INFO + orderInfo.getOrderNo();
            redisCache.setCacheObject(key, vo);
            redisCache.expire(key, 40, TimeUnit.MINUTES);
            rocketMqService.delayedSend(MqConstants.TOPIC_ORDER_EXPIRE, paymentOrderId, MqDelayLevelEnums.level_16);
        }
        log.info("随机单提交：完成，返回数据：{}", vo);
        return vo;
    }

    /**
     * 指定单提交
     *
     * @param dto 表单
     * @return 结果
     */
    @Transactional(rollbackFor = Exception.class)
    public ApiOrderPayInfoVo appointOrderSubmit(ApiAppointOrderFormDto dto) {
        log.info("指定单提交：开始，参数：{}", dto);
        ApiOrderPayInfoVo vo = new ApiOrderPayInfoVo();
        // 获取本地的bean实例
        ApiOrderService apiOrderService = SpringUtils.getBean(ApiOrderService.class);
        // 查询店员信息
        StaffInfo staffInfo = staffInfoMapper.selectStaffInfoByUserId(dto.getStaffId());
        if (ObjectUtil.isNull(staffInfo)) {
            log.warn("指定单提交：失败，无法找到对应的店员信息");
            throw new ServiceException("亲爱的，系统繁忙，请稍后重试", HttpStatus.WARN_WX);
        }
        // 判断店员是否离线
        if(SysYesNoEnums.NO.getCode().equals(staffInfo.getIfOnline())){
            log.warn("指定单提交：失败，店员离线无法下单");
            throw new ServiceException("亲爱的，店员离线中，暂无法下单哟", HttpStatus.WARN_WX);
        }
        // 查询店员等级配置信息
        StaffLevelConfig staffLevelConfig = selectStaffLevelInfo(staffInfo.getStaffLevel());
        if (ObjectUtil.isNull(staffLevelConfig)) {
            log.warn("指定单提交：失败，无法找到对应店员等级配置信息");
            throw new ServiceException("亲爱的，系统繁忙，请稍后重试", HttpStatus.WARN_WX);
        }
        // 查询服务信息
        ServiceInfo serviceInfo = serviceInfoMapper.selectServiceInfoById(dto.getServiceId());
        if (ObjectUtil.isNull(serviceInfo)) {
            log.warn("指定单提交：失败，服务信息为空");
            throw new ServiceException("亲爱的，系统繁忙，请稍后重试", HttpStatus.WARN_WX);
        }
        ServiceItem serviceItem = serviceItemMapper.selectServiceItemById(dto.getServiceItemId());
        if (ObjectUtil.isNull(serviceItem)) {
            log.warn("指定单提交：失败，服务子项目为空");
            throw new ServiceException("亲爱的，系统繁忙，请稍后重试", HttpStatus.WARN_WX);
        }
        ServiceItemPrice serviceItemPrice = serviceItem.getServiceItemPriceList().stream().filter(item -> item.getStaffLevelConfigId().equals(staffLevelConfig.getId())).findFirst().orElse(null);
        if (ObjectUtil.isNull(serviceItemPrice)) {
            log.warn("指定单提交：失败，服务子项目对应等级价格信息为空");
            throw new ServiceException("亲爱的，系统繁忙，请稍后重试", HttpStatus.WARN_WX);
        }
        // 计算一下订单金额
        BigDecimal orderAmount = serviceItemPrice.getPrice().multiply(BigDecimal.valueOf(dto.getNum()));
        // 判断与前台的订单金额是否一致，不一致需要提醒
        if (orderAmount.compareTo(dto.getAmount()) != 0) {
            log.warn("指定单提交：失败，订单金额与前台传入不一致，需提醒客户");
            throw new ServiceException("亲爱的用户，当前提交的订单金额与系统计算金额不一致，提交订单金额为：" + dto.getAmount() + "，系统计算金额为：" + orderAmount + "，请刷新重试，或咨询客服进行解决，感谢您的支持！", HttpStatus.WARN_WX);
        }
        // 分销关系绑定
        apiOrderService.bindDistributionRelation(dto.getShareUserId());
        // 当前用户标识
        Long userId = TokenUtils.getUserId();
        // 续单首单结果
        Boolean ifContinuous = ifContinuous(userId, dto.getStaffId());
        // 自动取消时间
        String autoCancelTime = DateUtils.rollMinute(5, DateUtils.YYYY_MM_DD_HH_MM_SS);
        // 商品描述
        String description = "指定单：" + serviceInfo.getName() + "-" + serviceItem.getName();
        // 支付和订单状态（主要处理余额支付的）
        String payState = PayStateEnums.WAIT_PAY.getCode();
        String orderState = OrderStateEnums.WAIT_PAY.getCode();
        // 构建订单数据
        OrderInfo orderInfo = buildBaseOrderInfo();
        BeanUtils.copyBeanProp(orderInfo, dto);
        orderInfo.setOrderType(OrderTypeEnums.APPOINT.getCode())
                .setAmount(orderAmount)
                .setIfContinuous(ifContinuous ? SysYesNoEnums.YES.getCode() : SysYesNoEnums.NO.getCode())
                .setStaffUserId(dto.getStaffId())
                .setStaffLevel(staffInfo.getStaffLevel())
                .setCommissionRatio(ifContinuous ? staffLevelConfig.getOrderRatio() : staffLevelConfig.getFirstOrderRatio())
                .setPayAmount(orderAmount)
                .setAutoExpireTime(DateUtils.parseDate(autoCancelTime));
        // 根据支付方式进行对应业务
        // 微信支付
        if (PayWayEnums.WEI_XIN_PAY.getCode().equals(orderInfo.getPayWay())) {
            log.info("指定单提交：描述，微信支付，开始微信业务");
            // 开始构建支付数据
            WxPayResultVo wxPayResultVo = buildWxPayResultVo(orderInfo, description);
            buildPayInfo(vo, wxPayResultVo);
            // 开始插入order信息和order详情信息和支付单信息
            orderInfoMapper.insertOrderInfo(orderInfo);
        }
        // 余额支付
        if (PayWayEnums.AMOUNT_PAY.getCode().equals(orderInfo.getPayWay())) {
            log.info("指定单提交：描述，余额支付，开始余额业务");
            orderInfo.setOrderState(OrderStateEnums.PAID.getCode());
            // 开始插入order信息和order详情信息和支付单信息
            orderInfoMapper.insertOrderInfo(orderInfo);
            // 余额支付业务
            apiOrderService.balancePay(orderInfo);
            // 修改支付订单的状态为已完成
            payState = PayStateEnums.SUCCESS.getCode();
            orderState = OrderStateEnums.FINISH.getCode();
            log.info("指定单提交：余额支付完成，进行下一步业务");
        }
        // 构建并插入orderDetails
        OrderDetails orderDetails = new OrderDetails();
        orderDetails.setOrderId(orderInfo.getId())
                .setServiceId(dto.getServiceId())
                .setServiceItemId(dto.getServiceItemId())
                .setStaffLevelConfigId(staffLevelConfig.getId())
                .setState(orderState)
                .setDetailsTitle(serviceInfo.getName() + "-" + serviceItem.getName())
                .setNum(dto.getNum())
                .setTotalPrice(orderInfo.getPayAmount());
        orderDetailsMapper.insertOrderDetails(orderDetails);
        // 构建并插入支付订单
        Long paymentOrderId = apiOrderService.buildPaymentOrder(orderInfo, description, payState, autoCancelTime);
        // 根据支付类型判断发送什么类型的消息
        if (PayWayEnums.AMOUNT_PAY.getCode().equals(orderInfo.getPayWay())) {
            log.info("指定单提交：描述，支付类型：余额支付，发送指定下单成功消息");
            // 发送订阅消息通知客户和店员
            rocketMqService.delayedSend(MqConstants.TOPIC_APPOINT_ORDER_SUCCESS_NOTICE, orderInfo.getId(), MqDelayLevelEnums.level_2);
            // 发送延时消息自动退单（5分钟）
            rocketMqService.delayedSend(MqConstants.TOPIC_ORDER_AUTO_CANCEL, orderInfo.getId(), MqDelayLevelEnums.level_9);
        }
        if (PayWayEnums.WEI_XIN_PAY.getCode().equals(orderInfo.getPayWay())) {
            log.info("指定单提交：描述，支付类型：微信支付，发送订单超时自动取消消息");
            String key = RedisKeyConstants.ORDER_PAY_INFO + orderInfo.getOrderNo();
            redisCache.setCacheObject(key, vo);
            redisCache.expire(key, 40, TimeUnit.MINUTES);
            rocketMqService.delayedSend(MqConstants.TOPIC_ORDER_EXPIRE, paymentOrderId, MqDelayLevelEnums.level_16);
        }
        log.info("指定单提交：完成，返回数据：{}", vo);
        return vo;
    }

    /**
     * 绑定分销关系
     *
     * @param shareUserId 分享者
     */
    @Transactional(rollbackFor = Exception.class)
    public void bindDistributionRelation(Long shareUserId) {
        log.info("分销关系绑定：开始，参数：{}", shareUserId);
        if (ObjectUtil.isNotNull(shareUserId)) {
            Long userId = TokenUtils.getUserId();
            if (userId.equals(shareUserId)){
                log.warn("分销关系绑定：完成，自己不允许绑定自己");
                return;
            }
            // 判断该子用户是否已经绑定关系
            DistributionRelation selectOneLevel = new DistributionRelation();
            selectOneLevel.setLevel(Long.parseLong(DistributionLevelEnums.ONE_LEVEL.getCode()))
                    .setCId(userId);
            if (ObjectUtil.isNotEmpty(distributionRelationMapper.selectDistributionRelationList(selectOneLevel))) {
                log.warn("分销关系绑定：完成，该用户已绑定关系");
                return;
            }
            List<Long> userIdList = new ArrayList<>();
            Date now = DateUtils.getNowDate();
            // 开始绑定一级分销数据
            DistributionRelation insertOneLevelDistribution = new DistributionRelation();
            insertOneLevelDistribution.setPId(shareUserId)
                    .setCId(userId)
                    .setLevel(Long.parseLong(DistributionLevelEnums.ONE_LEVEL.getCode()))
                    .setCreateTime(now);
            distributionRelationMapper.insertDistributionRelation(insertOneLevelDistribution);
            userIdList.add(insertOneLevelDistribution.getPId());
            // 开始处理二级分销数据是否需要绑定
            DistributionRelation selectTwoLevel = new DistributionRelation();
            selectTwoLevel.setCId(shareUserId)
                    .setLevel(Long.parseLong(DistributionLevelEnums.ONE_LEVEL.getCode()));
            List<DistributionRelation> twoDistributionRelations = distributionRelationMapper.selectDistributionRelationList(selectTwoLevel);
            if (ObjectUtil.isNotEmpty(twoDistributionRelations)) {
                DistributionRelation twoDistributionRelation = twoDistributionRelations.get(0);
                // 开始构建二级分销数据
                DistributionRelation insertTwoLevelDistribution = new DistributionRelation();
                insertTwoLevelDistribution.setLevel(Long.parseLong(DistributionLevelEnums.TWO_LEVEL.getCode()))
                        .setPId(twoDistributionRelation.getPId())
                        .setCId(userId)
                        .setCreateTime(now);
                distributionRelationMapper.insertDistributionRelation(insertTwoLevelDistribution);
                userIdList.add(insertTwoLevelDistribution.getPId());
            }
            // 发送绑定成功通知
            if (ObjectUtil.isNotEmpty(userIdList)) {
                rocketMqService.asyncSend(MqConstants.TOPIC_DISTRIBUTION_RELATION_BIND_NOTICE, userIdList);
            }
        }
        log.info("分销关系绑定：完成");
    }

    /**
     * 余额支付构建固定的数据（仅支持打赏和送礼）
     *
     * @param orderInfo 订单信息
     */
    private void buildAmountPayOrder(OrderInfo orderInfo) {
        Date now = DateUtils.getNowDate();
        orderInfo.setOrderState(OrderStateEnums.FINISH.getCode())
                .setOrderReceivingTime(now)
                .setOrderServiceTime(now)
                .setOrderFinshTime(now);
    }

    /**
     * 构建微信支付返回数据
     *
     * @param vo            数据
     * @param wxPayResultVo 微信支付下单返回数据
     */
    private void buildPayInfo(ApiOrderPayInfoVo vo, WxPayResultVo wxPayResultVo) {
        vo.setPackageValue(wxPayResultVo.getPrepayId())
                .setTimeStamp(wxPayResultVo.getTimeStamp())
                .setNonceStr(wxPayResultVo.getNonceStr())
                .setSignType(wxPayResultVo.getSignType())
                .setPaySign(wxPayResultVo.getPaySign());
    }

    /**
     * 查询店员当前等级配置信息
     *
     * @param level 等级
     * @return 结果
     */
    private StaffLevelConfig selectStaffLevelInfo(Long level) {
        StaffLevelConfig selectLevelConfig = new StaffLevelConfig();
        selectLevelConfig.setLevel(level);
        List<StaffLevelConfig> staffLevelConfigs = staffLevelConfigMapper.selectStaffLevelConfigList(selectLevelConfig);
        return staffLevelConfigs.stream().findFirst().orElse(null);
    }


    /**
     * 构建并插入支付订单记录
     *
     * @param orderInfo      订单信息
     * @param description    订单描述
     * @param payState       支付状态
     * @param autoCancelTime 自动取消时间字符串
     */
    public Long buildPaymentOrder(OrderInfo orderInfo, String description, String payState, String autoCancelTime) {
        log.info("构建并插入支付订单记录：开始");
        Date now = DateUtils.getNowDate();
        OrderTypeEnums orderTypeEnums = OrderTypeEnums.getByCode(orderInfo.getOrderType());
        PaymentOrder paymentOrder = new PaymentOrder();
        paymentOrder.setOrderType(orderInfo.getOrderType())
                .setUserId(orderInfo.getCustomUserId())
                .setOrderId(orderInfo.getId())
                .setOutTradeNo(orderInfo.getOrderNo())
                .setPayWay(orderInfo.getPayWay())
                .setSubject(orderTypeEnums.getDesc())
                .setBody(description)
                .setAmount(orderInfo.getPayAmount())
                .setPayState(payState)
                .setUserIp(ServletUtils.getClientIp())
                .setExpireTime(DateUtils.parseDate(autoCancelTime))
                .setCreateTime(now);
        // 支付完成时间
        if (PayWayEnums.AMOUNT_PAY.getCode().equals(orderInfo.getPayWay())) {
            paymentOrder.setSuccessTime(now);
        }
        paymentOrderMapper.insertPaymentOrder(paymentOrder);
        log.info("构建并插入支付订单记录：完成");
        return paymentOrder.getId();
    }

    /**
     * 构建统一下单数据并请求
     *
     * @param orderInfo   订单信息
     * @param description 订单描述
     * @return 结果
     */
    private WxPayResultVo buildWxPayResultVo(OrderInfo orderInfo, String description) {
        log.info("构建统一下单数据并请求：开始，参数：{}，{}", orderInfo, description);
        WxPayResultVo wxPayResultVo = null;
        try {
            OrderTypeEnums orderTypeEnums = OrderTypeEnums.getByCode(orderInfo.getOrderType());
            UnifiedOrderRequestDto unifiedOrderRequestDto = new UnifiedOrderRequestDto();
            unifiedOrderRequestDto.setOpenid(TokenUtils.getOpenId())
                    .setOutTradeNo(orderInfo.getOrderNo())
                    .setAmount(Integer.valueOf(orderInfo.getPayAmount().multiply(new BigDecimal(100)).setScale(0, RoundingMode.DOWN).toString()))
                    .setTimeExpire(DateUtils.rollMinute(5, DateUtils.YYYYMMDDHHMMSS))
                    .setPayerClientIp(ServletUtils.getClientIp())
                    .setBody(orderTypeEnums.getDesc())
                    .setDescription(description);
            // 开始构建支付数据
            WxPayUnifiedOrderResult wxPayUnifiedOrderResult = wxService.unifiedOrder(unifiedOrderRequestDto);
            if (!Constants.PAY_SUCCESS.equals(wxPayUnifiedOrderResult.getReturnCode())) {
                log.warn("礼物赠送订单提交：失败，无法请求微信支付，返回数据：{}", wxPayUnifiedOrderResult);
                throw new ServiceException("赠送礼物失败，请稍后重试", HttpStatus.WARN_WX);
            }
            wxPayResultVo = wxService.handleWxPayResult(wxPayUnifiedOrderResult);
        } catch (Exception e) {
            log.warn("构建统一下单数据并请求：异常，异常信息：{}", e.getMessage());
            e.printStackTrace();
            throw new ServiceException("亲爱的，系统繁忙，请稍后重试", HttpStatus.WARN_WX);
        }
        log.info("构建统一下单数据并请求：完成，返回数据：{}", wxPayResultVo);
        return wxPayResultVo;
    }

    /**
     * 余额支付业务
     */
    @Transactional(rollbackFor = Exception.class)
    public void balancePay(OrderInfo orderInfo) {
        log.info("余额支付业务：开始，订单数据：{}", orderInfo);
        // 开始判断用户是否拥有足够余额支付
        UserWallet userWallet = userWalletMapper.selectByUserIdForUpdate(orderInfo.getCustomUserId());
        if (ObjectUtil.isNull(userWallet)) {
            log.warn("余额支付业务：失败，获取用户钱包数据失败");
            throw new ServiceException("亲爱的，系统繁忙，请稍后重试", HttpStatus.WARN_WX);
        }
        // 客户总余额
        BigDecimal userTotalBalance = userWallet.getBalance().add(userWallet.getGiftBalance());
        if (userTotalBalance.compareTo(orderInfo.getPayAmount()) <= -1) {
            log.warn("余额支付业务：失败，检测到用户余额不足本次抵扣，总余额：{}，支付金额：{}", userTotalBalance, orderInfo.getPayAmount());
            throw new ServiceException("亲爱的，余额不足本次抵扣，请充值后再支付哟", HttpStatus.WARN_WX);
        }
        // 开始扣减用户余额，先扣减实际充值余额再减扣赠送余额
        BigDecimal newBalance = userWallet.getBalance().subtract(orderInfo.getPayAmount());
        if (newBalance.compareTo(BigDecimal.ZERO) <= -1) {
            newBalance = userWallet.getGiftBalance().subtract(newBalance.abs());
            userWallet.setBalance(BigDecimal.ZERO)
                    .setGiftBalance(newBalance);
        } else {
            userWallet.setBalance(newBalance);
        }
        userWallet.setUpdateTime(DateUtils.getNowDate());
        userWalletMapper.updateUserWallet(userWallet);
        // 开始构建用户钱包记录数据
        // 根据订单类型生成余额记录类型
        String recordType = UserWalletRecordTypeEnums.ORDER.getCode();
        OrderTypeEnums orderTypeEnums = OrderTypeEnums.getByCode(orderInfo.getOrderType());
        switch (Objects.requireNonNull(orderTypeEnums)) {
            case RECHARGE:
                recordType = UserWalletRecordTypeEnums.RECHARGE.getCode();
                break;
            case GIFT:
                recordType = UserWalletRecordTypeEnums.GIFT.getCode();
                break;
            case REWARD:
                recordType = UserWalletRecordTypeEnums.REWARD.getCode();
                break;
            default:
                recordType = UserWalletRecordTypeEnums.ORDER.getCode();
                break;
        }
        UserWalletRecord insertUwr = new UserWalletRecord();
        insertUwr.setWalletRecordType(recordType)
                .setUserId(userWallet.getUserId())
                .setRechargeRecordId(orderInfo.getId())
                .setAmount(orderInfo.getPayAmount())
                .setAmountBefore(userTotalBalance)
                .setAmountAfter(userWallet.getBalance().add(userWallet.getGiftBalance()))
                .setCreateTime(DateUtils.getNowDate());
        userWalletRecordMapper.insertUserWalletRecord(insertUwr);
        log.info("余额支付业务：完成");
    }

    /**
     * 是否续单判断
     *
     * @return false 首单， true 续单
     */
    private Boolean ifContinuous(Long userId, Long staffId) {
        String[] stateArr = {OrderStateEnums.SERVICE_ING.getCode(), OrderStateEnums.FINISH.getCode()};
        List<Long> orderIdList = orderInfoMapper.selectIdByUserIdAndStaffUserIdAndStateList(ListUtil.toList(stateArr), userId, staffId);
        return ObjectUtil.isNotEmpty(orderIdList);
    }

    /**
     * 构建基础的order信息
     *
     * @return 结果
     */
    private OrderInfo buildBaseOrderInfo() {
        Date now = DateUtils.getNowDate();
        OrderInfo orderInfo = new OrderInfo();
        orderInfo.setOrderNo(IdUtil.getSnowflake().nextIdStr())
                .setOrderState(OrderStateEnums.WAIT_PAY.getCode())
                .setCustomUserId(TokenUtils.getUserId())
                .setCreateTime(now)
                .setUpdateTime(now);
        return orderInfo;
    }

    /**
     * 查询订单数据
     *
     * @param dto 查询数据
     * @return 结果
     */
    public List<OrderInfo> select(ApiPageOrderInfoDto dto) {
        OrderInfo select = new OrderInfo();
        BeanUtils.copyBeanProp(select, dto);
        Long userId = TokenUtils.getUserId();
        if ("0".equals(dto.getFormUser())) {
            select.setCustomUserId(userId);
        }
        if ("1".equals(dto.getFormUser())) {
            select.setStaffUserId(userId);
        }
        if ("2".equals(dto.getFormUser())) {
            StaffInfo staffInfo = staffInfoMapper.selectStaffInfoByUserId(userId);
            select.setFilterStaffIdIsNull(SysYesNoEnums.YES.getCode())
                    .setLessThanOrEqualLevel(staffInfo.getStaffLevel());
        }
        return orderInfoMapper.selectJoinAll(select);
    }

    /**
     * 订单取消
     *
     * @param dto 取消表单
     * @return 结果
     */
    @Transactional(rollbackFor = Exception.class)
    public Boolean orderCancel(ApiOrderCancelDto dto) {
        log.info("订单取消：开始，参数：{}", dto);
        // 获取本地的bean实例
        ApiOrderService apiOrderService = SpringUtils.getBean(ApiOrderService.class);
        // 查询订单信息
        OrderInfo orderInfo = orderInfoMapper.selectByOrderNo(dto.getOrderNo());
        if (ObjectUtil.isNull(orderInfo)) {
            log.warn("订单取消：失败，无法查询对应订单信息");
            throw new ServiceException("亲爱的，系统繁忙，请稍后重试", HttpStatus.WARN_WX);
        }
        // 构建订单信息
        Date now = DateUtils.getNowDate();
        OrderInfo updateOi = new OrderInfo();
        BeanUtils.copyBeanProp(updateOi, dto);
        updateOi.setId(orderInfo.getId())
                .setOrderState(OrderStateEnums.CANCEL.getCode())
                .setCanceller(TokenUtils.getUserId().equals(orderInfo.getCustomUserId()) ? "老板" : "店员")
                .setOrderCancelTime(now)
                .setUpdateTime(now);
        // 判断订单状态是否属于待支付或者待服务
        if (!OrderStateEnums.WAIT_PAY.getCode().equals(orderInfo.getOrderState()) && !OrderStateEnums.PAID.getCode().equals(orderInfo.getOrderState()) && !OrderStateEnums.WAIT_SERVICE.getCode().equals(orderInfo.getOrderState())) {
            log.warn("订单取消：失败，订单状态不为：待支付/待服务");
            throw new ServiceException("亲爱的，取消失败啦，请刷新重试哟", HttpStatus.WARN_WX);
        }
        // 根据状态进行对应的业务
        // 待支付-直接取消
        if (OrderStateEnums.WAIT_PAY.getCode().equals(orderInfo.getOrderState())) {
            // 直接更新订单状态
            orderInfoMapper.updateOrderInfo(updateOi);
            // 更新订单详情状态
            orderDetailsMapper.updateStateByOrderId(orderInfo.getId(), OrderStateEnums.CANCEL.getCode());
            // 更新支付订单
            PaymentOrder updatePo = new PaymentOrder();
            updatePo.setOrderId(orderInfo.getId())
                    .setPayState(PayStateEnums.CANCEL.getCode());
            paymentOrderMapper.updateByOrderId(updatePo);
        }
        orderInfo.setCancelRemark(updateOi.getCancelRemark());
        // 待服务-需要进行退款业务
        if (OrderStateEnums.PAID.getCode().equals(orderInfo.getOrderState()) || OrderStateEnums.WAIT_SERVICE.getCode().equals(orderInfo.getOrderState())) {
            // 查询订单详情信息
            List<OrderDetails> orderDetailsList = orderDetailsMapper.selectByOrderId(orderInfo.getId());
            if (ObjectUtil.isEmpty(orderDetailsList)) {
                log.warn("订单取消：失败，查询订单详情失败");
                throw new ServiceException("亲爱的，取消失败啦，请刷新重试哟", HttpStatus.WARN_WX);
            }
            OrderDetails orderDetails = orderDetailsList.stream().findFirst().orElse(null);

            // 微信支付
            if (PayWayEnums.WEI_XIN_PAY.getCode().equals(orderInfo.getPayWay())) {
                updateOi.setOrderState(OrderStateEnums.REFUNDED.getCode());
                orderInfoMapper.updateOrderInfo(updateOi);
                // 退款订单
                PaymentRefund paymentRefund = apiOrderService.buildPaymentRefund(orderInfo, orderDetails);
                paymentRefundMapper.insertPaymentRefund(paymentRefund);
                // 发起微信退款
                apiOrderService.wxRefund(orderInfo, paymentRefund);
            }

            // 余额支付
            if (PayWayEnums.AMOUNT_PAY.getCode().equals(orderInfo.getPayWay())) {
                updateOi.setOrderState(OrderStateEnums.REFUNDED.getCode());
                orderInfoMapper.updateOrderInfo(updateOi);
                // 更新订单详情状态
                orderDetailsMapper.updateStateByOrderId(orderInfo.getId(), OrderStateEnums.REFUNDED.getCode());
                // 更新支付单状态
                PaymentOrder updatePo = new PaymentOrder();
                updatePo.setOrderId(orderInfo.getId())
                        .setPayState(PayStateEnums.REFUND.getCode());
                paymentOrderMapper.updateByOrderId(updatePo);
                // 退款单号生成
                PaymentRefund paymentRefund = apiOrderService.buildPaymentRefund(orderInfo, orderDetails);
                paymentRefundMapper.insertPaymentRefund(paymentRefund);
                // 用户订单金额回退到余额中
                apiOrderService.balanceRefund(orderInfo);
                // 发送取消通知
                rocketMqService.asyncSend(MqConstants.TOPIC_ORDER_CANCEL_NOTICE, orderInfo.getId());
            }
        }
        log.info("订单取消：完成");
        return Boolean.TRUE;
    }

    /**
     * 订单退款到用户余额中
     */
    public void balanceRefund(OrderInfo orderInfo) {
        log.info("订单退款到用户余额中：开始，参数：{}", orderInfo);
        Date now = DateUtils.getNowDate();
        // 开始处理用户钱包数据
        UserWallet userWallet = userWalletMapper.selectByUserId(orderInfo.getCustomUserId());
        if (ObjectUtil.isNull(userWallet)) {
            log.warn("订单退款到用户余额中：失败，无法找到用户对应钱包数据");
            throw new ServiceException("订单退款到用户余额中：失败，无法找到用户对应钱包数据");
        }
        userWallet.setGiftBalance(userWallet.getGiftBalance().add(orderInfo.getPayAmount()))
                .setUpdateTime(now);
        userWalletMapper.updateUserWallet(userWallet);
        // 构建一条用户钱包记录数据
        UserWalletRecord walletRecord = new UserWalletRecord();
        walletRecord.setUserId(orderInfo.getCustomUserId())
                .setWalletRecordType(UserWalletRecordTypeEnums.REFUND.getCode())
                .setAmount(orderInfo.getPayAmount())
                .setAmountBefore(userWallet.getBalance().add(userWallet.getGiftBalance()).subtract(orderInfo.getPayAmount()))
                .setAmountAfter(userWallet.getBalance().add(userWallet.getGiftBalance()))
                .setRemark(orderInfo.getCancelRemark())
                .setCreateTime(now);
        userWalletRecordMapper.insertUserWalletRecord(walletRecord);
        log.info("订单退款到用户余额中：完成");
    }

    /**
     * 发起微信退款申请
     *
     * @param orderInfo     订单信息
     * @param paymentRefund 支付退款单
     */
    @Transactional(rollbackFor = Exception.class)
    public void wxRefund(OrderInfo orderInfo, PaymentRefund paymentRefund) {
        log.info("发起微信退款申请：开始，参数：{},{}", orderInfo, paymentRefund);
        PaymentOrder paymentOrder = paymentOrderMapper.selectByOutTradeNo(orderInfo.getOrderNo());
        // 请求微信发起退款
        WxPayRefundDto wxPayRefundDto = new WxPayRefundDto();
        wxPayRefundDto.setTransactionId(paymentOrder.getTransactionId())
                .setOutRefundNo(paymentRefund.getRefundNo())
                .setTotalFee(orderInfo.getPayAmount().multiply(new BigDecimal(100)).setScale(100, RoundingMode.DOWN).intValue())
                .setRefundFee(orderInfo.getPayAmount().multiply(new BigDecimal(100)).setScale(100, RoundingMode.DOWN).intValue())
                .setRefundDesc(orderInfo.getCancelRemark());
        try {
            WxPayRefundResult wxPayRefundResult = wxService.refundRequest(wxPayRefundDto);
            if (ObjectUtil.isNull(wxPayRefundResult) || !Constants.PAY_SUCCESS.equals(wxPayRefundResult.getResultCode())) {
                log.warn("发起微信退款申请：失败，发起微信退款申请失败，微信返回数据：{}", wxPayRefundResult);
                throw new ServiceException("发起微信退款申请：失败，发起微信退款申请失败");
            }
            log.info("发起微信退款申请：描述，已成功发起微信退款，待退款后回调处理");
        } catch (Exception e) {
            e.printStackTrace();
            log.warn("发起微信退款申请：失败，发起微信退款申请异常");
            throw new ServiceException("发起微信退款申请：失败，发起微信退款申请异常");
        }
    }

    /**
     * 构建退款单
     *
     * @param orderInfo    订单信息
     * @param orderDetails 订单详情数据
     * @return 结果
     */
    public PaymentRefund buildPaymentRefund(OrderInfo orderInfo, OrderDetails orderDetails) {
        PaymentRefund paymentRefund = new PaymentRefund();
        paymentRefund.setUserId(orderInfo.getCustomUserId())
                .setOrderId(orderInfo.getId())
                .setOrderDetailsId(orderDetails.getId())
                .setRefundNo(IdUtils.randomId())
                .setRefundRoute(orderInfo.getPayWay())
                .setStart(PayWayEnums.WEI_XIN_PAY.getCode().equals(orderInfo.getPayWay()) ? RefundStateEnums.APPLY.getCode() : RefundStateEnums.SUCCESS.getCode())
                .setRemark(orderInfo.getCancelRemark())
                .setCreateTime(DateUtils.getNowDate());
        return paymentRefund;
    }

    /**
     * 订单再次支付
     *
     * @param orderNo 订单编号
     * @return 支付信息
     */
    public ApiOrderPayInfoVo payAgain(String orderNo) {
        log.info("订单再次支付：开始，参数：{}", orderNo);
        String key = RedisKeyConstants.ORDER_PAY_INFO + orderNo;
        ApiOrderPayInfoVo vo = redisCache.getCacheObject(key);
        if (ObjectUtil.isNull(vo)) {
            log.warn("订单再次支付：失败，缓存信息不存在");
            throw new ServiceException("支付信息失效，请重新下单", HttpStatus.WARN_WX);
        }
        log.info("订单再次支付：完成");
        return vo;
    }

    /**
     * 订单完成
     *
     * @param dto 表单
     * @return 结果
     */
    @Transactional(rollbackFor = Exception.class)
    public Boolean orderFinish(ApiOrderFinishDto dto) {
        log.info("订单完成：开始，参数：{}", dto);
        OrderInfo orderInfo = orderInfoMapper.selectByOrderNo(dto.getOrderNo());
        if (ObjectUtil.isNull(orderInfo)) {
            log.warn("订单完成：失败，无法找到对应的订单数据");
            throw new ServiceException("亲爱的，系统繁忙，请稍后重试", HttpStatus.WARN_WX);
        }
        // 查询订单详情信息
        List<OrderDetails> orderDetailsList = orderDetailsMapper.selectByOrderId(orderInfo.getId());
        if (ObjectUtil.isEmpty(orderDetailsList)) {
            log.warn("订单完成：失败，查询订单详情失败");
            throw new ServiceException("亲爱的，系统繁忙，请刷新重试哟", HttpStatus.WARN_WX);
        }
        OrderDetails orderDetails = orderDetailsList.stream().findFirst().orElse(null);
        // 查询用户信息
        UserInfo userInfo = userInfoMapper.selectUserInfoById(orderInfo.getCustomUserId());
        if (ObjectUtil.isNull(userInfo)) {
            log.warn("订单完成：失败，查询用户信息失败");
            throw new ServiceException("亲爱的，系统繁忙，请刷新重试哟", HttpStatus.WARN_WX);
        }

        Date now = DateUtils.getNowDate();
        // 开始构建订单
        OrderInfo updateOi = new OrderInfo();
        updateOi.setId(orderInfo.getId())
                .setCommentState(SysYesNoEnums.YES.getCode())
                .setUpdateTime(now);
        if (OrderStateEnums.SERVICE_ING.getCode().equals(orderInfo.getOrderState())) {
            updateOi.setOrderState(OrderStateEnums.FINISH.getCode())
                    .setOrderFinshTime(now);
        }
        orderInfoMapper.updateOrderInfo(updateOi);
        // 订单评论构建
        String comment = "默认好评";
        if (StringUtils.isNotBlank(dto.getComment())) {
            comment = dto.getComment();
        }
        OrderComment orderComment = new OrderComment();
        orderComment.setOrderId(orderInfo.getId())
                .setStaffUserId(orderInfo.getStaffUserId())
                .setUserId(orderInfo.getCustomUserId())
                .setUserAvatar(userInfo.getAvatarUrl())
                .setUserName(userInfo.getNickName())
                .setComment(comment)
                .setServiceContent(orderDetails.getDetailsTitle())
                .setCreateTime(now);
        orderCommentMapper.insertOrderComment(orderComment);
        // 订单佣金结算/订单完成通知
        if (OrderStateEnums.SERVICE_ING.getCode().equals(orderInfo.getOrderState())) {
            rocketMqService.asyncSend(MqConstants.TOPIC_ORDER_FINISH_NOTICE, orderInfo.getId());
            rocketMqService.asyncSend(MqConstants.TOPIC_ORDER_COMMISSION_SETTLEMENT, orderInfo.getId());
        }
        log.info("订单完成：完成");
        return Boolean.TRUE;
    }

    /**
     * 店员随机单接单
     *
     * @param orderNo 订单编号
     * @return 结果
     */
    @Transactional(rollbackFor = Exception.class)
    public Boolean randomOrderTaking(String orderNo) {
        log.info("店员随机单接单：开始，参数：{}", orderNo);
        String tipMsg = null;
        // 先判断店员是否可以接单
        String[] tempStateArr = {OrderStateEnums.WAIT_SERVICE.getCode(), OrderStateEnums.SERVICE_ING.getCode()};
        List<Long> hasRandomOrderIds = orderInfoMapper.selectIdByStaffIdAndTypeAndStateList(TokenUtils.getUserId(), OrderTypeEnums.RANDOM.getCode(), ListUtil.toList(tempStateArr));
        if (ObjectUtil.isNotEmpty(hasRandomOrderIds)) {
            log.warn("店员随机单接单：失败，当前还有未完成的随机单");
            throw new ServiceException("亲爱的，您当前有未完成随机单，暂不可接单", HttpStatus.WARN_WX);
        }
        lock.lock();
        try {
            Date now = DateUtils.getNowDate();
            OrderInfo orderInfo = orderInfoMapper.selectByOrderNo(orderNo);
            // 可以接单
            if (ObjectUtil.isNotNull(orderInfo) && ObjectUtil.isNull(orderInfo.getStaffUserId())) {
                // 查询店员信息
                StaffInfo staffInfo = staffInfoMapper.selectStaffInfoByUserId(TokenUtils.getUserId());
                if (ObjectUtil.isNotNull(staffInfo)) {
                    // 查询当前店员等级佣金
                    StaffLevelConfig staffLevelConfig = selectStaffLevelInfo(staffInfo.getStaffLevel());
                    if (ObjectUtil.isNotNull(staffLevelConfig)) {
                        Boolean ifContinuous = ifContinuous(orderInfo.getCustomUserId(), staffInfo.getUserId());
                        OrderInfo updateOi = new OrderInfo();
                        updateOi.setId(orderInfo.getId())
                                .setStaffUserId(staffInfo.getUserId())
                                .setStaffLevel(staffInfo.getStaffLevel())
                                .setIfContinuous(ifContinuous ? SysYesNoEnums.YES.getCode() : SysYesNoEnums.NO.getCode())
                                .setCommissionRatio(ifContinuous ? staffLevelConfig.getOrderRatio() : staffLevelConfig.getFirstOrderRatio())
                                .setOrderState(OrderStateEnums.WAIT_SERVICE.getCode())
                                .setOrderReceivingTime(now)
                                .setUpdateTime(now);
                        orderInfoMapper.updateOrderInfo(updateOi);
                        // 发送通知消息
                        rocketMqService.asyncSend(MqConstants.TOPIC_ORDER_TAKING_NOTICE, orderInfo.getId());
                    } else {
                        tipMsg = "亲爱的，服务器拥挤，请刷新重试";
                    }
                } else {
                    tipMsg = "亲爱的，服务器拥挤，请刷新重试";
                }
            } else {
                tipMsg = "亲爱的，服务器拥挤，请刷新重试";
            }
        } catch (Exception e) {
            log.warn("店员随机单接单：异常");
            throw new ServiceException(e.getMessage());
        } finally {
            lock.unlock();
        }
        if (StringUtils.isNotBlank(tipMsg)) {
            throw new ServiceException(tipMsg, HttpStatus.WARN_WX);
        }
        log.info("店员随机单接单：完成");
        return Boolean.TRUE;
    }

    /**
     * 店员接单
     *
     * @param orderNo 订单编号
     * @return 结果
     */
    @Transactional(rollbackFor = Exception.class)
    public Boolean orderTaking(String orderNo) {
        log.info("店员接单：开始，参数：{}", orderNo);
        OrderInfo orderInfo = orderInfoMapper.selectByOrderNo(orderNo);
        if (ObjectUtil.isNull(orderInfo)) {
            log.warn("店员接单：失败，订单信息查询为空");
            throw new ServiceException("亲爱的，系统繁忙，请刷新重试", HttpStatus.WARN_WX);
        }
        Date now = DateUtils.getNowDate();
        OrderInfo updateOi = new OrderInfo();
        updateOi.setId(orderInfo.getId())
                .setOrderState(OrderStateEnums.WAIT_SERVICE.getCode())
                .setOrderReceivingTime(now)
                .setUpdateTime(now);
        orderInfoMapper.updateOrderInfo(updateOi);
        // 发送通知消息
        rocketMqService.asyncSend(MqConstants.TOPIC_ORDER_TAKING_NOTICE, orderInfo.getId());
        log.info("店员接单：完成");
        return Boolean.TRUE;
    }

    /**
     * 店员开始服务
     *
     * @param orderNo 订单编号
     * @return 结果
     */
    @Transactional(rollbackFor = Exception.class)
    public Boolean orderStartService(String orderNo) {
        log.info("店员开始服务：开始，参数：{}", orderNo);
        OrderInfo orderInfo = orderInfoMapper.selectByOrderNo(orderNo);
        if (ObjectUtil.isNull(orderInfo)) {
            throw new ServiceException("亲爱的，系统繁忙，请刷新重试", HttpStatus.WARN_WX);
        }
        Date now = DateUtils.getNowDate();
        OrderInfo updateOi = new OrderInfo();
        updateOi.setId(orderInfo.getId())
                .setOrderState(OrderStateEnums.SERVICE_ING.getCode())
                .setOrderServiceTime(now)
                .setUpdateTime(now);
        // 计算订单自动服务完成时间
        MqDelayLevelEnums mqDelayLevelEnums = null;
        List<OrderDetails> orderDetailsList = orderDetailsMapper.selectByOrderId(orderInfo.getId());
        if (ObjectUtil.isNotEmpty(orderDetailsList)) {
            OrderDetails orderDetails = orderDetailsList.get(0);
            Long serviceItemId = orderDetails.getServiceItemId();
            ServiceItem serviceItem = serviceItemMapper.selectServiceItemById(serviceItemId);
            if (ObjectUtil.isNotNull(serviceItem)) {
                DateTimeUnitEnums timeUnitEnums = DateTimeUnitEnums.getByCode(serviceItem.getOrderExpireTimeUnit());
                if (ObjectUtil.isNotNull(timeUnitEnums)) {
                    int orderServiceDuration = serviceItem.getOrderServiceDuration().intValue();
                    Date autoFinishDate;
                    switch (timeUnitEnums) {
                        case MONTH:
                            autoFinishDate = DateUtils.calculateMonthsDifference(orderServiceDuration);
                            mqDelayLevelEnums = MqDelayLevelEnums.level_18;
                            break;
                        case WEEK:
                            autoFinishDate = DateUtils.calculateWeeksDifference(orderServiceDuration);
                            mqDelayLevelEnums = MqDelayLevelEnums.level_18;
                            break;
                        case DAY:
                            autoFinishDate = DateUtils.calculateDaysDifference(orderServiceDuration);
                            mqDelayLevelEnums = MqDelayLevelEnums.level_18;
                            break;
                        case HOUR:
                            autoFinishDate = DateUtils.calculateHoursDifference(orderServiceDuration);
                            if (orderServiceDuration == 2) {
                                mqDelayLevelEnums = MqDelayLevelEnums.level_18;
                            } else {
                                mqDelayLevelEnums = MqDelayLevelEnums.level_17;
                            }
                            break;
                        case MINUTE:
                            if (orderServiceDuration <= 30) {
                                mqDelayLevelEnums = MqDelayLevelEnums.level_16;
                            } else {
                                mqDelayLevelEnums = MqDelayLevelEnums.level_17;
                            }
                            autoFinishDate = DateUtils.calculateMinutesDifference(orderServiceDuration);
                            break;
                        default:
                            mqDelayLevelEnums = MqDelayLevelEnums.level_18;
                            autoFinishDate = DateUtils.calculateMonthsDifference(7);
                            break;
                    }
                    updateOi.setAutoFinshTime(autoFinishDate);
                }
            }
        }
        orderInfoMapper.updateOrderInfo(updateOi);
        // 发送订单自动完成消息
        if (ObjectUtil.isNotNull(mqDelayLevelEnums)) {
            rocketMqService.delayedSend(MqConstants.TOPIC_ORDER_AUTO_SUCCESS, orderInfo.getId(), mqDelayLevelEnums);
        }
        // 发送通知消息
        rocketMqService.asyncSend(MqConstants.TOPIC_ORDER_START_NOTICE, orderInfo.getId());
        log.info("店员开始服务：完成");
        return Boolean.TRUE;
    }

    /**
     * 查询服务过的用户id集合
     *
     * @return 结果
     */
    public List<Long> selectServedUserId() {
        log.info("查询服务过的用户id集合：开始");
        List<Long> idList = new ArrayList<>();
        Long staffUserId = TokenUtils.getUserId();
        List<Long> userIdList = orderInfoMapper.selectUserIdByStaffUserId(staffUserId);
        if (ObjectUtil.isNotEmpty(userIdList)) {
            idList = userIdList;
        }
        log.info("查询服务过的用户id集合：完成，返回数据：{}", idList);
        return idList;
    }

    /**
     * 查询订单评论数据
     *
     * @param dto 查询表单
     * @return 结果
     */
    public List<OrderComment> selectOrderComment(ApiPageOrderCommentDto dto) {
        OrderComment orderComment = new OrderComment();
        orderComment.setIfHideDefault(SysYesNoEnums.YES.getCode());
        if (ObjectUtil.isNotNull(dto.getStaffId())) {
            orderComment.setStaffUserId(dto.getStaffId());
        }
        return orderCommentMapper.selectOrderCommentList(orderComment);
    }

    /**
     * 查询店员时间段订单详细情况
     *
     * @return 结果
     */
    public ApiOrderOfStaffThisWeekVo selectStaffOrderInfoByDate(ApiSelectStaffOrderInfoByDateDto dto) {
        log.info("查询店员时间段订单详细情况：开始");
        ApiOrderOfStaffThisWeekVo vo = new ApiOrderOfStaffThisWeekVo();
        int orderNum = 0;
        int settledNum = 0;
        int unSettledNum = 0;
        int cancelNum = 0;
        int overTimeNum = 0;
        BigDecimal renewalRate = BigDecimal.ZERO;
        BigDecimal waitOrderFinishCommission = BigDecimal.ZERO;
        BigDecimal orderTotalAmount = BigDecimal.ZERO;
        BigDecimal effectiveTotalAmount = BigDecimal.ZERO;
        List<ApiOrderInfoVo> orderInfoVoList = new ArrayList<>();
        // 构建查询数据
        Map<String, Object> params = new HashMap<>();
        params.put("beginCreateTime", dto.getBeginCreateTime());
        params.put("endCreateTime", dto.getEndCreateTime());
        // 用户标识
        Long staffUserId = TokenUtils.getUserId();
        if (ObjectUtil.isNotNull(dto.getStaffUserId())) {
            staffUserId = dto.getStaffUserId();
        }
        OrderInfo orderInfo = new OrderInfo();
        orderInfo.setStaffUserId(staffUserId)
                .setParams(params);
        // 开始查询
        List<OrderInfo> orderInfos = orderInfoMapper.selectOrderInfoList(orderInfo);
        // 计算基本数据
        if (ObjectUtil.isNotEmpty(orderInfos)) {
            String[] unSettledStateArr = {OrderStateEnums.PAID.getCode(), OrderStateEnums.WAIT_SERVICE.getCode(), OrderStateEnums.SERVICE_ING.getCode(), OrderStateEnums.WAIT_COMMENT.getCode()};
            orderNum = orderInfos.size();
            // 获取已结单数
            List<OrderInfo> settledOrderList = orderInfos.stream().filter(temp -> OrderStateEnums.FINISH.getCode().equals(temp.getOrderState())).collect(Collectors.toList());
            if (ObjectUtil.isNotEmpty(settledOrderList)) {
                settledNum = settledOrderList.size();
            }
            // 获取未结单数
            List<OrderInfo> unSettledList = orderInfos.stream().filter(temp -> ListUtil.toList(unSettledStateArr).contains(temp.getOrderState())).collect(Collectors.toList());
            if (ObjectUtil.isNotEmpty(unSettledList)) {
                unSettledNum = unSettledList.size();
                for (OrderInfo item : unSettledList) {
                    waitOrderFinishCommission = waitOrderFinishCommission.add(item.getPayAmount().multiply(item.getCommissionRatio())).setScale(2, RoundingMode.HALF_UP);
                }
            }
            // 取消单数
            List<OrderInfo> cancelOrderList = orderInfos.stream().filter(temp -> OrderStateEnums.CANCEL.getCode().equals(temp.getOrderState())).collect(Collectors.toList());
            if (ObjectUtil.isNotEmpty(cancelOrderList)) {
                cancelNum = cancelOrderList.size();
            }
            // 超时订单数
            List<OrderInfo> overTimeOrderList = orderInfos.stream().filter(temp -> SysTipsConstants.ORDER_OVER_TIME_CANCEL_TIPS.equals(temp.getCancelRemark())).collect(Collectors.toList());
            if (ObjectUtil.isNotEmpty(overTimeOrderList)) {
                overTimeNum = overTimeOrderList.size();
            }
            // 获取续单订单率
            List<OrderInfo> renewalList = orderInfos.stream().filter(temp -> SysYesNoEnums.YES.getCode().equals(temp.getIfContinuous())).collect(Collectors.toList());
            if (ObjectUtil.isNotEmpty(renewalList)) {
                renewalRate = BigDecimal.valueOf(renewalList.size()).divide(BigDecimal.valueOf(orderNum), 2, RoundingMode.HALF_UP).multiply(BigDecimal.valueOf(100));
            }
            ArrayList<String> normalStateList = ListUtil.toList(unSettledStateArr);
            normalStateList.add(OrderStateEnums.FINISH.getCode());
            // 循环计算金额数据
            for (OrderInfo item : orderInfos) {
                ApiOrderInfoVo orderInfoVo = new ApiOrderInfoVo();
                BeanUtils.copyBeanProp(orderInfoVo, item);
                orderInfoVoList.add(orderInfoVo);
                orderTotalAmount = orderTotalAmount.add(item.getPayAmount());
                if (normalStateList.contains(item.getOrderState())) {
                    effectiveTotalAmount = effectiveTotalAmount.add(item.getAmount());
                }
            }
        }
        vo.setOrderNum(orderNum)
                .setSettledNum(settledNum)
                .setUnSettledNum(unSettledNum)
                .setCancelNum(cancelNum)
                .setOverTimeNum(overTimeNum)
                .setRenewalRate(renewalRate)
                .setWaitOrderFinishCommission(waitOrderFinishCommission)
                .setEffectiveTotalAmount(effectiveTotalAmount)
                .setOrderTotalAmount(orderTotalAmount)
                .setOrderInfoVoList(orderInfoVoList);
        log.info("查询店员时间段订单详细情况：完成，返回数据：{}", vo);
        return vo;
    }
}
