package com.ruoyi.system.domain.dto;

import lombok.Data;

/**
 * @Author : XGF（徐桂烽）
 * @create 2023/12/25/025 16:11
 * @Description :
 */
@Data
public class CreateUserMerchantRefDTO {

    private Long userId;

    private String userName;

    private String merchantId;

    private String plMerchantId;

    /**
     * 默认普通商家
     */
    private Integer merchantType = 0;


}
