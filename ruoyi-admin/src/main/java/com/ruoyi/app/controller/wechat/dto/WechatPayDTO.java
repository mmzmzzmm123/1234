package com.ruoyi.app.controller.wechat.dto;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class WechatPayDTO implements Serializable {
    private Integer userId;
    private Integer courseId;

    private Integer gaugeId;

    // 咨询服务
    private Long serveId;
    private Long workId;
    private Long orderId;

    private BigDecimal amount;

    private String module;
}
