package com.ruoyi.common.constant;

public class IntegralRecordConstants {

    /** 积分记录类型—增加 */
    public static final Integer INTEGRAL_RECORD_TYPE_ADD = 1;

    /** 积分记录类型—扣减 */
    public static final Integer INTEGRAL_RECORD_TYPE_SUB = 2;

    /** 积分记录状态—创建 */
    public static final Integer INTEGRAL_RECORD_STATUS_CREATE = 1;

    /** 积分记录状态—冻结期 */
    public static final Integer INTEGRAL_RECORD_STATUS_FROZEN = 2;

    /** 积分记录状态—完成 */
    public static final Integer INTEGRAL_RECORD_STATUS_COMPLETE = 3;

    /** 积分记录状态—失效（订单退款） */
    public static final Integer INTEGRAL_RECORD_STATUS_INVALIDATION = 4;

    /** 积分记录关联类型—注册 */
    public static final Integer INTEGRAL_RECORD_LINK_TYPE_REGISTER = 1;

    /** 积分记录关联类型—咨询订单 */
    public static final Integer INTEGRAL_RECORD_LINK_TYPE_ORDER = 2;

    /** 积分记录关联类型—课程订单 */
    public static final Integer INTEGRAL_RECORD_LINK_TYPE_COURSE = 3;

    /** 积分记录关联类型—测评订单 */
    public static final Integer INTEGRAL_RECORD_LINK_TYPE_GAUGE = 4;

    /** 积分记录关联类型—系统后台 */
    public static final Integer INTEGRAL_RECORD_LINK_TYPE_SYSTEM = 0;

    /** 积分记录标题—用户订单付款成功 */
    public static final String BROKERAGE_RECORD_TITLE_ORDER = "用户订单付款成功";

    /** 积分记录标题—注册经验奖励 */
    public static final String BROKERAGE_RECORD_TITLE_REGISTER = "注册积分奖励";

    /** 积分记录标题—后台积分操作 */
    public static final String BROKERAGE_RECORD_TITLE_SYSTEM = "后台积分操作";

    /** 积分记录标题—订单退款 */
    public static final String BROKERAGE_RECORD_TITLE_REFUND = "订单退款";
}
