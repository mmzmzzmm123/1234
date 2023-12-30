package com.ruoyi.system.extend.data;

import lombok.Data;

@Data
public class UpdateTaskRuleData {
    private String nTaskId;
    private Integer nDifferentEnterRoomIntervalMin;
    private Integer nDifferentEnterRoomIntervalMax;
    private Integer nDifferentRobotIntervalMin;
    private Integer nDifferentRobotIntervalMax;
    private Integer nEnterRoomInviteIntervalMin;
    private Integer nEnterRoomInviteIntervalMax;
    private Integer nContactsEnterRoomIntervalMin;
    private Integer nContactsEnterRoomIntervalMax;
    private Integer nAllowCountryCount;
}
