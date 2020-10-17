package com.ruoyi.thirdparty.sms;

/**
 * @author : yelifeng
 * @date : 2020/10/17 0017 15:06 xuc5566@gmail.com
 */
public class SMSConstant {

    // 1:表示开启
    public static final int SMS_SWITCH_OPEN = 1;

    // 短信模板代码
    public static final String SMS_TEMPLATE_CODE = "SMS_98005045";

    // 短信模板名称
    public static final String SMS_TEMPLATE_NAME = "验证码";

    // 短信签名
    public static final String SMS_SIGN = "YYYYY";

    // 验证码过期时间
    public static final int SMS_VALID_EXPIRE = 60 * 15;

    // 短信发送成功
    public static final int SMS_SEND_STATUS_SUCCESS = 1;

    // 短信发送失败
    public static final int SMS_SEND_STATUS_FAIL = 0;


}
