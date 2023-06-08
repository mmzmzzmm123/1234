package com.ruoyi.office.domain.vo;

import lombok.Data;

@Data
public class WxPayOrderQueryResp {
    //由微信生成的应用ID，全局唯一。请求基础下单接口时请注意APPID的应用属性，例如公众号场景下，需使用应用属性为公众号的服务号APPID string[1,32]
    String appid;
    //直连商户的商户号，由微信支付生成并下发。示例值：1230000109 string[1,32]
    String mchid;
    // 商户系统内部订单号，只能是数字、大小写字母_-*且在同一个商户号下唯一
    String out_trade_no;
    // 交易状态，枚举值： SUCCESS：支付成功 REFUND：转入退款 NOTPAY：未支付 CLOSED：已关闭 REVOKED：已撤销（仅付款码支付会返回） USERPAYING：用户支付中（仅付款码支付会返回） PAYERROR：支付失败（仅付款码支付会返回）
    String trade_state;
    // 交易状态描述
    String trade_state_desc;
    // 用户标识: openid 用户在直连商户appid下的唯一标识。 下单前需获取到用户的Openid
    WxPayPayer payer;
}
