package com.renxin.wechat.vo;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class PrepayVO implements Serializable {
    /** 用户编号，用于生成商户订单号out_trade_no */
    private String userId;

    /** 用户标识 */
    private String openid;

    /** 订单金额 */
    private BigDecimal amount;

    /** 商品描述 */
    private String description;

    /** 附加数据 */
    private String attach;

    /** 测评id */
    private Long gaugeId;

    /** 课程编号 */
    private String courseId;

    /** 模块名 */
    private String moduleName;
}
