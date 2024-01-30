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

    private String orderId;

    private String biLinMerchantId = "";

    private String biLinGroupId = "";

    /**
     * 任务类型：0常规任务、1[比邻指定号任务->内部灰度任务]
     */
    private Integer taskType = 0;

    /**
     * 是否仅分配优先拉手国 0：否 1：是
     */
    private Integer isOnlyPriorityCountry;

    /**
     * 选择群类型：1普通群组、2超级群组
     */
    private Integer selectChatroomType;

    /**
     * 最后拉人时间超过多少分钟
     */
    private Integer nLastInviteTimeOverMinute;

    /**
     * 优先使用超过多少次的拉手号
     */
    private Integer nPriorityUsedCount;

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
        private Integer nChatRoomRobotCount;
    }
}
