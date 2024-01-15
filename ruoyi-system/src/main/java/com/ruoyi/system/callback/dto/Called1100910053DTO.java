package com.ruoyi.system.callback.dto;

import com.alibaba.fastjson2.annotation.JSONField;
import lombok.Data;

@Data
public class Called1100910053DTO {

    /**
     * 新的群编号
     */
    @JSONField(name = "new_chatroom_serial_no")
    private String newChatroomSerialNo;

}
