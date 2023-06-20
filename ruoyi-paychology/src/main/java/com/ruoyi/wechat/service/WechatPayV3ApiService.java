package com.ruoyi.wechat.service;

import com.ruoyi.wechat.vo.WechatPayVO;

public interface WechatPayV3ApiService {

    void wechatPay(WechatPayVO wechatPay);

    void wechatPayNotify(String outTradeNo, String payId);

}
