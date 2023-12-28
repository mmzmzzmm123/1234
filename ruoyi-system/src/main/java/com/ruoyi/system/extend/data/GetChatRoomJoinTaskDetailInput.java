package com.ruoyi.system.extend.data;

import lombok.Data;

@Data
public class GetChatRoomJoinTaskDetailInput {

    private long taskId;

    private long isCache;

    private String userCode;

}
