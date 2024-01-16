package com.onethinker.user.domain;

import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;
import lombok.Data;

/**
 * 平台用户积分流水记录对象 t_platform_user_integral_history
 *
 * @author yangyouqi
 * @date 2023-10-27
 */
@Data
public class PlatformUserIntegralHistory extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 表主键
     */
    private Long id;

    /**
     * 是否可见，0为审核中，1为已处理 2：审核不通过
     */
    @Excel(name = "是否可见，0为审核中，1为已处理 2：审核不通过")
    private Integer enabled;

    /**
     * 积分
     */
    @Excel(name = "积分")
    private Long integral;

    /**
     * 1:充值 2:活动获得奖励 3：参与活动扣除门槛 4：提现
     */
    @Excel(name = "1:充值 2:活动获得奖励 3：参与活动扣除门槛 4：提现")
    private Integer type;

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
     * 审批操作人员
     */
    @Excel(name = "审批操作人员")
    private Long sysUserId;


}
