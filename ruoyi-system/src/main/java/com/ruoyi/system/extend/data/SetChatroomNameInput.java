package com.ruoyi.system.extend.data;

import lombok.Data;

import java.util.List;

@Data
public class SetChatroomNameInput {
    private String taskId;

    private List<String> chatroomSerialNos;

    private String name;

    private Integer chatroomNumberMin;

    private String userCode;

}
