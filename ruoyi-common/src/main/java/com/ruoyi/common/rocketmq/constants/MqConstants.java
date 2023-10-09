package com.ruoyi.common.rocketmq.constants;

/**
 * @author LAM
 * @date 2023/10/5 23:33
 */
public class MqConstants {

    /**
     * 充值
     * */
    public static final String TOPIC_RECHARGE = "topic_recharge";
    public static final String GROUP_RECHARGE = "group_recharge";

    /**
     * 支付成功通知
     */
    public static final String TOPIC_PAY_SUCCESS_CALLBACK = "topic_pay_success_callback";
    public static final String GROUP_PAY_SUCCESS_CALLBACK = "group_pay_success_callback";
}
