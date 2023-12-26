package com.ruoyi.system.domain.dto;

import lombok.Data;

/**
 * @Author : XGF（徐桂烽）
 * @create 2023/12/25/025 17:35
 * @Description :
 */
@Data
public class AmountConsumptionDTO {

    private String frozenId;

    // 实际扣款金额  可以不传，按冻结金额来扣
    private Long amount;


}
