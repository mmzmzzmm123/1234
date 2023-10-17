package com.ruoyi.common.constant;

/**
 * @author LAM
 * @date 2023/10/10 16:51
 */
public class WeChatServiceNotifyTemplateIdConstants {

    /**
     * 用户-赠礼成功通知
     *
     * 收礼人
     * {{thing2.DATA}}
     *
     * 礼物名称
     * {{thing1.DATA}}
     *
     * 礼物数量
     * {{number3.DATA}}
     *
     * 备注
     * {{thing5.DATA}}
     * */
    public static final String USER_GIVE_GIFT_SUCCESS = "KnNPGUWE5AoTiZ0b4AUyY0muylaIK3dpUdA5rrW2LXI";


    /**
     * 用户-下单成功通知
     *
     * 订单号
     * {{number2.DATA}}
     *
     * 支付金额
     * {{amount1.DATA}}
     *
     * 温馨提醒
     * {{thing4.DATA}}
     * */
    public static final String USER_ORDER_SUCCESS = "XQkRpWhVAxE99X6XkE7EajVVIleRvnNA_7wEYOMRx7Q";


    /**
     * 订单已接单提醒
     *
     * 订单号
     * {{number1.DATA}}
     *
     * 接单人员
     * {{thing2.DATA}}
     *
     * 温馨提醒
     * {{thing4.DATA}}
     * */
    public static final String USER_RECEIVED_ORDER = "f--gE3LkP87xwyzA9py0SaWf3UcNTGd7m0htGbbBQfE";


    /**
     * 店员-新订单通知
     *
     * 订单号
     * {{number2.DATA}}
     *
     * 支付金额
     * {{amount1.DATA}}
     *
     * 温馨提醒
     * {{thing4.DATA}}
     * */
    public static final String STAFF_NEW_ORDER = "KnNPGUWE5AoTiZ0b4AUyY0muylaIK3dpUdA5rrW2LXI";


}
