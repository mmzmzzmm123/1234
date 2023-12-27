// GetChatRoomJoinTaskDetailOutput.java

package com.ruoyi.system.extend.data;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class GetChatRoomJoinTaskDetailOutput {
    private Integer contactPackageObjectCount;
    private Integer contactObjectSuccessCount;
    private Integer count;
    private Integer failCount;
    private Integer notOpen;
    private Integer setPriviteCount;
    private Integer chatRoomCount;
    private Integer chatRoomCompleteCount;
    private Integer urlCount;
    private Integer executingCount;
    private Integer joinCount;
    private Integer disableCount;
    private Integer notRunCount;
    private List<ChatRoomJoinList> chatRoomJoinAllList;
    private List<ChatRoomJoinList> chatRoomJoinCompleteList;
    private List<ChatRoomJoinList> chatRoomJoinInProgressList;
    private List<ChatRoomJoinList> chatRoomJoinSuspendList;
    private List<ChatRoomJoinList> chatRoomJoinCancelList;
    private List<ChatRoomJoinList> chatRoomJoinExecutList;

    @Data
    public static class ChatRoomJoinList {
        private String id;
        private String name;
        private String url;
        private Integer status;
        private String vcChatRoomSerialNo;
        private Integer joinSuccessCount;
        private Integer startMemberCount;
        private Integer memberCount;
        private Integer needMemberCount;
        private Integer relationshipCount;
        private String percent;
        private LocalDateTime createTime;
        private Integer chatRoomType;
        private Integer getRobotStatus;
        private String taskCancelReason;
        private Integer chatRoomNo;
        private String targetOwnerUserName;
        private Integer targetOwnerStatus;
        private String headImgURL;
    }
}

