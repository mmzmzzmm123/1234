package com.ruoyi.common.constant;

/**
 * @author LAM
 * @date 2023/10/10 16:51
 */
public class WxMpTemplateIdConstants {

    /**
     * 新订单通知
     *
     * 订单编号 {{character_string2.DATA}}
     * 服务名称 {{thing9.DATA}}
     * 订单金额 {{amount3.DATA}}
     * 下单时间 {{time5.DATA}}
     * */
    public static final String NEW_ORDER_TIPS_ID = "9A3pOdKrKcnXc9A2STbs0-YbK_jwesVOO4UJOwGHTRQ";

    /**
     * 订单支付成功通知
     *
     订单编号 {{number1.DATA}}
     服务类型 {{phrase2.DATA}}
     支付金额 {{amount3.DATA}}
     下单时间 {{time4.DATA}}
     * */
    public static final String PAY_ORDER_SUCCESS = "aeWqm57gKjtxeUywowD3h5tLOTaLH_Ih1SLxtU1felc";

    /**
     * 订单取消通知
     *
     * 订单编号 {{character_string1.DATA}}
     * 订单类型 {{short_thing2.DATA}}
     * 订单金额 {{amount3.DATA}}
     * 下单时间 {{time4.DATA}}
     * 取消原因 {{thing5.DATA}}
     * */
    public static final String ORDER_CANCEL = "OJfHlV6vHIc83R0REpwevshGfSnj3QY2KXlEOWCFwe0";

    /**
     * 接单成功通知
     *
     * 订单号 {{character_string1.DATA}}
     * 接单时间 {{time2.DATA}}
     * */
    public static final String ORDER_TAKING = "9lwcTRe5qUNaAgGUF-NGnzHDshqeKKjY_vDPS6vMaOQ";

    /**
     * 订单开始通知
     *
     * 订单号 {{character_string6.DATA}}
     * 开始时间 {{time4.DATA}}
     * */
    public static final String ORDER_START = "-iPn_8rcUegcXxFS6wNVBlppDn3rNNxAkwJ-_oJCST4";

    /**
     * 订单完成通知
     *
     * 订单编号 {{number1.DATA}}
     * 服务类型 {{phrase2.DATA}}
     * 完成时间 {{time5.DATA}}
     * */
    public static final String ORDER_FINISH = "tGxvl5NRyyLWX_rhCkS65qRq23EWhHc9jjjrbdbi8CI";
}
