package com.ruoyi.system.extend.data;

import lombok.Data;

@Data
public class GetChatRoomJoinTaskDetailStatisticsInput  extends UtTouchQueryPage {

    private String taskId;

    private String vcChatRoomSerialNo;

    private String userCode;

}
