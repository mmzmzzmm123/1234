package com.ruoyi.psychology.constant;

public class ConsultConstant {
    public static final String MODULE_CONSULT= "consult";

    /** 订单状态0-待付款 1-待预约 2-待咨询 3-已完成 4-已取消 */
    public static final int CONSULT_ORDER_STATUE_CREATED = 0;
    public static final int CONSULT_ORDER_STATUE_PENDING = 1;
    public static final int CONSULT_ORDER_STATUE_UNCONSULT = 2;
    public static final int CONSULT_ORDER_STATUE_FINISHED = 3;
    public static final int CONSULT_ORDER_STATUE_CANCELED = 4;

    public static final int PAY_STATUE_PENDING = 1;
    public static final int PAY_STATUE_PAID = 2;
    public static final int PAY_STATUE_CANCEL = 3;
    public static final Integer CONSULT_NOT_BUY= 0;

    public static final Integer CONSULT_IS_BUY= 1;
}
