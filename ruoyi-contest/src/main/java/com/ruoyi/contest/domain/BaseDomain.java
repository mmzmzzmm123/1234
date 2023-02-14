package com.ruoyi.contest.domain;

import com.ruoyi.common.core.domain.BaseEntity;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author lsyonlygoddes
 * @time 2023/02/13 22:27
 */
@Data
public class BaseDomain extends BaseEntity {

    /**
     * 删除标志（0代表存在 1代表删除）
     */
    private String delFlag;
}
