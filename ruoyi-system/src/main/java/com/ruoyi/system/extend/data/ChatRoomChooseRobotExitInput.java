package com.ruoyi.system.extend.data;

import lombok.Data;

import java.util.List;

@Data
public class ChatRoomChooseRobotExitInput {

    private long taskChatRoomId;

    private List<String> robotList;

    private long taskId;

}
