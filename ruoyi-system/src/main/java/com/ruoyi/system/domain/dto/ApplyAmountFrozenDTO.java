package com.ruoyi.system.domain.dto;

import lombok.Data;

/**
 * @Author : XGF（徐桂烽）
 * @create 2023/12/25/025 16:58
 * @Description :
 */
@Data
public class ApplyAmountFrozenDTO {

    /**
     * 商家ID
     */
    private String merchantId;

    /**
     * 冻结金额
     */
    private Long frozenAmount;

    /**
     * 订单ID
     */
    private String orderId;

    /**
     * 描述
     */
    private String describe;

}
