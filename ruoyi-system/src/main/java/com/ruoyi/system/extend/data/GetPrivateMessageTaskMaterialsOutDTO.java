package com.ruoyi.system.extend.data;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * @Author : XGF（徐桂烽）
 * @create 2023/12/19/019 15:23
 * @Description :
 */
@Data
public class GetPrivateMessageTaskMaterialsOutDTO {

    private String msgContent;
    private String msgTitle;
    private Integer msgType;
    private Integer dayGainCount;
    private LocalDateTime lastGainTime;
    private String materialContentId;
    private String postBotMsgJson;
    private Integer status;
    private Integer materialFailCount;

//    {
//        "msgContent": "string",
//            "msgTitle": "string",
//            "msgType": 0,
//            "dayGainCount": 0,
//            "lastGainTime": "2023-12-19T07:22:25.659Z",
//            "materialContentId": 0,
//            "postBotMsgJson": "string",
//            "status": 0,
//            "materialFailCount": 0
//    }

}
