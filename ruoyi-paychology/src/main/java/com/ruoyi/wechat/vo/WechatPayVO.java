package com.ruoyi.wechat.vo;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class WechatPayVO implements Serializable {
    private Integer userId;
    private Integer courseId;

    private Integer gaugeId;

    private BigDecimal amount;

    // 咨询服务
    private Long serveId;
    private Long workId;
    private Long orderId;

    private String module;

    private String outTradeNo;
}
