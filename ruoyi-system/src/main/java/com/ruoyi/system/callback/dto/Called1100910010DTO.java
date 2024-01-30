package com.ruoyi.system.callback.dto;

import com.alibaba.fastjson2.annotation.JSONField;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Called1100910010DTO {

    @JSONField(name = "msg_num")
    private Integer msgNum;

    @JSONField(name = "msg_id")
    private String msgId;

    @JSONField(name = "date_time")
    private LocalDateTime dateTime;
}
