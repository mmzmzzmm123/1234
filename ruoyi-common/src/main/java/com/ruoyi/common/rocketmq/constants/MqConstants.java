package com.ruoyi.common.rocketmq.constants;

/**
 * @author LAM
 * @date 2023/10/5 23:33
 */
public class MqConstants {

    /**
     * 订单过期取消
     * */
    public static final String TOPIC_ORDER_EXPIRE = "topic_order_expire";
    public static final String GROUP_ORDER_EXPIRE = "group_order_expire";

    /**
     * 支付成功回调
     */
    public static final String TOPIC_PAY_SUCCESS_CALLBACK = "topic_pay_success_callback";
    public static final String GROUP_PAY_SUCCESS_CALLBACK = "group_pay_success_callback";

    /**
     * 退款成功回调
     */
    public static final String TOPIC_REFUND_SUCCESS_CALLBACK = "topic_refund_success_callback";
    public static final String GROUP_REFUND_SUCCESS_CALLBACK = "group_refund_success_callback";



    /**
     * 礼物赠送成功
     * */
    public static final String TOPIC_GIVE_GIFT_SUCCESS = "topic_give_gift_success";
    public static final String GROUP_GIVE_GIFT_SUCCESS = "group_give_gift_success";

    /**
     * 打赏成功
     * */
    public static final String TOPIC_REWARD_SUCCESS = "topic_reward_success";
    public static final String GROUP_REWARD_SUCCESS = "group_reward_success";

    /**
     * 礼物赠送成功通知
     * */
    public static final String TOPIC_GIVE_GIFT_SUCCESS_NOTICE = "topic_give_gift_success_notice";
    public static final String GROUP_GIVE_GIFT_SUCCESS_NOTICE = "group_give_gift_success_notice";

    /**
     * 打赏成功通知
     * */
    public static final String TOPIC_REWARD_SUCCESS_NOTICE = "topic_reward_success_notice";
    public static final String GROUP_REWARD_SUCCESS_NOTICE = "group_reward_success_notice";

    /**
     * 指定单下单后通知
     * */
    public static final String TOPIC_APPOINT_ORDER_SUCCESS_NOTICE = "topic_appoint_order_success_notice";
    public static final String GROUP_APPOINT_ORDER_SUCCESS_NOTICE = "group_appoint_order_success_notice";

    /**
     * 随机单下单后通知
     * */
    public static final String TOPIC_RANDOM_ORDER_SUCCESS_NOTICE = "topic_random_order_success_notice";
    public static final String GROUP_RANDOM_ORDER_SUCCESS_NOTICE = "group_random_order_success_notice";

    /**
     * 订单接单通知
     * */
    public static final String TOPIC_ORDER_TAKING_NOTICE = "topic_order_taking_notice";
    public static final String GROUP_ORDER_TAKING_NOTICE = "group_order_taking_notice";

    /**
     * 订单取消通知
     * */
    public static final String TOPIC_ORDER_CANCEL_NOTICE = "topic_order_cancel_notice";
    public static final String GROUP_ORDER_CANCEL_NOTICE = "group_order_cancel_notice";

    /**
     * 订单完成通知
     * */
    public static final String TOPIC_ORDER_FINISH_NOTICE = "topic_order_finish_notice";
    public static final String GROUP_ORDER_FINISH_NOTICE = "group_order_finish_notice";

    /**
     * 服务开始通知
     * */
    public static final String TOPIC_ORDER_START_NOTICE = "topic_order_start_notice";
    public static final String GROUP_ORDER_START_NOTICE = "group_order_start_notice";

    /**
     * 随机单和指定单下单后N分钟后未接单自动退单
     * */
    public static final String TOPIC_ORDER_AUTO_CANCEL = "topic_order_auto_cancel";
    public static final String GROUP_ORDER_AUTO_CANCEL = "group_order_auto_cancel";

    /**
     * 随机单和指定单自动完成
     * */
    public static final String TOPIC_ORDER_AUTO_SUCCESS = "topic_order_auto_success";
    public static final String GROUP_ORDER_AUTO_SUCCESS = "group_order_auto_success";

    /**
     * 订单默认好评
     * */
    public static final String TOPIC_ORDER_AUTO_COMMENT = "topic_order_auto_comment";
    public static final String GROUP_ORDER_AUTO_COMMENT = "group_order_auto_comment";

    /**
     * 订单佣金结算
     * */
    public static final String TOPIC_ORDER_COMMISSION_SETTLEMENT = "topic_order_commission_settlement";
    public static final String GROUP_ORDER_COMMISSION_SETTLEMENT = "group_order_commission_settlement";

    /**
     * 分销关系绑定成功通知
     * */
    public static final String TOPIC_DISTRIBUTION_RELATION_BIND_NOTICE = "topic_distribution_relation_bind_notice";
    public static final String GROUP_DISTRIBUTION_RELATION_BIND_NOTICE = "group_distribution_relation_bind_notice";
}
