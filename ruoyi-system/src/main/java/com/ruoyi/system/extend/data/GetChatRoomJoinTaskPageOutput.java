package com.ruoyi.system.extend.data;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author Jing.Zhang
 */
@Data
public class GetChatRoomJoinTaskPageOutput {

    private String taskId;

    private String name;

    private Integer executeStatus;

    private List<TaskObjectDetail> taskObjectDetails;

    private Integer completeCount;
    private Integer totalCount;
    private Integer joiningCount;
    private Integer executeCount;
    private Integer joinSuccessCount;
    private Integer joinFailCount;
    private Integer cancelCount;
    private Integer notOpenCount;
    private LocalDateTime executeTime;
    private Integer robotCount;
    private Integer robotOnlineCount;
    private Integer tgCount;
    private String createrId;
    private String creater;
    private LocalDateTime createTime;
    private boolean robotError;
    private Integer executeMode;
    private String showMessage;
    private String contactPackageTaskId;
    private String exception;
    private Integer nGetRobotStatus;
    private Integer nGetObjectStatus;
    private Integer setPriviteCount;
    private Integer nLevelId;
    private String levelJson;
    private Integer nIsCountryCode;

    private String countryCodes;

    private List<CountryCodeList> countryCodeList;

    private Integer nIsOpenChatRoomMemberLimitCount;

    private Integer chatroomUrlSource;

    private LocalDateTime endTime;

    @Data
    public static class TaskObjectDetail {
        private String contactPackageName;
        private Integer contactPackageObjectCount;
        private Integer contactObjectSuccessCount;
    }


    @Data
    public static class CountryCodeList {
        private String key;
        private String value;
    }
}