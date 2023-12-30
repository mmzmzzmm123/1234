package com.ruoyi.system.extend.data;

import lombok.Data;

import java.util.List;

@Data
public class OperateChatRoomJoinTaskInput {

    private List<String> taskIdList;

    private String userCode;

}
