package com.ruoyi.system.domain.dto;

import lombok.Data;

/**
 * @Author : XGF（徐桂烽）
 * @create 2023/12/25/025 17:36
 * @Description :
 */
@Data
public class AmountRefundDTO {
    // 退款商家
    private String merchantId;

    // 退款金额
    private Long amount;

    // 描述
    private String describe;

    // 关联订单
    private String orderId;

}
