package com.onethinker.user.dto;

import lombok.Data;

/**
 * @author : yangyouqi
 * @date : 2023/10/27 0027 19:03
 * 用户积分DTO
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
    private Long integral;

    /**
     * 批次号
     */
    private String batchNo;

    /**
     * 备注
     */
    private String remark;

    /**
     * userId
     */
    private Long puUserId;
}
