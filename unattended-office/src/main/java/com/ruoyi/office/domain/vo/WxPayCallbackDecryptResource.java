package com.ruoyi.office.domain.vo;

public class WxPayCallbackDecryptResource {
    private String appid ;
    private String mchid;
    private String out_trade_no;
    //微信支付系统生成的订单号。
    private String transaction_id;
    //交易类型，枚举值： JSAPI：公众号支付 NATIVE：扫码支付 APP：APP支付 MICROPAY：付款码支付 MWEB：H5支付 FACEPAY：刷脸支付
    private String trade_type;
    //交易状态，枚举值： SUCCESS：支付成功 REFUND：转入退款 NOTPAY：未支付 CLOSED：已关闭 REVOKED：已撤销（付款码支付） USERPAYING：用户支付中（付款码支付） PAYERROR：支付失败(其他原因，如银行返回失败)
    private String trade_state;
    //交易状态描述 示例值：支付成功。
    private String trade_state_desc;
    //银行类型，采用字符串类型的银行标识。银行标识请参考《银行类型对照表》
    private String bank_type;
    //否 。 附加数据，在查询API和支付通知中原样返回，可作为自定义参数使用，实际情况下只有支付完成状态才会返回该字段。
    private String attach;
    //支付完成时间，遵循rfc3339标准格式，格式为yyyy-MM-DDTHH:mm:ss+TIMEZONE，yyyy-MM-DD表示年月日，T出现在字符串中，表示time元素的开头，HH:mm:ss表示时分秒，TIMEZONE表示时区（+08:00表示东八区时间，领先UTC 8小时，即北京时间）。例如：2015-05-20T13:29:35+08:00表示，北京时间2015年5月20日 13点29分35秒。 示例值：2018-06-08T10:34:56+08:00。
    private String success_time;
    //
    private String payer;
    //微信支付系统生成的订单号。
    private String amount;
}
