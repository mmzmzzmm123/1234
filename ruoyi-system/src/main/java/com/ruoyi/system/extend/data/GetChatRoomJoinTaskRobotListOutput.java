package com.ruoyi.system.extend.data;

import java.time.LocalDateTime;
import java.util.List;

@lombok.Data
public class GetChatRoomJoinTaskRobotListOutput {
    private String robotId;
    private String tgCountry;
    private Integer robotLogicStatus;
    private Integer status;
    private Integer onlineStatus;
    private Integer bothwayStatus;
    private Integer prohibitStatus;
    private Integer isInChatRoom;
    private Integer success;
    private Integer fail;
    private Integer notOpen;
    private Integer setPriviteCount;
    private String exitReason;
    private String resultMsg;
    private Integer phoneNotUse;
    private Integer userNameNotUse;
    private List<String> notUserReason;
    private Integer syncFriendSuccess;
    private Integer syncFriendFail;
    private LocalDateTime joinChatRoomTime;
    private LocalDateTime exitChatRoomTime;
    private String exitResultMsg;
    private String ip;
}
