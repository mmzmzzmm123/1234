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

    private String module;

    private String outTradeNo;
}
