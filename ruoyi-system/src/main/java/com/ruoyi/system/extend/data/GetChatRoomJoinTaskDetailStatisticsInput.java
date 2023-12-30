package com.ruoyi.system.extend.data;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class GetChatRoomJoinTaskDetailStatisticsInput  extends UtTouchQueryPage {

    private String taskId;

    /**
     * 已废弃 - ut-touch接口错误
     */
    @Deprecated
    @ApiModelProperty(hidden = true)
    private String vcChatRoomSerialNo;

    private String userCode;

    private String chatRoomUrl;

}
