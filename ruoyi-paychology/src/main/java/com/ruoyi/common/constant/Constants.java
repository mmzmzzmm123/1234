package com.ruoyi.common.constant;

public class Constants {

    //积分
    public static final String INTEGRAL_RATE = "0.1"; //积分抵用比例(1积分抵多少金额
    public static final String INTEGRAL_ORDER_RATE = "0.5"; //整单可抵扣比率
    public static final String INTEGRAL_RATE_ORDER_GIVE = "1"; //下单支付金额按比例赠送积分（实际支付1元赠送多少积分

    // 微信公众号
    public static final String CONSULT_TEMPLATE_ID = "aCI2Sgo63NRZLKQ3khNpI6o6a4TG0sFoN2eRuomJsB4"; // 咨询预约成功模版

    //消息模板队列key,后续异步处理
    public static final String WE_CHAT_MESSAGE_KEY_PUBLIC = "we_chat_public_message_list";
    public static final String WE_CHAT_MESSAGE_KEY_PROGRAM = "we_chat_program_message_list";

    public static final String WECHAT_PUBLIC_KEY_WXJS_TOKEN = "KEY_WxJS_TOKEN";
    public static final String WECHAT_PUBLIC_KEY_WXJS_TICKET = "KEY_WxJS_TICKET";
    public static final int WECHAT_PUBLIC_EXPIRE_TIME = 7200;

    // 公众号access_token
    public static final String WECHAT_PUBLIC_ACCESS_TOKEN_URL = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid={}&secret={}";
    public static final String WECHAT_PUBLIC_TICKET_URL = "https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token={}&type=jsapi";

    // 公众号发送模板消息接口地址
    public static final String WECHAT_PUBLIC_SEND_TEMPLATE_URL = "https://api.weixin.qq.com/cgi-bin/message/template/send?access_token={}";

}