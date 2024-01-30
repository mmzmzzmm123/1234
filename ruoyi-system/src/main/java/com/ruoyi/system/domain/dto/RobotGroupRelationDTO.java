package com.ruoyi.system.domain.dto;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author Sharly
 */
@Data
public class RobotGroupRelationDTO {

    private String groupId;

    private String robotId;

    private String playId;

    private String merchantId;

    private Integer state;

    private LocalDateTime createTime;

    private LocalDateTime endTime;

}
