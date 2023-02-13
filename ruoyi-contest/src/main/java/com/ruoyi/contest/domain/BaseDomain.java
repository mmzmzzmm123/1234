package com.ruoyi.contest.domain;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author lsyonlygoddes
 * @time 2023/02/13 22:27
 */
@Data
public class BaseDomain {
    /**
     * 备注
     */
    private String remark;

    /**
     * 删除标志（0代表存在 2代表删除）
     */
    private String delFlag;

    /**
     * 创建者
     */
    private String createBy;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 更新者
     */
    private String updateBy;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;
}
