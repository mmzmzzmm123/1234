package com.ruoyi.onethinker.dto;

import com.ruoyi.common.annotation.Excel;
import lombok.Data;

/**
 * @Title: PlatformUserIntegralResDTO
 * @Author itmei
 * @Package com.ruoyi.onethinker.dto
 * @Date 2023/10/28 22:46
 * @description: 用户积分返回对象
 */
@Data
public class PlatformUserIntegralResDTO {
    /**
     * 表主键
     */
    private Long id;

    /**
     * 总积分
     */
    @Excel(name = "总积分")
    private Long totalIntegral;

    /**
     * 剩余积分
     */
    @Excel(name = "剩余积分")
    private Long residualIntegral;

    /**
     * 平台用户
     */
    @Excel(name = "平台用户")
    private Long puUserId;

    /**
     * 活动批次号
     */
    @Excel(name = "活动批次号")
    private String batchNo;

    /**
     * 活动名称
     */
    private String activityName;

    /**
     * 平台用户手机号
     */
    private String phone;

}
