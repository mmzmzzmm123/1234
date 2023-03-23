package com.ruoyi.wechat.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class PayVO implements Serializable {
    /** 应用ID */
    private String appId;
    /** 时间戳 */
    private String timeStamp;
    /** 随机字符串 */
    private String nonceStr;
    /** 订单详情扩展字符串 */
    private String packageInfo;
    /** 签名方式 */
    private String signType;
    /** 签名 */
    private String paySign;
    /** 应用ID */
    private String orderId;
}
