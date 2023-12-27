package com.ruoyi.system.extend.data;

import java.time.LocalDateTime;

@lombok.Data
public class GetChatRoomJoinTaskPageInput extends UtTouchQueryPage {

    private String id;

    private String name;

    private Integer executeStatus;

    private LocalDateTime createTimeStart;

    private LocalDateTime createTimeEnd;

    private Integer isCache;

    private String userCode;

    private String extendKey;
}
