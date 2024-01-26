package com.ruoyi.system.callback.dto;

import com.alibaba.fastjson2.annotation.JSONField;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 封群回调
 *
 * @author Sharly
 */
@Data
public class Called1100850217DTO {

    @JSONField(name = "chatroom_serial_no")
    private String chatroomSerialNo;

    @JSONField(name = "seal_time")
    private LocalDateTime sealTime;
}
