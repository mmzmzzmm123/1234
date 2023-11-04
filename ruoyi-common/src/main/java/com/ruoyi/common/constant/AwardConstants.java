package com.ruoyi.common.constant;

/**
 * @author : yangyouqi
 * @date : 2023/11/4 0004 11:20
 * 奖品常量配置
 */
public class AwardConstants {
    /**
     * 是否生成幸运奖 0：不设置 1：设置
     */
    public static final Integer LUCKY_AWARD_TYPE_NO = 0;
    /**
     * 是否生成幸运奖 0：不设置 1：设置
     */
    public static final Integer LUCKY_AWARD_TYPE_YES = 1;
    /**
     * 生成类型（1：固定生成、2:随机生成）
     */
    public static final Integer TYPE_FIXED = 1;
    /**
     * 生成类型（1：固定生成、2:随机生成）
     */
    public static final Integer TYPE_RANDOM = 2;
    /**
     * 创建二维码状态 0：未创建 1：创建中 2：已创建 3：创建失败
     */
    public static final Integer CREATE_QR_CODE_STATUS_INIT = 0;
    /**
     * 创建二维码状态 0：未创建 1：创建中 2：已创建 3：创建失败
     */
    public static final Integer CREATE_QR_CODE_STATUS_DOING = 1;
    /**
     * 创建二维码状态 0：未创建 1：创建中 2：已创建 3：创建失败
     */
    public static final Integer CREATE_QR_CODE_STATUS_SUCCESS = 2;
    /**
     * 创建二维码状态 0：未创建 1：创建中 2：已创建 3：创建失败
     */
    public static final Integer CREATE_QR_CODE_STATUS_FAIL = 3;


    /**
     * 当前批次创建明细状态 0：未生成明细数据 1：生成明细数据中 2：已生成明细数据
     */
    public static final Integer CREATE_DTL_STATUS_INIT = 0;
    /**
     * 当前批次创建明细状态 0：未生成明细数据 1：生成明细数据中 2：已生成明细数据
     */
    public static final Integer CREATE_DTL_STATUS_DOING = 1;
    /**
     * 当前批次创建明细状态 0：未生成明细数据 1：生成明细数据中 2：已生成明细数据
     */
    public static final Integer CREATE_DTL_STATUS_SUCCESS = 2;

    /**
     *创建二维码内容分隔符
     */
    public static final String  CREATE_QR_CODE_CONTENT_DELIMITER = "##";


    /**
     * 红包每批生成数
     */
    public static final Integer AWARD_BATCH_CREATE_SUM = 200;
}
