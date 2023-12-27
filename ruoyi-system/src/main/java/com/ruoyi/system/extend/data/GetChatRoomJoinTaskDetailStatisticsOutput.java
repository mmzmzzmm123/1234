package com.ruoyi.system.extend.data;

import lombok.Data;

@Data
public class GetChatRoomJoinTaskDetailStatisticsOutput {

    private Integer successCount;
    private Integer failCount;
    private Integer notOpenCount;
    private Integer setPriviteCount;
    private Integer notUseCount;
    private Integer twoWayNumber;
    private Integer twoWayCount;
    private Integer blockCount;
    private Integer joinCount;
    private Integer executeCount;
}
