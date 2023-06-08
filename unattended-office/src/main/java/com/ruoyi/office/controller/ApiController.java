package com.ruoyi.office.controller;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.github.binarywang.wxpay.bean.notify.*;
import com.github.binarywang.wxpay.constant.WxPayConstants;
import com.github.binarywang.wxpay.exception.WxPayException;
import com.github.binarywang.wxpay.service.WxPayService;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.office.domain.vo.WxPayCallback;
import com.ruoyi.office.service.ITRoomOrderService;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

@RestController
@RequestMapping("/office/api")
public class ApiController {
    protected org.apache.commons.logging.Log log = LogFactory.getLog(getClass());

    @Autowired
    ITRoomOrderService roomOrderService;

    @Autowired
    private WxPayService wxPayService;

    /**
     * 预定成功 api 回调
     */
    @ApiOperation("支付成功 api 回调")
    @Log(title = "支付成功 api 回调")
    @PostMapping("/wxnotify")
    public JSONObject wxnotify(@RequestBody String jsonData, HttpServletRequest request, HttpServletResponse response) throws WxPayException {
        JSONObject wxPayResult = new JSONObject();
        Lock lock = new ReentrantLock();
        if (lock.tryLock()) {
            // 支付成功结果通知
            OriginNotifyResponse notifyResponse = JSON.parseObject(jsonData, OriginNotifyResponse.class);
            WxPayOrderNotifyV3Result v3Result = null;
            try {
                v3Result = wxPayService.parseOrderNotifyV3Result(jsonStrSort(notifyResponse), getRequestHeader(request));

                //解密后的数据
                WxPayOrderNotifyV3Result.DecryptNotifyResult result = v3Result.getResult();

                if (result.getTradeState().equalsIgnoreCase(WxPayConstants.WxpayTradeStatus.SUCCESS)) {
                    roomOrderService.wxnotify(result.getOutTradeNo(), result.getPayer().getOpenid(), result.getAmount().getTotal());
                }

                //通知应答：接收成功：HTTP应答状态码需返回200或204，无需返回应答报文。
                response.setStatus(HttpServletResponse.SC_OK);
                return null;
            } catch (WxPayException e) {
                e.printStackTrace();
                log.error("支付回调失败：" + e.getLocalizedMessage());
                // 通知应答：HTTP应答状态码需返回5XX或4XX，同时需返回应答报文
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                wxPayResult.fluentPut("code", "FAIL");
                wxPayResult.fluentPut("message", e.getMessage());
                return wxPayResult;
            } finally {
                lock.unlock();
            }
        }
        // 通知应答码：HTTP应答状态码需返回5XX或4XX，同时需返回应答报文
        response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        wxPayResult.fluentPut("code", "FAIL");
        wxPayResult.fluentPut("message", "失败");
        return wxPayResult;
    }

    /**
     * 请求报文：按官方接口示例键值 --- 排序(必须)
     * 官方文档：https://pay.weixin.qq.com/wiki/doc/apiv3/apis/chapter3_1_5.shtml，
     * https://pay.weixin.qq.com/wiki/doc/apiv3/wechatpay/wechatpay4_1.shtml
     * 《微信支付API v3签名验证》 中注意：应答主体（response Body），需要按照接口返回的顺序进行验签，错误的顺序将导致验签失败。
     *
     * @param originNotifyResponse OriginNotifyResponse
     * @return String
     */
    private String jsonStrSort(OriginNotifyResponse originNotifyResponse) {

        Map<String, Object> jsonSort = new LinkedHashMap<>();
        jsonSort.put("id", originNotifyResponse.getId());
        jsonSort.put("create_time", originNotifyResponse.getCreateTime());
        jsonSort.put("resource_type", originNotifyResponse.getResourceType());
        jsonSort.put("event_type", originNotifyResponse.getEventType());
        jsonSort.put("summary", originNotifyResponse.getSummary());
        Map<String, Object> resource = new LinkedHashMap();
        resource.put("original_type", originNotifyResponse.getResource().getOriginalType());
        resource.put("algorithm", originNotifyResponse.getResource().getAlgorithm());
        resource.put("ciphertext", originNotifyResponse.getResource().getCiphertext());
        resource.put("associated_data", originNotifyResponse.getResource().getAssociatedData());
        resource.put("nonce", originNotifyResponse.getResource().getNonce());
        jsonSort.put("resource", resource);
        return JSON.toJSONString(jsonSort);
    }


    /**
     * 获取回调请求头：签名相关
     *
     * @param request HttpServletRequest
     * @return SignatureHeader
     */
    public SignatureHeader getRequestHeader(HttpServletRequest request) {
        // 获取通知签名
        String signature = request.getHeader("wechatpay-signature");
        String nonce = request.getHeader("wechatpay-nonce");
        String serial = request.getHeader("wechatpay-serial");
        String timestamp = request.getHeader("wechatpay-timestamp");

        SignatureHeader signatureHeader = new SignatureHeader();
        signatureHeader.setSignature(signature);
        signatureHeader.setNonce(nonce);
        signatureHeader.setSerial(serial);
        signatureHeader.setTimeStamp(timestamp);
        return signatureHeader;
    }

}
