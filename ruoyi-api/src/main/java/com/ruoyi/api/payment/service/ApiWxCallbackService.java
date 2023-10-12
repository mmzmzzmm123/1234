package com.ruoyi.api.payment.service;

import com.alibaba.druid.support.json.JSONUtils;
import com.alibaba.fastjson.JSON;
import com.github.binarywang.wxpay.bean.notify.WxPayRefundNotifyResult;
import com.github.binarywang.wxpay.service.WxPayService;
import com.ruoyi.api.payment.model.vo.ApiWxPayCallbackVo;
import com.ruoyi.api.payment.model.vo.ApiWxRefundCallbackVo;
import com.ruoyi.common.constant.Constants;
import com.ruoyi.common.constant.RedisKeyConstants;
import com.ruoyi.common.core.redis.RedisCache;
import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.common.rocketmq.RocketMqService;
import com.ruoyi.common.rocketmq.constants.MqConstants;
import com.ruoyi.common.weixin.utils.WxUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.util.XmlUtils;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author LAM
 * @date 2023/10/6 0:10
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class ApiWxCallbackService {

    private final WxPayService wxPayService;
    private final RedisCache redisCache;
    private final RocketMqService rocketMqService;

    /**
     * 微信支付成功回调
     *
     * @param request 请求头信息
     * @return 结果
     * */
    public String payCallback(HttpServletRequest request) {
        log.info("微信支付成功回调：开始");
        String resultXml = null;
        try {
            resultXml = WxUtils.parseWxNotify(request);
        }catch (Exception e){
            throw new ServiceException("微信支付成功回调：异常，无法转换微信请求头数据为xml");
        }
        Map<String, Object> dataMap = XmlUtils.xml2Map(resultXml);
        String dataJsonStr = JSONUtils.toJSONString(dataMap);
        ApiWxPayCallbackVo wxPayCallbackVo = JSON.toJavaObject(JSON.parseObject(dataJsonStr), ApiWxPayCallbackVo.class);
        wxPayCallbackVo.setCallbackData(dataJsonStr);
        String key = RedisKeyConstants.PAYMENT_PAY_SUCCESS+wxPayCallbackVo.getTransactionId();
        if (redisCache.hasKey(key)){
            log.warn("微信支付成功回调：完成，已发送消息并存在缓存");
            return Constants.SUCCESS_SMALL_LETTER;
        }
        Boolean sendResult = rocketMqService.asyncSend(MqConstants.TOPIC_PAY_SUCCESS_CALLBACK, wxPayCallbackVo);
        if (!sendResult){
            log.warn("微信支付成功回调：失败，发送mq失败");
            throw new ServiceException("微信支付成功回调：失败，发送mq失败");
        }
        redisCache.setCacheObject(key, wxPayCallbackVo, 1, TimeUnit.DAYS);
        log.info("微信支付成功回调：完成");
        return Constants.SUCCESS_SMALL_LETTER;
    }

    /**
     * 微信退款成功回调
     *
     * @param request 请求头
     * @return 结果
     * */
    public void refundCallback(HttpServletRequest request){
        log.info("微信退款成功回调：开始");
        try {
            String resultXml = WxUtils.parseWxNotify(request);
            WxPayRefundNotifyResult wxPayRefundNotifyResult = wxPayService.parseRefundNotifyResult(resultXml);
            WxPayRefundNotifyResult.ReqInfo reqInfo = wxPayRefundNotifyResult.getReqInfo();
            ApiWxRefundCallbackVo wxRefundCallbackVo = new ApiWxRefundCallbackVo();
            wxRefundCallbackVo.setResult(reqInfo.toString())
                    .setOutTradeNo(reqInfo.getOutRefundNo())
                    .setRefundId(reqInfo.getRefundId())
                    .setRefundStatus(reqInfo.getRefundStatus());
            String key = RedisKeyConstants.PAYMENT_REFUND_SUCCESS+wxRefundCallbackVo.getRefundId();
            if (redisCache.hasKey(key)){
                log.warn("微信退款成功回调：完成，已发送mq消息并存在缓存");
                return;
            }
            Boolean sendResult = rocketMqService.asyncSend(MqConstants.TOPIC_REFUND_SUCCESS_CALLBACK, wxRefundCallbackVo);
            if (!sendResult){
                log.warn("微信退款成功回调：失败，发送mq失败");
                throw new ServiceException("微信退款成功回调：失败，发送mq失败");
            }
            redisCache.setCacheObject(key, wxRefundCallbackVo, 1, TimeUnit.DAYS);
        }catch (Exception e){
            log.warn("微信退款成功回调：失败，异常出现，异常信息：{}", e.getMessage());
            throw new ServiceException("微信退款成功回调：失败，异常出现");
        }
        log.info("微信退款成功回调：完成");
    }
}
