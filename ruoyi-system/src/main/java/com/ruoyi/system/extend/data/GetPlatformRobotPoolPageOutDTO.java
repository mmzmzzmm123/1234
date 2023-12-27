package com.ruoyi.system.extend.data;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class GetPlatformRobotPoolPageOutDTO {

    private String robotId;
    private String name;
    private String surname;
    private String headImgUrl;
    private String robotUserName;
    private String phone;
    private Integer status;
    private Integer onlineStatus;
    private LocalDateTime lastLogIntegerime;
    private String lastLoginSerialNo;
    private LocalDateTime lastOfflineTime;
    private String lastOfflineSerialNo;
    private LocalDateTime lastSyncTime;
    private String lastSyncSerialNo;
    private LocalDateTime lastBlackTime;
    private String lastBlackTimeSerialNo;
    private LocalDateTime dtCreateTime;
    private Integer robotLogicStatus;
    private Integer bothwayStatus;
    private String country;
    private String countryCode;
    private Integer prohibitStatus;
    private LocalDateTime lastBothwayTime;
    private LocalDateTime lastProhibitStatusTime;
    private Integer lock;
    private String lockReason;
    private Integer asyncContactSuccessCount;
    private Integer asyncContactFailCount;
    private Integer asyncContactTodaySuccessCount;
    private Integer asyncContactTodayFailCount;
    private Integer searchUserNameSuccessCount;
    private Integer searchUserNameFailCount;
    private Integer searchUserNameTodaySuccessCount;
    private Integer searchUserNameTodayFailCount;
    private Integer privateMsgSuccessCount;
    private Integer privateMsgFailCount;
    private Integer privateMsgTodaySuccessCount;
    private Integer privateMsgTodayFailCount;
    private Integer blockTodayCount;
    private Integer blockCount;
    private Integer inviteJoinRoomSuccessCount;
    private Integer inviteJoinRoomFailCount;
    private Integer inviteJoinRoomTodaySuccessCount;
    private Integer inviteJoinRoomTodayFailCount;
    private Boolean isSearchAddPostBotRobot;
    private Integer setPhoneVisibilityStatus;
    private LocalDateTime setPhoneVisibilityTime;
    private String setPhoneVisibilityRemark;
    private LocalDateTime bothwayUnfreezeTime;
    private LocalDateTime bjBothwayUnfreezeTime;
    private String briefIntegerro;
    private List<RobotUserTaskRanks> todayRobotUserTaskRanks;
    private List<RobotUserTaskRanks> totalRobotUserTaskRanks;
    private String allModelName;
    private List<ForbidReasonList> forbidReasonList;
    private String registerCountry;
    private String loginCountry;
    private String groupName;
    private Integer isSuperMerchantNo;


    @Data
    public static class RobotUserTaskRanks {
        private String robotUserName;
        private String taskId;
        private String taskName;
        private LocalDateTime time;
        private String robotId;
    }

    @Data
    public static class ForbidReasonList {
        private String modelName;
        private String modelDesc;
        private int forbidSeconds;
        private String keyWords;
        private LocalDateTime lastTime;
    }
}
