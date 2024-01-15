package com.ruoyi.system.callback.dto;

import com.alibaba.fastjson2.annotation.JSONField;
import lombok.Data;

@Data
public class Called1100910039DTO {

    /**
     * 群名称
     */
    @JSONField(name = "chatroom_name")
    private String chatroomName;


    /**
     * 群编号
     */
    @JSONField(name = "chatroom_serial_no")
    private String chatroomSerialNo;

}
