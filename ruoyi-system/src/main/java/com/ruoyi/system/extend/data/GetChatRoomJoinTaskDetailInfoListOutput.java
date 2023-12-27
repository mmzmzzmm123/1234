// GetChatRoomJoinTaskDetailInfoListOutput.java

package com.ruoyi.system.extend.data;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class GetChatRoomJoinTaskDetailInfoListOutput {
    private String taskId;
    private String chatRoomSerialNo;
    private String phone;
    private String userName;
    private String objName;
    private Integer syncFriendType;
    private String objCountry;
    private Integer runStatus;
    private String chatRoomJoinResultMsg;
    private String robotId;
    private String tgCountry;
    private LocalDateTime chatRoomJoinTime;
    private LocalDateTime chatRoomJoinCallbackTime;
    private LocalDateTime searchFriendTime;
    private LocalDateTime searchFriendCallBackTime;
    private List<Detail> details;
    private Integer count;

    @Data
    public static class Detail {
        private Integer nTaskDetailID;
        private String vcUserCode;
        private Integer nTaskID;
        private String vcPhone;
        private String vcUserName;
        private String vcRobotID;
        private Integer nSyncFriendType;
        private String vcSyncFriendSerialNo;
        private Integer nSyncFriendResultCode;
        private String vcSyncFriendResultMsg;
        private LocalDateTime dtSyncFriendTime;
        private LocalDateTime dtSyncFriendCallBackTime;
        private Integer nIsSyncFriend;
        private String vcFriendSerialNo;
        private String vcChatRoomURL;
        private String vcChatRoomSerialNo;
        private String vcChatRoomJoinSerialNo;
        private Integer nChatRoomJoinResultCode;
        private String vcChatRoomJoinResultMsg;
        private LocalDateTime dtChatRoomJoinTime;
        private LocalDateTime dtChatRoomJoinCallbackTime;
        private Integer nIsChatRoomJoin;
        private Integer nRunStatus;
        private String vcSearchFriendSerialNo;
        private Integer nSearchFriendResultCode;
        private String vcSearchFriendResultMsg;
        private LocalDateTime dtSearchFriendTime;
        private LocalDateTime dtSearchFriendCallBackTime;
        private LocalDateTime dtCreateTime;
        private LocalDateTime dtUpdateTime;
        private Integer nTaskObjectID;
    }
}
