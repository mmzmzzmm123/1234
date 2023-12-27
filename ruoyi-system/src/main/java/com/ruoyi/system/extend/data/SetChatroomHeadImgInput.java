package com.ruoyi.system.extend.data;

import java.util.List;

@lombok.Data
public class SetChatroomHeadImgInput {
    private String taskId;
    private List<String> chatroomSerialNos;
    private List<String> headImgUrls;
    private String userCode;
}
