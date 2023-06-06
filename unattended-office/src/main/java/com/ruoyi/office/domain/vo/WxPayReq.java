package com.ruoyi.office.domain.vo;

import lombok.Data;

@Data
public class WxPayReq {
    //由微信生成的应用ID，全局唯一。请求基础下单接口时请注意APPID的应用属性，例如公众号场景下，需使用应用属性为公众号的服务号APPID string[1,32]
    String appid;
    //直连商户的商户号，由微信支付生成并下发。示例值：1230000109 string[1,32]
    String mchid;
    //商品描述
    //示例值：Image形象店-深圳腾大-QQ公仔
    String description;
    // 商户系统内部订单号，只能是数字、大小写字母_-*且在同一个商户号下唯一
    String out_trade_no;
    // 订单失效时间 必输 否
    String time_expire;
    //必输 否 附加数据，在查询API和支付通知中原样返回，可作为自定义参数使用，实际情况下只有支付完成状态才会返回该字段。
    String attach;
    //异步接收微信支付结果通知的回调地址，通知url必须为外网可访问的url，不能携带参数。 公网域名必须为https，如果是走专线接入，使用专线NAT IP或者私有回调域名可使用http
    String notify_url;
    //总金额	total	int
    WxPayAmount amount;
    // 用户标识: openid 用户在直连商户appid下的唯一标识。 下单前需获取到用户的Openid
    WxPayPayer payer;
}
