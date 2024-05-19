package com.onethinker.domain;

import com.onethinker.common.annotation.Excel;
import com.onethinker.common.core.domain.BaseEntity;
import lombok.Data;

/**
 * 平台用户积分对象 t_platform_user_integral
 *
 * @author yangyouqi
 * @date 2023-10-27
 */
@Data
public class PlatformUserIntegral extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 表主键
     */
    private Long id;

    /**
     * 是否可见，0为不可见，1为可见
     */
    @Excel(name = "是否可见，0为不可见，1为可见")
    private Integer enabled;

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
     * 原剩余积分
     */
    private Long orgResidualIntegral;


}
