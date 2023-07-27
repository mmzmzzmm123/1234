package com.ruoyi.psychology.constant;

public class ConsultConstant {
    public static final String MODULE_CONSULT= "consult";

    /** 订单状态0-待付款 1-进行中 2-已完成 3-已取消 */
    public static final String CONSULT_ORDER_STATUE_CREATED = "0";
    public static final String CONSULT_ORDER_STATUE_PENDING = "1";
    public static final String CONSULT_ORDER_STATUE_FINISHED = "2";
    public static final String CONSULT_ORDER_STATUE_CANCELED = "3";

    // 1,未支付 2,支付成功 3,退款中 4,部分退 5,全单退 6,退款失败
    public static final String PAY_STATUE_PENDING = "1";
    public static final String PAY_STATUE_PAID = "2";
    public static final String PAY_STATUE_REFUNDING = "3";
    public static final String PAY_STATUE_PART_REFUNDING= "4";
    public static final String PAY_STATUE_REFUN= "5";
    public static final String PAY_STATUE_REFUN_FAIL= "6";

    public static final String ONSULT_ORDER_ITEM_CREATED = "0";
    public static final String ONSULT_ORDER_ITEM_FINISHED = "1";

    // 咨询形式 语音咨询、视频咨询、面对面咨询
    public static final Integer CONSULT_MODE_SOUND= 1;
    public static final Integer CONSULT_MODE_VOICE= 2;
    public static final Integer CONSULT_MODE_FACE= 3;

    // 服务类型 单次咨询 套餐咨询
    public static final Integer CONSULT_TYPE_ONCE = 1;
    public static final Integer CONSULT_TYPE_PACKAGE = 2;

    // 限购 0-不限制 1-限制
    public static final Integer CONSULT_NO_LIMIT = 0;
    public static final Integer CONSULT_LIMIT = 1;

}
