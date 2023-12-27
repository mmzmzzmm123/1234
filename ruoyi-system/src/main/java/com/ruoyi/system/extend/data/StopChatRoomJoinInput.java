package com.ruoyi.system.extend.data;

import lombok.Data;

@Data
public class StopChatRoomJoinInput {

    private String taskId;

    private String id;

    private Integer type;

    private String userCode;
}
