package com.ruoyi.api.payment.service;

import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.ObjectUtil;
import com.github.binarywang.wxpay.bean.result.WxPayUnifiedOrderResult;
import com.ruoyi.api.payment.model.dto.ApiUserRechargeDto;
import com.ruoyi.api.payment.model.vo.ApiOrderPayInfoVo;
import com.ruoyi.common.constant.Constants;
import com.ruoyi.common.constant.HttpStatus;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.common.enums.MqDelayLevelEnums;
import com.ruoyi.common.enums.OrderTypeEnums;
import com.ruoyi.common.enums.PayStateEnums;
import com.ruoyi.common.enums.PayWayEnums;
import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.common.rocketmq.RocketMqService;
import com.ruoyi.common.rocketmq.constants.MqConstants;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.ServletUtils;
import com.ruoyi.common.utils.TokenUtils;
import com.ruoyi.common.utils.bean.BeanUtils;
import com.ruoyi.common.weixin.WxService;
import com.ruoyi.common.weixin.model.dto.UnifiedOrderRequestDto;
import com.ruoyi.common.weixin.model.vo.WxPayResultVo;
import com.ruoyi.payment.domain.PaymentOrder;
import com.ruoyi.payment.mapper.PaymentOrderMapper;
import com.ruoyi.platform.domain.PlatformRechargeConfig;
import com.ruoyi.platform.mapper.PlatformRechargeConfigMapper;
import com.ruoyi.user.domain.UserRechargeRecord;
import com.ruoyi.user.mapper.UserRechargeRecordMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * @author LAM
 * @date 2023/10/5 21:13
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class ApiPaymentService {

    private static final long MQ_DELAYED_SEND_TIME_OUT = 10000;
    private final PlatformRechargeConfigMapper rechargeConfigMapper;
    private final UserRechargeRecordMapper rechargeRecordMapper;
    private final PaymentOrderMapper paymentOrderMapper;
    private final WxService wxService;
    private final RocketMqService rocketMqService;

    /**
     * 充值金额
     *
     * @param dto 数据
     * @return 结果
     */
    @Transactional(rollbackFor = Exception.class)
    public R<ApiOrderPayInfoVo> recharge(ApiUserRechargeDto dto) {
        log.info("充值金额：开始，参数：{}", dto);
        ApiOrderPayInfoVo vo = new ApiOrderPayInfoVo();
        // 判断参数是否都为空
        if (ObjectUtil.isNull(dto.getAmount()) && ObjectUtil.isNull(dto.getRechargeConfigId())) {
            log.warn("充值金额：失败，参数为空：{}", dto);
            return R.warn("亲爱的，充值失败啦，错误信息：参数为空");
        }
        // 用户标识
        Long userId = TokenUtils.getUserId();
        Date now = DateUtils.getNowDate();
        // 构建充值记录
        UserRechargeRecord userRechargeRecord = buildRechargeRecord(dto);
        userRechargeRecord.setUserId(userId)
                .setCreateTime(now)
                .setUpdateBy(userId.toString())
                .setUpdateTime(now);
        // 开始创建支付订单信息
        String outTradeNo = IdUtil.getSnowflake().nextIdStr();
        try {
            // 开始构建支付数据
            UnifiedOrderRequestDto unifiedOrderRequestDto = new UnifiedOrderRequestDto();
            unifiedOrderRequestDto.setOpenid(TokenUtils.getOpenId())
                    .setOutTradeNo(outTradeNo)
                    .setAmount(Integer.valueOf(userRechargeRecord.getRechargeAmount().multiply(new BigDecimal(100)).setScale(0, RoundingMode.DOWN).toString()))
                    .setTimeExpire(DateUtils.rollMinute(40, DateUtils.YYYYMMDDHHMMSS))
                    .setPayerClientIp(ServletUtils.getClientIp())
                    .setBody(OrderTypeEnums.RECHARGE.getDesc())
                    .setDescription("充值枫叶币-"+ userRechargeRecord.getRechargeAmount().add(userRechargeRecord.getGiftAmount()) +"个");
            WxPayUnifiedOrderResult wxPayUnifiedOrderResult = wxService.unifiedOrder(unifiedOrderRequestDto);
            if (!Constants.PAY_SUCCESS.equals(wxPayUnifiedOrderResult.getReturnCode())){
                log.warn("充值金额：失败，无法请求微信支付，返回数据：{}", wxPayUnifiedOrderResult);
                throw new ServiceException("充值失败，请稍后重试",HttpStatus.WARN_WX);
            }
            WxPayResultVo wxPayResultVo = wxService.handleWxPayResult(wxPayUnifiedOrderResult);
            vo.setPackageValue(wxPayResultVo.getPrepayId())
                    .setTimeStamp(wxPayResultVo.getTimeStamp())
                    .setNonceStr(wxPayResultVo.getNonceStr())
                    .setSignType(wxPayResultVo.getSignType())
                    .setPaySign(wxPayResultVo.getPaySign());
            // 支付单请求完毕后，开始插入记录数据与支付订单数据
            rechargeRecordMapper.insertUserRechargeRecord(userRechargeRecord);
            // 开始构建支付订单数据
            PaymentOrder paymentOrder = new PaymentOrder();
            paymentOrder.setOrderType(OrderTypeEnums.RECHARGE.getCode())
                    .setUserId(userId)
                    .setOrderId(userRechargeRecord.getId())
                    .setOutTradeNo(outTradeNo)
                    .setSubject(unifiedOrderRequestDto.getBody())
                    .setBody(unifiedOrderRequestDto.getDescription())
                    .setPayState(PayStateEnums.WAIT_PAY.getCode())
                    .setPayWay(PayWayEnums.WEI_XIN_PAY.getCode())
                    .setAmount(userRechargeRecord.getRechargeAmount())
                    .setUserIp(unifiedOrderRequestDto.getPayerClientIp())
                    .setExpireTime(DateUtils.toDate(LocalDateTime.now().plusMinutes(30)))
                    .setCreateTime(now);
            paymentOrderMapper.insertPaymentOrder(paymentOrder);
            // 发送延时消息支付订单过期
            rocketMqService.delayedSend(MqConstants.TOPIC_RECHARGE, paymentOrder.getId(), MQ_DELAYED_SEND_TIME_OUT, MqDelayLevelEnums.level_16);
        }catch (Exception e){
            e.printStackTrace();
            log.warn("充值金额：异常，异常信息：{}", e.getMessage());
            throw new ServiceException("充值失败，请稍后重试",HttpStatus.WARN_WX);
        }
        log.info("充值金额：完成，返回数据：{}", vo);
        return R.ok(vo);
    }



    /**
     * 构建用户充值记录数据
     *
     * @param dto 数据
     * @return 结果
     */
    private UserRechargeRecord buildRechargeRecord(ApiUserRechargeDto dto) {
        UserRechargeRecord userRechargeRecord = new UserRechargeRecord();
        if (ObjectUtil.isNotNull(dto.getAmount())) {
            userRechargeRecord.setRechargeAmount(dto.getAmount())
                    .setGiftAmount(BigDecimal.ZERO);
        } else {
            PlatformRechargeConfig platformRechargeConfig = rechargeConfigMapper.selectPlatformRechargeConfigById(dto.getRechargeConfigId());
            if (ObjectUtil.isNull(platformRechargeConfig)) {
                return null;
            }
            userRechargeRecord.setRechargeAmount(platformRechargeConfig.getAmount())
                    .setGiftAmount(platformRechargeConfig.getGiveAmount());
        }
        userRechargeRecord.setPayState(PayStateEnums.WAIT_PAY.getCode());
        return userRechargeRecord;
    }

}
