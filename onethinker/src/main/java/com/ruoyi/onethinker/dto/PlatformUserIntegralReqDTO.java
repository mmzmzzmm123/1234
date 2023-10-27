package com.ruoyi.onethinker.dto;

import lombok.Data;

/**
 * @author : yangyouqi
 * @date : 2023/10/27 0027 19:03
 */
@Data
public class PlatformUserIntegralReqDTO {
    /**
     * 用户手机号
     */
    private String phone;

    /**
     * 添加积分值
     */
    private Integer integral;

    /**
     * 批次号
     */
    private String batchNo;
}
