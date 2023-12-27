package com.ruoyi.system.extend.data;

import lombok.Data;

import java.util.List;

@Data
public class SaveChatRoomJoinTaskDTO {
    
    private String userCode;

    private String name;

    private Integer importType;

    private List<String> contentList;

    private List<String> chatRoomUrls;

    private Integer nIsCountryCode;

    private String countryCodes;

    private Integer needCount;

    private Integer inviteSpanMin;

    private Integer inviteSpanMax;

    private Integer nIsOpenChatRoomMemberLimitCount;

    private Integer chatroomUrlSource;

    private Integer autoCreateChatroomCount;

    private List<String> targetOwnerUserNames;

    private String extendKey;

    private TaskRuleData taskRuleData;

    private Integer nIsRobotExit;


    @Data
    public static class TaskRuleData {
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
}
